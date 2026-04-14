package hust.soict.dsai.aims.store;
import hust.soict.dsai.aims.disc.DigitalVideoDisc;

public class Store {
    // Mang luu tru cac dia DVD co trong cua hang (gia su kho chua duoc toi da 50 dia) 
    private DigitalVideoDisc itemsInStore[] = new DigitalVideoDisc[50]; 
    private int qtyInStore = 0;

    // Ham them dia vao cua hang 
    public void addDVD(DigitalVideoDisc dvd) {
        if (qtyInStore < itemsInStore.length) {
            itemsInStore[qtyInStore] = dvd;
            qtyInStore++;
            System.out.println("The DVD '" + dvd.getTitle() + "' has been added to the store.");
        } else {
            System.out.println("The store is full. Cannot add '" + dvd.getTitle() + "'");
        }
    }

    // Ham xoa dia khoi cua hang 
    public void removeDVD(DigitalVideoDisc dvd) {
        if (qtyInStore == 0) {
            System.out.println("The store is empty. Nothing to remove.");
            return;
        }
        for (int i = 0; i < qtyInStore; i++) {
            if (itemsInStore[i] == dvd) {
                // Don mang len de lap cho trong
                for (int j = i; j < qtyInStore - 1; j++) {
                    itemsInStore[j] = itemsInStore[j + 1];
                }
                itemsInStore[qtyInStore - 1] = null;
                qtyInStore--;
                System.out.println("The DVD '" + dvd.getTitle() + "' has been removed from the store.");
                return;
            }
        }
        System.out.println("The DVD '" + dvd.getTitle() + "' was not found in the store.");
    }
}