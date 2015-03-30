package convolution;

import utils.AbstractMatrix;

/**
 * Created by andrew on 3/29/15.
 */
public class BitMatrix extends AbstractMatrix{

    final boolean[][] mat;

    public BitMatrix(boolean[][] mat) {
        super(mat.length, mat[0].length);
        this.mat = mat;
    }

    public BitMatrix(boolean[] vec) {
        super(vec.length, 1);
        mat = new boolean[vec.length][1];
        for (int i = 0; i < vec.length; i++) {
            mat[i][0] = vec[i];
        }
    }

    public BitMatrix(int[][] ints) {
        super(ints.length, ints[0].length);
        mat = new boolean[ints.length][ints[0].length];
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[0].length; j++) {
                mat[i][j] = ints[i][j] != 0;
            }
        }
    }

    public BitMatrix(int[] ints) {
        super(ints.length, 1);
        mat = new boolean[ints.length][1];
        for (int i = 0; i < mat.length; i++) {
            mat[i][0] = ints[i] != 0;
        }
    }

    @Override
    public double get(int i, int j) {
        return mat[i][j] ? 1 : 0;
    }

    public boolean getBool(int i, int j) {
        return mat[i][j];
    }

    public static boolean[][] identityRaw(int height, int width) {
        int smaller = height < width ? height : width;
        boolean[][] mat = new boolean[height][width];
        for (int i = 0; i < smaller; i++) {
            mat[i][i] = true;
        }
        return mat;
    }

    public static BitMatrix identity(int height, int width) {
        return new BitMatrix(identityRaw(height, width));
    }

    public static boolean[] intsToBoolsVecRaw(int... ints) {
        boolean[] vec = new boolean[ints.length];
        for (int i = 0; i < vec.length; i++) {
            vec[i] = ints[i] != 0;
        }
        return vec;
    }

    public static BitMatrix intsToBoolsVec(int... ints) {
        return new BitMatrix(intsToBoolsVecRaw(ints));
    }

    public static BitMatrix matrixMultiply(BitMatrix a, BitMatrix b) {
        boolean[][] r = new boolean[a.getRows()][b.getCols()];
        for (int i = 0; i < r.length; i++) {
            for (int j = 0; j < r[0].length; j++) {
                for (int k = 0; k < a.getCols(); k++) {
                    // "XOR" aka "Add"
                    // "AND" aka "Multiply"
                    r[i][j] = r[i][j] != (a.getBool(i, k) && b.getBool(k, j));
                }
            }
        }
        return new BitMatrix(r);
    }

    /**
     * This method takes two BitMatrices and performs binary addition
     * on the elements to produce a final result.
     * @param a
     * @param b
     * @return
     */
    public static BitMatrix matrixAdd(BitMatrix a, BitMatrix b) {
        boolean[][] r = new boolean[a.getRows()][b.getCols()];
        for (int i = 0; i < r.length; i++) {
            for (int j = 0; j < r[0].length; j++) {
                // "XOR" aka "Add"
                r[i][j] = a.getBool(i, j) != b.getBool(i, j);
            }
        }
        return new BitMatrix(r);
    }

    @Override
    public String toString() {
        String s = "";
        if (width == 1) {
            s += "{";
            for (int i = 0; i < mat.length; i++) {
                s += mat[i][0] ? " 1 " : " 0 ";
            }
            s += "}";
        } else {
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    s += " " + (mat[i][j] ? "1" : "0") + " ";
                }
                s += "\n";
            }
        }
        return s;
    }

}
