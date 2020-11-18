package repository;

import model.Login;
import org.junit.jupiter.api.*;
import util.DBUtil;

import java.sql.Connection;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestLoginRepository {

    public Login login1, login2, login3, login4;
    public LoginRepository loginRep;

    @BeforeAll
    public static void befAll() {
        Connection connection = null;
        connection = DBUtil.newConnection();
        LoginRepository loginRep = new LoginRepository(connection);
    }

    @BeforeEach
    public void start() {
        login1 = new Login("Leo", "Leo");
        login2 = new Login("Mikael", "Mikael");
        login3 = new Login("Joel", "Joel");
        login4 = new Login("guest", "guest");
    }

    @Test
    @Order(1)
    @DisplayName("FindAll")
    public void checkFindAll() {
        List<Login> loginList = loginRep.findAll();

        Assertions.assertEquals(login1.toString(), loginList.get(0).toString());
        Assertions.assertEquals(login2.toString(), loginList.get(1).toString());
    }

    @Test()
    @Order(2)
    @DisplayName("getLastAddedLogin")
    public void checkGetLastAddedLogin() {
        Login login = loginRep.getLastAddedLogin();
        Assertions.assertEquals(login4.toString(), login.toString());
    }

    @Test
    @Order(3)
    @DisplayName("FindById")
    public void checkFindById() {
        Login login = loginRep.findById(1);
        Assertions.assertEquals(login1.toString(), login.toString());
    }

    @Test
    @Order(4)
    @DisplayName("SaveNew")
    public void checkSaveNewLogin() {
        int size = loginRep.findAll().size();
        loginRep.saveNewLogin(login3);
        List<Login> loginList = loginRep.findAll();
        assert loginList.size() - size == 1;
        Assertions.assertEquals(login3.toString(), loginList.get(3).toString());
    }

    @Test()
    @Order(5)
    @DisplayName("GetLastLoginId")
    public void checkGetLastLoginId() {
        assert loginRep.getLastLoginId() == 4;
    }

    @Test
    @Order(6)
    @DisplayName("DeleteLoginById")
    public void checkDeleteLoginById() {
        int size = loginRep.findAll().size();
        int lastId = loginRep.getLastLoginId();
        loginRep.deleteLoginById(lastId);
        List<Login> loginList = loginRep.findAll();
        assert size - loginList.size() == 1;
        Assertions.assertEquals(login4.toString(), loginList.get(loginList.size() - 1).toString());
    }

    @Test
    @Order(7)
    @DisplayName("UpdateLoginById")
    public void checkUpdateLoginById() {
        loginRep.updateLoginById(2, login3);
        Login login = loginRep.findById(2);
        Assertions.assertEquals(login3.toString(), login.toString());
    }

    @Test
    @Order(8)
    @DisplayName("FindAccountId")
    public void checkFindAccountId() {
        Assertions.assertEquals(login1.toString(), loginRep.findAccountId("Leo", "Leo").toString());
    }

    @Test
    @Order(9)
    @DisplayName("Check if account is already in use")
    public void checkAccountInUse() {
        Assertions.assertTrue(loginRep.accountInUse("Leo"));
    }


}

