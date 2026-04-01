public class Cart {
    // 1. Khai bao cac thuoc tinh cua gio hang
    public static final int MAX_NUMBERS_ORDERED = 20; // Toi da 20 dia
    private DigitalVideoDisc itemsOrdered[] = new DigitalVideoDisc[MAX_NUMBERS_ORDERED]; // Mang chua dia
    private int qtyOrdered = 0; // Bien theo doi so luong dia hien co trong gio

    // 2. Ham them dia vao gio
    public void addDigitalVideoDisc(DigitalVideoDisc disc) {
        if (qtyOrdered == MAX_NUMBERS_ORDERED) { // Kiem tra gio da day chua
            System.out.println("The cart is almost full. Cannot add more.");
        } else {
            itemsOrdered[qtyOrdered] = disc;
            qtyOrdered++;
            System.out.println("The disc has been added.");
        }
    }

    // 3. Ham xoa dia khoi gio
    public void removeDigitalVideoDisc(DigitalVideoDisc disc) {
        if (qtyOrdered == 0) {
            System.out.println("The cart is empty. Nothing to remove.");
            return;
        }
        
        // Tim kiem dia trong gio va xoa
        for (int i = 0; i < qtyOrdered; i++) {
            if (itemsOrdered[i] == disc) {
                // Don cac phan tu phia sau len truoc de lap day khoang trong
                for (int j = i; j < qtyOrdered - 1; j++) {
                    itemsOrdered[j] = itemsOrdered[j + 1];
                }
                itemsOrdered[qtyOrdered - 1] = null; // Xoa phan tu cuoi cung
                qtyOrdered--;
                System.out.println("The disc has been removed.");
                return;
            }
        }
        System.out.println("The disc was not found in the cart.");
    }

    // 4. Ham tinh tong tien cua gio hang
    public float totalCost() {
        float total = 0;
        for (int i = 0; i < qtyOrdered; i++) { // Duyet qua mang
            total += itemsOrdered[i].getCost(); // Cong don gia tien
        }
        return total; // Tra ve tong tien
    }
}