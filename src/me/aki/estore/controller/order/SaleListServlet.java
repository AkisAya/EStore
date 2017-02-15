package me.aki.estore.controller.order;

import me.aki.estore.domain.SaleRank;
import me.aki.estore.factory.BasicFactory;
import me.aki.estore.service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

/**
 * Created by Aki on 2017/2/15.
 */
@WebServlet(name = "SaleListServlet", urlPatterns = "/servlet/SaleListServlet")
public class SaleListServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1 查询销售榜单
        OrderService orderService = BasicFactory.getFactory().getService(OrderService.class);
        List<SaleRank> saleRankList = orderService.findSaleRank();


        // 2 组织成csv格式数据
        StringBuffer sb = new StringBuffer("序号,商品编号,商品名称,商品数量\r\n");
        for (int i = 0; i < saleRankList.size(); i++) {
            SaleRank saleRank = saleRankList.get(i);
            sb.append(String.valueOf(i+1) + "," + saleRank.getProductId() + "," + saleRank.getProductName() + "," + saleRank.getSaleNum() + "\r\n");
        }
        String filename = "销售榜单_" + new Date().toString() + ".csv";

        response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(filename,"utf-8"));
        response.setContentType(this.getServletContext().getMimeType(filename));
        response.getWriter().write(sb.toString());

    }
}
