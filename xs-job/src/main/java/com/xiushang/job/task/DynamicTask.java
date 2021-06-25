package com.xiushang.job.task;

import com.google.common.collect.Lists;
import com.xiushang.job.service.SubscribeMsgService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.CronTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronSequenceGenerator;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.util.CollectionUtils;

import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Configuration
@Slf4j
public class DynamicTask implements SchedulingConfigurer {

  private final SubscribeMsgService subscribeMsgService;

  private static final ExecutorService es = new ThreadPoolExecutor(10, 20,
      0L, TimeUnit.MILLISECONDS,
      new LinkedBlockingQueue<>(10),
      new DynamicTaskConsumeThreadFactory());


  private volatile ScheduledTaskRegistrar registrar;
  private final ConcurrentHashMap<String, ScheduledFuture<?>> scheduledFutures = new ConcurrentHashMap<>();
  private final ConcurrentHashMap<String, CronTask> cronTasks = new ConcurrentHashMap<>();

  private volatile List<TaskConstant> taskConstants = Lists.newArrayList();

  public DynamicTask(SubscribeMsgService msgService) {
    this.subscribeMsgService = msgService;
  }

  @Override
  public void configureTasks(ScheduledTaskRegistrar registrar) {
    this.registrar = registrar;
    this.registrar.addTriggerTask(() -> {
          if (!CollectionUtils.isEmpty(taskConstants)) {
            //log.info("检测动态定时任务列表...");
            List<TimingTask> tts = new ArrayList<>();
            taskConstants
                .forEach(taskConstant -> {
                  TimingTask tt = new TimingTask();
                  tt.setExpression(taskConstant.getCron());
                  tt.setTaskId(taskConstant.getTaskId());
                  tts.add(tt);
                });
            this.refreshTasks(tts);
          }
        }
        , triggerContext -> new PeriodicTrigger(5L, TimeUnit.SECONDS).nextExecutionTime(triggerContext));
  }


  public List<TaskConstant> getTaskConstants() {
    return taskConstants;
  }

  private void refreshTasks(List<TimingTask> tasks) {
    //取消已经删除的策略任务
    Set<String> taskIds = scheduledFutures.keySet();
    for (String taskId : taskIds) {
      if (!exists(tasks, taskId)) {
        scheduledFutures.get(taskId).cancel(false);
      }
    }
    for (TimingTask tt : tasks) {
      String expression = tt.getExpression();
      if (StringUtils.isBlank(expression) || !CronSequenceGenerator.isValidExpression(expression)) {
        log.error("定时任务DynamicTask cron表达式不合法: " + expression);
        continue;
      }
      //如果配置一致，则不需要重新创建定时任务
      if (scheduledFutures.containsKey(tt.getTaskId())
          && cronTasks.get(tt.getTaskId()).getExpression().equals(expression)) {
        continue;
      }
      //如果策略执行时间发生了变化，则取消当前策略的任务
      if (scheduledFutures.containsKey(tt.getTaskId())) {
        scheduledFutures.remove(tt.getTaskId()).cancel(false);
        cronTasks.remove(tt.getTaskId());
      }
      CronTask task = new CronTask(tt, expression);
      ScheduledFuture<?> future = registrar.getScheduler().schedule(task.getRunnable(), task.getTrigger());
      cronTasks.put(tt.getTaskId(), task);
      scheduledFutures.put(tt.getTaskId(), future);
    }
  }

  private boolean exists(List<TimingTask> tasks, String taskId) {
    for (TimingTask task : tasks) {
      if (task.getTaskId().equals(taskId)) {
        return true;
      }
    }
    return false;
  }

  @PreDestroy
  public void destroy() {
    this.registrar.destroy();
  }

  public static class TaskConstant {
    private String cron;
    private String taskId;

    public String getCron() {
      return cron;
    }

    public void setCron(String cron) {
      this.cron = cron;
    }

    public String getTaskId() {
      return taskId;
    }

    public void setTaskId(String taskId) {
      this.taskId = taskId;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (!(o instanceof TaskConstant)) {
        return false;
      }
      TaskConstant that = (TaskConstant) o;
      return taskId.equals(that.taskId);
    }

    @Override
    public int hashCode() {
      return Objects.hash(taskId);
    }
  }

  private class TimingTask implements Runnable {
    private String expression;

    private String taskId;

    public String getTaskId() {
      return taskId;
    }

    public void setTaskId(String taskId) {
      this.taskId = taskId;
    }

    @Override
    public void run() {
      //设置队列大小10
      log.info("当前CronTask: " + this);
      DynamicBlockingQueue queue = new DynamicBlockingQueue(3);
      es.submit(() -> {
        while (!queue.isDone() || !queue.isEmpty()) {
          try {
            String content = queue.poll(500, TimeUnit.MILLISECONDS);
            if (StringUtils.isBlank(content)) {
              return;
            }
            subscribeMsgService.sendSubscribeMsg(this.getTaskId());
            log.info("DynamicBlockingQueue 消费："+this.getTaskId());
            TimeUnit.MILLISECONDS.sleep(500);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      });

      //队列放入数据
      try {
        queue.put(this.getTaskId());
        log.info("DynamicBlockingQueue 生产：" + this.getTaskId());
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      queue.setDone(true);
    }

    public String getExpression() {
      return expression;
    }

    public void setExpression(String expression) {
      this.expression = expression;
    }

    @Override
    public String toString() {
      return ReflectionToStringBuilder.toString(this
          , ToStringStyle.JSON_STYLE
          , false
          , false
          , TimingTask.class);
    }

  }

  /**
   * 队列消费线程工厂类
   */
  private static class DynamicTaskConsumeThreadFactory implements ThreadFactory {
    private static final AtomicInteger poolNumber = new AtomicInteger(1);
    private final ThreadGroup group;
    private final AtomicInteger threadNumber = new AtomicInteger(1);
    private final String namePrefix;

    DynamicTaskConsumeThreadFactory() {
      SecurityManager s = System.getSecurityManager();
      group = (s != null) ? s.getThreadGroup() :
          Thread.currentThread().getThreadGroup();
      namePrefix = "pool-" +
          poolNumber.getAndIncrement() +
          "-dynamic-task-";
    }

    @Override
    public Thread newThread(Runnable r) {
      Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(),
          0);
      if (t.isDaemon()) {
        t.setDaemon(false);
      }
      if (t.getPriority() != Thread.NORM_PRIORITY) {
        t.setPriority(Thread.NORM_PRIORITY);
      }
      return t;
    }
  }

  private static class DynamicBlockingQueue extends LinkedBlockingQueue<String> {
    DynamicBlockingQueue(int capacity) {
      super(capacity);
    }


    private volatile boolean done = false;

    public boolean isDone() {
      return done;
    }

    public void setDone(boolean done) {
      this.done = done;
    }
  }
}
