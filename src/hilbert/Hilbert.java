package hilbert;

import java.util.ArrayDeque;
import java.util.Deque;
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
     * @param a matrix to LU factorize, the matrix must be symmetric
     * @return a Result object where the first Matrix (a) is L
     * and the second (b) is U
     */
    public static Result lu_fact(Matrix a) {
        // starts as identity matrix (done below)
        double[][] l = new double[a.getRows()][a.getRows()];
        double[][] u = new double[a.getRows()][a.getCols()];

        // Copy matrix a to u
        for (int row = 0; row < a.getRows(); row++) {
            for (int col = 0; col < a.getCols(); col++) {
                u[row][col] = a.get(row, col);
            }
        }
        // Goes through all the rows (i is the current-pivot-row)
        for (int i = 0; i < a.getRows(); i++) {
            double pivot = u[i][i]; // TODO fix later if pivot = 0
            l[i][i] = 1; // makes L into an identity matrix
            double[] pRow = u[i];
            // Goes through all the rows underneath the "i'th" row
            for (int j = i + 1; j < a.getRows(); j++) {
                // Row reduce for u
                double[] nextRow = u[j];
                double ratio = nextRow[i] / pivot;
                // Copy things and construct l
                l[j][i] = ratio;
//                System.out.println(nextRow[i] + " / " + pivot + " = " + l[j][i]);
                // Row reduction (k - column)
                for (int k = i; k < nextRow.length; k++) {
                    u[j][k] = nextRow[k] - pRow[k] * ratio;
                }
            }
//            System.out.println("\n Now on row: " + i + "\n" + new Matrix(u));
        }

        Matrix L = new Matrix(l);
        Matrix U = new Matrix(u);
        Matrix LxU = LinearAlgebra.matrixMultiply(L, U);
        Matrix err = LinearAlgebra.matrixSubtract(LxU, a);
        return new Result(L, U, norm(err));
    }

    /**
     * Finds the QR factorization and error of a given matrix
     * with the Householders method.
     * @param a matrix to QR factorize
     * @return a Result object where the first Matrix (a) is Q
     * and the second (B) is R
     */
    public static Result qr_fact_househ(Matrix a) {
        Deque<Matrix> qParts = new ArrayDeque<Matrix>();
        double[][] r = new double[a.getRows()][a.getCols()];
        for (int i = 0; i < r.length; i++) {
            for (int j = 0; j < r[0].length; j++) {
                r[i][j] = a.get(i, j);
            }
        }
        // Iterating though the "sub matrices"
        // a(i, i) is the top left corner of the sub matrix
        for (int i = 0; i < r.length - 1; i++) {
            // first column in the sub matrix
            double[] ai = new double[r.length - i];
            // determining the length of ai
            double length = 0;
            // iterating down the rows and constructing col vector
            for (int j = i; j < r.length; j++) {
                ai[j - i] = r[j][i];
                length += ai[j - i] * ai[j - 1];
            }
            length = Math.sqrt(length);
            ai[0] += length;
            Vector u = new Vector(ai).normalizeVector();
            IdentityMatrix id = new IdentityMatrix(r.length - i);
            // (u)(uTrans)
            double[][] uut = new double[u.getLength()][u.getLength()];
            for (int row = 0; row < uut.length; row++) {
                for (int col = 0; col < uut.length; col++) {
                    uut[row][col] = 2 * u.get(row) * u.get(col);
                }
            }
            Matrix hi = LinearAlgebra.matrixSubtract(id, new Matrix(uut));

        }

        return null;
    }

    /**
     * Finds the QR factorization and error of a given matrix
     * with the Givens Rotation method.
     * @param a matrix to QR factorize
     * @return a Result object where the first Matrix (a) is Q
     * and the second (B) is R
     */
    public static Result qr_fact_givens(Matrix a) {

        return null;
    }

    /**
     * Solve for x in Ax = b with LU factorization
     *
     * @param a matrix to be LU factorized
     * @param b vector to multiply
     * @return the resultant vector
     */
    public static Vector solve_lu_b(Matrix a, Vector b) {
        Result lu = lu_fact(a);
        Matrix lInv = inverseDown(lu.getA());
        Matrix uInv = inverseUp(lu.getB());
        // Ax = b
        // A = LU
        // LUx = b
        // x = (Uinv)(Linv)b
        return LinearAlgebra.matrixVectorMultiply(uInv, LinearAlgebra.matrixVectorMultiply(lInv, b));
    }

    /**
     * Solve for x in Ax = b with QR factorization
     *
     * @param a matrix to be QR factorized and multiplied with the vector
     * @param v vector to multiply
     * @return the resultant vector
     */
    public static Vector solve_qr_b(Matrix a, Vector v) {
        Result qr = qr_fact_househ(a);
        Matrix q = qr.getA();
        Matrix r = qr.getB();

        return null;
    }

    /**
     * Finds the norm of the given matrix (max value)
     * @param mat matrix to find the norm of
     * @return the maximum value (absolute)
     */
    public static double norm(AbstractMatrix mat) {
        double norm = mat.get(0, 0);
        for (int i = 0; i < mat.getRows(); i++) {
            for (int j = 0; j < mat.getCols(); j++) {
                double curr = Math.abs(mat.get(i, j));
                if (curr > norm) {
                    norm = curr;
                }
            }
        }
        return norm;
    }

    /**
     * Finds the inverse of a matrix with forwards substitution
     * @param mat given matrix
     * @return inverse of the given matrix
     */
    public static Matrix inverseDown(AbstractMatrix mat) {
        if (mat.getRows() != mat.getCols()) {
            System.out.println("Matrix must be square to be invertible");
            return null;
        }
        double[][] inverse = new double[mat.getRows()][mat.getRows()];
        double[][] original = new double[mat.getRows()][mat.getRows()];

        // Copy matrix mat to original
        for (int row = 0; row < mat.getRows(); row++) {
            inverse[row][row] = 1; // makes L into an identity matrix
            for (int col = 0; col < mat.getCols(); col++) {
                original[row][col] = mat.get(row, col);
            }
        }
        // Goes through all the rows (i is the current-pivot-row)
        for (int i = 0; i < original.length; i++) {
//            System.out.println("Original:\n" + new Matrix(original) + "\nInverse:\n" + new Matrix(inverse));
            double pivot = original[i][i]; // TODO fix later if pivot = 0
            // Goes through all the rows underneath the "i'th" row (j - row)
            for (int j = i + 1; j < original.length; j++) {
                // Row reduce for original and copy operations to inverse
                double ratio = original[j][i] / pivot;
                // Row reduction (k - column)
                for (int k = 0; k < original[i].length; k++) {
                    original[j][k] -= original[i][k] * ratio;
                    inverse[j][k] -= inverse[i][k] * ratio;
                }
            }
            // Row reduce so inverse pivots are 1 (j - column)
            for (int j = i; j < inverse[i].length; j++) {
                inverse[i][j] /= pivot;
            }
        }

        return new Matrix(inverse);
    }

    /**
     * Finds the inverse of a matrix with backwards substitution
     * @param mat given matrix
     * @return inverse of the given matrix
     */
    public static Matrix inverseUp(AbstractMatrix mat) {
        if (mat.getRows() != mat.getCols()) {
            System.out.println("Matrix must be square to be invertible");
            return null;
        }
        double[][] inverse = new double[mat.getRows()][mat.getRows()];
        double[][] original = new double[mat.getRows()][mat.getRows()];

        // Copy matrix mat to original
        for (int row = 0; row < mat.getRows(); row++) {
            inverse[row][row] = 1; // makes L into an identity matrix
            for (int col = 0; col < mat.getCols(); col++) {
                original[row][col] = mat.get(row, col);
            }
        }
        // Goes through all the rows (i is the current-pivot-row)
        for (int i = original.length - 1; i > 0; i--) {
//            System.out.println("Original:\n" + new Matrix(original) + "\nInverse:\n" + new Matrix(inverse));
            double pivot = original[i][i]; // TODO fix later if pivot = 0
            // Goes through all the rows underneath the "i'th" row
            for (int j = i - 1; j >= 0; j--) {
                // Row reduce for original and copy operations to inverse
                double ratio = original[j][i] / pivot;
                // Row reduction (k - column)
                for (int k = 0; k < original[i].length; k++) {
                    original[j][k] -= original[i][k] * ratio;
                    inverse[j][k] -= inverse[i][k] * ratio;
                }
            }
            // Row reduce so inverse pivots are 1 (j - column)
            for (int j = i; j < inverse[i].length; j++) {
                inverse[i][j] /= pivot;
            }
        }

        return new Matrix(inverse);
    }

    public String toString() {
        return mat.toString();
    }

}
