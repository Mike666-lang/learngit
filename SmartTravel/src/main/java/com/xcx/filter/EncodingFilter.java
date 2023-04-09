package com.xcx.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "encodingFilter", urlPatterns = {"/*"})
public class EncodingFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpServletRequest req = (HttpServletRequest) request;

        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
//        resp.setContentType("application/json;charset=utf-8");
        System.out.println("编码集过滤器");
        chain.doFilter(req, resp);
    }
}
