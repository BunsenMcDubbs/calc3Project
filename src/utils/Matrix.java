/**
 * Immutable abstraction of Matrix.
 *
 * @author Andrew Dai
 * @version 1.3
 */
package utils;

public final class Matrix {

    /*
    Create final instance variables
    */
    private final double[][] matrix;
    private final int height;
    private final int width;

    /**
     * Initialize instance variables
     * @param matrix 2D array representation of Matrix
     */
    public Matrix(double[][] matrix) {
        this.matrix = matrix;
        height = matrix.length;
        if (height == 0) {
            width = 0;
        } else { width = matrix[0].length; }
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

    public Vector getCol(int i) {
        // TODO
        return null;
    }

    public double[] getRow(int i) {
        return matrix[i];
    }

    /**
     * Get's the height of the matrix.
     * @return number of rows in matrix
     */
    public int getHeight() {
        return height;
    }

    public int getRows() { return getHeight(); }

    /**
     * Get's the width of the matrix.
     * @return number of columns in matrix
     */
    public int getWidth() {
        return width;
    }

    public int getCols() { return getWidth(); }

    /**
     * Gets String representation of matrix.
     * Columns separated by tabs, rows by new lines.
     * @return String representation of matrix.
     */
    public String toString() {
        String s = "";
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width - 1; j++) {
                s += matrix[i][j] + "\t";
            }
            if (width > 0) {
                s += matrix[i][width - 1];
            }
            s += "\n";
        }
        return s;
    }
}
