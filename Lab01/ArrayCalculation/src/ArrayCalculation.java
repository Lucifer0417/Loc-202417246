import java.util.Arrays;
import java.util.Scanner;

public class ArrayCalculation {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Nhap so luong phan tu cua mang: ");
        int n = scanner.nextInt();
        
        // Khoi tao mang so thuc
        double[] myArr = new double[n];
        
        System.out.println("Nhap cac phan tu cua mang:");
        for (int i = 0; i < n; i++) {
            System.out.print("Phan tu thu " + (i + 1) + ": ");
            myArr[i] = scanner.nextDouble();
        }
        
        // 1. Sap xep mang tang dan bang thu vien co san
        Arrays.sort(myArr);
        
        // 2. Tinh tong
        double sum = 0;
        for (double num : myArr) {
            sum += num;
        }
        
        // 3. Tinh trung binh cong
        double average = sum / n;
        
        // In ket qua
        System.out.println("\n--- KET QUA ---");
        System.out.println("Mang sau khi sap xep: " + Arrays.toString(myArr));
        System.out.println("Tong cac phan tu: " + sum);
        System.out.println("Trung binh cong: " + average);
        
        scanner.close();
    }
}