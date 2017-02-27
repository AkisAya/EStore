package me.aki.estore.controller;

import me.aki.estore.factory.BasicFactory;
import me.aki.estore.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Aki on 2017/2/27.
 */
@WebServlet(name = "ValidateName", urlPatterns = "/servlet/ValidateName")
public class ValidateName extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        UserService service = BasicFactory.getFactory().getService(UserService.class);
        String username = req.getParameter("username");

        String msg = "{'msg':'用户名有效', 'stat':1}";
        if (service.hasUser(username)) {
            msg = "{'msg':'用户名已存在', 'stat':0}";
        }
        resp.getWriter().write(msg);

    }
}
