package com.xcx.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/indexServlet")
public class IndexServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        HttpSession session = req.getSession(false);

        if (null == session || null ==session.getAttribute("username")) {
            resp.getWriter().append("<h1>未登录状态，请登陆</h1>");
            resp.setHeader("refresh", "3;url=login.html");
        } else {
            Object username = session.getAttribute("username");
            resp.getWriter().append("<h1>欢迎光临~~~ 尊贵的" + username + "</h1>")
                    .append("<a href='logoutServlet'>退出~~~</a>")
                    .append("<a href='indexAll.html'>进入系统</a>");
        }
    }
}
