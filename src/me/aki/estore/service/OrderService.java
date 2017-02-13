package me.aki.estore.service;

import me.aki.estore.annotation.OpenTransaction;
import me.aki.estore.domain.Order;

/**
 * Created by Aki on 2017/2/13.
 */
public interface OrderService  extends Service {
    @OpenTransaction
    void addOrder(Order order);
}