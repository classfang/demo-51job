package org.java1928.test;

import org.java1928.dao.JobDao;
import org.java1928.service.JobService;
import redis.clients.jedis.Jedis;

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

//		JobDao jobDao = new JobDao();
//		Long count = null;
//		try {
//			count = jobDao.count();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		System.out.println(count);

		// 1.创建jedis对象
		Jedis jedis = new Jedis("localhost", 6379);

		// 2.进行权限校验
		jedis.auth("123456");

		// 3.存储一条数据
		jedis.set("wyk", "ssb");

		// 4.设置key的过期时间
		jedis.expire("wyk", 10);

	}
	
}
