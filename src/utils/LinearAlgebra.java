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
    public static Vector matrixVectorMultiply(AbstractMatrix m, Vector v) {

        if (m.getWidth() != v.getLength()) {
            String s = "Cannot multiply together matrix and vector with "
                + "mismatching dimensions.\nMatrix width (" + m.getWidth()
                + ") must " + "equal vector length (" + v.getLength() + ")";
            System.out.println(s);
            return null;
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
    public static Matrix matrixAdd(Matrix m1, Matrix m2) {

        double[][] sum = new double[m1.getHeight()][m1.getWidth()];
        for (int i = 0; i < m1.getHeight(); i++) {
            for (int j = 0; j < m1.getWidth(); j++) {
                sum[i][j] = m1.get(i, j) + m2.get(i, j);
            }
        }
        return new Matrix(sum);
    }

    /**
     * Adds two matrices together and returns the resulting matrix.
     *
     * @param m1 - first matrix
     * @param m2 - second matrix
     * @return the matrix sum
     * @throws IllegalOperandException when dimensions don't match
     */
    public static Matrix matrixSubtract(AbstractMatrix m1, AbstractMatrix m2) {
        double[][] diff = new double[m1.getHeight()][m1.getWidth()];
        for (int i = 0; i < m1.getHeight(); i++) {
            for (int j = 0; j < m1.getWidth(); j++) {
                diff[i][j] = m1.get(i, j) - m2.get(i, j);
            }
        }
        return new Matrix(diff);
    }

    public static Matrix matrixMultiply(AbstractMatrix a, AbstractMatrix b) {
        double[][] r = new double[a.getRows()][b.getCols()];
        for (int i = 0; i < r.length; i++) {
            for (int j = 0; j < r[0].length; j++) {
                for (int k = 0; k < a.getCols(); k++) {
                    r[i][j] += a.get(i, k) * b.get(k, j);
                }
            }
        }
        return new Matrix(r);
    }

    public static Matrix matrixMultiply(ElementaryMatrix a, AbstractMatrix b) {
        double[][] r = new double[a.getRows()][b.getCols()];
        for (int i = 0; i < r.length; i++) {
            for (int j = 0; j < r[0].length; j++) {
                if (i == a.getTo()) {
                    r[i][j] = a.calc(b.get(a.getFrom(), j), r[i][j]);
                } else {
                    r[i][j] = b.get(i, j);
                }
            }
        }
        return new Matrix(r);
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
     */
    public static Vector vectorAdd(Vector v1, Vector v2) {

        double[] sum = new double[v1.getLength()];
        for (int i = 0; i < sum.length; i++) {
            sum[i] = v1.get(i) + v2.get(i);
        }
        return new Vector(sum);
    }

    /**
     * Subtracts two vectors and returns the difference.
     * @param v1 - first vector
     * @param v2 - second vector
     * @return the resulting vector diff
     */
    public static Vector vectorSubtract(Vector v1, Vector v2) {

        double[] diff = new double[v1.getLength()];
        for (int i = 0; i < diff.length; i++) {
            diff[i] = v1.get(i) - v2.get(i);
        }
        return new Vector(diff);
    }

}
