package hilbert;

import utils.*;

/**
 * Created by andrew on 3/23/15.
 */
public class Hilbert {

    private Matrix mat;

    /**
     * Constructs an n by n Hilbert matrix
     * @param n number of row/col for the square matrix
     */
    public Hilbert (int n) {
        double[][] mat = new double[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= i; j++) {
                mat[i][j] = 1. / (i + j + 1);
                mat[j][i] = mat[i][j];
            }
        }

        this.mat = new Matrix(mat);
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

    public String toString() {
        return mat.toString();
    }

    public static void main(String[] args) {
        System.out.println(new Hilbert(4));
    }

}
