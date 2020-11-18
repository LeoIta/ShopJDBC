package repository;

import model.Product;
import org.junit.jupiter.api.*;
import util.DBUtil;

import java.sql.Connection;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestProductRepository {

    public Product product1, product2, product3, product4, product5,
            product6, product7, product8, product9;
    public ProductRepository productRep;

    @BeforeAll
    public static void befAll() {
        Connection connection = null;
        connection = DBUtil.newConnection();
        ProductRepository productRep = new ProductRepository(connection);
    }

    @BeforeEach
    public void start() {
        product1 = new Product(1, "JEANS", "RED", "XS", "0JNS00XS00RD", 40);
        product2 = new Product(2, "SHIRT", "BLUE", "XL", "0SHT00XL00BE", 25);
        product3 = new Product(3, "T-SHIRT", "GREEN", "XS", "0TST00XS00GN", 15);
        product4 = new Product(4, "SKIRT", "BLACK", "XS", "0SKT00XS00BK", 40);
        product5 = new Product(5, "TROUSERS", "YELLOW", "XS", "0TRS00XS00YW", 45);
        product6 = new Product(6, "JACKET", "WHITE", "S", "0JKT00SS00WE", 60);
        product7 = new Product(7, "COAT", "RED", "XL", "0CAT00XL00RD", 100);
        product8 = new Product(8, "T-SHIRT", "GREEN", "XS", "0TST00XS00GN", 15);
        product9 = new Product(9, "T-SHIRT", "BLACK", "XS", "0TST00XS00BK", 40);

    }

    @Test()
    @Order(1)
    @DisplayName("FindAll")
    public void checkFindAll() {
        List<Product> productList = productRep.findAll();
        assert productList.size() == 7;
        Assertions.assertEquals(product1.toString(), productList.get(0).toString());
        Assertions.assertEquals(product2.toString(), productList.get(1).toString());
        Assertions.assertEquals(product3.toString(), productList.get(2).toString());
        Assertions.assertEquals(product4.toString(), productList.get(3).toString());
        Assertions.assertEquals(product5.toString(), productList.get(4).toString());
        Assertions.assertEquals(product6.toString(), productList.get(5).toString());
        Assertions.assertEquals(product7.toString(), productList.get(6).toString());

    }

    @Test()
    @Order(2)
    @DisplayName("SaveNew")
    public void checkSaveNewProduct() {
        int size = productRep.findAll().size();
        productRep.saveNewProduct(product8);
        List<Product> productList = productRep.findAll();
        assert productList.size() - size == 1;
        Assertions.assertEquals(product8.toString(), productList.get(7).toString());
    }

    @Test()
    @Order(3)
    @DisplayName("GetLastProductId")
    public void checkGetLastProductId() {
        assert productRep.getLastProductId() == 8;
    }

    @Test()
    @Order(4)
    @DisplayName("FindById")
    public void checkFindById() {
        Product product = productRep.findById(1);
        Assertions.assertEquals(product1.toString(), product.toString());
    }

    @Test()
    @Order(5)
    @DisplayName("UpdateProductById")
    public void checkUpdateProductById() {
        productRep.updateProductById(8, product9);
        Product product = productRep.findById(8);
        Assertions.assertEquals(product9.toString(), product.toString());
    }

    @Test()
    @Order(6)
    @DisplayName("DeleteProductById")
    public void checkDeleteProductById() {

        int size = productRep.findAll().size();
        int lastId = productRep.getLastProductId();
        productRep.deleteProductById(lastId);
        List<Product> productList = productRep.findAll();
        assert size - productList.size() == 1;
        Assertions.assertEquals(product7.toString(), productList.get(productList.size() - 1).toString());
    }


}
