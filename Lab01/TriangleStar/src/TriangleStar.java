import java.util.Scanner;

public class TriangleStar {
    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        
        System.out.print("Nhap vao chieu cao cua tam giac (n): ");
        int n = keyboard.nextInt();
        
        // Vong lap chay tung hang (tu 1 den n)
        for (int i = 1; i <= n; i++) {
            
            // In khoang trang (n - i khoang trang)
            for (int j = 1; j <= n - i; j++) {
                System.out.print(" ");
            }
            
            // In dau sao (2*i - 1 dau sao)
            for (int k = 1; k <= 2 * i - 1; k++) {
                System.out.print("*");
            }
            
            // Xuong dong sau khi in xong 1 hang
            System.out.println();
        }
        
        keyboard.close();
    }
}