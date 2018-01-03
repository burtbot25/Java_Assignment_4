package q1;

import java.text.DecimalFormat;
import java.util.Scanner;

/**
 * <p>This program calculates and prints the mean and standard deviation of
 * a set of integer inputs.</p>
 *
 * @author Wilburt Herrera
 * @version 1.0
 */
public class Statistics {
    
    /**
     * Maximum number of integers in the array.
     */
    public static final int MAX = 50;
    
    /**
     * Holds all the integers.
     */
    private static int[] numbers;
    
    /**
     * Count of user-entered integers.
     */
    private static int count;
    
    /**
     * Computes the standard deviation of the number array.
     * @param numberArray an array of integers.
     * @return the standard deviation of the array.
     */
    public static double getStandardDev(int[] numberArray) {
        double mean = getMean(numberArray);
        double sum = 0;
        double variance;
        double standardDev;
        
        // To obtain the sum of the numbers in the array
        for (int i = 0; i < count; i++) {
            sum += Math.pow((numberArray[i] - mean), 2);
        }
        
        // Calculates variance and standard deviation
        variance = (double) (sum / (count - 1));
        standardDev = (double) Math.sqrt(variance);
        
        return standardDev;
    }
    
    /**
     * Obtains the mean of the array of integers.
     * @param numberArray an int array.
     * @return mean a double.
     */
    public static double getMean(int[] numberArray) {
        double sum = 0;
        
        // Sums all the numbers in the numberArray
        for (int i = 0; i < numberArray.length; i++) {
            sum += numberArray[i];
        }
        
        // Calculates the mean of the numberArray
        double mean =  (double) sum / count;
        return mean;
    }
    
    /**
     * <p>This is the main method (entry point) that gets called by the JVM.</p>
     *
     * @param args command line arguments.
     */
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        DecimalFormat format = new DecimalFormat("0.00");
        String userInput;
        numbers = new int[MAX];
        
        // Reads input and adds it to an array of numbers.
        try {
            while (scan.hasNext()) {
                userInput = scan.nextLine();
                numbers[count] = Integer.parseInt(userInput);
                count++;
            }
        } catch (NumberFormatException ex) {
            /**
             * Catches an exception if a non-integer is read and prints
             * the corresponding error message.
             */
            System.out.println("You may only enter integers");
        }
        
        // Prints the mean and standard deviation of the input
        System.out.println("The mean of your numbers is: " 
            + format.format(getMean(numbers)));
        System.out.println("The standard deviation is: " 
            + format.format(getStandardDev(numbers)));
        
        scan.close();
        System.out.println("Question one was called and ran sucessfully.");
    }

};
