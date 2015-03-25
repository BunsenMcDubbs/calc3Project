/**
 * This class manages the calculations to manipulate matrices and vectors.
 * IllegalOperandExceptions are thrown when the parameters for functions
 * aren't matching.
 *
 * @author Andrew Dai
 * @version 1.0
 */
package utils;

public class LinearAlgebra {

    /**
     * Multiplies a given matrix and vector together and returns the resulting
     * vector.
     * @param m matrix
     * @param v vector to multiply with the matrix
     * @return resulting vector
     * @throws IllegalOperandException when dimensions don't match
     */
    public static Vector matrixVectorMultiply(Matrix m, Vector v) throws
        IllegalOperandException {

        if (m.getWidth() != v.getLength()) {
            String s = "Cannot multiply together matrix and vector with "
                + "mismatching dimensions.\nMatrix width (" + m.getWidth()
                + ") must " + "equal vector length (" + v.getLength() + ")";
            throw new IllegalOperandException(s);
        }

        double[] prod = new double[m.getHeight()];

        for (int i = 0; i < m.getHeight(); i++) {
            double x = 0;
            for (int j = 0; j < m.getWidth(); j++) {
                x += m.get(i, j) * v.get(j);
            }
            prod[i] = x;
        }

        return new Vector(prod);
    }

    /**
     * Adds two matrices together and returns the resulting matrix.
     *
     * @param m1 - first matrix
     * @param m2 - second matrix
     * @return the matrix sum
     * @throws IllegalOperandException when dimensions don't match
     */
    public static Matrix matrixAdd(Matrix m1, Matrix m2) throws
        IllegalOperandException {

        if (m1.getHeight() != m2.getHeight() || m1.getWidth() != m2.getWidth())
        {
            String s = "Cannot add together two matrices of different "
                + "dimensions. (" + m1.getHeight() + "x" + m1.getWidth()
                + " and " + m2.getHeight() + "x" + m2.getWidth() + ")";
            throw new IllegalOperandException(s);
        }

        double[][] sum = new double[m1.getHeight()][m1.getWidth()];
        for (int i = 0; i < m1.getHeight(); i++) {
            for (int j = 0; j < m1.getWidth(); j++) {
                sum[i][j] = m1.get(i, j) + m2.get(i, j);
            }
        }
        return new Matrix(sum);
    }

    /**
     * Returns the dot product of two given vectors.
     *
     * @param v1 - first vector
     * @param v2 - second vector
     * @return dot product as a double value
     * @throws IllegalOperandException when dimensions don't match
     */
    public static double dotProduct(Vector v1, Vector v2) throws
        IllegalOperandException {

        if (v1.getLength() != v2.getLength()) {
            String s = "Cannot obtain the dot product of two vectors of "
                + "different lengths.\n"
                + "(" + v1.getLength() + " and " + v2.getLength() + ")";
            throw new IllegalOperandException(s);
        }
        double dot = 0;
        for (int i = 0; i < v1.getLength(); i++) {
            dot += v1.get(i) * v2.get(i);
        }
        return dot;
    }

    /**
     * Adds two vectors together and returns the sum.
     * @param v1 - first vector
     * @param v2 - second vector
     * @return the resulting vector sum
     * @throws IllegalOperandException when dimensions don't match
     */
    public static Vector vectorAdd(Vector v1, Vector v2) throws
        IllegalOperandException {

        if (v1.getLength() != v2.getLength()) {
            String s = "Cannot add together two vectors of different lengths.\n"
                + "(" + v1.getLength() + " and " + v2.getLength() + ")";
            throw new IllegalOperandException(s);
        }

        double[] sum = new double[v1.getLength()];
        for (int i = 0; i < sum.length; i++) {
            sum[i] = v1.get(i) + v2.get(i);
        }
        return new Vector(sum);
    }

    /**
     * Finds the LU factorization and error of a given matrix
     * @param m matrix to LU factorize
     * @return a Result object where the first Matrix (a) is L
     * and the second (b) is U
     */
    public static Result lu_fact(Matrix m) {
        return null;
    }

    /**
     * Finds the QR factorization and error of a given matrix
     * with the Householders method.
     * @param m matrix to QR factorize
     * @return a Result object where the first Matrix (a) is Q
     * and the second (B) is R
     */
    public static Result qr_fact_househ(Matrix m) {
        return null;
    }

    /**
     * Finds the QR factorization and error of a given matrix
     * with the Givens Rotation method.
     * @param m matrix to QR factorize
     * @return a Result object where the first Matrix (a) is Q
     * and the second (B) is R
     */
    public static Result qr_fact_givens(Matrix m) {
        return null;
    }

    public static Vector solve_lu_b(Matrix m, Vector v) {
        return null;
    }

    public static Vector solve_qr_b(Matrix m, Vector v) {
        return null;
    }

}
