/**
 * Immutable abstraction for Vector
 *
 * @author Andrew Dai
 * @version 1.4
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
        // TODO how many digits?
        String s = "{ ";
        for (int i = 0; i < length - 1; i++) {
            s += String.format("%05.16f\t", get(i));
        }
        if (length > 0) {
            s += String.format("%05.16f", get(length - 1));
        }
        return s + " }";
    }
    
    /**
     * Calculates the magnitude of the vector.
     * Added by William Greenleaf.
     * @return double vector's magnitude
     */
     public double getMagnitude() {
         double sum = 0;
         for (double i : vector) {
             sum = sum + java.lang.Math.pow(i, 2.0);
         }
         return java.lang.Math.sqrt(sum);
    }
    
    /**
     * Returns a normalized version of the vector.
     * Added by William Greenleaf. 
     * @return Vector normalized copy of the vector
     */
     public Vector normalizeVector() {
        double[] newVec = new double[vector.length];
        for (int i = 0; i < vector.length; i++) {
            newVec[i] = vector[i];
        }
        double magVec = this.getMagnitude();
        for (int i = 0; i < newVec.length; i++) {
            newVec[i] = newVec[i]/magVec;
        }
        return new Vector(newVec);
    }
    
    /**
     * Returns absolute minimum of vector.
     * @return absolute minimum of vector
     */
    public double getAbsMin() {
        double min;
        min = vector[0];
        for (double i : vector) {
            if (Math.abs(i) > min) {
                min = i;
            }
        }
        return min;
    }
    
    /**
     * Returns maximum element of vector.
     * @return maximum element of vector
     */
    public double getMax() {
        double max = vector[0];
        for (double i : vector) {
            if (i > max) {
                max = i;
            }
        }
        return max;
    }
    
    /**
     * Divides all elemnts of vector by a scalar
     * @return vector divided by scalar
     */
    public Vector divideComponents(double quotient) {
        double[] newVec = new double[vector.length];
        for (int i = 0; i < vector.length; i++) {
             newVec[i] = vector[i];
        }
        for (int i = 0; i < newVec.length; i++) {
            newVec[i] = newVec[i]/quotient;
        }
        return new Vector(newVec);
    }
    
    /**
     * Creates a copy of a vector.
     * @return copied vector
     */
    public Vector deepCopy() {
        double[] newVec = new double[vector.length];
        for (int i = 0; i < vector.length; i++) {
             newVec[i] = vector[i];
        }
        return new Vector(newVec);
    }
}