package model;

public class Product {
    private int productID;
    private String type;
    private String color;
    private String size;
    private String productCode;
    private int price;

    public Product(int productID,String type, String color, String size, String productCode, int price) {
        this.type = type;
        this.productID = productID;
        this.color = color;
        this.size = size;
        this.productCode = productCode;
        this.price = price;
    }

    public Product(String type, String color, String size, String productCode, int price) {
        this.type = type;
        this.color = color;
        this.size = size;
        this.productCode = productCode;
        this.price = price;
    }

    public Product() {

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getProductID() {
        return this.productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getColor() {
        return this.color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return this.size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getProductCode() {
        return this.productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public int getPrice() {
        return this.price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "type='" + type + '\'' +
                ", color='" + color + '\'' +
                ", size='" + size + '\'' +
                ", productCode='" + productCode + '\'' +
                ", price=" + price +
                '}';
    }
}