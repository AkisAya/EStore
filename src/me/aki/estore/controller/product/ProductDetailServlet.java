package me.aki.estore.controller.product;

import me.aki.estore.domain.Product;
import me.aki.estore.factory.BasicFactory;
import me.aki.estore.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.plaf.basic.BasicIconFactory;
import java.io.IOException;

/**
 * Created by Aki on 2017/2/11.
 */
@WebServlet(name = "ProductDetailServlet", urlPatterns = "/servlet/ProductDetailServlet")
public class ProductDetailServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProductService productService = BasicFactory.getFactory().getService(ProductService.class);
        String id = request.getParameter("id");
        Product product = productService.findProductById(id);
        if (product != null) {
            request.setAttribute("product", product);
            request.getRequestDispatcher("/productDetail.jsp").forward(request, response);
        } else {
            throw new RuntimeException("找不到该商品!!!");
        }
    }
}
