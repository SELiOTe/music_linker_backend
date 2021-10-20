package com.seliote.mlb.task.service;

import org.quartz.*;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * Task 任务 Service
 *
 * @author seliote
 * @version 2021-07-01
 */
@Validated
public interface TaskService {

    /**
     * 创建 JobData
     *
     * @param map JobData 数据
     * @return JobData 对象
     */
    @NotNull
    JobDataMap createJobData(@NotNull Map<?, ?> map);

    /**
     * 创建 JobDetail
     *
     * @param jobClass        执行的 Job 对象
     * @param jobKey          JobKey 对象
     * @param desc            JobDetail 描述部分
     * @param jobDataMap      JobDataMap 对象
     * @param requestRecovery 执行失败后是否重试
     * @return JobDetail 对象
     */
    @NotNull
    JobDetail createJobDetail(@NotNull Class<? extends Job> jobClass,
                              @NotNull JobKey jobKey,
                              @NotBlank String desc,
                              @NotNull JobDataMap jobDataMap,
                              boolean requestRecovery);

    /**
     * 创建 CronTrigger
     *
     * @param triggerKey     TriggerKey 对象
     * @param cronExpression trigger cron 表达式
     * @param desc           trigger 描述
     * @param jobDataMap     JobDataMap 对象
     * @return CronTrigger 对象
     */
    @NotNull
    CronTrigger createCronTrigger(@NotNull TriggerKey triggerKey,
                                  @NotBlank String cronExpression,
                                  @NotBlank String desc,
                                  @NotNull JobDataMap jobDataMap);

    /**
     * 创建任务
     * 新增任务失败时抛出 TaskException 异常
     *
     * @param jobDetail   任务详情
     * @param cronTrigger 基于 Cron 任务触发器
     */
    void addTask(@NotNull JobDetail jobDetail, @NotNull CronTrigger cronTrigger);

    /**
     * 根据任务 JobKey 删除任务
     * 任务不存在时抛出 TaskException 异常
     *
     * @param jobKey 任务 JobKey
     */
    void deleteTask(@NotNull JobKey jobKey);

    /**
     * 暂停 Job
     * 暂停失败时抛出 TaskException 异常
     *
     * @param jobKey 任务 JobKey
     */
    void pauseTask(@NotNull JobKey jobKey);

    /**
     * 恢复任务执行
     * 恢复失败时抛出 TaskException 异常
     *
     * @param jobKey 任务 JobKey
     */
    void resumeTask(@NotNull JobKey jobKey);

    /**
     * 检查任务是否存在
     *
     * @param jobKey 任务 JobKey
     * @return 存在返回 true，否则返回 false
     */
    boolean exists(@NotNull JobKey jobKey);
}
