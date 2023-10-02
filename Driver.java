import java.io.File;
import java.io.FileNotFoundException;

public class Driver {

    public static void main(String[] args) {
        //new polynomials
        Polynomial poly1 = new Polynomial(); 
        Polynomial poly2 = new Polynomial(new double[]{1, -7, 3}, new int[]{8, 2, 3, 5}); 

        // Display test
        System.out.println("Polynomial 1: " + poly1);
        System.out.println("Polynomial 2: " + poly2);

        // Add and multiply test
        Polynomial resultAdd = poly1.add(poly2);
        Polynomial resultMultiply = poly1.multiply(poly2);

        // Display results
        System.out.println("Result of addition: " + resultAdd);
        System.out.println("Result of multiplication: " + resultMultiply);

        // Evaluate polynomial at x
        double x = 4.0;
        System.out.println("Evaluation at x = " + x + ": " + poly2.evaluate(x));

        // Check if polynomial has a root at x
        System.out.println("if polynomial 2 has a root at x = " + x + ":" + poly2.hasRoot(x));

        try { //careful, "try" to avoid any errors.
            // Create a polynomial from a file
            File file = new File("polynomial.txt");
            Polynomial polyFromFile = new Polynomial(file);
            System.out.println("Polynomial from file: " + polyFromFile);
            // Save polynomial to file
            polyFromFile.saveToFile("test_polynimial.txt"); //test of the save_to_file method.
            
            //how exactly do we check the content is saved? by opening it? 
            System.out.println("Polynomial saved."); //to see whether saved or not.
        } 
        catch (FileNotFoundException e) {
            e.printStackTrace(); //standard closing.
        }
    }
}

