package me.aki.estore.controller;

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
@WebServlet(name = "LogoutServlet", urlPatterns = "/servlet/LogoutServlet")
public class LogoutServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession(false) != null) {
            request.getSession().invalidate();
            // 删除自动登录的cookie
            Cookie autoLoginCookie = new Cookie("autoLogin", "");
            autoLoginCookie.setPath("/");
            autoLoginCookie.setMaxAge(0);
            response.addCookie(autoLoginCookie);
        }
        response.sendRedirect(request.getContextPath() + "/index.jsp");
    }
}
