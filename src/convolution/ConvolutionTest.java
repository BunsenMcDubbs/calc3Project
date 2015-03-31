package convolution;

import hilbert.Hilbert;

/**
 * Created by andrew on 3/29/15.
 */
public class ConvolutionTest {

    public static void main(String... args) {
//        findXWithY0ExampleJacobi();
//        findXWithY1ExampleJacobi();
//        testInverseDown();
//        findXWithY1ExampleGaussSeidel();
        testJacobiRandom(10000);
        System.out.println("\n\n");
        testGaussSeidelRandom(10000);
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

    public static void testJacobiRandom(int length) {
        System.out.printf("Jacobi decoding with %d length input%n", length);
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

    public static void testGaussSeidelRandom(int length) {
        System.out.printf("Gauss Seidel decoding with %d length input%n", length);
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
//            System.out.println("This is x from y0: " + y0x);
            System.out.println("This is x from y0: ");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            // Decode y1 ->
            BitMatrixAndCount y1x = Convolution.gauss_seidel(a1, y1, x0, 0);
            System.out.println("This is x from y1: ");
//            System.out.println("This is x from y1: " + y1x);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("This is x (again): " + x);
    }

    public static void findXWithY1ExampleGaussSeidel() {
        BitMatrix x0 = BitMatrix.intsToBoolsVec(1, 1, 0, 1, 1, 1, 1, 1);
        BitMatrix x = BitMatrix.intsToBoolsVec(1, 0, 1, 1, 0);
        BitMatrix y1 = Convolution.findy1(x);
        BitMatrix a1 = Convolution.getA1Square(x.getRows());
        System.out.println(x);
        try {
            System.out.println(Convolution.gauss_seidel(a1, y1, x0, 0));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }



}
