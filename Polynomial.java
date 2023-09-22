public class Polynomial {
    double[] coefficients;

    // Constructor with no arguments, sets the polynomial to zero
    public Polynomial() {
        coefficients = new double[] {0}; // Initializing all coefficients to 0s.
    }

    // A constructor that takes an array of double as an argument
    public Polynomial(double[] coefficients) {
        this.coefficients = coefficients;
    }

    // Method 1:
    // A method named add that takes one argument of type Polynomial and
    // returns the polynomial resulting from adding the calling object and the argument
    public Polynomial add(Polynomial input) {
        // Find the max length
        int max = Math.max(coefficients.length, input.coefficients.length);
        // Establish a new array
        double[] result = new double[max]; // Use "new" to create a new array.
        // Add the corresponding entries
        for (int i = 0; i < coefficients.length; i++) {
            result[i] += coefficients[i];
        }
        for (int j = 0; j < input.coefficients.length; j++) {
            result[j] += input.coefficients[j];
        }
        // Return the new polynomial
        return new Polynomial(result); // Using the second constructor, make sure to use "new".
    }

    // Method 2
    public double evaluate(double x) { // Output is double type, input is double type
        double output = 0;
        for (int i = 0; i < coefficients.length; i++) {
            output += coefficients[i] * Math.pow(x, i); // Use Math library.
        }
        return output;
    }

    // Method 3
    public boolean hasRoot(double x) {
        return evaluate(x) == 0;
    }
}
