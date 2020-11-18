package repository;

import model.Customer;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepository {
    private Connection connection = null;

    public CustomerRepository(Connection connection) {
        this.connection = connection;
    }

    public List<Customer> findAll() {
        List<Customer> customerList = new ArrayList<Customer>();
        String selectAll = "SELECT * FROM Customer";
        {
            try {
                PreparedStatement pstmt = connection.prepareStatement(selectAll);
                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {

                    int customerID = rs.getInt(1);
                    String firstName = rs.getString(2);
                    String lastName = rs.getString(3);
                    String mail = rs.getString(4);
                    ;
                    String telephone = rs.getString(5);
                    int addressID = rs.getInt(6);
                    int accountID = rs.getInt(7);

                    Customer customer = new Customer(firstName, lastName, mail, telephone, addressID, accountID);
                    customerList.add(customer);
                }
                rs.close();
                pstmt.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return customerList;
    }

    public Customer findById(int id) {
        Customer customer = null;
        String selectById = "SELECT * FROM Customer where customerId=?";

        try {
            PreparedStatement pstmt = connection.prepareStatement(selectById);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {

                int customerID = rs.getInt(1);
                String firstName = rs.getString(2);
                String lastName = rs.getString(3);
                String mail = rs.getString(4);
                String telephone = rs.getString(5);
                int addressID = rs.getInt(6);
                int accountID = rs.getInt(7);

                customer = new Customer(firstName, lastName, mail, telephone, addressID, accountID);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return customer;
    }

    public void deleteCustomerById(int id) {
        String deleteById = "DELETE FROM Customer where customerId=?";
        {
            try {
                PreparedStatement pstmt = connection.prepareStatement(deleteById);
                pstmt.setInt(1, id);
                int deletedRecords = pstmt.executeUpdate();
                pstmt.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public void saveNewCustomer(Customer customer) {
        String newCustomer = "INSERT INTO Customer VALUES(null,?,?,?,?,?,?)";
        {
            try {
                PreparedStatement pstmt = connection.prepareStatement(newCustomer);
                pstmt.setString(1, customer.getFirstName());
                pstmt.setString(2, customer.getLastName());
                pstmt.setString(3, customer.getMail());
                pstmt.setString(4, customer.getTelephone());
                pstmt.setInt(5, customer.getAddressId());
                pstmt.setInt(6, customer.getAccountId());

                int newRecords = pstmt.executeUpdate();
                pstmt.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public void updateCustomerById(int Id, Customer customer) {
        String updateCustomer = "UPDATE Customer SET first_name=?, last_name=?,email=?,telephone=? where customerId=?";
        {
            try {
                PreparedStatement pstmt = connection.prepareStatement(updateCustomer);

                pstmt.setString(1, customer.getFirstName());
                pstmt.setString(2, customer.getLastName());
                pstmt.setString(3, customer.getMail());
                pstmt.setString(4, customer.getTelephone());
                pstmt.setInt(5, Id);

                int newRecords = pstmt.executeUpdate();
                pstmt.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public int getLastCustomerId() {
        int customerId = 0;
        String lastCustomerId = "SELECT MAX(customerId) FROM customer";

        try {
            PreparedStatement pstmt = connection.prepareStatement(lastCustomerId);
            ResultSet rs = pstmt.executeQuery(lastCustomerId);
            while (rs.next()) {
                customerId = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return customerId;
    }

    public Customer getLastAddedCustomer() {
        //SELECT * FROM customer WHERE customerId=(SELECT MAX(customerId) FROM customer);
        String getLastCustomer = "SELECT * FROM customer WHERE customerId=(SELECT MAX(customerId) FROM customer)";
        Customer customer = new Customer();
        try {
            PreparedStatement pstmt = connection.prepareStatement(getLastCustomer);
            ResultSet rs = pstmt.executeQuery(getLastCustomer);
            while (rs.next()) {

                int customerID = rs.getInt(1);
                String firstName = rs.getString(2);
                String lastName = rs.getString(3);
                String mail = rs.getString(4);
                String telephone = rs.getString(5);
                int addressID = rs.getInt(6);
                int accountId = rs.getInt(7);

                customer = new Customer(customerID, firstName, lastName, mail, telephone, addressID, accountId);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return customer;
    }

    public Customer findByAccountId(int accountId) {
        Customer customer = new Customer();
        String selectById = "SELECT * FROM Customer where accountId=?";

        try {
            PreparedStatement pstmt = connection.prepareStatement(selectById);
            pstmt.setInt(1, accountId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {

                int customerID = rs.getInt(1);
                String firstName = rs.getString(2);
                String lastName = rs.getString(3);
                String mail = rs.getString(4);
                String telephone = rs.getString(5);
                int addressID = rs.getInt(6);

                customer = new Customer(customerID, firstName, lastName, mail, telephone, addressID, accountId);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return customer;
    }
}