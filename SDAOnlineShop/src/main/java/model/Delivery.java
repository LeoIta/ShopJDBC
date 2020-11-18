package model;

import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Delivery {
    private int deliveryId;
    private String name;
    private int deliveryCost;

    public Delivery(){

    }

    public Delivery(String name, int deliveryCost) {
        this.name = name;
        this.deliveryCost = deliveryCost;
    }
    public Delivery(int deliveryId, String name, int deliveryCost) {
        this.deliveryId = deliveryId;
        this.name = name;
        this.deliveryCost = deliveryCost;
    }

    public int getDeliveryId() {
        return this.deliveryId;
    }

    public void setDeliveryId(int deliveryId) {
        this.deliveryId = deliveryId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDeliveryCost() {
        return this.deliveryCost;
    }

    public void setDeliveryCost(int deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

    @Override
    public String toString() {
        return "Delivery{" +
                "name='" + name + '\'' +
                ", deliveryCost=" + deliveryCost +
                '}';
    }
}
