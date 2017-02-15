package me.aki.estore.service;

import me.aki.estore.annotation.OpenTransaction;
import me.aki.estore.domain.Order;
import me.aki.estore.domain.OrderExt;
import me.aki.estore.exception.OrderException;

import java.util.List;

/**
 * Created by Aki on 2017/2/13.
 */
public interface OrderService  extends Service {
    @OpenTransaction
    void addOrder(Order order) throws OrderException;

    List<OrderExt> findAllOrders(int userId);

    @OpenTransaction
    void cancelOrder(String id) throws OrderException;

    Order findOrderById(String orderId);

    void changePayState(String r6_order, int i);
}
