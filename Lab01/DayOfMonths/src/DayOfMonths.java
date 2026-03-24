import java.util.Scanner;

public class DayOfMonths {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String monthInput;
        int year = -1;
        int days = 0;

        // Vong lap yeu cau nhap nam hop le
        while (true) {
            System.out.print("Please enter a year (e.g., 1999): ");
            if (scanner.hasNextInt()) {
                year = scanner.nextInt();
                if (year >= 0) {
                    break; // Nam hop le thi thoat vong lap
                }
            } else {
                scanner.next(); // Bo qua du lieu sai
            }
            System.out.println("Invalid year. Please enter a valid non-negative number.");
        }

        scanner.nextLine(); // Xoa bo nho dem

        // Vong lap yeu cau nhap thang hop le va tinh so ngay
        while (true) {
            System.out.print("Please enter a month (e.g., January, Jan., Jan, 1): ");
            monthInput = scanner.nextLine().trim().toLowerCase();

            // Kiem tra nam nhuan
            boolean isLeapYear = (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);

            switch (monthInput) {
                case "january": case "jan.": case "jan": case "1":
                case "march": case "mar.": case "mar": case "3":
                case "may": case "5":
                case "july": case "jul.": case "jul": case "7":
                case "august": case "aug.": case "aug": case "8":
                case "october": case "oct.": case "oct": case "10":
                case "december": case "dec.": case "dec": case "12":
                    days = 31;
                    break;
                case "april": case "apr.": case "apr": case "4":
                case "june": case "jun.": case "jun": case "6":
                case "september": case "sept.": case "sep": case "9":
                case "november": case "nov.": case "nov": case "11":
                    days = 30;
                    break;
                case "february": case "feb.": case "feb": case "2":
                    days = isLeapYear ? 29 : 28;
                    break;
                default:
                    System.out.println("Invalid month. Please try again.");
                    continue; // Lap lai neu thang khong hop le
            }
            break; // Thoat vong lap khi da tim duoc so ngay
        }

        System.out.println("The number of days in month " + monthInput + " of year " + year + " is: " + days);
        scanner.close();
    }
}