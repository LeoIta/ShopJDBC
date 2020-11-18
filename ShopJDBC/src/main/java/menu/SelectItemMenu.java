package menu;

import model.Product;
import repository.ProductRepository;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

public class SelectItemMenu {
    private Connection connection = null;
    private Scanner scan = null;
    private CartMenu cart;
    private TransactionMenu transaction = new TransactionMenu();

    public SelectItemMenu(Connection connection, Scanner scan) {
        this.connection = connection;
        this.scan = scan;
    }

    //    ---------------------------------------------------------------------
//    check available item type
//    ---------------------------------------------------------------------
    public void selectItem() {
        System.out.println("\nChoose the Item you wish to buy: ");

        List<String> typeSelection = displayByType();
        String selectedType = getSelectedString(typeSelection);
        if (!selectedType.equals("")) {
            int choice;
            System.out.println("\nChoose if you want: " +
                    "\n0) exit shop " +
                    "\n1) go to the cart " +
                    "\n2) come back to previous menu " +
                    "\n3) look for available colors " +
                    "\n4) look for available size");
            choice = selectAllowedValue(4);
            cart = new CartMenu(connection,scan);
            switch (choice) {
                case 2:
                    selectItem();
                    break;
                case 3:
                    displayAvailableColor(selectedType);
                    break;
                case 4:
                    displayAvailableSize(selectedType);
                    break;
            }
        }
    }

    public List<String> displayByType() {
        ProductRepository prodRep = new ProductRepository(connection);
        List<String> listItem = prodRep.getAvailableType();

        if (listItem != null) {
            System.out.println("0) exit shop");
            System.out.println("1) go to the cart");
            for (int index = 0; index < listItem.size(); index++) {
                System.out.println(index + 2 + ") " + listItem.get(index).toLowerCase());
            }
        }
        return listItem;
    }
//    ---------------------------------------------------------------------
//    check available item color
//    ---------------------------------------------------------------------

    private void displayAvailableColor(String type) {

        List<String> colorSelection = displayColorByType(type);
        String selectedColor = getSelectedString(colorSelection);
        if (!selectedColor.equals("")) {
            int choice;
            System.out.println("\nChoose if you want: " +
                    "\n0) exit shop " +
                    "\n1) go to the cart " +
                    "\n2) come back to previous menu " +
                    "\n3) look for available sizes");
            choice = selectAllowedValue(3);
            cart = new CartMenu(connection,scan);
            switch (choice) {
                case 2:
                    displayAvailableColor(type);
                    break;
                case 3:
                    displayAvailableSize(type, selectedColor);
                    break;
            }
        }
    }

    private Product displayAvailableColor(String type, String size) {
        System.out.println("\nChoose the color you wish to buy: ");
        List<String> colorSelection = displayColorBySize(type, size);
        String selectedColor = getSelectedString(colorSelection);
        Product product = new Product();
        if (!selectedColor.equals("")) {
            int choice;
            System.out.println("\nChoose if you want: " +
                    "\n0) exit shop " +
                    "\n1) go to the cart " +
                    "\n2) add article to the cart");
            choice = selectAllowedValue(2);
            ProductRepository prodRep = new ProductRepository(connection);
            product = prodRep.getProduct(type, selectedColor, size);
            cart = new CartMenu(connection,scan);
            switch (choice) {
                case 2:
                    cart.addToCart(product);
                    break;
            }}
            return product;
    }

    private List<String> displayColorBySize(String type, String size) {
        ProductRepository prodRep = new ProductRepository(connection);
        List<String> listColor = prodRep.getAvailableColorBySize(size, type);

        if (listColor != null) {
            System.out.println("0) exit shop");
            System.out.println("1) go to the cart");
            for (int index = 0; index < listColor.size(); index++) {
                System.out.println(index + 2 + ") " + listColor.get(index).toLowerCase());
            }
        }
        return listColor;
    }

