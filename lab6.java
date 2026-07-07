import java.util.Scanner;
public class ATMSimulator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double balance = 5000.00;  // Initial balance
        int choice;
        System.out.println("=== Welcome to the Java ATM Machine ===");
        while (true) {
            System.out.println("\n1. Check Balance");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        System.out.printf("Your current balance is ₹%.2f\n", balance);
                        break;

                    case 2:
                        System.out.print("Enter amount to deposit: ");
                        double deposit = Double.parseDouble(scanner.nextLine());

                        if (deposit <= 0) {
                            throw new IllegalArgumentException("Amount must be positive.");
                        }

                        balance += deposit;
                        System.out.println("Deposit successful!");
                        break;

                    case 3:
                        System.out.print("Enter amount to withdraw: ");
                        double withdraw = Double.parseDouble(scanner.nextLine());

                        if (withdraw <= 0) {
                            throw new IllegalArgumentException("Amount must be positive.");
                        }
                        if (withdraw > balance) {
                            throw new ArithmeticException("Insufficient funds.");
                        }

                        balance -= withdraw;
                        System.out.println("Withdrawal successful!");
                        break;

                    case 4:
                        System.out.println("Thank you for using the ATM. Goodbye!");
                        scanner.close();
                        return;

                    default:
                        System.out.println("Invalid choice. Please select between 1 and 4.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter numeric values.");
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (ArithmeticException e) {
                System.out.println("Error: " + e.getMessage());
            } finally {
                System.out.println("Returning to main menu...\n");
            }
        }
    }
}
