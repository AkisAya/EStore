package me.aki.estore.controller.cart;

import me.aki.estore.domain.Product;
import me.aki.estore.factory.BasicFactory;
import me.aki.estore.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by Aki on 2017/2/12.
 */
@WebServlet(name = "DelCartServlet", urlPatterns = "/servlet/DelCartServlet")
public class DelCartServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1 根据商品id查找商品
        String id = request.getParameter("id");
        ProductService productService = BasicFactory.getFactory().getService(ProductService.class);
        Product product = productService.findProductById(id);

        // 2 移除cart中的key/value
        Map<Product, Integer> cart = (Map<Product, Integer>) request.getSession().getAttribute("shoppingCart");
        cart.remove(product);

        // 3 重定向到购物车页面
        response.sendRedirect(request.getContextPath() + "/shoppingCart.jsp");
    }
}
