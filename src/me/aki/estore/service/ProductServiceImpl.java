package me.aki.estore.service;

import me.aki.estore.dao.ProductDao;
import me.aki.estore.domain.Product;
import me.aki.estore.factory.BasicFactory;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

/**
 * Created by Aki on 2017/2/11.
 */
public class ProductServiceImpl implements ProductService {
    ProductDao dao = BasicFactory.getFactory().getDao(ProductDao.class);
    @Override
    public void addProduct(Product product) {
        product.setId(UUID.randomUUID().toString());
        try {
            dao.addProduct(product);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Product> findAllProducts() {
        List<Product> productList = null;
        try {
            productList = dao.findAllProducts();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return productList;
    }

    @Override
    public Product findProductById(String id) {
        Product product = null;
        try {
            product = dao.findProductById(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return product;
    }
}
