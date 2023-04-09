package com.xcx.filter;

import util.LoginStatus;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter({"/*"})
public class LoginFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpServletRequest req = (HttpServletRequest) request;
        String uri = req.getRequestURI();
        if (uri.contains("login")) {
            chain.doFilter(req, resp);
        } else {
            HttpSession session = req.getSession(false);
            if (session == null) {
                resp.sendRedirect("/login.html");


            } else {
                boolean isLogin = (boolean) session.getAttribute("isLogin");
                if (isLogin) {
                    chain.doFilter(req, resp);
                } else {
                    resp.sendRedirect("/login.html");
                }
            }
        }
    }
}

