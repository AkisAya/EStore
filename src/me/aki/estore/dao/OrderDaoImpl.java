package me.aki.estore.dao;

import me.aki.estore.domain.Order;
import me.aki.estore.domain.OrderItem;
import me.aki.estore.util.TransactionManager;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.SQLException;

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
}
