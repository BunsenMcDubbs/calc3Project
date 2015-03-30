package convolution;

import java.util.Random;

/**
 * Created by andrew on 3/29/15.
 */
public class Convolution {

    static Random r = new Random();
    static final int MAX_ITERATIONS = 500;

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
        return jacobiHelp(new BitMatrix(luRaw), y, x0, tol * tol);
    }

    private static BitMatrixAndCount jacobiHelp(BitMatrix t, BitMatrix y,
                                    BitMatrix prev, double tolSq)
            throws Exception{

        int count = 1;
        int diffNormSq = 0;
        BitMatrix next = prev;
        do {
            if (count > MAX_ITERATIONS) {
                System.out.println(count);
                throw new Exception("Unable to approach convergence " +
                        "before exceeding the maximum number of iterations" +
                        " (" + MAX_ITERATIONS + ")");
            }
            prev = next;
            next = BitMatrix.matrixAdd(BitMatrix.matrixMultiply(t, next), y);
            // now we need to check if |xi1 - xi| < tol
            // BUT when bitwise, component-wise subtraction followed with
            // squaring is the same as only component-wise addition
            BitMatrix diff = BitMatrix.matrixAdd(next, prev);
            diffNormSq = 0;
            for (int i = 0; i < diff.getRows(); i++) {
                diffNormSq += diff.get(i, 0);
            }
            count++;
            System.out.println(next);
        } while (diffNormSq > tolSq);
        return new BitMatrixAndCount(next, count);
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
        return getAHelper(length, BitMatrix.intsToBoolsVecRaw(1, 0, 1, 1));
    }

    public static BitMatrix getA1(int length) {
        return getAHelper(length, BitMatrix.intsToBoolsVecRaw(1, 1, 0, 1));
    }

    private static BitMatrix getAHelper(int length, boolean[] row) {
        boolean[][] id = BitMatrix.identityRaw(length + 3, length);
        for (int i = 0; i < id.length; i++) {
            int j = i < length ? i : length - 1;
            for (; j > i - row.length && j >= 0; j--) {
                id[i][j] = id[i][j] || row[i - j];
            }
        }
        return new BitMatrix(id);
    }


}
