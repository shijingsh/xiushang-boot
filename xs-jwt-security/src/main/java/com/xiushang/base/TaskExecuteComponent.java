package com.xiushang.base;

/**
 * @Description: 任务执行接口
 */
public interface TaskExecuteComponent {

    /**
     * 添加任务到队列
     * @param task
     */
    void setTask(String task);

    /**
     * 实际业务执行,抛出异常为执行失败
     */
    void execute();
}
