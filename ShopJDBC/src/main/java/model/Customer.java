package model;

public class Customer {
    private int customerId;
    private String firstName;
    private String lastName;
    private String mail;
    private String telephone;
    private int addressId;
    private int accountId;

    // Added - constructor for guess user.
    public Customer(){

    }

    public Customer(String firstName) {
        this.firstName = firstName;
        this.lastName = "guest";
        this.mail = "unknown";
        this.telephone = "unknown";
        this.accountId = 3;
    }

    public Customer(String firstName, String lastName, String mail, String telephone, int addressID, int accountId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.mail = mail;
        this.telephone = telephone;
        this.addressId = addressID;
        this.accountId = accountId;
    }
    public Customer(int customerId, String firstName, String lastName, String mail, String telephone, int addressID, int accountId) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mail = mail;
        this.telephone = telephone;
        this.addressId = addressID;
        this.accountId = accountId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMail() {
        return this.mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public int getAddressId() {
        return this.addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public int getAccountId() {
        return this.accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }



    @Override
    public String toString() {
        return "Customer{" +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", mail='" + mail + '\'' +
                ", telephone='" + telephone + '\'' +
                ", addressId=" + addressId +
                ", accountId=" + accountId +
                '}';
    }
}
