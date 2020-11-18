package menu;

import model.Order;
import model.Product;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

public class CartMenu {

    public List<Product> cart = null;

    private Connection connection = null;
    private Scanner scan = null;
    private TransactionMenu transaction = new TransactionMenu();
    private SelectItemMenu itemMenu;
    private DeliveryMenu deliverMenu;

    public CartMenu(Connection connection, Scanner scan) {
        this.connection = connection;
        this.scan = scan;
    }

    public void goToCart(Scanner scan) {
        if (cart == null) {
            System.out.println("you cart is empty");
        } else {
            System.out.println("in your cart you have:");
            cart.stream().forEach(System.out::println);
            cartOption();
        }
        System.out.println("\nChoose what you want to do next : ");
        System.out.println("0) exit shop");
        System.out.println("1) proceed with purchase");
        System.out.println("2) buy another article");
        System.out.println("3) remove an article");
        System.out.println("4) empty the cart");

        int choice = selectAllowedValue(4);
        switch (choice) {
            case 0:
                transaction.cancelTransaction();
                break;
            case 1:
                deliverMenu.selectDelivery();
                break;
            case 2:
                deliverMenu.selectDelivery();
                break;
            case 3:
                deliverMenu.selectDelivery();
                break;
            case 4:
                deliverMenu.selectDelivery();
                break;
        }
    }

    public List<Product> getCart() {
        return cart;
    }

    private int selectAllowedValue(int numberItem) {
        SelectItemMenu item = new SelectItemMenu(connection,scan);
        return item.selectAllowedValue(numberItem);
    }

    //        String selectedColor = getSelectedString(colorSelection);
//        Product product = new Product();
//        if (!selectedColor.equals("")) {
//            int choice;
//            System.out.println("\nChoose if you want: " +
//                    "\n0) exit shop " +
//                    "\n1) go to the cart " +
//                    "\n2) add article to the cart");
//            choice = selectAllowedValue(2);
//            ProductRepository prodRep = new ProductRepository(connection);
//            product = prodRep.getProduct(type, selectedColor, size);
//            switch (choice) {
//                case 0:
//                    transaction.cancelTransaction();
//                    break;
//                case 1:
//                    cart.goToCart(scan);
//                    break;
//                case 2:
//                    cart.addToCart(product);
//                    break;
//                default:
//                    System.out.println("Please select one of the available options");
//                    break;
//            }}
//        return product;


    private void cartOption() {
        System.out.println("you are in the cartOption");
    }

    public void addToCart(Product product) {
        System.out.println("you add something to the cart");
    }

    public void removeFromCart(Product product) {
        System.out.println("you removed one item from your cart");
    }
}
