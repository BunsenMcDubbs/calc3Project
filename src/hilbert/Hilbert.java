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

    public Matrix getMat() { return mat; }

    /**
     * Finds the LU factorization and error of a given matrix
     * @param a matrix to LU factorize
     * @return a Result object where the first Matrix (a) is L
     * and the second (b) is U
     */
    public static Result lu_fact(Matrix a) {
        double[][] l = new double[a.getRows()][a.getRows()];
        double[][] u = new double[a.getRows()][a.getCols()];

        // Copy first row of matrix a to u
        for (int col = 0; col < a.getCols(); col++) {
            u[0][col] = a.get(0, col);
            double pivot = u[0][0];
            for (int row = 0; row < a.getRows(); row++) {
                l[row][0] = a.get(row, 0) / pivot;
            }
        }
        // Goes through all the rows (i is the current-pivot-row)
        for (int i = 0; i < a.getRows(); i++) {
            double pivot = a.get(i, i); // TODO fix later if pivot = 0
            l[i][i] = 1;
            double[] pRow = a.getRow(i);
            // Goes through all the rows underneath the "i'th" row
            for (int j = i + 1; j < a.getRows(); j++) {
                // Copy things and construct l
                l[j][i] = a.get(j, i) / pivot;
                // Row reduce for u
                double[] nextRow = a.getRow(j);
                double ratio = nextRow[i] / pivot;
                // Row reduction (k - column)
                for (int k = i; k < nextRow.length; k++) {
                    u[j][k] = nextRow[k] - pRow[k] * ratio;
                }
            }
        }

        // TODO find error
        Matrix L = new Matrix(l);
        Matrix U = new Matrix(u);
        Matrix LxU = LinearAlgebra.matrixMultiply(L, U);
        Matrix err = LinearAlgebra.matrixSubtract(LxU, a);
        // now find the norm of the err matrix
        // aka max eigenvalue of SYMMETRIC matrix
        double error = 0;
        // TODO USE WILLIAM'S POWER METHOD METHOD (max eigenvalue)
        return new Result(L, U, error);
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
        Hilbert a = new Hilbert(3);
        Result r = lu_fact(a.getMat());
        System.out.println(r.getA());
        System.out.println(r.getB());
        System.out.println(LinearAlgebra.matrixMultiply(r.getA(), r.getB()));
    }

}
