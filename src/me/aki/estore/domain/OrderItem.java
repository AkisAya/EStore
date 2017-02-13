package me.aki.estore.domain;

/**
 * Created by Aki on 2017/2/13.
 */
public class OrderItem {
    private String order_id;
    private String product_id;
    private int quantity; // 购买数量

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
