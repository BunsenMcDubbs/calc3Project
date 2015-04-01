package convolution;

import java.util.Random;

import utils.*;

/**
 * Created by andrew on 3/29/15.
 */
public class Convolution {

    static Random r = new Random();
    static final int MAX_ITERATIONS = 2000;

    /**
     * Performs Jacobi iteration to solve for vector x in Ax = y on
     * matrices and vectors of doubles.
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
    public static VectorAndCount jacobi(AbstractMatrix a, Vector y,
                                           Vector x0, double tol)
            throws Exception {

        double tolSq = tol * tol; // tolerance squared to make comparing
                                  // to the norm of the difference easier
                                  // (no square rooting)
        double diffNormSq; // norm of the difference between iterations squared.
        Vector prev = x0;
        Vector next = prev;
        int count = 0; // number of iterations performed
        do {
            if (count > MAX_ITERATIONS) {
                throw new Exception("Unable to approach convergence " +
                        "before exceeding the maximum number of iterations" +
                        " (" + MAX_ITERATIONS + ")");
            }
            prev = next;
            double[] nextRaw = new double[prev.getLength()];
            for (int i = 0; i < a.getRows(); i++) {
                nextRaw[i] = y.get(i);
                for (int j = 0; j < a.getCols(); j++) {
                    if (i != j) {
                        nextRaw[i] -= (a.get(i, j) * prev.get(j));
                    }
                }
                nextRaw[i] /= a.get(i, i);
            }
            next = new Vector(nextRaw);
            // now we need to check if |xi1 - xi| < tol
            // BUT when bitwise, component-wise subtraction followed with
            // squaring is the same as *only* component-wise addition
            Vector diff = LinearAlgebra.vectorSubtract(next, prev);
            diffNormSq = 0;
            for (int i = 0; i < diff.getLength(); i++) {
                diffNormSq += Math.pow(diff.get(i), 2);
            }
            count++;
        } while (diffNormSq > tolSq);
        return new VectorAndCount(next, count);
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
    public static VectorAndCount gauss_seidel(AbstractMatrix a, Vector b,
                                                 Vector x0, double tol)
            throws Exception {

        double tolSq = tol * tol;
        // Copy x0 into xRaw
        double[] xRaw = new double[x0.getLength()];
        for (int i = 0; i < xRaw.length; i++) {
            xRaw[i] = x0.get(i);
        }
        int count = 0;
        double diffNormSq;
        do {
            if (count > MAX_ITERATIONS) {
                throw new Exception("Unable to approach convergence " +
                        "before exceeding the maximum number of iterations" +
                        " (" + MAX_ITERATIONS + ")");
            }
            diffNormSq = 0;
            for (int i = 0; i < xRaw.length; i++) {
                double xiNext = b.get(i);
                for (int j = 0; j < xRaw.length; j++) {
                    if (i != j) {
                        xiNext -= (xRaw[j] * a.get(i, j));
                    }
                }
                xiNext /= a.get(i, i);
                diffNormSq += Math.pow((xiNext - xRaw[i]), 2);
                xRaw[i] = xiNext;
            }
            count++;
        } while (diffNormSq > tolSq);
        return new VectorAndCount(new Vector(xRaw), count);
    }

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
            boolean[] nextRaw = new boolean[prev.getRows()];
            for (int i = 0; i < nextRaw.length; i++) {
                nextRaw[i] = y.getBool(i, 0);
                for (int j = 0; j < a.getCols(); j++) {
                    if (i != j) {
                        nextRaw[i] = nextRaw[i] !=
                                (a.getBool(i, j) && nextRaw[j]);
                    }
                }
            }
            next = new BitMatrix(nextRaw);
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

    public static BitMatrix getRandomVector(int length) {
        boolean[] v = new boolean[length];
        for (int i = 0; i < length; i++) {
            v[i] = r.nextBoolean();
        }
        return new BitMatrix(v);
    }

    public static BitMatrix jacobiFindXFromY0(BitMatrix y0) {
        BitMatrix a0 = getA0(y0.getRows() - 3);
        BitMatrix x0 = new BitMatrix(new boolean[y0.getRows() - 3]);
        try {
            return jacobi(a0, y0, x0, 0).getVector();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public static BitMatrix jacobiFindXFromY1(BitMatrix y1) {
        BitMatrix a1 = getA1(y1.getRows() - 3);
        BitMatrix x0 = new BitMatrix(new boolean[y1.getRows() - 3]);
        try {
            return jacobi(a1, y1, x0, 0).getVector();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static BitMatrix gaussSeidelFindXFromY0(BitMatrix y0) {
        BitMatrix a0 = getA0(y0.getRows() - 3);
        BitMatrix x0 = new BitMatrix(new boolean[y0.getRows() - 3]);
        try {
            return gauss_seidel(a0, y0, x0, 0).getVector();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static BitMatrix gaussSeidelFindXFromY1(BitMatrix y1) {
        BitMatrix a1 = getA1(y1.getRows() - 3);
        BitMatrix x0 = new BitMatrix(new boolean[y1.getRows() - 3]);
        try {
            return gauss_seidel(a1, y1, x0, 0).getVector();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
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

        int height = length + row.length - 1;
        boolean[][] id = BitMatrix.identityRaw(height, length);

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < row.length && i - j >= 0; j++) {
                id[i][i - j] = row[j];
            }
        }
        return new BitMatrix(id);
    }


}
