package convolution;

/**
 * Created by andrew on 3/29/15.
 */
public class ConvolutionTest {

    public static void main(String... args) {
        findYExample();
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

}
