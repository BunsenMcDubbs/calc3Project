/**
 * Thrown to indicate when an index for a Vector is out of range.
 *
 * @author Andrew Dai
 * @version 1.0
 */
package utils;

public class VectorIndexOutOfBoundsException extends IndexOutOfBoundsException {

    /**
     * Constructs a VectorIndexOutOfBoundsException with no detail message.
     */
    public VectorIndexOutOfBoundsException() {
        super();
    }

    /**
     * Constructs a VectorIndexOutOfBoundsException with a detail message
     *
     * @param s - message
     */
    public VectorIndexOutOfBoundsException(String s) {
        super(s);
    }

    /**
     * Constructs a VectorIndexOutOfBoundsException with a detail message
     * containing the illegal index.
     *
     * @param i - illegal index
     */
    public VectorIndexOutOfBoundsException(int i) {
        super("" + i);
    }

}
