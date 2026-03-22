import javax.swing.JOptionPane;

public class CalculateTwoNumbers {
    public static void main(String[] args) {
        // Nhap so thu nhat
        String strNum1 = JOptionPane.showInputDialog(null,
                "Please input the first number: ", "Input",
                JOptionPane.INFORMATION_MESSAGE);
        double num1 = Double.parseDouble(strNum1);

        // Nhap so thu hai
        String strNum2 = JOptionPane.showInputDialog(null,
                "Please input the second number: ", "Input",
                JOptionPane.INFORMATION_MESSAGE);
        double num2 = Double.parseDouble(strNum2);

        // Cac phep tinh co ban
        double sum = num1 + num2;
        double difference = num1 - num2;
        double product = num1 * num2;

        String result = "Sum: " + sum + "\nDifference: " + difference + "\nProduct: " + product;

        // Kiem tra mau so khac 0 cho phep chia
        if (num2 != 0) {
            double quotient = num1 / num2;
            result += "\nQuotient: " + quotient;
        } else {
            result += "\nQuotient: Cannot divide by zero!";
        }

        // Hien thi ket qua
        JOptionPane.showMessageDialog(null, result, "Calculation Results", JOptionPane.INFORMATION_MESSAGE);
        
        System.exit(0);
    }
}