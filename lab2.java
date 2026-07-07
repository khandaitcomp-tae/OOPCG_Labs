import java.util.Scanner;
class Product {
    String name;
    double price;
    int quantity;
    // Default constructor
    public Product() {
        this.name = "Unknown";
        this.price = 0;
        this.quantity = 0;
    }

    // Overloaded constructor 1
    public Product(String name, double price) {
        this.name = name;
        this.price = price;
        this.quantity = 1;
    }
    // Overloaded constructor 2
    public Product(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    double getTotalCost() {
        return price * quantity;
    }

    void displayProduct() {
        System.out.printf("%-20s %-10.2f %-10d %-10.2f\n", name, price, quantity, getTotalCost());
    }
}

public class ECommerceOrderProcessor {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Product[] cart = new Product[10];
        int count = 0;
        System.out.println("==== E-COMMERCE ORDER PROCESSING ====\n");
        // Predefined Products
        cart[count++] = new Product("Laptop", 45000, 1);
        cart[count++] = new Product("Mouse", 500, 2);

        // Input additional products manually
        System.out.print("How many more products do you want to add? ");
        int more = Integer.parseInt(scanner.nextLine());
        for (int i = 0; i < more; i++) {
            System.out.println("\nEnter details for product " + (i + 1) + ":");
            System.out.print("Product Name: ");
            String name = scanner.nextLine();
            System.out.print("Price: ");
            double price = Double.parseDouble(scanner.nextLine());	
            System.out.print("Quantity: ");
            int quantity = Integer.parseInt(scanner.nextLine());

            cart[count++] = new Product(name, price, quantity);
        }
        // Print Invoice
        double total = 0;
        System.out.println("\n=========== INVOICE ===========");
        System.out.printf("%-20s %-10s %-10s %-10s\n", "Product", "Price", "Qty", "Subtotal");
        System.out.println("------------------------------------------------------");
        for (int i = 0; i < count; i++) {
            cart[i].displayProduct();
            total += cart[i].getTotalCost();
        }
        // Apply discount
        double discount = 0;
        if (total > 5000) {
            discount = total * 0.10; // 10% off
        } else if (total > 2000) {
            discount = total * 0.05; // 5% off
        }
        double finalTotal = total - discount;
        System.out.println("------------------------------------------------------");
        System.out.printf("%-42s: ₹%.2f\n", "Total", total);
        System.out.printf("%-42s: ₹%.2f\n", "Discount", discount);
        System.out.printf("%-42s: ₹%.2f\n", "Final Amount Payable", finalTotal);
        System.out.println("\nThank you for shopping with us!");
        scanner.close();
    }
}
