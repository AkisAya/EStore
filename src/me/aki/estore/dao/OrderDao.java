package me.aki.estore.dao;

import me.aki.estore.domain.Order;
import me.aki.estore.domain.OrderItem;

import java.sql.SQLException;

/**
 * Created by Aki on 2017/2/13.
 */
public interface OrderDao extends Dao {

    void addOrder(Order order) throws SQLException;

    void addOrderItem(OrderItem orderItem) throws SQLException;
}
