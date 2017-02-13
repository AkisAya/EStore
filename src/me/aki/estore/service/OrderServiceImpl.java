package me.aki.estore.service;

import me.aki.estore.dao.OrderDao;
import me.aki.estore.dao.ProductDao;
import me.aki.estore.domain.Order;
import me.aki.estore.domain.OrderItem;
import me.aki.estore.factory.BasicFactory;

import java.sql.SQLException;

/**
 * Created by Aki on 2017/2/13.
 */
public class OrderServiceImpl implements OrderService {
    private OrderDao orderDao = BasicFactory.getFactory().getDao(OrderDao.class);
    private ProductDao productDao = BasicFactory.getFactory().getDao(ProductDao.class);

    @Override
    public void addOrder(Order order) {

        try {
            // 1 添加订单
            orderDao.addOrder(order);
            // 2 添加订单项
            for (OrderItem orderItem : order.getList()) {
                orderDao.addOrderItem(orderItem);
                productDao.decreaseInventory(orderItem.getProduct_id(), orderItem.getQuantity());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
