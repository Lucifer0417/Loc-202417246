import java.util.Scanner;

public class MatrixAdd {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Nhap so hang cua ma tran: ");
        int rows = scanner.nextInt();
        System.out.print("Nhap so cot cua ma tran: ");
        int cols = scanner.nextInt();
        
        // Khoi tao 3 ma tran
        int[][] matrixA = new int[rows][cols];
        int[][] matrixB = new int[rows][cols];
        int[][] sumMatrix = new int[rows][cols];
        
        System.out.println("\n--- NHAP MA TRAN A ---");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print("A[" + i + "][" + j + "] = ");
                matrixA[i][j] = scanner.nextInt();
            }
        }
        
        System.out.println("\n--- NHAP MA TRAN B ---");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print("B[" + i + "][" + j + "] = ");
                matrixB[i][j] = scanner.nextInt();
            }
        }
        
        // Thuc hien cong 2 ma tran
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                sumMatrix[i][j] = matrixA[i][j] + matrixB[i][j];
            }
        }
        
        // In ma tran ket qua
        System.out.println("\n--- MA TRAN TONG (A + B) ---");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(sumMatrix[i][j] + "\t"); // \t de can cot cho dep
            }
            System.out.println(); // Xuong dong sau moi hang
        }
        
        scanner.close();
    }
}