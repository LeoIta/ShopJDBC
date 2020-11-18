package menu;

import model.*;
import repository.*;
import util.DBUtil;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

public class Menu {

    public static void welcomeToShopMessage() {
        System.out.println("Welcome to LeoMi Shop");
    }

    public static int displayBill(List<Product> productList) {

        System.out.println("_______________________________________________________________________");
        int nbOfItem = productList.size();
        int amount = 0;

        for (int i = 0; i < nbOfItem; i++) {
            String type = productList.get(i).getType();
            String color = productList.get(i).getColor();
            String size = productList.get(i).getSize();
            int price = productList.get(i).getPrice();
            amount += price;
            System.out.println((i + 1) + ") Type: " + type + "\t Color: " + color + "\t Size: " + size + "\t Price: " + price);
        }
        System.out.println("_______________________________________________________________________");

        return amount;
    }

    public static int displayBillWithDelivery(List<Product> productList, Delivery delivery,
                                              int addressId, Connection connection) {
        int amount = displayBill(productList) + delivery.getDeliveryCost();
        System.out.println((productList.size() + 1) + ") Delivery: Yes    \tCourier:  "
                + delivery.getName() + "\t Price: " + delivery.getDeliveryCost());
        AddressRepository addressRep = new AddressRepository(connection);
        Address address = addressRep.findById(addressId);

        System.out.println(" Address of delivery : " + address.getStreet() + "," +
                address.getPostalCode() + " " + address.getStreet() + "," + address.getCountry() + ".");
        System.out.println("_______________________________________________________________________");
        System.out.println("Total to pay : ______________________________________________________ "
                + amount);
        return amount;
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

//        open connection for the app

        Connection connection = null;
        connection = DBUtil.newConnection();

        // WELCOME & SIGN IN or LOGIN :  Welcome and registering user
        welcomeToShopMessage();
        CustomerTypeMenu selection = new CustomerTypeMenu(connection, scan);
        Customer customer = selection.selectType();

        //start shopping
        SelectItemMenu item = new SelectItemMenu(connection, scan);
        item.selectItem();

//
//        boolean boughtSomething = false;
//        boolean exitShop = false;
//
//        // DISPLAYING ITEM AND SHOPPING
//        List<Product> shoppingCart = new ArrayList<Product>();
//        int amountToPay = 0;
//        ProductRepository productRep = new ProductRepository(connection);
//        StorageRepository storageRep = new StorageRepository(connection);
//        List<Product> productList = productRep.findAll();
//        int availableChoice = productList.size();
//        boolean outOfStock = (availableChoice == 0);
//        int nbOfItemBought = 0;
//
//        if (outOfStock) {
//            System.out.println("Sorry we are out of stock please come next time . Thank you");
//        } else {
//            SelectItemMenu listItem = new SelectItemMenu(connection);
//            listItem.displayAvailableItem(scan);
//
//            while (!exitShop) {
//                int chosenItem = scan.nextInt();
//                exitShop = chosenItem == 0;
//
//                if (exitShop) {
//                    break;
//
//                } else {
//                    boolean wrongChoice = !((chosenItem >= 1) && (chosenItem <= availableChoice));
//                    boolean chosenItemOutOfStock = (!wrongChoice) && (storageRep.getQtyByCode(productList.get(chosenItem - 1).getProductCode()) == 0);
//
//                    while (wrongChoice || chosenItemOutOfStock) {
//                        if (wrongChoice) {
//                            System.out.println("wrong product selected try again by pressing the number before the item of your choice");
//                        } else if (chosenItemOutOfStock) {
//                            System.out.println("this product is out of stock choose an available product ");
//                        }
//                        listItem.displayAvailableItem(scan);
//                        ;
//                        chosenItem = scan.nextInt();
//                        wrongChoice = !((chosenItem >= 1) && (chosenItem <= availableChoice));
//                    }
//                    System.out.println("you successfully bought the item \n select another product  or Press (0) to exit and Pay : ");
//                    // we Add the product to the customer shopping cart and reduce the quantity from the storage
//                    shoppingCart.add(productList.get(chosenItem - 1));
//                    storageRep.updateStorageByCode(productList.get(chosenItem - 1).getProductCode(), 1);
//                    listItem.displayAvailableItem(scan);
//                    boughtSomething = true;
//                    nbOfItemBought++;
//
//
//                }
//            }
//        }


        // the customer Exist the shop we check if he bought something or not

//        if (shoppingCart.size() == 0) {
//            System.out.println("Visit us next time , you might find the perfect item for you");
//        } else { // shopping cart is not empty
//
//            System.out.println("would you like to your order to be delivered to you for a minimum additional fee ? \n YES : Press (1) \n No : Press (2)");
//
//            int custChoice = scan.nextInt();
//
//            while (!(custChoice == 1 || custChoice == 2)) {
//                System.out.println(" Wrong choice please select again : \n - If you wish delivery press (1)  \n - If you do not wish delivery press (2) ");
//                custChoice = scan.nextInt();
//            }

//            OrderRepository orderRep = new OrderRepository();
//            // we get/create the order id for this cust purchase
//            // we get last order that is in our data base and we +1 to get our new orderId for this customer purchase.
//            int orderId = orderRep.getLastOrderId() + 1;
//            DeliveryRepository deliveryRep = new DeliveryRepository();
//            // if the customer chose delivery we display delivery cpy and price
//            if (custChoice == 1) {
//                List<Delivery> deliveryList = deliveryRep.findAll();
//                int nbOfDeliveryCpy = deliveryList.size();
//                DeliveryMenu display = new DeliveryMenu();
//                display.displayDelivery(deliveryList);
//                int chosenDelivery = scan.nextInt();
//                while (!(chosenDelivery >= 1 && chosenDelivery <= nbOfDeliveryCpy)) {
//                    display.displayDelivery(deliveryList);
//                    chosenDelivery = scan.nextInt();
//                }
//                Delivery cstDeliveryMethod = deliveryList.get(chosenDelivery - 1);
//                int deliveryId = cstDeliveryMethod.getDeliveryId();
//
//                int addressId;
//
//                if (customer.getFirstName().equals("guest")) {
//                    System.out.println(" You are shopping as guest in order to get delivery provide us with your address ");
//                    System.out.println("1) Enter your Country: ");
//                    scan.nextLine();
//                    String country = scan.nextLine();
//
//                    System.out.println("2) Enter your City: ");
//                    String city = scan.nextLine();
//
//                    System.out.println("3) Enter your postal Code : ");
//                    String postalCode = scan.nextLine();
//
//                    System.out.println("4) Enter your Street and house number ex:  '15 longStreet' : ");
//                    String street = scan.nextLine();
//                    Address address = new Address(country, city, postalCode, street);
//                    AddressRepository addressRep = new AddressRepository(connection);
//                    addressRep.saveNewAddress(address);
//                    address = addressRep.getLastAddedAddress();
//                    addressId = address.getAddressID();
//                    customer.setAddressId(addressId);
//
//                } else {
//                    addressId = customer.getAddressId();
//                }
//
//
//                for (int i = 0; i < nbOfItemBought; i++) {
//                    orderRep.saveNewOrder(new Order(orderId, customer.getCustomerId(), deliveryId, shoppingCart.get(i).getProductID()));
//                }
//                int deliveryCost = cstDeliveryMethod.getDeliveryCost();
//                System.out.println(" Thank you for shopping with LEOMI SHOP (^_^) :\n BILL No: " + orderId + " to our dear: " + customer.getFirstName());
//
//                amountToPay = displayBillWithDelivery(shoppingCart, cstDeliveryMethod, addressId, connection);
//            } else {
//
//                System.out.println(" Thank you for shopping with LEOMI SHOP (^_^) :\n BILL No: " + orderId + " to our dear: " + customer.getFirstName());
//                System.out.println(customer.getCustomerId() + " this is the guess Id");
//                for (int i = 0; i < nbOfItemBought; i++) {
//                    orderRep.saveNewOrder(new Order(orderId, customer.getCustomerId(), shoppingCart.get(i).getProductID()));
//                }
//                amountToPay = displayBill(shoppingCart);
//                System.out.println(" Total to pay : ______________________________________________________ " + amountToPay);
//            }
//        }
    }
}
