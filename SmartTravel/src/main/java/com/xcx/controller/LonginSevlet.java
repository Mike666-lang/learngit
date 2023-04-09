package com.xcx.controller;

import com.xcx.service.UserService;
import com.xcx.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/login")
public class LonginSevlet extends HttpServlet {
    private final UserService userService = new UserServiceImpl();
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        System.out.println(username);
        Integer chap = 1;
        Map<String, Object> user = null;
        try {
             user = userService.findUserByName(username);
        } catch (Exception e) {
            System.out.println("rendRedirect1");
            resp.sendRedirect("/login.html");
        }

        if (null == user || !user.get("username").equals(username) || !user.get("password").equals(password) ) {
            System.out.println("rendRedirect2");
            resp.sendRedirect("http://localhost:8080/login.html");
        }

        if ( user.get("username").equals(username) && user.get("password").equals(password) && user.get("usertype") == chap) {
            HttpSession session = req.getSession(true);
            session.setAttribute("username", username);
            session.setAttribute("isLogin",true);
            session.setMaxInactiveInterval(60 * 60 * 24);
            Cookie cookie = new Cookie("JSESSIONID", session.getId());

            cookie.setMaxAge(-1);

            resp.addCookie(cookie);

            resp.sendRedirect("indexServlet.html");
            }
        if (user.get("username").equals(username) && user.get("password").equals(password) && (user.get("usertype") != chap)) {
            HttpSession session = req.getSession(true);
            session.setAttribute("username", username);
            session.setMaxInactiveInterval( 60 * 60 * 24 * 15);
            Cookie cookie = new Cookie("JSESSIONID", session.getId());

            cookie.setMaxAge(60 * 60 * 24 * 15);

            resp.addCookie(cookie);
            resp.sendRedirect("indexServlet.html");
        }


        }

}
