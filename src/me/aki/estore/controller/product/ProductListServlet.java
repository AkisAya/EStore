package me.aki.estore.controller.product;

import me.aki.estore.domain.Constant;
import me.aki.estore.domain.Page;
import me.aki.estore.domain.Product;
import me.aki.estore.factory.BasicFactory;
import me.aki.estore.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Aki on 2017/2/11.
 */
@WebServlet(name = "ProductListServlet", urlPatterns = "/servlet/ProductListServlet")
public class ProductListServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ProductService productService = BasicFactory.getFactory().getService(ProductService.class);

        String pageNumStr = request.getParameter("pageNum");
        int pageNum = Constant.DEFAULT_PAGE_NUMBER;
        if (pageNumStr != null && !"".equals(pageNumStr.trim())) {
            pageNum = Integer.parseInt(pageNumStr);
        }

        Page<Product> page = productService.findPageProduct(pageNum);
        request.setAttribute("pager", page);

        List<Product> productList = productService.findAllProducts();
        request.setAttribute("productList", productList);
        request.getRequestDispatcher("/productList.jsp").forward(request, response);
    }
}
