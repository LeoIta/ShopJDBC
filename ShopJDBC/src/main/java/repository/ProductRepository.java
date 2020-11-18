package repository;

import model.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ProductRepository {
    private Connection connection = null;

    public ProductRepository(Connection connection) {
        this.connection = connection;
    }

    public List<Product> findAll() {
        List<Product> productList = new ArrayList<Product>();
        String selectAll = "SELECT * FROM product";
        try {
            PreparedStatement pstmt = connection.prepareStatement(selectAll);
            ResultSet rs = pstmt.executeQuery(selectAll);

            while (rs.next()) {

                int productId = rs.getInt(1);
                String type = rs.getString(2);
                String color = rs.getString(3);
                String size = rs.getString(4);
                String productCode = rs.getString(5);
                int price = rs.getInt(6);

                Product product = new Product(productId, type, color, size, productCode, price);
                productList.add(product);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return productList;
    }

    public Product findById(int id) {
        Product product = null;
        String selectById = "SELECT * FROM product where productId=?";

        try {
            PreparedStatement pstmt = connection.prepareStatement(selectById);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {

                int productId = rs.getInt(1);
                String type = rs.getString(2);
                String color = rs.getString(3);
                String size = rs.getString(4);
                String productCode = rs.getString(5);
                int price = rs.getInt(6);

                product = new Product(productId, type, color, size, productCode, price);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return product;
    }

    public void deleteProductById(int id) {
        String deleteById = "DELETE FROM product where productId=?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(deleteById);
            pstmt.setInt(1, id);
            int deletedRecords = pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void saveNewProduct(Product product) {
        String newProduct = "INSERT INTO Product VALUES(null,?,?,?,?,?)";
        try {
            PreparedStatement pstmt = connection.prepareStatement(newProduct);
            pstmt.setString(1, product.getType());
            pstmt.setString(2, product.getColor());
            pstmt.setString(3, product.getSize());
            pstmt.setString(4, product.getProductCode());
            pstmt.setInt(5, product.getPrice());

            int newRecords = pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void updateProductById(int productId, Product product) {
        String updateProduct = "UPDATE Product SET color=?,size=?,productCode=?,price=? where productId=?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(updateProduct);

            pstmt.setString(1, product.getColor());
            pstmt.setString(2, product.getSize());
            pstmt.setString(3, product.getProductCode());
            pstmt.setInt(4, product.getPrice());
            pstmt.setInt(5, productId);

            int newRecords = pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public int getLastProductId() {
        int productId = 0;
        String lastProductId = "SELECT MAX(productId) FROM product";

        try {
            PreparedStatement pstmt = connection.prepareStatement(lastProductId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                productId = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return productId;
    }

    public List<String> getAvailableType() {
        List<String> availableType = new ArrayList<>();
        String itemType = "SELECT p.type, sum(s.available_quantity) " +
                "FROM product p " +
                "JOIN storage s " +
                "ON p.productCode = s.productCode " +
                "GROUP BY p.type " +
                "HAVING sum(s.available_quantity)>0 " +
                "ORDER BY p.type";
        try {
            PreparedStatement pstmt = connection.prepareStatement(itemType);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                availableType.add(rs.getString(1));
            }
            pstmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return availableType;
    }

    public List<String> getAvailableSizeByType(String type) {
        List<String> availableSize = new ArrayList<>();
        String itemType = "SELECT p.size, p.type, sum(s.available_quantity) " +
                "FROM product p " +
                "JOIN storage s " +
                "ON p.productCode = s.productCode " +
                "GROUP BY p.size, p.type " +
                "HAVING sum(s.available_quantity>0) and type = ? " +
                "ORDER BY p.size";
        try {
            PreparedStatement pstmt = connection.prepareStatement(itemType);
            pstmt.setString(1, type);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                availableSize.add(rs.getString(1));
            }
            pstmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return availableSize;
    }

    public List<String> getAvailableSizeByColor(String type,String color) {
        List<String> availableSize = new ArrayList<>();

        String itemType = "SELECT p.size, p.type, p.color, sum(s.available_quantity) " +
                "FROM product p " +
                "JOIN storage s " +
                "ON p.productCode = s.productCode " +
                "GROUP BY p.size, p.color, p.type " +
                "HAVING sum(s.available_quantity>0) and color = ? and type = ?" +
                "ORDER BY p.size";
        try {
            PreparedStatement pstmt = connection.prepareStatement(itemType);
            pstmt.setString(1, color);
            pstmt.setString(2, type);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                availableSize.add(rs.getString(1));
            }
            pstmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return availableSize;
    }

    public List<String> getAvailableColorByType(String type) {
        List<String> availableSize = new ArrayList<>();
        String itemType = "SELECT p.color, p.type, sum(s.available_quantity) " +
                "FROM product p " +
                "JOIN storage s " +
                "ON p.productCode = s.productCode " +
                "GROUP BY p.color, p.type " +
                "HAVING sum(s.available_quantity>0) and type = ?" +
                "ORDER BY p.color";
        try {
            PreparedStatement pstmt = connection.prepareStatement(itemType);
            pstmt.setString(1, type);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                availableSize.add(rs.getString(1));
            }
            pstmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return availableSize;
    }

    public List<String> getAvailableColorBySize(String size, String type) {
        List<String> availableSize = new ArrayList<>();
        String itemType = "SELECT p.color,p.type, p.size, sum(s.available_quantity) " +
                "FROM product p " +
                "JOIN storage s " +
                "ON p.productCode = s.productCode " +
                "GROUP BY p.color, p.size, p.type " +
                "HAVING sum(s.available_quantity>0) and size = ? and type = ? " +
                "ORDER BY p.color";
        try {
            PreparedStatement pstmt = connection.prepareStatement(itemType);
            pstmt.setString(1, size);
            pstmt.setString(2, type);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                availableSize.add(rs.getString(1));
            }
            pstmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return availableSize;
    }


    public Product getProduct(String type, String color, String size) {
        Product product = null;
        String findItem = "SELECT * FROM product where type=? and color=? and size=?";

        try {
            PreparedStatement pstmt = connection.prepareStatement(findItem);
            pstmt.setString(1, type);
            pstmt.setString(2, color);
            pstmt.setString(3, size);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {

                int productId = rs.getInt(1);
                String productCode = rs.getString(5);
                int price = rs.getInt(6);

                product = new Product(productId, type, color, size, productCode, price);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return product;
    }

}
