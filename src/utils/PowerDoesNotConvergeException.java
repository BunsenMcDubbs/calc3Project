/**
 * Exception that represesnts that the power method
 * does not converge to an eigen vector.
 * @author William Greenleaf
 * @version 1.0
 */
package utils;
public class PowerDoesNotConvergeException extends Exception {
    /**
     * Creates PowerDoesNotConvergeException with default message.
     */
    public PowerDoesNotConvergeException() {
        super();
    }
    
    /**
     * Creates PowerDoesNotConvergeException with string s as message.
     * @param s Exception message
     */
    public PowerDoesNotConvergeException(String s) {
        super(s);
    }
}