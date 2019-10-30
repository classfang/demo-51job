package org.java1928.filter;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 	跨域请求过滤器
 * @author junki
 * @date 2019年10月19日
 */
@WebFilter("/*")
public class CORSFilter implements Filter {

	@Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

	@Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        //*表示允许所有域名跨域
        httpResponse.addHeader("Access-Control-Allow-Origin", "*");
        httpResponse.addHeader("Access-Control-Allow-Headers",
                "Origin, X-Requested-With, Content-Type, Accept");
        //允许跨域的Http方法
        httpResponse.addHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    public void destroy() {

    }
}
