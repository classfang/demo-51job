package org.java1928.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.java1928.entity.Job;
import org.java1928.entity.PageBean;
import org.java1928.service.JobService;

import com.alibaba.fastjson.JSON;

/**
 * 职位信息控制器
 */
@WebServlet("/job")
public class JobController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String method = request.getParameter("method");
        switch (method) {
            case "init":
                init(request, response);
                break;
            case "query":
                query(request, response);
                break;
            case "page":
                page(request, response);
                break;
        }
    }

    // 分页查询
    private void page(HttpServletRequest request, HttpServletResponse response) {

        JobService jobService = new JobService();

        String currentPage = request.getParameter("currentPage");
        String pageSize = request.getParameter("pageSize");

        PageBean<Job> page = null;
        try {
            page = jobService.page(Long.valueOf(currentPage), Long.valueOf(pageSize));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            response.getWriter().print(JSON.toJSONString(page, true));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 查询所有数据
    private void query(HttpServletRequest request, HttpServletResponse response) {
        JobService jobService = new JobService();

        List<Job> jobs = null;
        try {
            jobs = jobService.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            response.getWriter().print(JSON.toJSONString(jobs));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 通过爬虫初始化数据
    private void init(HttpServletRequest request, HttpServletResponse response) {

        JobService jobService = new JobService();

        String startPage = request.getParameter("startPage");
        String endPage = request.getParameter("endPage");

        Long counts = jobService.init(Integer.parseInt(startPage), Integer.parseInt(endPage));

        HashMap<Object, Object> jsonObj = new HashMap<>();
        jsonObj.put("counts", counts);

        String json = JSON.toJSONString(jsonObj);
        try {
            response.getWriter().print(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}
