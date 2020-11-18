package repository;

import model.Order;
import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderRepository {
    private Connection connection = null;

    public OrderRepository() {
        this.connection = DBUtil.newConnection();
    }

    public List<Order> findAll() {
        List<Order> orderList = new ArrayList<Order>();
        String selectAll = "SELECT * FROM `order`";
        try {
            PreparedStatement pstmt = connection.prepareStatement(selectAll);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {

                int orderId = rs.getInt(1);
                int customerId = rs.getInt(2);
                int deliveryId = rs.getInt(3);
                int productId = rs.getInt(4);

                Order order = new Order(orderId, customerId, deliveryId, productId);
                orderList.add(order);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return orderList;
    }

    public int getLastOrderId() {
        int orderId = 0;
        String lastOrderId = "SELECT MAX(orderId) FROM `order`";

        try {
            PreparedStatement pstmt = connection.prepareStatement(lastOrderId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                orderId = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return orderId;
    }

    public List<Order> findById(int id) {
        List<Order> orderList = new ArrayList<Order>();
        String selectById = "SELECT * FROM `order` where orderId=?";

        try {
            PreparedStatement pstmt = connection.prepareStatement(selectById);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int customerId = rs.getInt(2);
                int deliveryId = rs.getInt(3);
                int productId = rs.getInt(4);

                Order order = new Order(id, customerId, deliveryId, productId);
                orderList.add(order);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return orderList;
    }

    public void deleteOrderById(int id) {
        String deleteById = "DELETE FROM `order` where orderId=?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(deleteById);
            pstmt.setInt(1, id);
            int deletedRecords = pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void saveNewOrder(Order order) {
        String newOrder = "INSERT INTO `order` VALUES(?,?,?,?)";
        try {
            PreparedStatement pstmt = connection.prepareStatement(newOrder);
            pstmt.setInt(1, order.getOrderId());
            pstmt.setInt(2, order.getCustomerId());
            pstmt.setInt(3, order.getDeliveryId());
            pstmt.setInt(4, order.getProductId());

            int newRecords = pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void updateProductByOrderId(int productId, Order order) {
        String updateQtyById = "UPDATE `order` SET productId=? where orderId=?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(updateQtyById);

            pstmt.setInt(1, productId);
            pstmt.setInt(2, order.getOrderId());

            int newRecords = pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void updateDeliveryByOrderId(int deliveryId, Order order) { //We add this when the customer choose delivery
        String updateQtyById = "UPDATE `order` SET deliveryId=? where orderId=?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(updateQtyById);

            pstmt.setInt(1, deliveryId);
            pstmt.setInt(2, order.getOrderId());

            int newRecords = pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
