package hust.soict.dsai.test.store;

import hust.soict.dsai.aims.disc.DigitalVideoDisc;
import hust.soict.dsai.aims.store.Store;

public class StoreTest {
    public static void main(String[] args) {
        // Tao ra mot cua hang moi
        Store store = new Store();
        
        // Nhap nguyen lieu (dia DVD)
        DigitalVideoDisc dvd1 = new DigitalVideoDisc("The Lion King", "Animation", "Roger Allers", 87, 19.95f);
        DigitalVideoDisc dvd2 = new DigitalVideoDisc("Star Wars", "Science Fiction", "George Lucas", 87, 24.95f);
        DigitalVideoDisc dvd3 = new DigitalVideoDisc("Aladin", "Animation", 18.99f);
        
        // Them dia vao kho
        store.addDVD(dvd1);
        store.addDVD(dvd2);
        store.addDVD(dvd3);
        
        System.out.println("-----------------------");
        
        // Thu xoa dia khoi kho
        store.removeDVD(dvd2); // Xoa Star Wars
        
        // Thu xoa lai lan nua xem he thong co bao loi khong
        store.removeDVD(dvd2); 
    }
}