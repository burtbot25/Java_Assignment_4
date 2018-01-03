package q3;

import java.util.Scanner;

/**
 * <p>This program accepts a String of MIX characters from the user then
 * encodes and decodes the characters within the String.</p>
 *
 * @author Wilburt Herrera.
 * @version 1.0
 */
public final class MIXChar {
    
    /**
     * Maximum number of characters in one array.
     */
    public static final int CHAR_MAX = 11;
    
    /**
     * Base used to decode the characters.
     */
    public static final int BASE_56 = 56;
    
    /**
     * Value for the Delta character.
     */
    private static final char DELTA = '\u0394';
    
    /**
     * Value for the Sigma character.
     */
    private static final char SIGMA = '\u03A3';
    
    /**
     * Value for the Pi character.
     */
    private static final char PI = '\u03A0';

    /**
     * Array to hold all MIX characters.
     */
    private static final char[] MIX_CHARACTERS 
        = {' ', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 
            DELTA, 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
            'R', SIGMA, PI, 'S', 'T', 'U', 'V', 'W', 'X', 'Y',
            'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8',
            '9', '.', ',', '(', ')', '+', '-', '*', '/', '=',
            '$', '<', '>', '@', ';', ':', '\''};

    /**
     * The integer value of the MIX character.
     */
    private int value;
    
    /**
     * Constructs an object of type MIXChar.
     * @param value an integer that represents the character.
     */
    private MIXChar(int value) {
        this.value = value;
    }

    /**
     * Determines if the character is a MIX character.
     * @param c a char input.
     * @return a boolean based on the input is a MIX character
     */
    public static boolean isMIXChar(char c) {
        for (char character : MIX_CHARACTERS) {
            if (c == character) {
                return true;
            }
        }
        return false;
    }

    /**
     * Converts the character into a MIX character.
     * @param c a character to convert.
     * @return mixChar a MIX character.
     */
    public static MIXChar toMIXChar(char c) {
        int index = 0;
        MIXChar mixChar = null;
        if (isMIXChar(c)) {
            // Uses linear search to find the corresponding character
            for (int i = 0; i < MIX_CHARACTERS.length; i++) {
                if (c == MIX_CHARACTERS[i]) {
                    index = i;
                    break;
                }
            }
            mixChar = new MIXChar(index);
        } else {
            throw new IllegalArgumentException("String must " 
                + "correspond to MIX characters only");
        }
        return mixChar;
    }
    
    /**
     * Converts the MIXChar provided to its corresponding
     * Java character.
     * @param x a MIXChar object to be converted.
     * @return javaChar a java character.
     */
    public static char toChar(MIXChar x) {
        char javaChar = MIX_CHARACTERS[x.ordinal()];
        return javaChar;
    }
    
    /**
     * Returns a String with characters corresponding to those
     * of the input array.
     * @param mixArray a MIXChar object.
     * @return string the characters in the array.
     */
    public static String toString(MIXChar[] mixArray) {
        String string = "";
        
        /**
         * Since mixArray stores MIXChars in reverse order,
         * it is being printed out in this for-loop in
         * reverse order to correct the toString output.
         * 
         */
        for (int i = mixArray.length - 1; i >= 0; i--) {
            string += toChar(mixArray[i]);
        }
        return string;
    }
    
    /**
     * Returns an array of MIXChar characters corresponding to the
     * chars in s.  Throws an exception if the String contains
     * non-MIX characters.
     * @param s a String used to create the MIXChar array
     * @return mixArray the MIXChar array.
     */
    public static MIXChar[] toMIXChar(String s) {
        char[] charArray = s.toCharArray();
        MIXChar[] mixArray = new MIXChar[s.length()];
        for (int i = 0; i < s.length(); i++) {
            if (!isMIXChar(charArray[i])) {
                throw new IllegalArgumentException("String may only" 
                    + " contain MIX characters");
            } else {
                mixArray[i] = toMIXChar(charArray[i]);
            }
        }
        return mixArray;
    }
    
