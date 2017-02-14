package me.aki.estore.service;

import me.aki.estore.dao.OrderDao;
import me.aki.estore.dao.ProductDao;
import me.aki.estore.domain.Order;
import me.aki.estore.domain.OrderItem;
import me.aki.estore.exception.OrderException;
import me.aki.estore.factory.BasicFactory;

import java.sql.SQLException;

/**
 * Created by Aki on 2017/2/13.
 */
public class OrderServiceImpl implements OrderService {
    private OrderDao orderDao = BasicFactory.getFactory().getDao(OrderDao.class);
    private ProductDao productDao = BasicFactory.getFactory().getDao(ProductDao.class);

    @Override
    public void addOrder(Order order) throws OrderException {

        try {
            // 1 添加订单
            orderDao.addOrder(order);
            // 2 添加订单项
            for (OrderItem orderItem : order.getList()) {
                orderDao.addOrderItem(orderItem);
                int count = productDao.decreaseInventory(orderItem.getProduct_id(), orderItem.getQuantity());
                if (count <= 0) {
                    throw new OrderException("库存不足");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
