/**
 * Immutable abstraction of Matrix.
 *
 * @author Andrew Dai
 * @version 1.3
 */
package utils;

public final class Matrix extends AbstractMatrix{

    /*
    Create final instance variables
    */
    private final double[][] matrix;

    /**
     * Initialize instance variables
     * @param matrix 2D array representation of Matrix
     */
    public Matrix(double[][] matrix) {
        super(matrix.length, matrix[0].length);
        this.matrix = matrix;
    }

    /**
     * Gets value located at specified row and column
     * @param i row
     * @param j column
     * @return double located at row i and column j in matrix
     */
    public double get(int i, int j) {
        try {
            return matrix[i][j];
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new MatrixIndexOutOfBoundsException(i, j);
        }
    }

    public double[] getCol(int i) {
        // TODO
        return null;
    }

    public double[] getRow(int i) {
        return matrix[i];
    }
}
