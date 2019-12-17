package com.snailxr.base.task.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import com.snailxr.base.task.QuartzJobFactory;
import com.snailxr.base.task.QuartzJobFactoryDisallowConcurrentExecution;
import com.snailxr.base.task.dao.ScheduleJobMapper;
import com.snailxr.base.task.domain.ScheduleJob;

// TODO: Auto-generated Javadoc
/**
 * The Class JobTaskService.
 *
 * @author Mohit Raj
 * @Description: Planning task management
 * @date 2019-09-09
 */
@Service
public class JobTaskService {
	
	/** The log. */
	public final Logger log = Logger.getLogger(this.getClass());
	
	/** The scheduler factory bean. */
	@Autowired
	private SchedulerFactoryBean schedulerFactoryBean;

	/** The schedule job mapper. */
	@Autowired
	private ScheduleJobMapper scheduleJobMapper;

	/**
	 * getAllJob.
	 *
	 * @return the all task
	 */
	public List<ScheduleJob> getAllTask() {
		return scheduleJobMapper.getAll();
	}

	/**
	 * addJob.
	 *
	 * @param job the job
	 */
	public void addTask(ScheduleJob job) {
		job.setCreateTime(new Date());
		scheduleJobMapper.insertSelective(job);
	}

	/**
	 * Query the job from the database.
	 *
	 * @param jobId the job id
	 * @return the task by id
	 */
	public ScheduleJob getTaskById(Long jobId) {
		return scheduleJobMapper.selectByPrimaryKey(jobId);
	}

	/**
	 * Change task status.
	 *
	 * @param jobId the job id
	 * @param cmd the cmd
	 * @throws SchedulerException the scheduler exception
	 */
	public void changeStatus(Long jobId, String cmd) throws SchedulerException {
		ScheduleJob job = getTaskById(jobId);
		if (job == null) {
			return;
		}
		if ("stop".equals(cmd)) {
			deleteJob(job);
			job.setJobStatus(ScheduleJob.STATUS_NOT_RUNNING);
		} else if ("start".equals(cmd)) {
			job.setJobStatus(ScheduleJob.STATUS_RUNNING);
			addJob(job);
		}
		scheduleJobMapper.updateByPrimaryKeySelective(job);
	}

	/**
	 * Change task cron expression.
	 *
	 * @param jobId the job id
	 * @param cron the cron
	 * @throws SchedulerException the scheduler exception
	 */
	public void updateCron(Long jobId, String cron) throws SchedulerException {
		ScheduleJob job = getTaskById(jobId);
		if (job == null) {
			return;
		}
		job.setCronExpression(cron);
		if (ScheduleJob.STATUS_RUNNING.equals(job.getJobStatus())) {
			updateJobCron(job);
		}
		scheduleJobMapper.updateByPrimaryKeySelective(job);

	}

	/**
	 * Add task.
	 *
	 * @param job the job
	 * @throws SchedulerException the scheduler exception
	 */
	public void addJob(ScheduleJob job) throws SchedulerException {
		if (job == null || !ScheduleJob.STATUS_RUNNING.equals(job.getJobStatus())) {
			return;
		}

		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		log.debug(scheduler + ".............................add");
		TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());

		CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

		// Does not exist, create
		if (null == trigger) {
			Class clazz = ScheduleJob.CONCURRENT_IS.equals(job.getIsConcurrent()) ? QuartzJobFactory.class : QuartzJobFactoryDisallowConcurrentExecution.class;

			JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(job.getJobName(), job.getJobGroup()).build();

			jobDetail.getJobDataMap().put("scheduleJob", job);

			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());

			trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobName(), job.getJobGroup()).withSchedule(scheduleBuilder).build();

			scheduler.scheduleJob(jobDetail, trigger);
		} else {
			// Trigger already exists, then update the corresponding timing settings
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());

			// Rebuild the trigger by the new cronExpression expression
			trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

			// Reset job execution by new trigger
			scheduler.rescheduleJob(triggerKey, trigger);
		}
	}

	/**
	 * Inits the.
	 *
	 * @throws Exception the exception
	 */
	@PostConstruct
	public void init() throws Exception {

		Scheduler scheduler = schedulerFactoryBean.getScheduler();

		// Get task information data here
		List<ScheduleJob> jobList = scheduleJobMapper.getAll();
	
		for (ScheduleJob job : jobList) {
			addJob(job);
		}
	}

	/**
	 * Get a list of all scheduled tasks.
	 *
	 * @return the all job
	 * @throws SchedulerException the scheduler exception
	 */
	public List<ScheduleJob> getAllJob() throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
		Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
		List<ScheduleJob> jobList = new ArrayList<ScheduleJob>();
		for (JobKey jobKey : jobKeys) {
			List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
			for (Trigger trigger : triggers) {
				ScheduleJob job = new ScheduleJob();
				job.setJobName(jobKey.getName());
				job.setJobGroup(jobKey.getGroup());
				job.setDescription("trigger:" + trigger.getKey());
				Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
				job.setJobStatus(triggerState.name());
				if (trigger instanceof CronTrigger) {
					CronTrigger cronTrigger = (CronTrigger) trigger;
					String cronExpression = cronTrigger.getCronExpression();
					job.setCronExpression(cronExpression);
				}
				jobList.add(job);
			}
		}
		return jobList;
	}

	/**
	 * All running jobs.
	 *
	 * @return the running job
	 * @throws SchedulerException the scheduler exception
	 */
	public List<ScheduleJob> getRunningJob() throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();
		List<ScheduleJob> jobList = new ArrayList<ScheduleJob>(executingJobs.size());
		for (JobExecutionContext executingJob : executingJobs) {
			ScheduleJob job = new ScheduleJob();
			JobDetail jobDetail = executingJob.getJobDetail();
			JobKey jobKey = jobDetail.getKey();
			Trigger trigger = executingJob.getTrigger();
			job.setJobName(jobKey.getName());
			job.setJobGroup(jobKey.getGroup());
			job.setDescription("trigger:" + trigger.getKey());
			Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
			job.setJobStatus(triggerState.name());
			if (trigger instanceof CronTrigger) {
				CronTrigger cronTrigger = (CronTrigger) trigger;
				String cronExpression = cronTrigger.getCronExpression();
				job.setCronExpression(cronExpression);
			}
			jobList.add(job);
		}
		return jobList;
	}

	/**
	 * Pause a job.
	 *
	 * @param scheduleJob the schedule job
	 * @throws SchedulerException the scheduler exception
	 */
	public void pauseJob(ScheduleJob scheduleJob) throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		scheduler.pauseJob(jobKey);
	}

	/**
	 * Resume a job.
	 *
	 * @param scheduleJob the schedule job
	 * @throws SchedulerException the scheduler exception
	 */
	public void resumeJob(ScheduleJob scheduleJob) throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		scheduler.resumeJob(jobKey);
	}

	/**
	 * delete job.
	 *
	 * @param scheduleJob the schedule job
	 * @throws SchedulerException the scheduler exception
	 */
	public void deleteJob(ScheduleJob scheduleJob) throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		scheduler.deleteJob(jobKey);

	}

	/**
	 * Execute the job immediately.
	 *
	 * @param scheduleJob the schedule job
	 * @throws SchedulerException the scheduler exception
	 */
	public void runAJobNow(ScheduleJob scheduleJob) throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		scheduler.triggerJob(jobKey);
	}

	/**
	 * Update job time expression.
	 *
	 * @param scheduleJob the schedule job
	 * @throws SchedulerException the scheduler exception
	 */
	public void updateJobCron(ScheduleJob scheduleJob) throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();

		TriggerKey triggerKey = TriggerKey.triggerKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());

		CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

		CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression());

		trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

		scheduler.rescheduleJob(triggerKey, trigger);
	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("xxxxx");
	}
}
