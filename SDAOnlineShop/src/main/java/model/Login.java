package model;

public class Login {
    private int accountID;
    private String userName;
    private String password;

    public Login(){

    }

    public Login(int accountID, String userName, String password) {
        this.accountID = accountID;
        this.userName = userName;
        this.password = password;
    }
    public Login( String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public int getAccountID() {
        return this.accountID;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    @Override
    public String toString() {
        return "Login{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}