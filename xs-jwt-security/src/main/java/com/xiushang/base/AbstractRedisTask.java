package com.xiushang.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.collections.DefaultRedisList;
import org.springframework.data.redis.support.collections.DefaultRedisMap;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.Assert;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @Description: Redis任务队列抽象类
 */
public abstract class AbstractRedisTask<T> implements InitializingBean {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());
	/*队列运行模式,默认为生产者 */
    protected Boolean consumer = false;
    /*任务名称*/
    protected String TASK_NAME = "redis-task-demo";
    /* 空闲时，休息时间*/
    protected static long SLEEP_TIME = 1000L;
    /*最大执行任务个数 阀值*/
    protected static int TASK_FULL_SIZE = 50;
    /*判定任务 执行失败的时间间隔 阀值*/
    protected static long INTERVAL_TIME = 3 * 60 * 1000L;
    /*redis 任务队列zset key*/
    protected String REDIS_TASK_LIST_KEY = "BAJIE_REDIS_TASK_LIST_USERID";
    /*redis 正在执行中的map key*/
    protected String REDIS_TASKING_SET_KEY = "BAJIE_REDIS_TASKING_SET_USERID";

    /*插入 redis任务队列失败 本地缓存队列*/
    private ConcurrentLinkedQueue<String> bakTasks = new ConcurrentLinkedQueue<String>();
    /*处理任务 队列*/
    private DefaultRedisList<String> taskQuene;
    /*处理中的任务  -- 处理完成remove，以确保任务不丢失，不重复*/
    private DefaultRedisMap<String, Object> taskIngMap;

    //队列实际业务类名称,内部类用$分割
    private String EXECUTE_CLASS_NAME;
    protected Boolean RETRY_EXCEPTION = false;
    protected Boolean RETRY_FAIL = false;

    @Autowired
    protected RedisTemplate redisTemplate;
    @Autowired
    protected ThreadPoolTaskExecutor taskExecutor;

    public AbstractRedisTask(String taskName, String executeClassName){
        Assert.notNull(taskName, "队列名称不能为空.");
        Assert.notNull(executeClassName, "业务执行类名称不能为空.");
        this.TASK_NAME = taskName;
        this.EXECUTE_CLASS_NAME = executeClassName;
        this.REDIS_TASK_LIST_KEY = "BAJIE_REDIS_TASK_LIST_" + taskName.toUpperCase();
        this.REDIS_TASKING_SET_KEY = "BAJIE_REDIS_TASKING_SET_" + taskName.toUpperCase();
    }

    /**
     * 添加待执行任务
     * @param taskId
     */
    public Boolean addTask(String taskId) {
        Assert.notNull(taskId,"任务ID不能为空");
        try {
            if (taskQuene.contains(taskId)) {
                log.info("[{} tasking add] task already in quene:{}", TASK_NAME, taskId);
                return false;
            }
            log.info("[{} tasking add] add task:{}",TASK_NAME, taskId);
            return taskQuene.add(taskId);
        } catch (RedisConnectionFailureException ex) {
            log.error("[{} tasking add] RedisConnectionFailureException task:{},error:{}.", TASK_NAME, taskId, ex);
            bakTasks.add(taskId);
        } catch (Exception e) {
            log.error("[{} tasking add] task:{},error:{}",TASK_NAME, taskId, e);
            bakTasks.add(taskId);
        }
        return true;
    }

   /**
    * 由BeanFactory设置所有提供的bean属性后调用
    *（以及满意的BeanFactoryAware和ApplicationContextAware）。
    * <p>此方法允许Bean实例仅执行初始化
    * 当所有bean属性都已设置并抛出一个
    * 在配置错误的情况下例外。
    * @throws在配置错误的情况下发生异常（例如
    * 表示无法设置基本属性）或初始化失败。
    */
    @Override
    public void afterPropertiesSet() throws Exception {
        this.taskQuene = new DefaultRedisList<String>(this.REDIS_TASK_LIST_KEY, redisTemplate);
        this.taskIngMap = new DefaultRedisMap<String, Object>(this.REDIS_TASKING_SET_KEY, redisTemplate);

        //判断是否为消费者
        if (consumer) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            if (!taskQuene.isEmpty()) {
                                /**如果 当前任务个数超过阀值，跳过*/
                                if (taskExecutor.getThreadPoolExecutor().getQueue().size() >= TASK_FULL_SIZE) {
                                    Thread.sleep(SLEEP_TIME);
                                    continue;
                                }
                                String taskId = taskQuene.poll();
                                log.info("[{} thread task] get start.taskId{}", TASK_NAME, taskId);
                                /*避免重复执行*/
                                if (taskIngMap.get(taskId) == null) {
                                    taskIngMap.put(taskId, taskId);
                                    taskExecutor.execute(new BaseTaskJobRunnable(taskId));
                                } else {
                                    log.error("[{} thread task] already running job.taskId:{}", TASK_NAME, taskId);
                                }
                                continue;
                            }
                        } catch (RedisConnectionFailureException ex) {
                            log.error("[{} thread task] async RedisConnectionFailureException error:{}.", TASK_NAME, ex);
                        } catch (Exception e) {
                            log.error("[{} thread task] async system error:{}.", TASK_NAME, e);
                        }
                        try {
                            if (Thread.interrupted()) {
                                log.error("[{} thread task] async breaked InterruptedException.", TASK_NAME);
                                break;
                            }
                            Thread.sleep(SLEEP_TIME);
                        } catch (InterruptedException ex) {
                            log.error("[{} thread task] async breaked InterruptedException:{}.", TASK_NAME, ex);
                            break;
                        }
                    }
                }
            }).start();
        }
        log.info("[{} thread task] start suc. is {}", TASK_NAME,consumer ? "Consumer":"Provider");
    }

    class BaseTaskJobRunnable implements Runnable {
        private Logger logger = LoggerFactory.getLogger(this.getClass());
        private String taskId;

        BaseTaskJobRunnable(String taskId) {
            this.taskId = taskId;
        }

        @Override
        public void run() {
            logger.info("[{} task] start.taskId:{}", TASK_NAME, taskId);
            long sTime = System.currentTimeMillis();
            try {
                //开始任务执行
                TaskExecuteComponent taskExecuteComponent = (TaskExecuteComponent)Class.forName(EXECUTE_CLASS_NAME).newInstance();
                taskExecuteComponent.setTask(taskId);
                taskExecuteComponent.execute();
                taskIngMap.remove(taskId);

                log.info("[{} task] end.context:{}.time:{}ms", TASK_NAME, taskId, System.currentTimeMillis() - sTime);
                return;
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                log.error("[{} task] system error,context:{},error:{}", TASK_NAME, e);
            } catch (Exception e){
                e.printStackTrace();
            }

            //异常是否重试
            if (RETRY_EXCEPTION) {
                taskQuene.add(taskId);
            }
            taskIngMap.remove(taskId);
        }
    }

    public Boolean getConsumer() {
        return consumer;
    }

    public void setConsumer(Boolean consumer) {
        this.consumer = consumer;
    }

    public RedisTemplate getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public ThreadPoolTaskExecutor getTaskExecutor() {
        return taskExecutor;
    }

    public void setTaskExecutor(ThreadPoolTaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }
}
