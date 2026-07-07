import java.util.Scanner;
public class Calculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean continueCalc = true;	
        System.out.println("====== JAVA CONSOLE CALCULATOR ======");
        while (continueCalc) {
            try {
                // Take first number
                System.out.print("Enter first number: ");
                double num1 = Double.parseDouble(scanner.nextLine());

                // Take operator
                System.out.print("Enter operator (+, -, *, /, %): ");
                char operator = scanner.nextLine().charAt(0);

                // Take second number
                System.out.print("Enter second number: ");
                double num2 = Double.parseDouble(scanner.nextLine());
                double result;
                // Conditional logic for operation
                switch (operator) {
                    case '+':
                        result = num1 + num2;
                        System.out.println("Result: " + result);
                        break;
                    case '-':
                        result = num1 - num2;
                        System.out.println("Result: " + result);
                        break;
                    case '*':
                        result = num1 * num2;
                        System.out.println("Result: " + result);
                        break;
                    case '/':
                        if (num2 == 0) {
                            System.out.println("Error: Cannot divide by zero.");
                        } else {
                            result = num1 / num2;
                            System.out.println("Result: " + result);
                        }
                        break;
                    case '%':
                        if (num2 == 0) {
                            System.out.println("Error: Cannot modulo by zero.");
                        } else {
                            result = num1 % num2;
                            System.out.println("Result: " + result);
                        }
                        break;
                    default:
                        System.out.println("Error: Invalid operator.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Error: Invalid number input.");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

            // Ask user if they want to continue
            System.out.print("Do you want to perform another calculation? (yes/no): ");
            String response = scanner.nextLine().trim().toLowerCase();
            if (!response.equals("yes")) {
                continueCalc = false;
            }
        }

        System.out.println("Thank you for using the calculator. Goodbye!");
        scanner.close();
    }
}

