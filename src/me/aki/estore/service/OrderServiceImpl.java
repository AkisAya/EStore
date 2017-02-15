package me.aki.estore.service;

import me.aki.estore.dao.OrderDao;
import me.aki.estore.dao.ProductDao;
import me.aki.estore.domain.Order;
import me.aki.estore.domain.OrderExt;
import me.aki.estore.domain.OrderItem;
import me.aki.estore.domain.Product;
import me.aki.estore.exception.OrderException;
import me.aki.estore.factory.BasicFactory;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                // 这里因为商品减少，所以传入一个负的参数
                int count = productDao.changeInventory(orderItem.getProduct_id(), -orderItem.getQuantity());
                if (count <= 0) {
                    throw new OrderException("库存不足");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new OrderException(e);
        }
    }

    @Override
    public List<OrderExt> findAllOrders(int userId) {
        try {
            // 根据用户id查询对应的订单
            List<Order> orderList = orderDao.findOrderByUserId(userId);
            List<OrderExt> orderExtList = new ArrayList<>();
            for (Order order : orderList) {
                OrderExt orderExt = new OrderExt();
                BeanUtils.copyProperties(orderExt, order);

                // 根据order的订单编号查找到对应的OrderItem
                List<OrderItem> orderItemList = orderDao.findOrderItemByOrderId(order.getId());
                Map<Product, Integer> productMap = new HashMap<>();
                for (OrderItem orderItem : orderItemList) {
                    // 查找到对应的商品
                    Product product = productDao.findProductById(orderItem.getProduct_id());
                    int num = orderItem.getQuantity();
                    productMap.put(product, orderItem.getQuantity());
                }
                orderExt.setProductMap(productMap);
                orderExtList.add(orderExt);
            }
            return orderExtList;

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void cancelOrder(String id) throws OrderException {

        try {
            List<OrderItem> orderItemList = orderDao.findOrderItemByOrderId(id);
            for (OrderItem orderItem : orderItemList) {
                // 取消订单商品增加，所以传入一个正的参数
                productDao.changeInventory(orderItem.getProduct_id(), orderItem.getQuantity());
            }
            orderDao.delOrderItem(id);
            orderDao.delOrder(id);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new OrderException(e);
        }
    }

    @Override
    public Order findOrderById(String orderId) {
        Order order = null;
        try {
            order = orderDao.findOrderByOrderId(orderId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return order;
    }

    @Override
    public void changePayState(String r6_order, int i) {
        try {
            orderDao.updatePayState(r6_order, 1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
