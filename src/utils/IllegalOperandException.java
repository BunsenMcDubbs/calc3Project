/**
 * Thrown when an illegal operation is attempted. Is a checked exception.
 *
 * @author Andrew Dai
 * @version 1.0
 */
package utils;

public class IllegalOperandException extends Exception {

    /**
     * Constructs a IllegalOperandException with no detail message.
     */
    public IllegalOperandException() {
        super();
    }

    /**
     * Constructs a IllegalOperandException with a detail message
     *
     * @param s - message
     */
    public IllegalOperandException(String s) {
        super(s);
    }


}
