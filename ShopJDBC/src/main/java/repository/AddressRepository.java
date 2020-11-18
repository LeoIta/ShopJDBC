package repository;

import model.Address;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AddressRepository {
    private Connection connection = null;

    public AddressRepository(Connection connection) {
        this.connection = connection;
    }

    public List<Address> findAll() {
        List<Address> addressList = new ArrayList<Address>();
        String selectAll = "SELECT * FROM address";
        try {
            PreparedStatement pstmt = connection.prepareStatement(selectAll);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {

                int addressId = rs.getInt(1);
                String country = rs.getString(2);
                String city = rs.getString(3);
                String postalCode = rs.getString(4);
                String street = rs.getString(5);

                Address address = new Address(addressId, country, city, postalCode, street);
                addressList.add(address);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return addressList;
    }

    public int getLastAddressId() {
        int addressId = 0;
        String lastAddressId = "SELECT MAX(addressId) FROM address";

        try {
            PreparedStatement pstmt = connection.prepareStatement(lastAddressId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                addressId = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return addressId;
    }

    public Address getLastAddedAddress() {
        Address address = new Address();
        String getLastAddress = "SELECT * FROM address WHERE addressId=(SELECT MAX(addressId) FROM address)";

        try {
            PreparedStatement pstmt = connection.prepareStatement(getLastAddress);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {

                int addressId = rs.getInt(1);
                String country = rs.getString(2);
                String city = rs.getString(3);
                String postalCode = rs.getString(4);
                String street = rs.getString(5);
                address = new Address(addressId, country, city, postalCode, street);

            }
            rs.close();
            pstmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return address;
    }

    public Address findById(int id) {
        Address address = null;
        String selectById = "SELECT * FROM address where addressId=?";

        try {
            PreparedStatement pstmt = connection.prepareStatement(selectById);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {

                int addressId = rs.getInt(1);
                String country = rs.getString(2);
                String city = rs.getString(3);
                String postalCode = rs.getString(4);
                String street = rs.getString(5);

                address = new Address(addressId, country, city, postalCode, street);

            }
            rs.close();
            pstmt.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return address;
    }

    public void deleteAddressById(int id) {
        String deleteById = "DELETE FROM address where addressId=?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(deleteById);
            pstmt.setInt(1, id);
            int deletedRecords = pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void saveNewAddress(Address address) {
        String newAddress = "INSERT INTO Address VALUES(null,?,?,?,?)";
        try {
            PreparedStatement pstmt = connection.prepareStatement(newAddress);
            pstmt.setString(1, address.getCountry());
            pstmt.setString(2, address.getCity());
            pstmt.setString(3, address.getPostalCode());
            pstmt.setString(4, address.getStreet());

            int newRecords = pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void updateAddressById(int Id, Address address) {
        String updateAddress = "UPDATE Address SET Country=?, City=?,PostalCode=?, Street=? where addressId=?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(updateAddress);

            pstmt.setString(1, address.getCountry());
            pstmt.setString(2, address.getCity());
            pstmt.setString(3, address.getPostalCode());
            pstmt.setString(4, address.getStreet());
            pstmt.setInt(5, Id);

            int newRecords = pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
