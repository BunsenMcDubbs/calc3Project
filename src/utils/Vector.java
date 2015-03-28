/**
 * Immutable abstraction for Vector
 *
 * @author Andrew Dai
 * @version 1.3
 */
package utils;

public class Vector {

    /*
    Create final instance variables
    */
    private final double[] vector;
    private final int length;

    /**
     * Initialize instance variables
     * @param vector array representation of vector
     */
    public Vector(double[] vector) {
        this.vector = vector;
        length = vector.length;
    }

    /**
     * Gets value located at specified index
     * @param i index in vector
     * @return double located at index 'i' in vector
     */
    public double get(int i) {
        try {
            return vector[i];
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new VectorIndexOutOfBoundsException(i);
        }
    }

    /**
     * Get's the length of the Vector.
     * @return number of components in vector
     */
    public int getLength() {
        return length;
    }

    /**
     * String representation of vector with components
     * separated by tabs
     * @return String representation of vector
     */
    public String toString() {
        String s = "{ ";
        for (int i = 0; i < length - 1; i++) {
            s += String.format("%02.3f\t", get(i));
        }
        if (length > 0) {
            s += String.format("%02.3f", get(length - 1));
        }
        return s + " }";
    }
}
