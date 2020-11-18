package repository;
import model.Login;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class LoginRepository {

    private Connection connection = null;

    public LoginRepository(Connection connection) {
        this.connection = connection;
    }

    public List<Login> findAll() {
        List<Login> loginList = new ArrayList<Login>();
        String selectAll = "SELECT * FROM login";
        try {
            PreparedStatement pstmt = connection.prepareStatement(selectAll);
            ResultSet rs = pstmt.executeQuery(selectAll);

            while(rs.next()) {

                int accountId = rs.getInt(1);
                String userName = rs.getString(2);
                String password = rs.getString(3);

                Login login = new Login(accountId, userName, password);
                loginList.add(login);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return loginList;
    }

    public Login findById(int id) {
        Login login = null;
        String selectById = "SELECT * FROM login where accountId=?";

        {try {
            PreparedStatement pstmt = connection.prepareStatement(selectById);
            //pstmt.setString(1,login.getAccountId());
            pstmt.setInt(1,id);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {

                int accountId = rs.getInt(1);
                String userName = rs.getString(2);
                String password = rs.getString(3);

                login = new Login(accountId, userName, password);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }}
        return login;
    }

    public int getLastLoginId(){
        int accountId = 0 ;
        String lastAccountId = "SELECT MAX(accountId) FROM login";

        try {
            PreparedStatement pstmt = connection.prepareStatement(lastAccountId);
            ResultSet rs = pstmt.executeQuery(lastAccountId);
            while(rs.next()) {
                accountId = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return accountId;
    }

    public Login getLastAddedLogin(){
        Login login = new Login();
        String getLastLogin = "SELECT * FROM login WHERE accountId=(SELECT MAX(accountId) FROM login)";

        try {
            PreparedStatement pstmt = connection.prepareStatement(getLastLogin);
            ResultSet rs = pstmt.executeQuery(getLastLogin);
            while(rs.next()) {

                int accountId = rs.getInt(1);
                String userName = rs.getString(2);
                String password = rs.getString(3);

                login = new Login(accountId, userName, password);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return login;
    }

    public Login findAccountId(String userName, String password) {
        Login login =null;
        String selectById = "SELECT accountId FROM login where userName=? and password=?";

        try {
            PreparedStatement pstmt = connection.prepareStatement(selectById);
            pstmt.setString(1,userName);
            pstmt.setString(2,password);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {

                int accountId = rs.getInt(1);

                login = new Login(accountId, userName, password);

           }

            rs.close();
            pstmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return login;
    }

    public void deleteLoginById(int id) {
        String deleteById = "DELETE FROM login where accountId=?";
        {try {
            PreparedStatement pstmt = connection.prepareStatement(deleteById);
            //pstmt.setString(1,login.getAccountId());
            pstmt.setString(1,Integer.toString(id));
            int deletedRecords = pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }}}

    public void saveNewLogin(Login login) {
        String newLogin = "INSERT INTO Login VALUES(null,?,?)";
        try {
            PreparedStatement pstmt = connection.prepareStatement(newLogin);
            pstmt.setString(1,login.getUserName());
            pstmt.setString(2,login.getPassword());

            int newRecords = pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void updateLoginById(int Id,Login login) {
        String updateLogin = "UPDATE Login SET userName=?,password=? where accountId=?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(updateLogin);

            pstmt.setString(1,login.getUserName());
            pstmt.setString(2,login.getPassword());
            pstmt.setInt(3,Id);

            int newRecords = pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public boolean accountInUse(String userName) {

        String countId = "select count(*) FROM login where username=?";
        int records=0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(countId);
            //pstmt.setString(1,login.getAccountId());
            pstmt.setString(1,userName);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {

            records = rs.getInt(1);

            }

            rs.close();
            pstmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return records!=0;
    }

}
