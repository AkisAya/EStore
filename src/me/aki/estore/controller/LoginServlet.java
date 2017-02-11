package me.aki.estore.controller;

import me.aki.estore.domain.User;
import me.aki.estore.exception.UserException;
import me.aki.estore.factory.BasicFactory;
import me.aki.estore.service.UserService;
import me.aki.estore.util.MD5Util;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Aki on 2017/2/10.
 */
@WebServlet(name = "LoginServlet", urlPatterns = "/servlet/LoginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取用户名和密码
        String username = request.getParameter("username");
        String password = MD5Util.md5(request.getParameter("password"));

        // 根据用户名和密码登录
        UserService userService = BasicFactory.getFactory().getInstance(UserService.class);
        try {
            User user = userService.login(username, password);
            // 将用户放入session中表示用户登录，然后进行页面跳转
            request.getSession().setAttribute("user", user);

        } catch (UserException e) {
            request.setAttribute("error_msg", e.getMessage());
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            e.printStackTrace();
        }

        // 处理记住用户名
        if ("checked".equals(request.getParameter("rememberName"))) {
            Cookie nameCookie = new Cookie("username", username);
            nameCookie.setPath("/");  // 设置cookie生效的路径，不设置则为此Servlet的路径
            nameCookie.setMaxAge(10*24*3600); // cookie保留10天
            response.addCookie(nameCookie);
        } else {
            Cookie nameCookie = new Cookie("username", "");
            nameCookie.setPath("/");
            nameCookie.setMaxAge(0);  // 删除cookie
            response.addCookie(nameCookie);
        }

        // 处理自动登录
        if ("checked".equals(request.getParameter("autoLogin"))) {
            Cookie autoLoginCookie = new Cookie("autoLogin", username + ":" + password);
            autoLoginCookie.setPath("/");
            autoLoginCookie.setMaxAge(10*24*3600);
            response.addCookie(autoLoginCookie);
        }




        response.sendRedirect(request.getContextPath() + "/index.jsp");
//        request.getRequestDispatcher("/index.jsp").forward(request, response);



    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
