package repository;

import model.Delivery;
import org.junit.jupiter.api.*;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestDeliveryRepository {

        public Delivery delivery1, delivery2, delivery3;
        public DeliveryRepository deliveryRep;
        @BeforeAll
        public static void befAll(){DeliveryRepository deliveryRep = new DeliveryRepository();}

        @BeforeEach
        public void start() {
            delivery1 = new Delivery("DHL",20);
            delivery2 = new Delivery("FEDEX",30);
            delivery3 = new Delivery("UPS",25);
        }

        @Test
        @Order(1)
        @DisplayName("FindAll")
        public void checkFindAll(){
            List<Delivery> deliveryList = deliveryRep.findAll();
            Assertions.assertEquals(delivery1.toString(),deliveryList.get(0).toString());
            Assertions.assertEquals(delivery2.toString(),deliveryList.get(1).toString());
        }

        @Test
        @Order(2)
        @DisplayName("FindById")
        public void checkFindById(){
            Delivery delivery = deliveryRep.findById(1);
            Assertions.assertEquals(delivery1.toString(),delivery.toString());
        }

        @Test()
        @Order(3)
        @DisplayName("GetLastDeliveryId")
        public void checkGetLastDeliveryId(){
            assert deliveryRep.getLastDeliveryId()==3;
        }


    @Test
        @Order(4)
        @DisplayName("DeleteDeliveryById")
        public void checkDeleteDeliveryById(){

            int size = deliveryRep.findAll().size();
            int lastId = deliveryRep.getLastDeliveryId();
        deliveryRep.deleteDeliveryById(lastId);
            List<Delivery> deliveryList = deliveryRep.findAll();
            assert size - deliveryList.size() == 1;
            Assertions.assertEquals(delivery2.toString(),deliveryList.get(deliveryList.size()-1).toString());
        }

        @Test
        @Order(5)
        @DisplayName("SaveNew")
        public void checkSaveNewDelivery(){
            int size = deliveryRep.findAll().size();
            deliveryRep.saveNewDelivery(delivery3);
            List<Delivery> deliveryList = deliveryRep.findAll();
            assert deliveryList.size() - size == 1;
            Assertions.assertEquals(delivery3.toString(),deliveryList.get(2).toString());
        }

        @Test
        @Order(6)
        @DisplayName("UpdateDeliveryById")
        public void checkUpdateDeliveryById(){
            deliveryRep.updateDeliveryById(2,delivery3);
            Delivery deliveryList = deliveryRep.findById(2);
            Assertions.assertEquals(delivery3.toString(),deliveryList.toString());
        }



}
