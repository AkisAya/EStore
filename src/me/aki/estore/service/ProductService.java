package me.aki.estore.service;

import me.aki.estore.domain.Page;
import me.aki.estore.domain.Product;

import java.util.List;

/**
 * Created by Aki on 2017/2/11.
 */
public interface ProductService extends Service {
    void addProduct(Product product);

    List<Product> findAllProducts();

    Product findProductById(String id);

    Page<Product> findPageProduct(int pageNum);
}
