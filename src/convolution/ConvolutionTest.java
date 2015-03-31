package convolution;

import hilbert.*;
import utils.*;

/**
 * Created by andrew on 3/29/15.
 */
public class ConvolutionTest {

    public static void main(String... args) {
//        findXWithY0ExampleJacobi();
//        findXWithY1ExampleJacobi();
//        testInverseDown();
//        findXWithY1ExampleGaussSeidel();
//        testBitJacobiRandom(100);
//        testBitGaussSeidelRandom(100);
//        jacobiSanityTest();
//        gaussSeidelSanityTest();

    }

    public static void randomVecToString() {
        System.out.println(Convolution.getRandomVector(10));
    }

    public static void getA0For5LongVec() {
        System.out.println(Convolution.getA0(5));
    }

    public static void getA1For5LongVec() {
        System.out.println(Convolution.getA1(5));
    }

    public static void identityMult() {
        BitMatrix id = BitMatrix.identity(8, 5);
        BitMatrix x = BitMatrix.intsToBoolsVec(1, 0, 1, 1, 0);
        System.out.println(BitMatrix.matrixMultiply(id, x));
    }

    public static void findYExample() {
        BitMatrix x = BitMatrix.intsToBoolsVec(1, 0, 1, 1, 0);
        System.out.println(x);
        System.out.println(Convolution.findy0(x));
        System.out.println(Convolution.findy1(x));
    }

    public static void findXWithY0ExampleJacobi() {
        BitMatrix x0 = BitMatrix.intsToBoolsVec(1, 0, 0, 0, 0, 0, 0, 0);
        BitMatrix x = BitMatrix.intsToBoolsVec(1, 0, 1, 1, 0);
        BitMatrix y0 = Convolution.findy0(x);
        BitMatrix a0 = Convolution.getA0(x.getRows());
        try {
            System.out.println(Convolution.jacobi(a0, y0, x0, 0));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void findXWithY1ExampleJacobi() {
        BitMatrix x0 = BitMatrix.intsToBoolsVec(0, 0, 0, 0, 0, 0, 0, 0);
        BitMatrix x = BitMatrix.intsToBoolsVec(1, 0, 1, 1, 0);
        BitMatrix y1 = Convolution.findy1(x);
        BitMatrix a1 = Convolution.getA1(x.getRows());
        System.out.println("A1\n" + a1);
        try {
            System.out.println(Convolution.jacobi(a1, y1, x0, 0));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void testBitJacobiRandom(int length) {
        System.out.printf("Jacobi bit decoding with %d length input%n", length);
        BitMatrix x = Convolution.getRandomVector(length);
        System.out.println("This is x: " + x);
        System.out.println("\nEncoding");
        // Encode x -> y0
        BitMatrix y0 = Convolution.findy0(x);
        System.out.println("This is y0: " + y0);
        // Encode y -> y1
        BitMatrix y1 = Convolution.findy1(x);
        System.out.println("This is y1: " + y1);

        // find A0 and A1
        BitMatrix a0 = Convolution.getA0(length);
        BitMatrix a1 = Convolution.getA1(length);

        // x0 (first guess)
        BitMatrix x0 = new BitMatrix(new boolean[length + 3]);
        System.out.println("\nDecoding");
        try {
            // Decode y0 -> x
            BitMatrixAndCount y0x = Convolution.jacobi(a0, y0, x0, 0);
            System.out.println("This is x from y0: " + y0x);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            // Decode y1 ->
            BitMatrixAndCount y1x = Convolution.jacobi(a1, y1, x0, 0);
            System.out.println("This is x from y1: " + y1x);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("This is x (again): " + x);
    }

    public static void testBitGaussSeidelRandom(int length) {
        System.out.printf("Gauss Seidel bit decoding with %d length input%n", length);
        BitMatrix x = Convolution.getRandomVector(length);
        System.out.println("This is x: " + x);
        System.out.println("\nEncoding");
        // Encode x -> y0
        BitMatrix y0 = Convolution.findy0(x);
        System.out.println("This is y0: " + y0);
        // Encode y -> y1
        BitMatrix y1 = Convolution.findy1(x);
        System.out.println("This is y1: " + y1);

        // find A0 and A1
        BitMatrix a0 = Convolution.getA0(length);
        BitMatrix a1 = Convolution.getA1(length);

        // x0 (first guess)
        BitMatrix x0 = new BitMatrix(new boolean[length + 3]);
        System.out.println("\nDecoding");
        try {
            // Decode y0 -> x
            BitMatrixAndCount y0x = Convolution.gauss_seidel(a0, y0, x0, 0);
            System.out.println("This is x from y0: " + y0x);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            // Decode y1 -> x
            BitMatrixAndCount y1x = Convolution.gauss_seidel(a1, y1, x0, 0);
            System.out.println("This is x from y1: " + y1x);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("This is x (again): " + x);
    }

    public static void jacobiSanityTest() {
        double[][] testMat = new double[][] {
                {2, 0, 1},
                {1, 3, 2},
                {0, 2, 3}
        };
        Matrix mat = new Matrix(testMat);
        Vector b = new Vector(new double[]{ 14, 12, 5 });
        Vector x0 = new Vector(new double[3]);
        System.out.println(mat);
        VectorAndCount xSol = null;
        try {
            xSol = Convolution.jacobi(mat, b, x0, 10e-8);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Vector xSolLu = Hilbert.solve_lu_b(mat, b).getVector();
        System.out.println("xlu = " + xSolLu);
        System.out.println("xgs = " + xSol.getVector());
        System.out.println("Mat * xLU  = " +
                LinearAlgebra.matrixVectorMultiply(mat, xSolLu));
        System.out.println("Mat * xSol = " +
                LinearAlgebra.matrixVectorMultiply(mat, xSol.getVector()));
        System.out.println(b);
    }

    public static void gaussSeidelSanityTest() {
        double[][] testMat = new double[][] {
                {2, 0, 1},
                {1, 3, 2},
                {0, 2, 3}
        };
        Matrix mat = new Matrix(testMat);
        Vector b = new Vector(new double[]{ 14, 12, 5 });
        Vector x0 = new Vector(new double[3]);
        System.out.println(mat);
        VectorAndCount xSol = null;
        try {
            xSol = Convolution.gauss_seidel(mat, b, x0, 10e-8);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Vector xSolLu = Hilbert.solve_lu_b(mat, b).getVector();
        System.out.println("xlu = " + xSolLu);
        System.out.println("xgs = " + xSol.getVector());
        System.out.println("Mat * xLU  = " +
                LinearAlgebra.matrixVectorMultiply(mat, xSolLu));
        System.out.println("Mat * xSol = " +
                LinearAlgebra.matrixVectorMultiply(mat, xSol.getVector()));
        System.out.println(b);
    }

}
