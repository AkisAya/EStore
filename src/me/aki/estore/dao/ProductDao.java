package me.aki.estore.dao;

import me.aki.estore.domain.Product;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Aki on 2017/2/11.
 */
public interface ProductDao extends Dao {

    void addProduct(Product product) throws SQLException;

    List<Product> findAllProducts() throws SQLException;

    Product findProductById(String id) throws SQLException;

    int changeInventory(String productId, int quantity) throws SQLException;
}
