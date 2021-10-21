package com.seliote.mlb.task.service.impl;

import com.seliote.mlb.task.exception.TaskException;
import com.seliote.mlb.task.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Task 任务 Service 实现
 *
 * @author seliote
 * @version 2021-07-02
 */
@Slf4j
@Service
public class TaskServiceImpl implements TaskService {

    private final Scheduler scheduler;

    @Autowired
    public TaskServiceImpl(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    public JobDataMap createJobData(Map<?, ?> map) {
        return new JobDataMap(map);
    }

    @Override
    public JobDetail createJobDetail(Class<? extends Job> jobClass, JobKey jobKey, String desc, JobDataMap jobDataMap, boolean requestRecovery) {
        return JobBuilder.newJob(jobClass)
                .withIdentity(jobKey)
                .withDescription(desc)
                .usingJobData(jobDataMap)
                .requestRecovery(requestRecovery)
                .storeDurably(true)
                .build();
    }

    @Override
    public CronTrigger createCronTrigger(TriggerKey triggerKey, String cronExpression, String desc, JobDataMap jobDataMap) {
        if (!CronExpression.isValidExpression(cronExpression)) {
            log.error("{} - {} cron {} is illegal", triggerKey.getGroup(), triggerKey.getName(), cronExpression);
            throw new TaskException("Illegal cron expression for create trigger");
        }
        return TriggerBuilder.newTrigger()
                .withIdentity(triggerKey)
                .withDescription(desc)
                .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                .usingJobData(jobDataMap)
                .build();
    }

    @Override
    public void addTask(JobDetail jobDetail, CronTrigger cronTrigger) {
        try {
            scheduler.scheduleJob(jobDetail, cronTrigger);
            log.info("Add task {} - {}", jobDetail.getKey().getGroup(), jobDetail.getKey().getName());
        } catch (SchedulerException exception) {
            log.error("Failed create {} - {}, {}",
                    jobDetail.getKey().getGroup(), jobDetail.getKey().getName(), exception.getMessage());
            throw new TaskException(exception);
        }
    }

    @Override
    public void deleteTask(JobKey jobKey) {
        try {
            scheduler.deleteJob(jobKey);
            log.info("Delete task {} -{}", jobKey.getGroup(), jobKey.getName());
        } catch (SchedulerException exception) {
            log.error("Failed delete task {} -{}, {}", jobKey.getGroup(), jobKey.getName(), exception.getMessage());
            throw new TaskException(exception);
        }
    }

    @Override
    public void pauseTask(JobKey jobKey) {
        try {
            scheduler.pauseJob(jobKey);
            log.info("Pause task {} -{}", jobKey.getGroup(), jobKey.getName());
        } catch (SchedulerException exception) {
            log.error("Failed pause task {} -{}, {}", jobKey.getGroup(), jobKey.getName(), exception.getMessage());
            throw new TaskException(exception);
        }
    }

    @Override
    public void resumeTask(JobKey jobKey) {
        try {
            scheduler.resumeJob(jobKey);
            log.info("Resume task {} -{}", jobKey.getGroup(), jobKey.getName());
        } catch (SchedulerException exception) {
            log.error("Failed resume task {} -{}, {}", jobKey.getGroup(), jobKey.getName(), exception.getMessage());
            throw new TaskException(exception);
        }
    }

    @Override
    public boolean exists(JobKey jobKey) {
        try {
            return scheduler.checkExists(jobKey);
        } catch (SchedulerException exception) {
            log.error("Failed check job {}-{} exists, {}",
                    jobKey.getGroup(), jobKey.getName(), exception.getMessage());
            throw new TaskException(exception);
        }
    }
}
