package me.aki.estore.dao;

import me.aki.estore.domain.Order;
import me.aki.estore.domain.OrderItem;
import me.aki.estore.domain.SaleRank;
import me.aki.estore.util.TransactionManager;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Aki on 2017/2/13.
 */
public class OrderDaoImpl implements OrderDao {
    @Override
    public void addOrder(Order order) throws SQLException {
        String sql = "insert into orders values(?, ?, ?, ?, null, ?)";
        QueryRunner queryRunner = new QueryRunner(TransactionManager.getDataSource());
        queryRunner.update(sql, order.getId(), order.getMoney(), order.getReceiverinfo(),
                order.getPaystate(), order.getUser_id());
    }

    @Override
    public void addOrderItem(OrderItem orderItem) throws SQLException {
        String sql = "insert into orderItem values(?, ?, ?)";
        QueryRunner queryRunner = new QueryRunner(TransactionManager.getDataSource());
        queryRunner.update(sql, orderItem.getOrder_id(), orderItem.getProduct_id(), orderItem.getQuantity());
    }

    @Override
    public List<Order> findOrderByUserId(int userId) throws SQLException {
        String sql = "SELECT * FROM orders where user_id = ?";
        QueryRunner queryRunner = new QueryRunner(TransactionManager.getDataSource());
        return queryRunner.query(sql, new BeanListHandler<Order>(Order.class), userId);
    }

    @Override
    public List<OrderItem> findOrderItemByOrderId(String id) throws SQLException {
        String sql = "SELECT * FROM orderitem where order_id = ?";
        QueryRunner queryRunner = new QueryRunner(TransactionManager.getDataSource());
        return queryRunner.query(sql, new BeanListHandler<OrderItem>(OrderItem.class), id);
    }

    @Override
    public void delOrderItem(String id) throws SQLException {
        String sql = "delete from orderitem where order_id = ?";
        QueryRunner queryRunner = new QueryRunner(TransactionManager.getDataSource());
        queryRunner.update(sql,id);
    }

    @Override
    public void delOrder(String id) throws SQLException {
        String sql = "delete from orders where id = ?";
        QueryRunner queryRunner = new QueryRunner(TransactionManager.getDataSource());
        queryRunner.update(sql,id);
    }

    @Override
    public Order findOrderByOrderId(String orderId) throws SQLException {
        String sql = "SELECT * FROM orders where id = ?";
        QueryRunner queryRunner = new QueryRunner(TransactionManager.getDataSource());
        return queryRunner.query(sql, new BeanHandler<Order>(Order.class), orderId);
    }

    @Override
    public void updatePayState(String orderId, int paystate) throws SQLException {
        String sql = "update orders set paystate = ? where id = ?";
        QueryRunner queryRunner = new QueryRunner(TransactionManager.getDataSource());
        queryRunner.update(sql, paystate, orderId);
    }

    @Override
    public List<SaleRank> findSalesOrderByNum() throws SQLException {
        String sql = "SELECT" +
                "    products.id productId," +
                "    products.name productName," +
                "    SUM(orderitem.quantity) saleNum" +
                " FROM" +
                "    orders," +
                "    products," +
                "    orderitem" +
                " WHERE" +
                "    orders.paystate = 1" +
                "        AND orders.id = orderitem.order_id" +
                "        AND orderitem.product_id = products.id" +
                " GROUP BY productId" +
                " ORDER BY saleNum DESC";
        System.out.println(sql);
        QueryRunner queryRunner = new QueryRunner(TransactionManager.getDataSource());
        return queryRunner.query(sql, new BeanListHandler<SaleRank>(SaleRank.class));
    }
}
