package me.aki.estore.controller.order;

import me.aki.estore.domain.OrderExt;
import me.aki.estore.domain.User;
import me.aki.estore.factory.BasicFactory;
import me.aki.estore.service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Aki on 2017/2/14.
 */
@WebServlet(name = "OrderListServlet", urlPatterns = "/servlet/OrderListServlet")
public class OrderListServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        OrderService orderService = BasicFactory.getFactory().getService(OrderService.class);

        // 根据用户id查询用户的订单信息
        User user = (User) request.getSession().getAttribute("user");
        int userId = user.getId();

        List<OrderExt> orderExtList = orderService.findAllOrders(userId);

        request.setAttribute("orderExtList", orderExtList);
        request.getRequestDispatcher("/orderList.jsp").forward(request, response);


    }
}
