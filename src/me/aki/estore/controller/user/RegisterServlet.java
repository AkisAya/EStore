package me.aki.estore.controller.user;

import me.aki.estore.domain.User;
import me.aki.estore.exception.UserException;
import me.aki.estore.factory.BasicFactory;
import me.aki.estore.service.UserService;
import me.aki.estore.util.MD5Util;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Aki on 2017/2/10.
 */
@WebServlet(name = "RegisterServlet", urlPatterns = "/servlet/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 校验验证码
        String validStr = request.getParameter("validStr");
        String validStr_check = (String) request.getSession().getAttribute("validStr_check");
        if (!validStr.equals(validStr_check)) {
            request.setAttribute("check_msg", "<label style='color: red;'>验证码错误!</label>");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }

        // 封装表单数据
        User user = new User();
        try {
            BeanUtils.populate(user, request.getParameterMap());
            // 密码使用md5加密
            user.setPassword(MD5Util.md5(user.getPassword()));

        } catch (Exception e) {
            e.printStackTrace();
        }

        // 调用service
        UserService userService = BasicFactory.getFactory().getService(UserService.class);
        try {
            userService.register(user);
            // 注册成功
            response.getWriter().write("注册成功,请到邮箱中进行激活。5秒钟后自动跳转到首页");
            response.setHeader("Refresh", "5;url=" + request.getContextPath() + "/index.jsp");
        } catch (UserException e) {
            response.getWriter().write("注册失败," + e.getMessage() + "，请重新操作。5秒钟后自动跳转到首页");
            response.setHeader("Refresh", "5;url=" + request.getContextPath() + "/index.jsp");
            e.printStackTrace();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
