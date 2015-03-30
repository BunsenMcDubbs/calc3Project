package convolution;

import java.util.Random;

/**
 * Created by andrew on 3/29/15.
 */
public class Convolution {

    static Random r = new Random();

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
