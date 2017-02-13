package me.aki.estore.controller.user;

import me.aki.estore.domain.User;
import me.aki.estore.exception.UserException;
import me.aki.estore.factory.BasicFactory;
import me.aki.estore.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Aki on 2017/2/10.
 */
@WebServlet(name = "ActiveUserServlet", urlPatterns = "/servlet/ActiveUserServlet")
public class ActiveUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取激活码
        String activeCode = request.getParameter("activecode");

        // 调用service激活用户
        UserService userService = BasicFactory.getFactory().getService(UserService.class);
        try {
            User user = userService.activeUser(activeCode);
            response.getWriter().write("激活成功！3秒钟后自动跳转到首页");
            response.setHeader("Refresh", "3;url=" + request.getContextPath() + "/index.jsp");
        } catch (UserException e) {
            response.getWriter().write("激活异常，" + e.getMessage());
            response.setHeader("Refresh", "3;url=" + request.getContextPath() + "/index.jsp");
            e.printStackTrace();
        }

        // 激活用户后登录
    }
}