    /**
     * Returns an array of longs holding the values
     * of the m. Packed 11 MIXChar characters per long.
     * @param m a MIXChar array
     * @return longArray an array of the packed characters.
     */
    public static long[] encode(MIXChar[] m) {
        // Sets array size
        int arraySize;
        
        // Sets position and remainder to the max index of the MIXChar Array
        int position = m.length - 1;
        int remainder = m.length - 1;
        
        /*
         * Starting index of the MIXChar array to be added. Initialized
         * here so index may continue to increase in the for-loop using index
         * within this method.
         */
        int index = 0;
        
        // Determining the size of the Long array
        if (m.length % CHAR_MAX == 0) {
            arraySize = m.length / CHAR_MAX;
        } else {
            arraySize = (m.length / CHAR_MAX) + 1;
        }
        long[] longArray = new long[arraySize];
        
        /*
         * Encodes the characters using base conversion and stores
         * them into an array. One long array only holds up to 11
         * characters. Storing is done in reverse ordering to reduce
         * from the largest number until no remainder remains.
         */
        for (int j = arraySize - 1; j >= 0; j--) {
            
            // Determines the current position of the number to add
            if (position >= CHAR_MAX) {
                position = CHAR_MAX - 1;
                remainder -= position;
            }
            
            /*
             * This for-loop adds the MIXChar value to the longArray in reverse.
             * Index is not initialized in this for-loop so that it would not 
             * revert back to it's initialized value during the start of each 
             * loop.
             */
            for (; index < m.length; index++) {
                // Ensures that the position doesn't go below 0
                if (position == -1) {
                    break;
                }
                
                // Adding the MIXChar's encoded value into the long array.
                longArray[j] += ((m[index]).ordinal() * power(position));
                position--;   
            }
            
            // Sets position to the amount of the remaining positions minus 1
            position = remainder - 1;
        }
        return longArray;
    }
    
    /**
     * Supporting power method to replace Math.pow. This support
     * method is used to return a long since Math.pow only returns
     * double values.
     * @param position the position of the current MIXChar
     * @return pow a long of the value of 56^position
     */
    public static long power(int position) {
        long pow = BASE_56;
        
        if (position == 0) {
            return 1;
        } else {
            for (int i = 0; i < position - 1; i++) {
                pow *= BASE_56;
            }
        }
        return pow;
    }
    
    /**
     * Returns the ordinal value of the MIXChar.
     * @return value an int of the ordinal value.
     */
    public int ordinal() {
        return value;
    }
    
    /**
     * Decodes the MIXChars in the long array. Returns
     * an array of MIXChar characters to unpacking the input array,
     * assuming 11 characters are packed per long, with the last
     * long possibly having fewer than 11 characters.
     * @param l a longArray to decode.
     * @return the revertingString of MIXChars as a MIXChar array
     */
    public static MIXChar[] decode(long[] l) {
        long remainder;
        String revertString = "";
        long encodedTotal = 0;

        // Decodes the encoded values in the long array per element.
        for (long element : l) {
            encodedTotal = 0;
            encodedTotal += element;
            
            /*
             * Decodes the encoded total using remainder division and "long" 
             * division until the encoded total reaches zero.
             */
            while (encodedTotal != 0) {
                
                // Adds decoded character to a string
                revertString += (MIX_CHARACTERS[
                    (int) (Long.remainderUnsigned(encodedTotal, BASE_56))]);
                
                // Determines the remainder of the encoded total after division
                remainder = Long.parseLong(Long.toUnsignedString(
                    Long.divideUnsigned(encodedTotal, BASE_56)));
                
                // Assigns the current remainder to be processed to the total
                encodedTotal = remainder;
                }
        }
        
        return toMIXChar(revertString);
    }
    
    /**
     * Returns a string representation of this MIXChar as a Java char.
     * @see java.lang.Object#toString()
     * @return javaChar a String of the MIXChar as a Java char.
     */
    @Override
    public String toString() {
        String javaChar = "" + MIX_CHARACTERS[value];
        return javaChar;        
    }

    /**
     * <p>This is the main method (entry point) that gets called by the JVM.</p>
     *
     * @param args command line arguments.
     */
    public static void main(String[] args) {
        String userInput;
        Scanner scan = new Scanner(System.in);
        
        // Prompts user for input
        System.out.println("Please enter a String of MIX characters");
        System.out.println("(Note: Spaces don't count as a character)");
        try {
            
            // Reads the user's input
            userInput = scan.next();
            
            // Converting the user's input to MIXChars
            MIXChar[] mixArray = toMIXChar(userInput);
            
            // Encoding the user's set of MIXChars
            System.out.println("Encoding...");
            long[] longArray = encode(mixArray);
            System.out.println("Encoding complete.");
            System.out.println("Value(s) within the encoded long array...");
            for (long longValue : longArray) {
                System.out.println(longValue);
            }
            
            // Decoding and printing the encoded values
            System.out.println("Decoding...");
            MIXChar[] decoded = decode(longArray);
            System.out.println(toString(decoded));
            
        } catch (IllegalArgumentException ex) {
            
            /*
             * Catches an exception if the user's String contains non-MIXChars
             * and prints the corresponding error message.
             */
            System.out.println(ex.getMessage());
        }
        
        scan.close();
        System.out.println("Question three was called and ran sucessfully.");
    }
}


