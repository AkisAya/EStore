package me.aki.estore.controller;

import me.aki.estore.domain.UploadMsg;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Aki on 2017/2/27.
 */
@WebServlet(name = "uploadProgress", urlPatterns = "/servlet/uploadProgress")
public class uploadProgress extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UploadMsg umsg = (UploadMsg) request.getSession().getAttribute("umsg");
        response.getWriter().write(umsg.toString());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
