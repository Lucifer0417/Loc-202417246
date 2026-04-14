package hust.soict.dsai.aims.cart;
import hust.soict.dsai.aims.disc.DigitalVideoDisc;

public class Cart {
    public static final int MAX_NUMBERS_ORDERED = 20; 
    private DigitalVideoDisc itemsOrdered[] = new DigitalVideoDisc[MAX_NUMBERS_ORDERED];
    private int qtyOrdered = 0; 

    // --- 1. THEM DIA (METHOD OVERLOADING) ---
    
    // Ham 1: Them 1 dia (Giong y het Lab 02)
    public void addDigitalVideoDisc(DigitalVideoDisc disc) {
        if (qtyOrdered == MAX_NUMBERS_ORDERED) { 
            System.out.println("The cart is almost full. Cannot add more.");
        } else {
            itemsOrdered[qtyOrdered] = disc;
            qtyOrdered++;
            System.out.println("The disc " + disc.getTitle() + " has been added.");
        }
    }

    // Ham 2: Them N-dia thong qua mot mang (Array) - [Phan 2.1 cua Lab]
    public void addDigitalVideoDisc(DigitalVideoDisc[] dvdList) {
        for (DigitalVideoDisc disc : dvdList) {
            if (qtyOrdered == MAX_NUMBERS_ORDERED) {
                System.out.println("The cart is almost full. Cannot add " + disc.getTitle());
                break; // Neu day gio thi ngung them
            } else {
                itemsOrdered[qtyOrdered] = disc;
                qtyOrdered++;
                System.out.println("The disc " + disc.getTitle() + " has been added.");
            }
        }
    }

    // Ham 3: Them cung luc 2 dia truyen vao - [Phan 2.2 cua Lab]
    public void addDigitalVideoDisc(DigitalVideoDisc dvd1, DigitalVideoDisc dvd2) {
        // Goi lai ham 1 de tranh viec code bi lap lai (Don't Repeat Yourself)
        addDigitalVideoDisc(dvd1);
        addDigitalVideoDisc(dvd2);
    }

    // --- 2. XOA DIA VA TINH TIEN ---

    public void removeDigitalVideoDisc(DigitalVideoDisc disc) {
        if (qtyOrdered == 0) {
            System.out.println("The cart is empty. Nothing to remove.");
            return;
        }
        for (int i = 0; i < qtyOrdered; i++) {
            if (itemsOrdered[i] == disc) {
                for (int j = i; j < qtyOrdered - 1; j++) {
                    itemsOrdered[j] = itemsOrdered[j + 1];
                }
                itemsOrdered[qtyOrdered - 1] = null; 
                qtyOrdered--;
                System.out.println("The disc " + disc.getTitle() + " has been removed.");
                return;
            }
        }
        System.out.println("The disc was not found in the cart.");
    }

    public float totalCost() {
        float total = 0;
        for (int i = 0; i < qtyOrdered; i++) { 
            total += itemsOrdered[i].getCost(); 
        }
        return total; 
    }
    
    // --- 3. IN GIO HANG VA TIM KIEM (LAB 03) ---
    
    // In danh sach gio hang cho dep
    public void print() {
        System.out.println("***********************CART***********************");
        System.out.println("Ordered Items:");
        for (int i = 0; i < qtyOrdered; i++) {
            // Goi ham toString() cua class DVD de in
            System.out.println((i + 1) + ". " + itemsOrdered[i].toString());
        }
        System.out.println("Total cost: " + totalCost() + " $");
        System.out.println("***************************************************");
    }
    
    // Tim kiem dia theo ID
    public void searchById(int id) {
        boolean found = false;
        for (int i = 0; i < qtyOrdered; i++) {
            if (itemsOrdered[i].getId() == id) {
                System.out.println("Found match DVD: " + itemsOrdered[i].toString());
                found = true;
                break; // Tim thay thi thoat vong lap luon
            }
        }
        if (!found) {
            System.out.println("No match found for ID: " + id);
        }
    }
    
    // Tim kiem dia theo Tieu de
    public void searchByTitle(String title) {
        boolean found = false;
        for (int i = 0; i < qtyOrdered; i++) {
            // Su dung ham isMatch() cua class DVD
            if (itemsOrdered[i].isMatch(title)) {
                System.out.println("Found match DVD: " + itemsOrdered[i].toString());
                found = true;
            }
        }
        if (!found) {
            System.out.println("No match found for Title: " + title);
        }
    }
}