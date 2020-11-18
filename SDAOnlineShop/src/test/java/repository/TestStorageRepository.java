package repository;

import model.Storage;
import org.junit.jupiter.api.*;
import util.DBUtil;

import java.sql.Connection;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestStorageRepository {

    public Storage storage1, storage2, storage3;
    public StorageRepository storageRep;
    @BeforeAll
    public static void befAll() {
        Connection connection = null;
        connection = DBUtil.newConnection();
        StorageRepository storageRep= new StorageRepository(connection);
    }

    @BeforeEach
    public void start() {
        storage1 = new Storage("0CAT00LL00BE", 1);
        storage2 = new Storage("0CAT00LL00BK", 9);
        storage3 = new Storage("0CAT00LL00GN", 1);
    }

    @Test()
    @Order(1)
    @DisplayName("FindAll")
    public void checkFindAll() {
        List<Storage> storageList = storageRep.findAll();
        Assertions.assertTrue(storageList.size() == 210);
        Assertions.assertEquals(storage1.toString(), storageList.get(0).toString());
        Assertions.assertEquals(storage2.toString(), storageList.get(1).toString());
        (new Storage("0JNS00MM00RD", 3)).toString().equals(storageList.get(2).toString());
        (new Storage("0JNS00LL00RD", 9)).toString().equals(storageList.get(3).toString());
        (new Storage("0JNS00XL00RD", 0)).toString().equals(storageList.get(4).toString());
        (new Storage("0JNS00XS00GN", 10)).toString().equals(storageList.get(5).toString());
        (new Storage("0JNS00SS00GN", 5)).toString().equals(storageList.get(6).toString());
        (new Storage("0JNS00MM00GN", 11)).toString().equals(storageList.get(7).toString());
    }

    @Test()
    @Order(2)
    @DisplayName("findByCode")
    public void checkFindByCode() {
        Storage storage = storageRep.findByCode("0JNS00MM00GN");
        Assertions.assertEquals((new Storage("0JNS00MM00GN", 11)).toString(), storage.toString());
    }

    @Test()
    @Order(3)
    @DisplayName("getQtyByCode")
    public void checkGetQtyByCode() {
        assert storageRep.getQtyByCode("0JNS00MM00GN") == 11;
    }

    @Test()
    @Order(4)
    @DisplayName("UpdateStorageByCode")
    public void checkUpdateStorageByCode() {
        storageRep.updateStorageByCode("0JNS00MM00GN", 3);
        assert storageRep.getQtyByCode("0JNS00MM00GN") == 8;
    }

    @Test()
    @Order(5)
    @DisplayName("DeleteStorageByCode")
    public void checkDeleteStorageByCode() {
        List<Storage> storageList = storageRep.findAll();
        int size = storageList.size();
        storageRep.deleteStorageByCode("0CAT00MM00BE");
        storageList = storageRep.findAll();
        assert size - storageList.size() == 1;
        Assertions.assertNull(storageRep.findByCode("0CAT00MM00BE"));
    }

}
