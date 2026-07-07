import java.util.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

class Transaction {
    LocalDate date;
    String type;
    double amount;
    double balance;

    public Transaction(String type, double amount, double balance) {
        this.date = LocalDate.now();
        this.type = type;
        this.amount = amount;
        this.balance = balance;
    }

    public String toString() {
        return date + " - " + type + ": ₹" + amount + " | Balance: ₹" + balance;
    }
}

class Account {
    private static int idCounter = 1001;
    private final String accountNumber;
    private String name;
    private double balance;
    private double dailyWithdrawn;
    private static final double DAILY_LIMIT = 20000.0;
    private List<Transaction> transactions;

    public Account(String name, double initialDeposit) {
        this.accountNumber = "ACC" + idCounter++;
        this.name = name;
        this.balance = initialDeposit;
        this.dailyWithdrawn = 0;
        this.transactions = new ArrayList<>();
        transactions.add(new Transaction("Deposit", initialDeposit, balance));
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("❌ Invalid deposit amount.");
            return;
        }
        balance += amount;
        transactions.add(new Transaction("Deposit", amount, balance));
        System.out.println("✅ Deposited ₹" + amount);
    }

    public void withdraw(double amount) {
        if (amount <= 0 || amount > balance) {
            System.out.println("❌ Invalid withdrawal.");
        } else if (dailyWithdrawn + amount > DAILY_LIMIT) {
            System.out.println("❌ Daily withdrawal limit exceeded.");
        } else {
            balance -= amount;
            dailyWithdrawn += amount;
            transactions.add(new Transaction("Withdraw", amount, balance));
            System.out.println("✅ Withdrawn ₹" + amount);
        }
    }

    public void checkBalance() {
        System.out.println("💰 Current balance: ₹" + balance);
    }

    public void displayInfo() {
        System.out.println("\n🔐 Account Number: " + accountNumber);
        System.out.println("👤 Account Holder: " + name);
        System.out.println("💰 Balance: ₹" + balance);
        System.out.println("📅 Today's Withdrawal: ₹" + dailyWithdrawn + " / ₹" + DAILY_LIMIT);
    }

    public void printPassbook(LocalDate from, LocalDate to) {
        System.out.println("\n📘 Passbook for Account: " + accountNumber);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        for (Transaction t : transactions) {
            if ((t.date.isEqual(from) || t.date.isAfter(from)) &&
                (t.date.isEqual(to) || t.date.isBefore(to))) {
                System.out.println(t);
            }
        }
    }
}

public class BankSystem {
    static Scanner sc = new Scanner(System.in);
    static Map<String, Account> accounts = new HashMap<>();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n===== BANK MENU =====");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Check Balance");
            System.out.println("5. Account Info");
            System.out.println("6. Print Passbook");
            System.out.println("7. Exit");
            System.out.print("Choose option: ");
            int ch = sc.nextInt();

            switch (ch) {
                case 1:
                    createAccount();
                    break;
                case 2:
                    depositMoney();
                    break;
                case 3:
                    withdrawMoney();
                    break;
                case 4:
                    checkBalance();
                    break;
                case 5:
                    displayAccountInfo();
                    break;
                case 6:
                    printPassbook();
                    break;
                case 7:
                    System.out.println("🏦 Thank you for using the banking system.");
                    return;
                default:
                    System.out.println("❌ Invalid choice.");
                    break;
            }
        }
    }

    static void createAccount() {
        sc.nextLine();
        System.out.print("Enter name: ");
        String name = sc.nextLine();
        System.out.print("Initial deposit: ");
        double deposit = sc.nextDouble();
        Account acc = new Account(name, deposit);
        accounts.put(acc.getAccountNumber(), acc);
        System.out.println("✅ Account created. Account Number: " + acc.getAccountNumber());
    }

    static Account getAccount() {
        sc.nextLine();
        System.out.print("Enter account number: ");
        String accNo = sc.nextLine();
        Account acc = accounts.get(accNo);
        if (acc == null) {
            System.out.println("❌ Account not found.");
        }
        return acc;
    }

    static void depositMoney() {
        Account acc = getAccount();
        if (acc != null) {
            System.out.print("Enter deposit amount: ");
            acc.deposit(sc.nextDouble());
        }
    }

    static void withdrawMoney() {
        Account acc = getAccount();
        if (acc != null) {
            System.out.print("Enter withdrawal amount: ");
            acc.withdraw(sc.nextDouble());
        }
    }

    static void checkBalance() {
        Account acc = getAccount();
        if (acc != null)
            acc.checkBalance();
    }

    static void displayAccountInfo() {
        Account acc = getAccount();
        if (acc != null)
            acc.displayInfo();
    }

    static void printPassbook() {
        Account acc = getAccount();
        if (acc != null) {
            System.out.print("Enter start date (yyyy-mm-dd): ");
            LocalDate from = LocalDate.parse(sc.next());
            System.out.print("Enter end date (yyyy-mm-dd): ");
            LocalDate to = LocalDate.parse(sc.next());
            acc.printPassbook(from, to);
        }
    }
}
