package me.aki.estore.controller.cart;

import me.aki.estore.domain.Product;

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
@WebServlet(name = "ClearCartServlet", urlPatterns = "/servlet/ClearCartServlet")
public class ClearCartServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<Product, Integer> cart = (Map<Product, Integer>) request.getSession().getAttribute("shoppingCart");
        cart.clear();
        response.sendRedirect(request.getContextPath() + "/shoppingCart.jsp");
    }
}
