// Interface 1
interface Printable {
    void print();
}
// Interface 2
interface Showable {
    void show();
}
// Class implementing both interfaces
class Document implements Printable, Showable {
    @Override
    public void print() {
        System.out.println("Printing the document...");
    }

    @Override
    public void show() {
        System.out.println("Showing the document on screen...");
    }
}
// Main class
public class InterfaceInheritanceDemo {
    public static void main(String[] args) {
        // Polymorphism: Using interface reference
        Printable p = new Document();
        Showable s = new Document();
        System.out.println("=== Using Printable interface reference ===");
        p.print();
        System.out.println("\n=== Using Showable interface reference ===");
        s.show();
        System.out.println("\n=== Using actual Document object ===");
        Document doc = new Document();
        doc.print();
        doc.show();
    }
}
