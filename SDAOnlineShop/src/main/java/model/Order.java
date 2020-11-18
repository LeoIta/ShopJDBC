package model;

public class Order {
    private int orderId;
    private int customerId;
    private int deliveryId;
    private int productId;


    public Order(int orderId, int customerId, int deliveryId, int productId) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.deliveryId = deliveryId;
        this.productId = productId;
    }

    public Order(int orderId, int customerId, int productId) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.productId = productId;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getDeliveryId() {
        return deliveryId;
    }

    public int getProductId() {
        return productId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setDeliveryId(int deliveryId) {
        this.deliveryId = deliveryId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    @Override
    public String toString() {
        return "Order{" +
                "customerId=" + customerId +
                ", deliveryId=" + deliveryId +
                ", productId=" + productId +
                '}';
    }
}
