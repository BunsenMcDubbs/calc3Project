package convolution;

import java.util.Random;

/**
 * Created by andrew on 3/29/15.
 */
public class Convolution {

    static Random r = new Random();
    static final int MAX_ITERATIONS = 2000;

    /**
     * Performs Jacobi iteration to solve for vector x in Ax = y on
     * matrices and vectors of booleans (bits).
     * Jacobi iteration will continue until the difference between
     * iterations is below the tolerance value. If MAX_ITERATIONS
     * number of iterations has been performed and the difference
     * is still above the tolerance value, then an Exception will
     * be thrown describing this error.
     *
     * @param a matrix A
     * @param y vector y
     * @param x0 initial guess for vector x
     * @param tol tolerance value
     * @return the final vector x and the number of iterations
     * performed
     * @throws Exception when the tolerance value is unable to be
     * attained even after MAX_ITERATIONS number of iterations
     */
    public static BitMatrixAndCount jacobi(BitMatrix a, BitMatrix y,
                                           BitMatrix x0, double tol)
            throws Exception {

        // Copying the diagonal out the given matrix (a)
        // BUT because the diagonal MUST be all "1's" then it
        // would resemble an identity matrix. Thus, we can simply
        // delete them from matrix (a) and pretend we are still
        // using it.
        // "luRaw" the boolean matrix of (a) with the diagonal
        // elements set to 0/false.
        boolean[][] luRaw = new boolean[a.getRows()][a.getCols()];
        for (int i = 0; i < a.getRows(); i++) {
            for (int j = 0; j < a.getCols(); j++) {
                if (i != j) {
                    luRaw[i][j] = a.getBool(i, j);
                }
            }
        }
        BitMatrix t = new BitMatrix(luRaw);
        double tolSq = tol * tol; // tolerance squared to make comparing
                                  // to the norm of the difference easier
                                  // (no square rooting)
        int diffNormSq; // norm of the difference between iterations squared.
        BitMatrix prev = x0;
        BitMatrix next = prev;
        int count = 0; // number of iterations performed
        do {
            if (count > MAX_ITERATIONS) {
                throw new Exception("Unable to approach convergence " +
                        "before exceeding the maximum number of iterations" +
                        " (" + MAX_ITERATIONS + ")");
            }
            prev = next;
            next = BitMatrix.matrixAdd(BitMatrix.matrixMultiply(t, next), y);
            // now we need to check if |xi1 - xi| < tol
            // BUT when bitwise, component-wise subtraction followed with
            // squaring is the same as *only* component-wise addition
            BitMatrix diff = BitMatrix.matrixAdd(next, prev);
            diffNormSq = 0;
            for (int i = 0; i < diff.getRows(); i++) {
                diffNormSq += diff.get(i, 0);
            }
            count++;
//            System.out.println(next + " Iteration: " + count);
        } while (diffNormSq > tolSq);
        return new BitMatrixAndCount(next, count);
    }

    /**
     *
     * @param a Matrix A should be a square matrix (invertible)
     * @param b
     * @param x0
     * @param tol
     * @return
     * @throws Exception
     */
    public static BitMatrixAndCount gauss_seidel(BitMatrix a, BitMatrix b,
                                                 BitMatrix x0, double tol)
            throws Exception{

        double tolSq = tol * tol;
        // Copy x0 into xRaw
        boolean[] xRaw = new boolean[x0.getRows()];
        for (int i = 0; i < xRaw.length; i++) {
            xRaw[i] = x0.getBool(i, 0);
        }
        int count = 0;
        int diffNormSq;
        do {
            if (count > MAX_ITERATIONS) {
                throw new Exception("Unable to approach convergence " +
                        "before exceeding the maximum number of iterations" +
                        " (" + MAX_ITERATIONS + ")");
            }
            diffNormSq = 0;
            for (int i = 0; i < xRaw.length; i++) {
                boolean xiNext = b.getBool(i, 0);
                for (int j = 0; j < xRaw.length; j++) {
                    if (i != j) {
                        xiNext = xiNext != (xRaw[j] && a.getBool(i, j));
                    }
                }
                if (xiNext != xRaw[i]) {
                    diffNormSq++;
                }
                xRaw[i] = xiNext;
            }
            count++;
        } while (diffNormSq > tolSq);
        return new BitMatrixAndCount(new BitMatrix(xRaw), count);
    }

