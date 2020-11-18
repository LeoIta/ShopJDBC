package repository;

import model.Storage;
import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class StorageRepository {
    private Connection connection = null;

    public StorageRepository(Connection connection) {
        this.connection = connection;
    }

    public List<Storage> findAll() {

        List<Storage> storageList = new ArrayList<Storage>();
        String selectAll = "SELECT * FROM storage";
        try {
            PreparedStatement pstmt = connection.prepareStatement(selectAll);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String userName = rs.getString(1);
                int quantity = rs.getInt(2);

                Storage storage = new Storage(userName, quantity);
                storageList.add(storage);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return storageList;
    }

    public int getQtyByCode(String code) {
        int qty = 0;
        String getQty = "SELECT available_quantity FROM storage where productCode=?";

        try {
            PreparedStatement pstmt = connection.prepareStatement(getQty);
            pstmt.setString(1, code);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                qty = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return qty;
    }

    public Storage findByCode(String code) {
        Storage storage = null;
        String selectByCode = "SELECT * FROM storage where productCode=?";

        try {
            PreparedStatement pstmt = connection.prepareStatement(selectByCode);
            pstmt.setString(1, code);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String productCode = rs.getString(1);
                int quantity = rs.getInt(2);

                storage = new Storage(productCode, quantity);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return storage;
    }

    public void deleteStorageByCode(String code) { // wont be needed
        String deleteByCode = "DELETE FROM storage where productCode=?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(deleteByCode);
            pstmt.setString(1, code);
            int deletedRecords = pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void saveNewStorage(Storage storage) { // wont be needed. we will populate the database ourselves
        String saveNewStorage = "INSERT INTO storage VALUES(?,?)";
        try {
            PreparedStatement pstmt = connection.prepareStatement(saveNewStorage);
            pstmt.setString(1, storage.getProductCode());
            pstmt.setInt(2, storage.getAvailable_quantity());
            System.out.println("we are here in storage");
            int newRecords = pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void updateStorageByCode(String code, int soldQty) {
        Storage storage = findByCode(code);
        int newAvailableQty = storage.getAvailable_quantity() - soldQty;
        String updateStorage = "UPDATE storage SET available_quantity=? where productCode=?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(updateStorage);

            pstmt.setInt(1, newAvailableQty);
            pstmt.setString(2, code);

            int newRecords = pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
