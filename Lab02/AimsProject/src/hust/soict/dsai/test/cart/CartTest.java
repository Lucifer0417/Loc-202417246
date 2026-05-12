package hust.soict.dsai.test.cart;

import hust.soict.dsai.aims.cart.Cart;
import hust.soict.dsai.aims.media.DigitalVideoDisc;

public class CartTest {
    public static void main(String[] args) {
        // Tao mot gio hang moi
        Cart cart = new Cart();
        
        // Tao cac doi tuong DVD moi va them vao gio hang
        DigitalVideoDisc dvd1 = new DigitalVideoDisc("The Lion King", "Animation", "Roger Allers", 87, 19.95f);
        cart.addMedia(dvd1); // Da doi ten ham
        
        DigitalVideoDisc dvd2 = new DigitalVideoDisc("Star Wars", "Science Fiction", "George Lucas", 87, 24.95f);
        cart.addMedia(dvd2); // Da doi ten ham
        
        DigitalVideoDisc dvd3 = new DigitalVideoDisc("Aladin", "Animation", 18.99f);
        cart.addMedia(dvd3); // Da doi ten ham
        
        // Test in danh sach gio hang
        cart.print();
        
        // Test tim kiem
        System.out.println("\n--- Tim kiem theo ID ---");
        cart.searchById(1);
        
        System.out.println("\n--- Tim kiem theo Tieu de ---");
        cart.searchByTitle("Aladin");
    }
}