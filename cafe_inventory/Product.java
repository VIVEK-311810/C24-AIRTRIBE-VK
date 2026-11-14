// Product.java

public class Product {

    // Static variable: one copy shared by all products
    static int totalProducts = 0;

    // Instance variables: each product has its own
    String name;
    double price;
    int quantity;


    // 1. No-argument constructor: sets default values
    Product() {
        name = "Unknown";
        price = 0;
        quantity = 0;
        totalProducts++;
    }


    // 2. Parameterized constructor
    Product(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        totalProducts++;
    }


    // 3. Copy constructor: builds a new, independent object from an existing one
    Product(Product other) {
        this.name = other.name;
        this.price = other.price;
        this.quantity = other.quantity;
        totalProducts++;
    }


    // price * quantity (the int quantity is widened to double automatically)
    double stockValue() {
        return price * quantity;
    }


    void display() {
        System.out.println(name + " | Rs " + price + " | qty " + quantity);
    }
}
