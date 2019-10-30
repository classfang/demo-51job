package org.java1928.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.java1928.entity.Job;

/**
 * 	职位信息数据库操作类
 * @author junki
 * @date 2019年10月30日
 */
public class JobDao {

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	String url = "jdbc:mysql://localhost:3306/java1928test?useSSL=true&characterEncoding=UTF-8";
	String username = "root";
	String password = "root";
	
	public int insert(Job job) throws SQLException {
		
		Connection conn = DriverManager.getConnection(url, username, password);
		
		String sql = "insert into job(name, company, address, salary, date) value(?,?,?,?,?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, job.getName());
		pstmt.setString(2, job.getCompany());
		pstmt.setString(3, job.getAddress());
		pstmt.setString(4, job.getSalary());
		pstmt.setString(5, job.getDate());
		
		int result = pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
		
		return result;
		
	}

	public List<Job> select() throws SQLException {

		Connection conn = DriverManager.getConnection(url, username, password);

		String sql = "select * from job";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		List<Job> jobs = new ArrayList<>();
		while (rs.next()) {
			Job job = Job.builder().id(rs.getLong("id"))
					.name(rs.getString("name"))
					.company(rs.getString("company"))
					.address(rs.getString("address"))
					.salary(rs.getString("salary"))
					.date(rs.getString("date"))
					.build();
			jobs.add(job);
		}

		rs.close();
		pstmt.close();
		conn.close();

		return jobs;

	}

	public Long count() throws SQLException {

		Connection conn = DriverManager.getConnection(url, username, password);

		String sql = "select count(id) as counts from job";
		PreparedStatement pstmt = conn.prepareStatement(sql);

		ResultSet rs = pstmt.executeQuery();

		Long result = 0L;
		if (rs.next())
			result = rs.getLong("counts");

		rs.close();
		pstmt.close();
		conn.close();

		return result;

	}

	public List<Job> selectPage(Long startIndex, Long pageSize) throws SQLException {

		Connection conn = DriverManager.getConnection(url, username, password);

		String sql = "select * from job limit ?,?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setLong(1, startIndex);
		pstmt.setLong(2, pageSize);
		ResultSet rs = pstmt.executeQuery();

		List<Job> jobs = new ArrayList<>();
		while (rs.next()) {
			Job job = Job.builder().id(rs.getLong("id"))
					.name(rs.getString("name"))
					.company(rs.getString("company"))
					.address(rs.getString("address"))
					.salary(rs.getString("salary"))
					.date(rs.getString("date"))
					.build();
			jobs.add(job);
		}

		rs.close();
		pstmt.close();
		conn.close();

		return jobs;

	}
}
