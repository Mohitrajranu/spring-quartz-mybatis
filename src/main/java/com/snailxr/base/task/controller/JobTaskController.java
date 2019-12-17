package com.snailxr.base.task.controller;

import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.snailxr.base.support.RetObj;
import com.snailxr.base.support.spring.SpringUtils;
import com.snailxr.base.task.domain.ScheduleJob;
import com.snailxr.base.task.service.JobTaskService;

// TODO: Auto-generated Javadoc
/**
 * The Class JobTaskController.
 */
@Controller
@RequestMapping("/task")
public class JobTaskController {
	
	/** The log. */
	// Logger
	public final Logger log = Logger.getLogger(this.getClass());
	
	/** The task service. */
	@Autowired
	private JobTaskService taskService;

	/**
	 * Task list.
	 *
	 * @param request the request
	 * @return the string
	 */
	@RequestMapping("taskList")
	public String taskList(HttpServletRequest request) {
		List<ScheduleJob> taskList = taskService.getAllTask();
		request.setAttribute("taskList", taskList);
		return "base/task/taskList";
	}

	/**
	 * Task list.
	 *
	 * @param request the request
	 * @param scheduleJob the schedule job
	 * @return the ret obj
	 */
	@RequestMapping("add")
	@ResponseBody
	public RetObj taskList(HttpServletRequest request, ScheduleJob scheduleJob) {
		RetObj retObj = new RetObj();
		retObj.setFlag(false);
		try {
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression());
		} catch (Exception e) {
			retObj.setMsg("cron The expression is incorrect and cannot be parsed!");
			return retObj;
		}
		Object obj = null;
		try {
			if (StringUtils.isNotBlank(scheduleJob.getBeanClass())) {
				Class clazz = Class.forName(scheduleJob.getBeanClass());
				obj = clazz.newInstance();
			}
			else {
				obj = SpringUtils.getBean(scheduleJob.getSpringId());
			} 
		} catch (Exception e) {
			// do nothing.........
		}
		if (obj == null) {
			retObj.setMsg("No target class found!");
			return retObj;
		} else {
			Class clazz = obj.getClass();
			Method method = null;
			try {
				method = clazz.getMethod(scheduleJob.getMethodName(), null);
			} catch (Exception e) {
				// do nothing.....
			}
			if (method == null) {
				retObj.setMsg("No target method found!");
				return retObj;
			}
		}
		try {
			taskService.addTask(scheduleJob);
		} catch (Exception e) {
			e.printStackTrace();
			retObj.setFlag(false);
			retObj.setMsg("Save failed, check if the name group combination has duplicates!");
			return retObj;
		}

		retObj.setFlag(true);
		return retObj;
	}

	/**
	 * Change job status.
	 *
	 * @param request the request
	 * @param jobId the job id
	 * @param cmd the cmd
	 * @return the ret obj
	 */
	@RequestMapping("changeJobStatus")
	@ResponseBody
	public RetObj changeJobStatus(HttpServletRequest request, Long jobId, String cmd) {
		RetObj retObj = new RetObj();
		retObj.setFlag(false);
		try {
			taskService.changeStatus(jobId, cmd);
		} catch (SchedulerException e) {
			log.error(e.getMessage(), e);
			retObj.setMsg("Task status change failed!");
			return retObj;
		}
		retObj.setFlag(true);
		return retObj;
	}

	/**
	 * Update cron.
	 *
	 * @param request the request
	 * @param jobId the job id
	 * @param cron the cron
	 * @return the ret obj
	 */
	@RequestMapping("updateCron")
	@ResponseBody
	public RetObj updateCron(HttpServletRequest request, Long jobId, String cron) {
		RetObj retObj = new RetObj();
		retObj.setFlag(false);
		try {
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cron);
		} catch (Exception e) {
			retObj.setMsg("The cron expression is incorrect and cannot be parsed!");
			return retObj;
		}
		try {
			taskService.updateCron(jobId, cron);
		} catch (SchedulerException e) {
			retObj.setMsg("Cron update failed!");
			return retObj;
		}
		retObj.setFlag(true);
		return retObj;
	}
}
