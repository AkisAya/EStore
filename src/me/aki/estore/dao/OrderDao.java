package me.aki.estore.dao;

import me.aki.estore.domain.Order;
import me.aki.estore.domain.OrderItem;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Aki on 2017/2/13.
 */
public interface OrderDao extends Dao {

    void addOrder(Order order) throws SQLException;

    void addOrderItem(OrderItem orderItem) throws SQLException;

    List<Order> findOrderByUserId(int userId) throws SQLException;

    List<OrderItem> findOrderItemByOrderId(String id) throws SQLException;

    void delOrderItem(String id) throws SQLException;

    void delOrder(String id) throws SQLException;

    Order findOrderByOrderId(String orderId) throws SQLException;

    void updatePayState(String orderId, int paystate) throws SQLException;
}
