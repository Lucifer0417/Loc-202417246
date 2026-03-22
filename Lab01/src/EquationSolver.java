import javax.swing.JOptionPane;

public class EquationSolver {

    public static void main(String[] args) {
        // Tao menu lua chon
        String menu = "CHON LOAI PHUONG TRINH:\n"
                    + "1. Phuong trinh bac 1 (ax + b = 0)\n"
                    + "2. He phuong trinh bac 1 (2 an)\n"
                    + "3. Phuong trinh bac 2 (ax^2 + bx + c = 0)";
        
        String choiceStr = JOptionPane.showInputDialog(null, menu, "Menu", JOptionPane.QUESTION_MESSAGE);
        
        // Xu ly neu nguoi dung bam Cancel
        if (choiceStr == null || choiceStr.isEmpty()) {
            System.exit(0);
        }
        
        int choice = Integer.parseInt(choiceStr);
        
        if (choice == 1) {
            // --- GIAI PHUONG TRINH BAC 1 ---
            double a = Double.parseDouble(JOptionPane.showInputDialog("Nhap a:"));
            double b = Double.parseDouble(JOptionPane.showInputDialog("Nhap b:"));
            
            if (a == 0) {
                if (b == 0) {
                    JOptionPane.showMessageDialog(null, "Phuong trinh vo so nghiem.");
                } else {
                    JOptionPane.showMessageDialog(null, "Phuong trinh vo nghiem.");
                }
            } else {
                double x = -b / a;
                JOptionPane.showMessageDialog(null, "Nghiem cua phuong trinh la: x = " + x);
            }
            
        } else if (choice == 2) {
            // --- GIAI HE PHUONG TRINH BAC 1 ---
            double a11 = Double.parseDouble(JOptionPane.showInputDialog("Nhap a11:"));
            double a12 = Double.parseDouble(JOptionPane.showInputDialog("Nhap a12:"));
            double b1 = Double.parseDouble(JOptionPane.showInputDialog("Nhap b1:"));
            
            double a21 = Double.parseDouble(JOptionPane.showInputDialog("Nhap a21:"));
            double a22 = Double.parseDouble(JOptionPane.showInputDialog("Nhap a22:"));
            double b2 = Double.parseDouble(JOptionPane.showInputDialog("Nhap b2:"));
            
            // Tinh cac dinh thuc
            double D = a11 * a22 - a21 * a12;
            double D1 = b1 * a22 - b2 * a12;
            double D2 = a11 * b2 - a21 * b1;
            
            if (D != 0) {
                double x1 = D1 / D;
                double x2 = D2 / D;
                JOptionPane.showMessageDialog(null, "He phuong trinh co nghiem:\nx1 = " + x1 + "\nx2 = " + x2);
            } else {
                if (D1 == 0 && D2 == 0) {
                    JOptionPane.showMessageDialog(null, "He phuong trinh vo so nghiem.");
                } else {
                    JOptionPane.showMessageDialog(null, "He phuong trinh vo nghiem.");
                }
            }
            
        } else if (choice == 3) {
            // --- GIAI PHUONG TRINH BAC 2 ---
            double a = Double.parseDouble(JOptionPane.showInputDialog("Nhap a:"));
            double b = Double.parseDouble(JOptionPane.showInputDialog("Nhap b:"));
            double c = Double.parseDouble(JOptionPane.showInputDialog("Nhap c:"));
            
            if (a == 0) {
                // Ve lai bai toan phuong trinh bac 1: bx + c = 0
                if (b == 0) {
                    if (c == 0) JOptionPane.showMessageDialog(null, "Phuong trinh vo so nghiem.");
                    else JOptionPane.showMessageDialog(null, "Phuong trinh vo nghiem.");
                } else {
                    double x = -c / b;
                    JOptionPane.showMessageDialog(null, "Phuong trinh co 1 nghiem: x = " + x);
                }
            } else {
                // Tinh Delta
                double delta = b * b - 4 * a * c;
                
                if (delta > 0) {
                    double x1 = (-b + Math.sqrt(delta)) / (2 * a);
                    double x2 = (-b - Math.sqrt(delta)) / (2 * a);
                    JOptionPane.showMessageDialog(null, "Phuong trinh co 2 nghiem phan biet:\nx1 = " + x1 + "\nx2 = " + x2);
                } else if (delta == 0) {
                    double x = -b / (2 * a);
                    JOptionPane.showMessageDialog(null, "Phuong trinh co nghiem kep: x = " + x);
                } else {
                    JOptionPane.showMessageDialog(null, "Phuong trinh vo nghiem.");
                }
            }
            
        } else {
            JOptionPane.showMessageDialog(null, "Lua chon khong hop le!");
        }
        
        System.exit(0);
    }
}