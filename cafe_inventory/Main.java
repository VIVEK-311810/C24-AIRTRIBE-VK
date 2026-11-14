// Main.java
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);   // reading input: provided for you
        Product[] products = new Product[10];  // an array with room for 10 products
        int count = 0;                         // how many products are stored so far
        int choice;

        do {
            System.out.println("\n--- Campus Cafe Inventory ---");
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
                case 1:
                    System.out.print("Name: ");
                    String name = sc.nextLine();
                    System.out.print("Price: ");
                    double price = sc.nextDouble();
                    System.out.print("Quantity: ");
                    int quantity = sc.nextInt();
                    sc.nextLine();
                    if(count==products.length){
                        System.out.println("Inventory Full");
                    }
                    else if(price <= 0 || quantity < 0){
                        System.out.println("Invalid data");
                    }
                    else{
                        products[count] = new Product(name, price, quantity);
                        count++;
                    }
                    break;

                case 2:
                    if(count==0){
                        System.out.println("Inventory Empty");
                    }else{
                        for(int i=0; i<count; i++){
                            System.out.println((i+1) + ". ");
                            products[i].display();
                        }
                    }
                    break;

                case 3:
                    if(count==0){
                        System.out.println("Inventory Empty");
                    }
                    else{
                        double sum = 0;
                        for(int i=0; i<count; i++){
                            sum = sum + products[i].stockValue();
                        }
                        System.out.println("Total Value of inventory: " + sum);
                    }
                    break;

                case 4:
                    if(count==0){
                        System.out.println("Inventory Empty");
                    }
                    else{
                        double total = 0;
                        for(int i=0; i<count; i++){
                            total = total + products[i].price;
                        }
                        System.out.println("Avg Value of inventory: " + total/count);
                        System.out.println("Avg Value of inventory in round off: " + (int)total/count);   
                    }
                    break;

                case 5:
                    System.out.print("Product number: ");
                    int num = sc.nextInt();
                    System.out.print("Quantity to add: ");
                    int extra = sc.nextInt();
                    sc.nextLine();
                    if(num>0 && num<count && extra > 0){
                        products[num-1].quantity += extra;
                    }
                    else{
                        System.out.println("Error in restocking check the data. Quantity to add must be greater than 0");
                    }
                    break;

                case 6:
                    System.out.print("Product number: ");
                    int n = sc.nextInt();
                    sc.nextLine();
                    if(n<1 || n>count){
                        System.out.println("No Product available with that number");
                    }
                    else if(count == products.length){
                        System.out.println("Inventory Full");
                    }
                    else{
                        Product copyProduct = new Product(products[n-1]);
                        copyProduct.name = copyProduct.name + " (copy)";
                        products[count] = copyProduct;
                        count++;
                    }
                    break;

                case 0:
                    System.out.println("Goodbye!");
                    break;
            }
        } while (choice != 0);

        System.out.println("Products created in total: " + Product.totalProducts);
    }
}
