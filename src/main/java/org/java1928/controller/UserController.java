package org.java1928.controller;

import com.alibaba.fastjson.JSON;
import redis.clients.jedis.Jedis;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

@WebServlet("/user")
public class UserController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String method = request.getParameter("method");
        switch (method) {
            case "login":
                login(request, response);
                break;
        }
    }

    // 登录接口
    private void login(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        String msg = "";
        HashMap<String, String> jsonObj = new HashMap<>();
        if (username.equals("root") && password.equals("123")) {
            msg = "登录成功";

            String key = UUID.randomUUID().toString();
            Jedis jedis = new Jedis("localhost", 6379);
            jedis.auth("123456");
            jedis.set(key, username + "::" + password);
            jedis.expire(key, 3600);

            jsonObj.put("token", key);

        } else {
            msg = "登录失败";
        }
        jsonObj.put("msg", msg);
        response.getWriter().print(JSON.toJSONString(jsonObj));

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
