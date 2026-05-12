package hust.soict.dsai.aims;

import java.util.Scanner;
import hust.soict.dsai.aims.cart.Cart;
import hust.soict.dsai.aims.media.DigitalVideoDisc;
import hust.soict.dsai.aims.store.Store;

public class Aims {
    // Khai bao Store va Cart la bien static de dung chung
    private static Store store = new Store();
    private static Cart cart = new Cart();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Them san vai mon do vao Store de test 
        DigitalVideoDisc dvd1 = new DigitalVideoDisc("The Lion King", "Animation", "Roger Allers", 87, 19.95f);
        DigitalVideoDisc dvd2 = new DigitalVideoDisc("Star Wars", "Science Fiction", "George Lucas", 87, 24.95f);
        DigitalVideoDisc dvd3 = new DigitalVideoDisc("Aladin", "Animation", 18.99f);
        store.addMedia(dvd1);
        store.addMedia(dvd2);
        store.addMedia(dvd3);
        
        // Them thu vao gio hang de test chuc nang xem gio
        cart.addMedia(dvd1);
        cart.addMedia(dvd2);

        int choice = -1;
        while (choice != 0) {
            showMenu();
            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // Xoa dau enter thua
                switch (choice) {
                    case 1:
                        viewStore();
                        break;
                    case 2:
                        updateStore();
                        break;
                    case 3:
                        seeCurrentCart(); // Goi den ham da duoc cap nhat
                        break;
                    case 0:
                        System.out.println("Cam on ban da su dung AIMS. Tam biet!");
                        break;
                    default:
                        System.out.println("Lua chon khong hop le. Vui long nhap so tu 0-3.");
                }
            } catch (Exception e) {
                System.out.println("Vui long nhap mot so nguyen!");
                scanner.nextLine(); // Xoa input bi loi
            }
        }
    }

    // 1. MENU CHINH
    public static void showMenu() {
        System.out.println("\nAIMS: ");
        System.out.println("--------------------------------");
        System.out.println("1. View store");
        System.out.println("2. Update store");
        System.out.println("3. See current cart");
        System.out.println("0. Exit");
        System.out.println("--------------------------------");
        System.out.println("Please choose a number: 0-1-2-3");
    }

    // 2. MENU CUA HANG
    public static void storeMenu() {
        System.out.println("\nOptions: ");
        System.out.println("--------------------------------");
        System.out.println("1. See a media's details");
        System.out.println("2. Add a media to cart");
        System.out.println("3. Play a media");
        System.out.println("4. See current cart");
        System.out.println("0. Back");
        System.out.println("--------------------------------");
        System.out.println("Please choose a number: 0-1-2-3-4");
    }

    // 3. MENU CHI TIET SAN PHAM
    public static void mediaDetailsMenu() {
        System.out.println("\nOptions: ");
        System.out.println("--------------------------------");
        System.out.println("1. Add to cart");
        System.out.println("2. Play");
        System.out.println("0. Back");
        System.out.println("--------------------------------");
        System.out.println("Please choose a number: 0-1-2");
    }

    // 4. MENU GIO HANG
    public static void cartMenu() {
        System.out.println("\nOptions: ");
        System.out.println("--------------------------------");
        System.out.println("1. Filter medias in cart");
        System.out.println("2. Sort medias in cart");
        System.out.println("3. Remove media from cart");
        System.out.println("4. Play a media");
        System.out.println("5. Place order");
        System.out.println("0. Back");
        System.out.println("--------------------------------");
        System.out.println("Please choose a number: 0-1-2-3-4-5");
    }

    // --- CAC HAM XU LY LOGIC DIEU HUONG ---
    
    public static void viewStore() {
        System.out.println("\n--- STORE INVENTORY ---");
        System.out.println("(Danh sach cac mon do dang co trong cua hang...)");
        
        int choice = -1;
        while (choice != 0) {
            storeMenu();
            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    System.out.println("Chuc nang: Xem chi tiet san pham (Tu phat trien them...)");
                    break;
                case 2:
                    System.out.println("Chuc nang: Them vao gio hang (Tu phat trien them...)");
                    break;
                case 3:
                    System.out.println("Chuc nang: Phat Media (Tu phat trien them...)");
                    break;
                case 4:
                    seeCurrentCart();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Lua chon khong hop le!");
            }
        }
    }

    public static void updateStore() {
        System.out.println("\n--- UPDATE STORE ---");
        System.out.println("Chuc nang: Them/Xoa san pham khoi Store (Tu phat trien them...)");
    }

    // HAM NAY DA DUOC CAP NHAT FULL TINH NANG SORT VA PLACE ORDER
    public static void seeCurrentCart() {
        cart.print();
        int choice = -1;
        while (choice != 0) {
            cartMenu();
            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    System.out.println("Chuc nang: Loc san pham (Tu phat trien them...)");
                    break;
                case 2:
                    System.out.println("Chon kieu sap xep:");
                    System.out.println("1. Theo tieu de (Title -> Cost)");
                    System.out.println("2. Theo gia (Cost -> Title)");
                    int sortChoice = scanner.nextInt();
                    scanner.nextLine();
                    if (sortChoice == 1) {
                        cart.sortByTitleCost();
                        cart.print(); 
                    } else if (sortChoice == 2) {
                        cart.sortByCostTitle();
                        cart.print();
                    } else {
                        System.out.println("Lua chon khong hop le!");
                    }
                    break;
                case 3:
                    System.out.println("Chuc nang: Xoa khoi gio hang (Tu phat trien them...)");
                    break;
                case 4:
                    System.out.println("Chuc nang: Phat Media (Tu phat trien them...)");
                    break;
                case 5:
                    System.out.println("An order has been created successfully!");
                    // Dat hang thanh cong thi tao gio hang moi luon
                    cart = new Cart(); 
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Lua chon khong hop le!");
            }
        }
    }
}