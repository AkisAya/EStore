package me.aki.estore.service;

import me.aki.estore.dao.ProductDao;
import me.aki.estore.domain.Constant;
import me.aki.estore.domain.Page;
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
        }
    }

    @Override
    public List<Product> findAllProducts() {
        List<Product> productList = null;
        try {
            productList = dao.findAllProducts();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }

    @Override
    public Product findProductById(String id) {
        Product product = null;
        try {
            product = dao.findProductById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    @Override
    public Page<Product> findPageProduct(int pageNum) {
        Page<Product> page = null;

        try {
            int totalRecord = dao.calculateProductNum();
            int totalPage = totalRecord % Constant.DEFAULT_PAGE_SIZE == 0 ?
                    totalRecord / Constant.DEFAULT_PAGE_SIZE : totalRecord / Constant.DEFAULT_PAGE_SIZE + 1;
            List<Product> productList = dao.finPageProducts(pageNum);
            page = new Page<>(totalRecord, totalPage, pageNum, productList);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return page;
    }
}
