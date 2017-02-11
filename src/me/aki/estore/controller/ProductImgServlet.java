package me.aki.estore.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Aki on 2017/2/11.
 */
@WebServlet(name = "ProductImgServlet", urlPatterns = "/servlet/ProductImgServlet")
public class ProductImgServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String imgurl =request.getParameter("imgurl");
        request.getRequestDispatcher(imgurl).forward(request, response);
    }
}
