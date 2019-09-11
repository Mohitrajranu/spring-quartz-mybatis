
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `task_schedule_job`
-- ----------------------------
DROP TABLE IF EXISTS `task_schedule_job`;
CREATE TABLE `task_schedule_job` (
  `job_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `job_name` varchar(255) DEFAULT NULL,
  `job_group` varchar(255) DEFAULT NULL,
  `job_status` varchar(255) DEFAULT NULL,
  `cron_expression` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `bean_class` varchar(255) DEFAULT NULL,
  `is_concurrent` varchar(255) DEFAULT NULL COMMENT '1',
  `spring_id` varchar(255) DEFAULT NULL,
  `method_name` varchar(255) NOT NULL,
  PRIMARY KEY (`job_id`),
  UNIQUE KEY `name_group` (`job_name`,`job_group`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of task_schedule_job
-- ----------------------------
INSERT INTO `task_schedule_job` VALUES ('1', '2019-09-19 11:50:17', '2019-09-19 14:17:34', 'test', 'test', '0', '0/1 * * * * ?', 'test', 'com.snailxr.base.task.TaskTest', '0', 'taskRun1', 'run1');
INSERT INTO `task_schedule_job` VALUES ('2', '2019-09-19 11:51:17', '2019-09-19 14:17:19', 'test1', 'test', '0', '0/5 * * * * ?', 'testsch', 'com.snailxr.base.task.TaskTest', '1', 'taskTest', 'testSchedule');
INSERT INTO `task_schedule_job` VALUES ('3', '2019-09-19 11:52:17', '2019-09-19 14:17:27', 'test2', '1111', '0', '0/1 * * * * ?', 'testrun', 'com.snailxr.base.task.TaskTest', '1', 'taskRun', 'run');
