package com.snailxr.base.task.domain;

import java.io.Serializable;
import java.util.Date;
// TODO: Auto-generated Javadoc

/**
 * The Class ScheduleJob.
 *
 * @author Mohit Raj
 * @Description: Scheduled task information
 * @date 2019-09-09
 */
public class ScheduleJob implements Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
		
		/** The Constant STATUS_RUNNING. */
		public static final String STATUS_RUNNING = "1";
	
	/** The Constant STATUS_NOT_RUNNING. */
	public static final String STATUS_NOT_RUNNING = "0";
	
	/** The Constant CONCURRENT_IS. */
	public static final String CONCURRENT_IS = "1";
	
	/** The Constant CONCURRENT_NOT. */
	public static final String CONCURRENT_NOT = "0";
	
	/** The job id. */
	private Long jobId;

	/** The create time. */
	private Date createTime;

	/** The update time. */
	private Date updateTime;
	
	/** jobName. */
	private String jobName;
	
	/** jobGroup. */
	private String jobGroup;
	
	/** jobStatus. */
	private String jobStatus;
	
	/** cronExpression. */
	private String cronExpression;
	
	/** description. */
	private String description;
	
	/** beanClass. */
	private String beanClass;
	
	/** isConcurrent. */
	private String isConcurrent;
	
	/** spring bean. */
	private String springId;
	
	/** methodName. */
	private String methodName;

	/**
	 * Gets the job id.
	 *
	 * @return the job id
	 */
	public Long getJobId() {
		return jobId;
	}

	/**
	 * Sets the job id.
	 *
	 * @param jobId the new job id
	 */
	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	/**
	 * Gets the creates the time.
	 *
	 * @return the creates the time
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * Sets the creates the time.
	 *
	 * @param createTime the new creates the time
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * Gets the update time.
	 *
	 * @return the update time
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * Sets the update time.
	 *
	 * @param updateTime the new update time
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * Gets the job name.
	 *
	 * @return the job name
	 */
	public String getJobName() {
		return jobName;
	}

	/**
	 * Sets the job name.
	 *
	 * @param jobName the new job name
	 */
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	/**
	 * Gets the job group.
	 *
	 * @return the job group
	 */
	public String getJobGroup() {
		return jobGroup;
	}

	/**
	 * Sets the job group.
	 *
	 * @param jobGroup the new job group
	 */
	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}

	/**
	 * Gets the job status.
	 *
	 * @return the job status
	 */
	public String getJobStatus() {
		return jobStatus;
	}

	/**
	 * Sets the job status.
	 *
	 * @param jobStatus the new job status
	 */
	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}

	/**
	 * Gets the cron expression.
	 *
	 * @return the cron expression
	 */
	public String getCronExpression() {
		return cronExpression;
	}

	/**
	 * Sets the cron expression.
	 *
	 * @param cronExpression the new cron expression
	 */
	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the bean class.
	 *
	 * @return the bean class
	 */
	public String getBeanClass() {
		return beanClass;
	}

	/**
	 * Sets the bean class.
	 *
	 * @param beanClass the new bean class
	 */
	public void setBeanClass(String beanClass) {
		this.beanClass = beanClass;
	}

	/**
	 * Gets the checks if is concurrent.
	 *
	 * @return the checks if is concurrent
	 */
	public String getIsConcurrent() {
		return isConcurrent;
	}

	/**
	 * Sets the checks if is concurrent.
	 *
	 * @param isConcurrent the new checks if is concurrent
	 */
	public void setIsConcurrent(String isConcurrent) {
		this.isConcurrent = isConcurrent;
	}

	/**
	 * Gets the spring id.
	 *
	 * @return the spring id
	 */
	public String getSpringId() {
		return springId;
	}

	/**
	 * Sets the spring id.
	 *
	 * @param springId the new spring id
	 */
	public void setSpringId(String springId) {
		this.springId = springId;
	}

	/**
	 * Gets the method name.
	 *
	 * @return the method name
	 */
	public String getMethodName() {
		return methodName;
	}

	/**
	 * Sets the method name.
	 *
	 * @param methodName the new method name
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
}