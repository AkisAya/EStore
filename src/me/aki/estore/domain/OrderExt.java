package me.aki.estore.domain;

/**
 * Created by Aki on 2017/2/14.
 */

import java.util.Map;

/**
 * 订单的包装类,用于用户查询订单时包装信息
 */
public class OrderExt extends Order {

    private Map<Product, Integer> productMap;

    public Map<Product, Integer> getProductMap() {
        return productMap;
    }

    public void setProductMap(Map<Product, Integer> productMap) {
        this.productMap = productMap;
    }
}
