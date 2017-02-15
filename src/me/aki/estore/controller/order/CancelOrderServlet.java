package me.aki.estore.controller.order;

import me.aki.estore.exception.OrderException;
import me.aki.estore.factory.BasicFactory;
import me.aki.estore.service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Aki on 2017/2/14.
 */
@WebServlet(name = "CancelOrderServlet", urlPatterns = "/servlet/CancelOrderServlet")
public class CancelOrderServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            OrderService orderService = BasicFactory.getFactory().getService(OrderService.class);
            String id = request.getParameter("id");
            orderService.cancelOrder(id);

            response.getWriter().write("取消成功");
            response.setHeader("Refresh", "3;url=" + request.getContextPath() + "/servlet/OrderListServlet");

        } catch (OrderException e) {
            e.printStackTrace();
        }

    }
}
