package model;

public class Address {
    private int addressID;
    private String country;
    private String city;
    private String postalCode;
    private String street;
    public Address(){

    }

    public Address( String country, String city, String postalCode, String street) {
        this.country = country;
        this.city = city;
        this.postalCode = postalCode;
        this.street = street;
    }

    public Address(int addressID, String country, String city, String postalCode, String street) {
        this.addressID = addressID;
        this.country = country;
        this.city = city;
        this.postalCode = postalCode;
        this.street = street;
    }


    public String getStreet() {
        return this.street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getAddressID() {
        return this.addressID;
    }

    public void setAddressID(int addressId) {
        this.addressID = addressId;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return this.postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Override
    public String toString() {
        return "Address{" +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", street='" + street + '\'' +
                '}';
    }
}