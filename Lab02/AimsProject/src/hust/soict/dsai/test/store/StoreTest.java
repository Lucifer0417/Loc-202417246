package hust.soict.dsai.test.store;

import hust.soict.dsai.aims.media.DigitalVideoDisc;
import hust.soict.dsai.aims.store.Store;

public class StoreTest {
    public static void main(String[] args) {
        // Tao ra mot cua hang moi
        Store store = new Store();
        
        // Nhap nguyen lieu (dia DVD)
        DigitalVideoDisc dvd1 = new DigitalVideoDisc("The Lion King", "Animation", "Roger Allers", 87, 19.95f);
        DigitalVideoDisc dvd2 = new DigitalVideoDisc("Star Wars", "Science Fiction", "George Lucas", 87, 24.95f);
        DigitalVideoDisc dvd3 = new DigitalVideoDisc("Aladin", "Animation", 18.99f);
        
        // Them dia vao kho (Da doi thanh addMedia)
        store.addMedia(dvd1);
        store.addMedia(dvd2);
        store.addMedia(dvd3);
        
        System.out.println("-----------------------");
        
        // Thu xoa dia khoi kho (Da doi thanh removeMedia)
        store.removeMedia(dvd2); // Xoa Star Wars
        
        // Thu xoa lai lan nua xem he thong co bao loi khong
        store.removeMedia(dvd2); 
    }
}