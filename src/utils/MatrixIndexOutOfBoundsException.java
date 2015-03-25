/**
 * Thrown to indicate when an index for a matrix is out of range.
 *
 * @author Andrew Dai
 * @version 1.0
 */
package utils;

public class MatrixIndexOutOfBoundsException extends IndexOutOfBoundsException {

    /**
     * Constructs a MatrixIndexOutOfBoundsException with no detail message.
     */
    public MatrixIndexOutOfBoundsException() {
        super();
    }

    /**
     * Constructs a MatrixIndexOutOfBoundsException with a detail message
     *
     * @param s - message
     */
    public MatrixIndexOutOfBoundsException(String s) {
        super(s);
    }

    /**
     * Constructs a MatrixIndexOutOfBoundsException with a detail message
     * containing the illegal index.
     *
     * @param i - illegal index
     */
    public MatrixIndexOutOfBoundsException(int i) {
        super("" + i);
    }

    /**
     * Constructs a MatrixIndexOutOfBoundsException with a detail message
     * containing the illegal index.
     *
     * @param i - row
     * @param j - column
     */
    public MatrixIndexOutOfBoundsException(int i, int j) {
        super("" + i + ", " + j);
    }

}
