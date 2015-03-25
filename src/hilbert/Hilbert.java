package hilbert;

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

    public String toString() {
        return mat.toString();
    }

    public static void main(String[] args) {
        System.out.println(new Hilbert(4));
    }

}
