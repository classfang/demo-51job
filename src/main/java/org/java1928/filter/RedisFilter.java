package org.java1928.filter;

import redis.clients.jedis.Jedis;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// http://localhost:1928/demo-51job/job?method=page&currentPage=1&pageSize=20
@WebFilter("/job")
public class RedisFilter implements Filter {
    public void destroy() {

    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        response.setContentType("text/html;charset=UTF-8");

        String method = request.getParameter("method");
        if (method != null && method.equals("page")) {
            Jedis jedis = new Jedis("localhost", 6379);
            jedis.auth("123456");

            String key = request.getQueryString();
            String value = jedis.get(key);
            if (value != null) {
                System.out.println("从redis取数据");
                response.getWriter().print(value);
                return;
            }
        }
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