    private List<String> displayColorByType(String type) {
        ProductRepository prodRep = new ProductRepository(connection);
        List<String> listColor = prodRep.getAvailableColorByType(type);

        if (listColor != null) {
            System.out.println("0) exit shop");
            System.out.println("1) go to the cart");
            for (int index = 0; index < listColor.size(); index++) {
                System.out.println(index + 2 + ") " + listColor.get(index).toLowerCase());
            }
        }
        return listColor;
    }

//    ---------------------------------------------------------------------
//    check available item size
//    ---------------------------------------------------------------------

    private void displayAvailableSize(String type) {
        System.out.println("\nChoose the size you wish to buy: ");
        List<String> sizeSelection = displaySizeByType(type);
        String selectedSize = getSelectedString(sizeSelection);
        if (!selectedSize.equals("")) {
            int choice;
            System.out.println("\nChoose if you want: " +
                    "\n0) cancel order and end session " +
                    "\n1) go to the cart " +
                    "\n2) come back to previous menu " +
                    "\n3) look for available colors");
            choice = selectAllowedValue(3);
            cart = new CartMenu(connection,scan);
            switch (choice) {
//                case 0:
//                    transaction.cancelTransaction();
//                    break;
//                case 1:
//                    cart.goToCart(scan);
//                    break;
                case 2:
                    displayAvailableSize(type);
                    break;
                case 3:
                    displayAvailableColor(type, selectedSize);
                    break;
            }
        }
    }

    private Product displayAvailableSize(String type, String color) {
        List<String> sizeSelection = displaySizeByColor(type, color);
        String selectedSize = getSelectedString(sizeSelection);
        Product product = null;
        if (!selectedSize.equals("")) {

            int choice;
            ProductRepository prodRep = new ProductRepository(connection);
            prodRep.getProduct(type, color, selectedSize);

            System.out.println("\nChoose if you want: " +
                    "\n0) exit shop " +
                    "\n1) go to the cart " +
                    "\n2) go back and change size" +
                    "\n3) add article to the cart");
            choice = selectAllowedValue(3);

            switch (choice) {
                case 2:
                    displayAvailableSize(type, color);
                    break;
                case 3:
                    cart.addToCart(product);
                    break;
            }
        }
        return product;
    }

    private List<String> displaySizeByColor(String type, String color) {
        ProductRepository prodRep = new ProductRepository(connection);
        List<String> listSize = prodRep.getAvailableSizeByColor(type, color);

        if (listSize != null) {
            System.out.println("0) exit shop" +
                    "\n1) go to the cart ");

            for (int index = 0; index < listSize.size(); index++) {
                System.out.println(index + 2 + ") " + listSize.get(index));
            }
        }
        return listSize;
    }

    private List<String> displaySizeByType(String type) {
        ProductRepository prodRep = new ProductRepository(connection);
        List<String> listSize = prodRep.getAvailableSizeByType(type);

        if (listSize != null) {
            System.out.println("0) exit shop");
            System.out.println("1) go to the cart");
            for (int index = 0; index < listSize.size(); index++) {
                System.out.println(index + 2 + ") " + listSize.get(index));
            }
        }
        return listSize;
    }
//    ---------------------------------------------------------------------

    public int selectAllowedValue(int numberItem) {
        int choice = -1;
        boolean isInt = false;
        if (scan.hasNextInt()) {
            choice = scan.nextInt();
            isInt = true;
        }
        boolean wrongChoice = !((choice >= 0) && (choice <= numberItem));
        while (wrongChoice) {
            if (isInt) {
                System.out.println("Please select one of the available options");
            }
            if (scan.hasNextInt()) {
                choice = scan.nextInt();
                wrongChoice = !(((choice >= 0) && (choice <= numberItem)));
                isInt = true;
            } else {
                scan.next();
                System.out.println("Please select one of the available options");
                isInt = false;
            }
        }
        if (choice == 0) {
            transaction.cancelTransaction();
        }

        return choice;
    }

    public String getSelectedString(List<String> typeSelection) {
        int choice = selectAllowedValue(typeSelection.size() + 1);
        if (choice == 1) {
            CartMenu cart = new CartMenu(connection,scan);
            cart.goToCart(scan);
        }
        return choice < 2 ? "" : typeSelection.get(choice - 2).toLowerCase();
    }

}