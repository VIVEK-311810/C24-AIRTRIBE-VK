// Main.java

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);   // reading input: provided for you
        Product[] products = new Product[10];  // an array with room for 10 products
        int count = 0;                         // how many products are stored so far
        int choice;

        do {
            System.out.println();
            System.out.println("--- Campus Cafe Inventory ---");
            System.out.println("1. Add product");
            System.out.println("2. Display all products");
            System.out.println("3. Total stock value");
            System.out.println("4. Average price");
            System.out.println("5. Restock a product");
            System.out.println("6. Duplicate a product");
            System.out.println("0. Exit");
            System.out.print("Choose: ");

            choice = sc.nextInt();
            sc.nextLine();   // clears the leftover newline after nextInt()

            if (choice < 0 || choice > 6) {
                System.out.println("Invalid choice, try again.");
            }

            switch (choice) {

                // Option 1: add a product
                case 1:
                    System.out.print("Name: ");
                    String name = sc.nextLine();

                    System.out.print("Price: ");
                    double price = sc.nextDouble();

                    System.out.print("Quantity: ");
                    int quantity = sc.nextInt();
                    sc.nextLine();

                    if (count == products.length) {
                        System.out.println("Error: the inventory is full (10 products).");
                    } else if (price <= 0 || quantity < 0) {
                        System.out.println("Error: price must be above 0 and quantity cannot be negative.");
                    } else {
                        products[count] = new Product(name, price, quantity);
                        count++;
                        System.out.println("Added: " + name);
                    }
                    break;

                // Option 2: display all products
                case 2:
                    if (count == 0) {
                        System.out.println("No products yet.");
                    } else {
                        for (int i = 0; i < count; i++) {
                            System.out.print((i + 1) + ". ");
                            products[i].display();
                        }
                    }
                    break;

                // Option 3: total stock value
                case 3:
                    if (count == 0) {
                        System.out.println("No products yet.");
                    } else {
                        double total = 0;
                        for (int i = 0; i < count; i++) {
                            total += products[i].stockValue();
                        }
                        System.out.println("Total stock value: Rs " + total);
                    }
                    break;

                // Option 4: average price (with explicit cast for the rounded value)
                case 4:
                    if (count == 0) {
                        System.out.println("No products yet.");
                    } else {
                        double sum = 0;
                        for (int i = 0; i < count; i++) {
                            sum += products[i].price;
                        }

                        // sum is a double, so the decimal part is kept
                        double average = sum / count;

                        System.out.println("Average price: Rs " + average);

                        // Narrowing needs an explicit cast
                        System.out.println("Rounded down: " + (int) average);
                    }
                    break;

                // Option 5: restock
                case 5:
                    System.out.print("Product number: ");
                    int num = sc.nextInt();

                    System.out.print("Quantity to add: ");
                    int extra = sc.nextInt();
                    sc.nextLine();

                    if (num < 1 || num > count) {
                        System.out.println("Error: no product with that number.");
                    } else if (extra <= 0) {
                        System.out.println("Error: quantity to add must be above 0.");
                    } else {
                        products[num - 1].quantity += extra;
                        System.out.println("Restocked " + products[num - 1].name
                                + ". New quantity: " + products[num - 1].quantity);
                    }
                    break;

                // Option 6: duplicate using the copy constructor
                case 6:
                    System.out.print("Product number: ");
                    int n = sc.nextInt();
                    sc.nextLine();

                    if (n < 1 || n > count) {
                        System.out.println("Error: no product with that number.");
                    } else if (count == products.length) {
                        System.out.println("Error: the inventory is full (10 products).");
                    } else {
                        Product copy = new Product(products[n - 1]);   // copy constructor
                        copy.name = copy.name + " (copy)";
                        products[count] = copy;
                        count++;
                        System.out.println("Duplicated as: " + copy.name);
                    }
                    break;

                // Exit
                case 0:
                    System.out.println("Goodbye!");
                    break;
            }

        } while (choice != 0);

        System.out.println("Products created in total: " + Product.totalProducts);
    }
}
