package me.aki.estore.controller.order;

import me.aki.estore.domain.Order;
import me.aki.estore.domain.OrderItem;
import me.aki.estore.domain.Product;
import me.aki.estore.domain.User;
import me.aki.estore.exception.OrderException;
import me.aki.estore.factory.BasicFactory;
import me.aki.estore.service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Aki on 2017/2/13.
 */
@WebServlet(name = "AddOrderServlet", urlPatterns = "/servlet/AddOrderServlet")
public class AddOrderServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        OrderService service = BasicFactory.getFactory().getService(OrderService.class);

        // 获取订单信息
        Order order = new Order();
        order.setId(UUID.randomUUID().toString());
        order.setPaystate(0); // 未支付状态
        order.setReceiverinfo(request.getParameter("receiverinfo"));

        // 获取购物车信息，从购物车中获取每个订单项，并计算金额
        double money = 0;
        List<OrderItem> orderItemList = new ArrayList<>();
        Map<Product, Integer> cart = (Map<Product, Integer>) request.getSession().getAttribute("shoppingCart");
        for (Map.Entry<Product, Integer> entry : cart.entrySet()) {
            money += entry.getKey().getPrice() * entry.getValue();

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder_id(order.getId());
            orderItem.setProduct_id(entry.getKey().getId());
            orderItem.setQuantity(entry.getValue());
            orderItemList.add(orderItem);
        }

        order.setList(orderItemList);
        order.setMoney(money);

        User user = (User) request.getSession().getAttribute("user");
        order.setUser_id(user.getId());


        // 调用service添加订单
        try {
            service.addOrder(order); // 添加失败会抛出异常
            //3.清空购物车
            cart.clear();
            //4.回到主页
            response.getWriter().write("订单生成成功!请去支付!");
            response.setHeader("Refresh", "3;url=" + request.getContextPath() + "/index.jsp");

        } catch (OrderException e) {
            e.printStackTrace();
            response.getWriter().write("订单生成失败，" + e.getMessage());
            response.setHeader("Refresh", "3;url=" + request.getContextPath() + "/index.jsp");
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
