package com.snailxr.base.task.dao;

import java.util.List;

import com.snailxr.base.task.domain.ScheduleJob;

// TODO: Auto-generated Javadoc
/**
 * The Interface ScheduleJobMapper.
 */
public interface ScheduleJobMapper {
	
	/**
	 * Delete by primary key.
	 *
	 * @param jobId the job id
	 * @return the int
	 */
	int deleteByPrimaryKey(Long jobId);

	/**
	 * Insert.
	 *
	 * @param record the record
	 * @return the int
	 */
	int insert(ScheduleJob record);

	/**
	 * Insert selective.
	 *
	 * @param record the record
	 * @return the int
	 */
	int insertSelective(ScheduleJob record);

	/**
	 * Select by primary key.
	 *
	 * @param jobId the job id
	 * @return the schedule job
	 */
	ScheduleJob selectByPrimaryKey(Long jobId);

	/**
	 * Update by primary key selective.
	 *
	 * @param record the record
	 * @return the int
	 */
	int updateByPrimaryKeySelective(ScheduleJob record);

	/**
	 * Update by primary key.
	 *
	 * @param record the record
	 * @return the int
	 */
	int updateByPrimaryKey(ScheduleJob record);

	/**
	 * Gets the all.
	 *
	 * @return the all
	 */
	List<ScheduleJob> getAll();
}