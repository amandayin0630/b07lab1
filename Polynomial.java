package polynomial;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Polynomial {
    double[] coefficients;
    int[] exponents;
    
    //a helper function to check whether a given integer is in an array of integers.
    public static boolean contains(int[] arr, int target) {
        for (int num : arr) {
            if (num == target) {
                return true; // Return true if the element is found
            }
        }
        return false; // Return false if the element is not found
    }

    // Constructor with no arguments, sets the polynomial to zero
    public Polynomial() {
        coefficients = new double[] {0}; // Initializing all coefficients to 0s.
        exponents = new int[] {0}; //by definition, they would have the same number if elements! exp: increasing!
    }

    // A constructor that takes an array of double as an argument
    public Polynomial(double[] coefficients, int[] exponents) {
        this.coefficients = coefficients;
        this.exponents = exponents;
    }

    // Method 1:
    // A method named add that takes one argument of type Polynomial and
    // returns the polynomial resulting from adding the calling object and the argument
    public Polynomial add(Polynomial input) {
        // Find the max length
        int max = Math.max(coefficients.length, input.coefficients.length);
        // Establish two new arrays for storing the co and exp.
        double[] new_coefficients = new double[max]; // Use "new" to create a new array.
        int[] new_exponents = new int[max];
        // Add the corresponding entries based on cases
        int p =0, q=0, i = 0; //why have to explicitly declare value?， p, q are to track indexing movement in the two polynomial
        //variable p represents the current position in the coefficients array of the calling object, and q represents the current position in the input.coefficients array.
        while(i<max) {
        	if (p < coefficients.length && (q >= input.coefficients.length || exponents[p] < input.exponents[q])) { //have reached all elements of input co OR the co of exp is smaller than input.
                new_coefficients[i] = coefficients[p];
                new_exponents[i] = exponents[p];
                p++;
            } else if (p < input.coefficients.length && (q >= coefficients.length || input.exponents[p] > exponents[q])) {
                new_coefficients[i] = input.coefficients[q];
                new_exponents[i] = input.exponents[q];
                q++;
            } 
        	//eg: 1-3x^2+4x^4, 4+3x+6x^3+x^4 +x^5 exp: [0,2,4],[0,1,3,4,5]
        	//if(p<coefficients.length && q<input.coefficients.length && exponents[p]==input.exponents[q])
            else{ //we have the same exponents
        		new_coefficients[i] = coefficients[p] + input.coefficients[q];
        		new_exponents[i] = exponents[p];
        		p+=1;
        		q+=1;
        	}   
        	i+=1;
        }
        return new Polynomial(new_coefficients, new_exponents);
    }

    // Method 2
    public double evaluate(double x) { // Output is double type, input is double type
        double output = 0;
        for (int i = 0; i < coefficients.length; i++) {
            output += coefficients[i] * Math.pow(x, exponents[i]); // Use Math library.
        }
        return output;
    }

    // Method 3
    public boolean hasRoot(double x) {
        return evaluate(x) == 0;
    }
    
    //method 4, multiplication
    public Polynomial multiply(Polynomial new_poly) {
    	//eg 1+x , 3+2x+x^2
    	// [1,1],[0,1]    [3,2,1], [0,1,2]
    	int term_size = coefficients.length * new_poly.coefficients.length;
        double[] all_coefficients = new double[term_size];
    	int[] all_exponents = new int[term_size];
        int k = 0; //tracking index of the new array.
    	for(int i = 0; i< coefficients.length; i++) {
    		for(int j = 0; j<new_poly.coefficients.length; j++) {
    			double new_co  = coefficients[i]*new_poly.coefficients[j]; //store it first.
    			int new_expo = exponents[i] + exponents[j];
    			//check for adundancy.
    			if(!contains(all_exponents, new_expo)) { //call for use of helper function
    				all_coefficients[k] = new_co;
        			all_exponents[k] = new_expo;	
    			}
    			else {
    				//search and locate the index of the repeated exponent
    				int p =0;
    				while(all_exponents[p]!= new_expo) {
    					p++;
    				}//by the end, p founded, should be the same indexing as the coefficients array since we updated both in the first case above.
    				//update the coefficient without adding anything new to the exponent array.
    				all_exponents[p]+=new_expo;
    			}
    		    k+=1; //move to the next index of the new arrays to fill in one by one.
    		}
    	}
    	return new Polynomial(all_coefficients, all_exponents);    	
    }
    
    //5, a constructor:  takes one argument of type File and initializes the polynomial based on the contents of the file
    //eg: the line 5-3x2+7x8  corresponds to the polynomial 5 − 3𝑥2 + 7𝑥8
        //nessasity of FileNotFoundException ?
        public Polynomial(File file) throws FileNotFoundException { //indicates that this constructor might throw an exception if the file is not found.
        	// a new Scanner object named scanner is created, initialized with the File object file.
            Scanner scanner = new Scanner(file);
            //nextLine() method of the Scanner to read the next line from the file-in this case, there is only one line.
            String polyString = scanner.nextLine();
            scanner.close();
            //establish an array of strings to put in the split terms.
            //(?=[-+]) matches positions before  + or - sign.
            String[] allterms = polyString.split("(?=[-+])"); // Split at every + or -.
            //assign co and exp with new arrays of double and ints.
            coefficients = new double[allterms.length];
            exponents = new int[allterms.length];
            //iterate to put all terms's co and exp into the array.
            for (int i = 0;i < allterms.length;i++) {
            	//first get rid of the white space oif each term using trim method.
                String term = allterms[i].trim();
                //notice all terms in the form of axb, or a, or ax form. x is always included
                //for each term, split the coe and exp and put in a new array.
                String[] part = term.split("x");
                //indexing 0 is always the coefficient. no problem.
                //use .parseDouble method:
                //it is used to convert a String representation of a floating-point number into a double primitive type
                coefficients[i] = Double.parseDouble(part[0]); //part[0] is a string.
                //get to the exponents part.
                
                // in the form of axb, or ax, part[1] exists regardless, it might be an empty string!(if we split ax)
                //very important eg: the split("x") method separates the string "ax" into an array of strings using "x" as the delimiter. The result is an array ["a", ""]
                if (part.length > 1) {
                	if(part[1].isEmpty()) { //ax form, method .isEmpty for Boolean value.
                		exponents[i] = 1;
                	}
                	else {  //axb form, use parseInt method.
                		exponents[i] = Integer.parseInt(part[1]);
                	}
                    exponents[i] = part[1].isEmpty() ? 1 : Integer.parseInt(part[1]);
                } 
                //in this case, the form is just a.
                else {
                    exponents[i] = 0;
                }
            }
        }
    
        
        //Add a method named saveToFile that takes one argument of type String representing a
        //file name and saves the polynomial in textual format in the corresponding file (similar to the format used in part d
        //return type is void! throw a FileNotFoundException if there's an issue with the file.(does not exist etc.)
        public void saveToFile(String fileName) throws FileNotFoundException {
        	//???other ways???  opens a PrintWriter for writing to the specified file
            try (PrintWriter text = new PrintWriter(fileName)) {
            	//iterate through the arrays of int and double typed co and exp, write step by step
                for (int i = 0; i < coefficients.length; i++) {
                	//first if condition, unless 0, coefficient is valid.
                    if (coefficients[i] != 0) {
                    	//second, is co is negative, already printed in text.print(coefficients[i]);
                    	//be careful that "+" is not explicitly included in text.
                    	//also, the first coefficient does not need a "plus" at the beginning.
                        if (coefficients[i] > 0 && i != 0) {
                            text.print("+"); //.print() method on PrintWriter typed text. ???usage.
                        }// run a few times to see better!
                        text.print(coefficients[i]);
                        //now, we can get to exp. there are form axb, ax, a, respective exp being b, 1, 0.
                        //when in the form of a, exp=0, nothing in text needs to be printed, case omitted.
                        //if exp>0, then x needs to be added to the text first.
                        if (exponents[i] > 0) {
                            text.print("x"); 
                            //further more, axb form, need to print the respective exponents. 
                            if (exponents[i] > 1) {
                                text.print(exponents[i]);
                                //after this strict sequence, next term goes to the second if condition to get a connection of "+" if needed.
                            }
                        }
                    }
                }
            }
        }
        
}
