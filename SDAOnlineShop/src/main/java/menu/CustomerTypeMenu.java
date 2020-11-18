package menu;

import model.Address;
import model.Customer;
import model.Login;
import repository.AddressRepository;
import repository.CustomerRepository;
import repository.LoginRepository;

import java.sql.Connection;
import java.util.Scanner;


public class CustomerTypeMenu {

    private Connection connection = null;
    private Scanner scan = null;

    public CustomerTypeMenu(Connection connection, Scanner scan) {
        this.connection = connection;
        this.scan = scan;
    }

    public Customer selectType() {
        Customer customer = new Customer();
        int choice;
        System.out.println("Select how you want to shop :" +
                "\n1) New User ( sign in and shop)" +
                "\n2) Already have an account (login and shop)" +
                "\n3) Shop as a guest");
        choice = selectAllowedValue(3);
        switch (choice) {
            case 1:
                customer = newUser();
                System.out.println("Welcome dear " + customer.getFirstName() +
                        " \nEnjoy your shopping");
                break;
            case 2:
                customer = oldUser();
                System.out.println("Welcome back dear " + customer.getFirstName() +
                        " \nEnjoy your shopping");
                break;
            case 3:
                customer = guestUser();
                System.out.println("You are shopping as a guest \nEnjoy your shopping");
                break;
        }
        return customer;
    }

    public Customer newUser() {
        String firstName, lastName, email, telephone, country, city,
                postalCode, street, userName, password;
        System.out.println(" Create a username and a password for your account ");

        boolean invalidAccount = true;
        System.out.println("1) Enter your username: ");
        scan.nextLine();
        while (invalidAccount) {
            userName = scan.nextLine();
            LoginRepository loginRep = new LoginRepository(connection);
            invalidAccount = loginRep.accountInUse(userName);
            System.out.println("2) Enter your password: ");
            password = scan.nextLine();
            if (!invalidAccount) {
                Login login = new Login(userName, password);
                loginRep.saveNewLogin(login);
            } else {
                System.out.println("username already in use, please enter another username: ");
            }
        }
        System.out.println("In oder to complete your registration, please provide us with " +
                "the following information : first name , last name, email , telephone and address");
        System.out.println("1) enter your first name: ");
        firstName = scan.nextLine();

        System.out.println("2) Enter your last name: ");
        lastName = scan.nextLine();

        System.out.println("3) Enter your email: ");
        email = scan.nextLine();

        System.out.println("4) Enter your telephone: ");
        telephone = scan.nextLine();

        System.out.println(" Now lets add your delivery information: ");

        System.out.println("1) Enter your Country: ");
        country = scan.nextLine();

        System.out.println("2) Enter your City: ");
        city = scan.nextLine();

        System.out.println("3) Enter your postal Code: ");
        postalCode = scan.nextLine();

        System.out.println("4) Enter your Street and house number (ex: '15 longStreet') : ");
        street = scan.nextLine();

        AddressRepository addressRep = new AddressRepository(connection);
        addressRep.saveNewAddress(new Address(country, city, postalCode, street));
        LoginRepository loginRep = new LoginRepository(connection);
        int accountId = loginRep.getLastLoginId();
        int addressId = addressRep.getLastAddressId();

        // we save the new cst in the database to generate his customer id.
        CustomerRepository customerRep = new CustomerRepository(connection);
        Customer customer = new Customer(firstName, lastName, email,
                telephone, addressId, accountId);
        customerRep.saveNewCustomer(customer);

        return customer;
    }

    public Customer oldUser() {
        String userName, password;

        System.out.println("Please Login - " +
                "\n Enter: your username: ");
        scan.nextLine();
        userName = scan.nextLine();
        System.out.println("Enter your password");
        password = scan.nextLine();
        LoginRepository loginRep = new LoginRepository(connection);

        Login login = loginRep.findAccountId(userName, password);

        while (login == null) {

            System.out.println("there is no user with this username and password : " +
                    "please retype your username and password");
            System.out.println(" Enter: your username: ");
            userName = scan.nextLine();
            System.out.println("Enter your password");
            password = scan.nextLine();
            login = loginRep.findAccountId(userName, password);
        }
        CustomerRepository customerRep = new CustomerRepository(connection);
        return customerRep.findByAccountId(login.getAccountID());
    }

    public Customer guestUser() {
        Customer customer = new Customer("guest", "guest",
                "unknown", "unknown", 1, 3);
        CustomerRepository customerRep = new CustomerRepository(connection);
        customerRep.saveNewCustomer(customer);
        return customer;
    }

    private int selectAllowedValue(int numberItem) {
        int choice = -1;
        boolean isInt = false;
        if (scan.hasNextInt()) {
            choice = scan.nextInt();
            isInt = true;
        }
        boolean wrongChoice = !((choice > 0) && (choice <= numberItem));
        while (wrongChoice) {
            if (isInt) {
                System.out.println("Please select one of the available options");
            }
            if (scan.hasNextInt()) {
                choice = scan.nextInt();
                wrongChoice = !(((choice > 0) && (choice <= numberItem)));
                isInt = true;
            } else {
                scan.next();
                System.out.println("Please select one of the available options");
                isInt = false;
            }
        }
        return choice;
    }

}