    /**
     * Finds the inverse of a matrix with forwards substitution
     * @param mat given matrix (lower triangular)
     * @return inverse of the given matrix
     */
    public static BitMatrix inverseDown(BitMatrix mat) {
        if (mat.getRows() != mat.getCols()) {
            System.out.println("Matrix must be square to be invertible");
            return null;
        }
        boolean[][] inverse = new boolean[mat.getRows()][mat.getRows()];
        boolean[][] original = new boolean[mat.getRows()][mat.getRows()];

        // Copy matrix mat to original
        for (int row = 0; row < mat.getRows(); row++) {
            inverse[row][row] = true; // start with inverse as identity matrix
            for (int col = 0; col < mat.getCols(); col++) {
                original[row][col] = mat.getBool(row, col);
            }
        }
        // Goes through all the rows (i is the current-pivot-row)
        for (int i = 0; i < original.length; i++) {
            // Assume pivots are already 1 because else... this is hard
            // Goes through all the rows underneath the "i'th" row (j -> row)
            for (int j = i + 1; j < original.length; j++) {
                // Row reduce for original and copy operations to inverse
                // Row reduction (k - column)
                // stop if original[j][i] (element below pivot) is false/0
                // because no substitution is needed
                for (int k = 0; original[j][i] && k < original[i].length; k++) {
                    // != (XOR) is sort of like ADDITION/SUBTRACTION
                    original[j][k] = original[j][k] != original[i][k];
                    inverse[j][k] = inverse[j][k] != inverse[i][k];
                }
            }
            // Don't need to row reduce inverse because pivots are all 1
        }

        return new BitMatrix(inverse);
    }


    public static BitMatrix getRandomVector(int length) {
        boolean[] v = new boolean[length];
        for (int i = 0; i < length; i++) {
            v[i] = r.nextBoolean();
        }
        return new BitMatrix(v);
    }

    public static BitMatrix findy0(boolean[] xRaw) {
        return findy0(new BitMatrix(xRaw));
    }

    public static BitMatrix findy0(BitMatrix x) {
        BitMatrix a0 = getA0(x.getRows());
        return BitMatrix.matrixMultiply(a0, x);
    }

    public static BitMatrix findy1(boolean[] xRaw) {
        return findy1(new BitMatrix(xRaw));
    }

    public static BitMatrix findy1(BitMatrix x) {
        BitMatrix a1 = getA1(x.getRows());
        return BitMatrix.matrixMultiply(a1, x);
    }

    public static BitMatrix getA0(int length) {
        return getAHelper(length, BitMatrix.intsToBoolsVecRaw(1, 0, 1, 1), false);
    }

    public static BitMatrix getA1(int length) {
        return getAHelper(length, BitMatrix.intsToBoolsVecRaw(1, 1, 0, 1), false);
    }

    public static BitMatrix getA0Square(int length) {
        return getAHelper(length, BitMatrix.intsToBoolsVecRaw(1, 0, 1, 1), true);
    }

    public static BitMatrix getA1Square(int length) {
        return getAHelper(length, BitMatrix.intsToBoolsVecRaw(1, 1, 0, 1), true);
    }


    private static BitMatrix getAHelper(int length, boolean[] row,
                                        boolean square) {

        int height = length + row.length - 1;
        boolean[][] id = BitMatrix.identityRaw(height,
                square ? height : length);

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < row.length && i - j >= 0; j++) {
                id[i][i - j] = row[j];
            }
        }
        return new BitMatrix(id);
    }


}
