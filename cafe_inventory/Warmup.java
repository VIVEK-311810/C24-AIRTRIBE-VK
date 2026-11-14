// Warmup.java
// Scratch file: predict the output BEFORE you run it.

public class Warmup {

    public static void main(String[] args) {

        // Part 1: casting and bytes
        int a = (int) 7.9;
        int x = 5;
        double d = x;
        int n = 130;
        byte b = (byte) n;

        System.out.println(a + " " + d + " " + b);


        // Part 2: switch without break (fall-through)
        int day = 2;

        switch (day) {
            case 1:
                System.out.println("One");
            case 2:
                System.out.println("Two");
            case 3:
                System.out.println("Three");
        }
    }
}
