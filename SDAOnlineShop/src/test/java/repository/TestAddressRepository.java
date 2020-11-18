package repository;

import model.Address;
import org.junit.jupiter.api.*;
import util.DBUtil;

import java.sql.Connection;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestAddressRepository {

    public Address address1, address2, address3;
    public AddressRepository addressRep;

    @BeforeAll
    public static void befAll() {
        Connection connection = null;
        connection = DBUtil.newConnection();
        new AddressRepository(connection);
    }

    @BeforeEach
    public void start() {
        address1 = new Address(1, "Estonia", "Tallin", "10145", "Maakri 19/1");
        address2 = new Address(2, "Poland", "Gdynia", "81-451", "aleja Zwyciestwa 96/98");
        address3 = new Address(3, "Italy", "Rome", "00186", "Via del Corso 114/115");
    }

    @Test
    @Order(1)
    @DisplayName("FindAll")
    public void checkFindAll() {
        List<Address> addressList = addressRep.findAll();

        Assertions.assertEquals(address1.toString(), addressList.get(0).toString());
        Assertions.assertEquals(address2.toString(), addressList.get(1).toString());
    }

    @Test()
    @Order(2)
    @DisplayName("getLastAddedAddress")
    public void checkGetLastAddedAddress() {
        Address address = addressRep.getLastAddedAddress();
        Assertions.assertEquals(address3.toString(), address.toString());
    }

    @Test()
    @Order(3)
    @DisplayName("GetLastAddressId")
    public void checkGetLastAddressId() {
        assert addressRep.getLastAddressId() == 3;
    }

    @Test()
    @Order(4)
    @DisplayName("DeleteAddressById")
    public void checkDeleteAddressById() {
        List<Address> addressList = addressRep.findAll();
        int size = addressList.size();
        int lastId = addressRep.getLastAddressId();
        addressRep.deleteAddressById(lastId);
        addressList = addressRep.findAll();
        assert size - addressList.size() == 1;
        Assertions.assertEquals(address2.toString(), addressList.get(addressList.size() - 1).toString());
    }

    @Test()
    @Order(5)
    @DisplayName("SaveNew")
    public void checkSaveNewAddress() {
        List<Address> addressList = addressRep.findAll();
        addressList.size();
        addressRep.saveNewAddress(address3);
        List<Address> addressList1 = addressRep.findAll();
        assert addressList1.size() - addressList.size() == 1;
        Assertions.assertEquals(address3.toString(), addressList1.get(2).toString());
    }

    @Test()
    @Order(6)
    @DisplayName("FindById")
    public void checkFindById() {
        Address address = addressRep.findById(2);
        Assertions.assertEquals(address2.toString(), address.toString());
    }

    @Test()
    @Order(7)
    @DisplayName("UpdateAddressById")
    public void checkUpdateAddressById() {
        addressRep.updateAddressById(addressRep.getLastAddressId(), address2);
        Address address = addressRep.findById(addressRep.getLastAddressId());
        Assertions.assertEquals(address2.toString(), address.toString());
    }


}