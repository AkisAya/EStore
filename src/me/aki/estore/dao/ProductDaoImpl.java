package me.aki.estore.dao;

import me.aki.estore.domain.Product;
import me.aki.estore.util.TransactionManager;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Aki on 2017/2/11.
 */
public class ProductDaoImpl implements ProductDao {
    @Override
    public void addProduct(Product product) throws SQLException {
        String sql = "insert into products values(?, ?, ?, ?, ?, ?, ?)";
        QueryRunner queryRunner = new QueryRunner(TransactionManager.getDataSource());
        queryRunner.update(sql, product.getId(), product.getName(), product.getPrice(), product.getCategory(),
                product.getPnum(), product.getImgurl(), product.getDescription());
    }

    @Override
    public List<Product> findAllProducts() throws SQLException {
        String sql = "select * from products";
        QueryRunner queryRunner = new QueryRunner(TransactionManager.getDataSource());
        return queryRunner.query(sql, new BeanListHandler<Product>(Product.class));
    }

    @Override
    public Product findProductById(String id) throws SQLException {
        String sql = "select * from products where id = ?";
        QueryRunner queryRunner = new QueryRunner(TransactionManager.getDataSource());
        return queryRunner.query(sql, new BeanHandler<Product>(Product.class), id);
    }

    @Override
    public int decreaseInventory(String productId, int quantity) throws SQLException {
        String sql = "update products set pnum = pnum - ? where id = ? and pnum - ? > 0";
        QueryRunner runner = new QueryRunner(TransactionManager.getDataSource());
        int count = runner.update(sql, quantity, productId, quantity);
        return count;
    }
}
