package repository;

import model.Order;
import org.junit.jupiter.api.*;

import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestOrderRepository {

    public Order order1, order2, order3, order4, order5;
    public OrderRepository orderRep;

    @BeforeAll
    public static void befAll() {
        OrderRepository orderRep = new OrderRepository();
    }

    @BeforeEach
    public void start() {
        order1 = new Order(1, 1, 1, 1);
        order2 = new Order(2, 2, 2, 2);
        order3 = new Order(3, 2, 3, 2);
        order4 = new Order(2, 2, 1, 2);
        order5 = new Order(2, 2, 1, 1);
    }

    @Test
    @org.junit.jupiter.api.Order(1)
    @DisplayName("FindAll")
    public void checkFindAll() {
        List<Order> orderList = orderRep.findAll();

        Assertions.assertEquals(order1.toString(), orderList.get(0).toString());
        Assertions.assertEquals(order2.toString(), orderList.get(1).toString());
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    @DisplayName("FindById")
    public void checkFindById() {
        List<Order> orderList = orderRep.findById(1);
        Assertions.assertEquals(order1.toString(), orderList.get(0).toString());
    }

    @Test()
    @org.junit.jupiter.api.Order(3)
    @DisplayName("GetLastOrderId")
    public void checkGetLastOrderId() {
        assert orderRep.getLastOrderId() == 2;
    }

    @Test
    @org.junit.jupiter.api.Order(4)
    @DisplayName("SaveNew")
    public void checkSaveNewOrder() {
        int size = orderRep.findAll().size();
        orderRep.saveNewOrder(order3);
        List<Order> orderList1 = orderRep.findAll();
        assert orderList1.size() - size == 1;
        Assertions.assertEquals(order3.toString(), orderList1.get(2).toString());
    }

    @Test
    @org.junit.jupiter.api.Order(5)
    @DisplayName("DeleteOrderById")
    public void checkDeleteOrderById() {

        int size = orderRep.findAll().size();
        int lastId = orderRep.getLastOrderId();
        orderRep.deleteOrderById(lastId);
        List<Order> orderList = orderRep.findAll();
        assert size - orderList.size() == 1;
        Assertions.assertEquals(order2.toString(), orderList.get(orderList.size() - 1).toString());
    }

    @Test
    @org.junit.jupiter.api.Order(6)
    @DisplayName("UpdateDeliveryByOrderId")
    public void checkUpdateDeliveryByOrderId() {
        orderRep.updateDeliveryByOrderId(1, order2);
        List<Order> orderList = orderRep.findAll();
        Assertions.assertEquals(order4.toString(), orderList.get(1).toString());
    }

    @Test
    @org.junit.jupiter.api.Order(7)
    @DisplayName("UpdateProductByOrderId")
    public void checkUpdateProductByOrderId() {
        orderRep.updateProductByOrderId(1, order2);
        List<Order> orderList = orderRep.findAll();
        Assertions.assertEquals(order5.toString(), orderList.get(1).toString());
    }

}

