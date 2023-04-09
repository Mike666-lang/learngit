package com.xcx.controller;

import com.alibaba.fastjson.JSON;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;

public class BaseServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json;charset=utf-8");


        String method = req.getParameter("method");

        try {
            Object returnValue = this.getClass()
                    .getMethod(method, HttpServletRequest.class, HttpServletResponse.class)
                    .invoke(this, req, resp);
            if (null == returnValue) {
                return;
            }
            PrintWriter writer = resp.getWriter();

            if (returnValue instanceof String) {
                String value = (String) returnValue;

                if (value.startsWith("forward:")) {
                    req.getRequestDispatcher(value.substring(value.indexOf(":") + 1)).forward(req, resp);
                } else if (value.startsWith("redirect:")) {
                    resp.sendRedirect(value.substring(value.indexOf(":") + 1));
                } else {
                    writer.write(value);
                }
            } else {
                writer.write(JSON.toJSONString(returnValue));
            }

        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }


    }
}
