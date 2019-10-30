package org.java1928.test;

import org.java1928.dao.JobDao;
import org.java1928.service.JobService;

import java.sql.SQLException;

/**
 * 	测试用类
 * @author junki
 * @date 2019年10月30日
 */
public class MyTest {

	public static void main(String[] args) {
		
//		JobService jobService = new JobService();
//
//		jobService.init(1, 20);

		JobDao jobDao = new JobDao();
		Long count = null;
		try {
			count = jobDao.count();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(count);

	}
	
}
