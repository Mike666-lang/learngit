package com.xcx.controller;

import com.xcx.entity.User;
import com.xcx.service.UserService;
import com.xcx.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
@WebServlet("/userSystem")
public class UserController extends BaseServlet {
    private final UserService userService = new UserServiceImpl();


    public void addUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String[]> parameterMap = req.getParameterMap();

        if (userService.addUser(parameterMap)) {
            resp.getWriter().append("<h1>添加成功</h1>");
        } else {
            resp.getWriter().append("<h1>添加失败</h1>");
        }
        resp.setHeader("refresh", "2;url=indexAll.html");
    }


    public List<User> allUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        return userService.findAllUser();
    }


    public Map<String, Object> findUserById(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        return userService.findUserById(Integer.parseInt(req.getParameter("id")));
    }


    public boolean delUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        return userService.delUser(req.getParameter("id"));
    }


    public boolean updateUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String[]> parameterMap = req.getParameterMap();

        return  userService.updateUser(parameterMap);
    }


    public List<Map<String,Object>> searchUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 获取指定的字段信息，传入到service层进行处理操作
        return userService.searchUser(req.getParameter("field"),req.getParameter("keyword"));
    }
}
