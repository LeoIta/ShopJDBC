package menu;

import model.Storage;

import java.util.Scanner;

public class TransactionMenu {

    private CartMenu cart;

    public void cancelTransaction() {
        System.out.println("Thank you for your visit! \nVisit us next time, " +
                "you might find the perfect item for you!");
        if (cart != null && cart.getCart().size()!=0){
            //for each product I have to restore the stock
            Storage stock = new Storage();
            stock.restoreStock(cart.getCart());
        }
        System.exit(0);
    }

    public void confirmTransaction(Scanner scan) {
        System.out.println("confirm");
    }
}
