package hust.soict.dsai.test.cart;
import hust.soict.dsai.aims.cart.Cart;
import hust.soict.dsai.aims.disc.DigitalVideoDisc;

public class CartTest {
    public static void main(String[] args) {
        // Tao mot gio hang moi
        Cart cart = new Cart();
        
        // Tao cac doi tuong DVD moi va them vao gio hang
        DigitalVideoDisc dvd1 = new DigitalVideoDisc("The Lion King", "Animation", "Roger Allers", 87, 19.95f);
        cart.addDigitalVideoDisc(dvd1);
        
        DigitalVideoDisc dvd2 = new DigitalVideoDisc("Star Wars", "Science Fiction", "George Lucas", 87, 24.95f);
        cart.addDigitalVideoDisc(dvd2);
        
        DigitalVideoDisc dvd3 = new DigitalVideoDisc("Aladin", "Animation", 18.99f);
        cart.addDigitalVideoDisc(dvd3);
        
        // Test chuc nang in gio hang
        cart.print();
        
        // Test chuc nang tim kiem theo ID
        System.out.println("\n--- TEST SEARCH BY ID ---");
        cart.searchById(1); // Tim dia co ID = 1
        cart.searchById(5); // Tim dia co ID = 5 (Khong ton tai)
        
        // Test chuc nang tim kiem theo Tieu de (Title)
        System.out.println("\n--- TEST SEARCH BY TITLE ---");
        cart.searchByTitle("Aladin"); // Tim dia ten Aladin
        cart.searchByTitle("Cinderella"); // Tim dia ten Cinderella (Khong ton tai)
    }
}