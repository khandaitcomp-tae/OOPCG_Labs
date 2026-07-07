// Superclass
class Person {
    String name;
    int age;
    void setDetails(String name, int age) {
        this.name = name;
        this.age = age;
    }
    void displayDetails() {
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
    }
}

// Subclass
class Employee extends Person {
    String designation;
    void setEmployeeDetails(String name, int age, String designation) {
        // Call superclass method
        setDetails(name, age);
        this.designation = designation;
    }
    void displayEmployeeDetails() {
        // Call superclass method
        displayDetails();
        System.out.println("Designation: " + designation);
    }
}

// Main class
public class InheritanceDemo {
    public static void main(String[] args) {
        Employee emp = new Employee();
        emp.setEmployeeDetails("Alice", 28, "Software Engineer");
        System.out.println("=== Employee Details ===");
        emp.displayEmployeeDetails();
    }
}
