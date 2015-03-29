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
                // Row reduction (k - column)
                for (int k = i; k < nextRow.length; k++) {
                    u[j][k] = nextRow[k] - pRow[k] * ratio;
                }
            }
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
        Matrix r = new Matrix(a.cloneRaw());
        Matrix q = new IdentityMatrix(a.getRows()).getMat();
        // Iterating though the "sub matrices"
        // a(i, i) is the top left corner of the sub matrix
        for (int i = 0; i < r.getRows() - 1; i++) {
            // current length
            int currLen =  r.getRows() - i;
            // first column in the sub matrix
            double[] ai = new double[currLen];
            // determining the length of ai
            double length = 0;
            // iterating down the rows to construct col vector
            for (int j = i; j < r.getRows(); j++) {
                ai[j - i] = r.get(j, i);
                length += ai[j - i] * ai[j - i];
            }
            length = Math.sqrt(length);
            ai[0] += length; // adding e(i) * length to a(i)
            Vector u = new Vector(ai).normalizeVector();
            IdentityMatrix id = new IdentityMatrix(currLen);
            // (u)(uTrans) = matrix of n by n with n is the length of u
            double[][] uut = new double[u.getLength()][u.getLength()];
            // calculating 2 * u * uTrans
            for (int row = 0; row < uut.length; row++) {
                for (int col = row; col < uut.length; col++) {
                    // takes of advantage of the fact that this
                    // matrix is symmetrical
                    uut[row][col] = 2 * u.get(row) * u.get(col);
                    uut[col][row] = uut[row][col];
                }
            }
            // "hiSmall" is the sub matrix of H(i) that needs to be
            // reintegrated into the bigger H(i) matrix
            Matrix hiSmall = LinearAlgebra.matrixSubtract(id, new Matrix(uut));
            // copying back into a full sized H
            double[][] hiBigTemp = new IdentityMatrix(r.getRows()).cloneRaw();
            for (int row = 0; row < hiSmall.getWidth(); row++) {
                for (int col = 0; col < hiSmall.getCols(); col++) {
                    hiBigTemp[row + i][col + i] = hiSmall.get(row, col);
                }
            }
            Matrix hiBig = new Matrix(hiBigTemp);
            q = LinearAlgebra.matrixMultiply(q, transpose(hiBig));
            r = LinearAlgebra.matrixMultiply(hiBig, r);
        }
        double error = norm(
                LinearAlgebra.matrixSubtract(
                        LinearAlgebra.matrixMultiply(q, r), a));

        return new Result(q, r, error);
    }

    /**
     * Finds the QR factorization and error of a given matrix
     * with the Givens Rotation method.
     * This method performs best/assumes a square matrix
     * @param a matrix to QR factorize
     * @return a Result object where the first Matrix (a) is Q
     * and the second (B) is R
     */
    public static Result qr_fact_givens(Matrix a) {
        Matrix q = new IdentityMatrix(a.getRows()).getMat();
        Matrix r = a.getMat();

        // Traverse down the lower triangle to rotate and zero-out
        // elements to obtain R
        // Q will equal G(1)t * G(2)t * ... etc
        for (int row = 1; row < r.getRows(); row++) {
            for (int col = 0; col < row; col++) {
//                System.out.println("Eliminating element at: " + row + " " + col);
                double x = r.get(col, col);
                double y = r.get(row, col);
                // using Math.hypot to obtain sqrt(x^2 + y^2) more accurately
                // see http://en.wikipedia.org/wiki/Hypot
                double hypot = Math.hypot(x, y);
                // calculating cos(theta)
                double c = x / hypot;
                // calculating sin(theta)
                double s = -y / hypot;
//                System.out.println("cos = " + c + " sin = " + s);
                // Construct the G(i) matrix
                double[][] gi = new IdentityMatrix(a.getRows()).cloneRaw();
                gi[col][col] = c;
                gi[col][row] = -s;
                gi[row][col] = s;
                gi[row][row] = c;
                // R = ... * G(3) * G(2) * G(1) * A
                r = LinearAlgebra.matrixMultiply(new Matrix(gi), r);
                // Q = G(1)t * G(2)t * ...
                q = LinearAlgebra.matrixMultiply(q, transpose(new Matrix(gi)));
//                System.out.println("G(i)\n" + new Matrix(gi));
//                System.out.println("A(i)\n" + r);
            }
        }

        Matrix qxr = LinearAlgebra.matrixMultiply(q, r);
        double error = norm(LinearAlgebra.matrixSubtract(qxr, a));
        return new Result(q, r, error);
    }

    /**
     * Solve for x in Ax = b with LU factorization
     *
     * @param a matrix to be LU factorized
     * @param b
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
        return LinearAlgebra.matrixVectorMultiply(uInv,
                LinearAlgebra.matrixVectorMultiply(lInv, b));
    }

    /**
     * Solve for x in Ax = b with QR factorization
     * This method currently uses Householder's method to
     * perform QR factorization
     *
     * @param a matrix to be QR factorized and multiplied with the vector
     * @param b
     * @return the resultant vector
     */
    public static Vector solve_qr_b(Matrix a, Vector b) {
        Result qr = qr_fact_househ(a);
        Matrix q = qr.getA();
        Matrix r = qr.getB();
        Matrix qT = transpose(q);
        Matrix rInv = inverseUp(r);
        // Ax = b
        // ==> QRx = b
        // ==> x = (rInv) ((qT) (b))
        return LinearAlgebra.matrixVectorMultiply(rInv,
                LinearAlgebra.matrixVectorMultiply(qT, b));
    }

    /**
     * Solve for x in Ax = b with QR factorization
     * This method currently uses Householder's method to
     * perform QR factorization
     *
     * @param a matrix to be QR factorized and multiplied with the vector
     * @param b
     * @return the resultant vector
     */
    public static Vector solve_qr_b_givens(Matrix a, Vector b) {
        Result qr = qr_fact_givens(a);
        Matrix q = qr.getA();
        Matrix r = qr.getB();
        Matrix qT = transpose(q);
        Matrix rInv = inverseUp(r);
        // Ax = b
        // ==> QRx = b
        // ==> x = (rInv) ((qT) (b))
        return LinearAlgebra.matrixVectorMultiply(rInv,
                LinearAlgebra.matrixVectorMultiply(qT, b));
    }

    /**
     * Finds the norm of the given matrix (max value)
     * @param mat matrix to find the norm of
     * @return the maximum value (absolute)
     */
    public static double norm(AbstractMatrix mat) {
        double norm = Math.abs(mat.get(0, 0));
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
     * @param mat given matrix (lower triangular)
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
//            System.out.println("Original:\n" + new Matrix(original) +
//                  "\nInverse:\n" + new Matrix(inverse));
            double pivot = original[i][i]; // TODO fix later if pivot = 0
            // Goes through all the rows underneath the "i'th" row (j -> row)
            for (int j = i + 1; j < original.length; j++) {
                // Row reduce for original and copy operations to inverse
                double ratio = original[j][i] / pivot;
                // Row reduction (k - column)
                for (int k = 0; k < original[i].length; k++) {
                    original[j][k] -= original[i][k] * ratio;
                    inverse[j][k] -= inverse[i][k] * ratio;
                }
            }
            // Row reduce so inverse pivots are 1 (j -> column)
            for (int j = i; j < inverse[i].length; j++) {
                inverse[i][j] /= pivot;
            }
        }

        return new Matrix(inverse);
    }

    /**
     * Finds the inverse of a matrix with backwards substitution
     * @param mat given matrix (upper triangular)
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
        for (int i = original.length - 1; i >= 0; i--) {
//            System.out.println("Original:\n" + new Matrix(original) +
//                  "\nInverse:\n" + new Matrix(inverse));
            double pivot = original[i][i]; // TODO fix later if pivot = 0
            // Goes through all the rows underneath the "i'th" row
            for (int j = i - 1; j >= 0; j--) {
                // Row reduce for original and copy operations to inverse
                double ratio = original[j][i] / pivot;
                // Row reduction (k -> column)
                for (int k = 0; k < original[i].length; k++) {
                    original[j][k] -= original[i][k] * ratio;
                    inverse[j][k] -= inverse[i][k] * ratio;
                }
            }
            // Row reduce so inverse pivots are 1 (j -> column)
            for (int j = i; j < inverse[i].length; j++) {
                inverse[i][j] /= pivot;
            }
        }

        return new Matrix(inverse);
    }

    public static Matrix transpose(AbstractMatrix mat) {
        double[][] trans = new double[mat.getCols()][mat.getRows()];
        for (int i = 0; i < trans.length; i++) {
            for (int j = 0; j < trans[0].length; j++) {
                trans[i][j] = mat.get(j, i);
            }
        }
        return new Matrix(trans);
    }

    public String toString() {
        return mat.toString();
    }

}
