package utils;

/**
 * Created by andrew on 3/28/15.
 */
public class ElementaryMatrix extends AbstractMatrix {

    int size;
    int row1;
    double scalar;
    int row2;

    public ElementaryMatrix(int size, int row1, double scalar,
                            int row2) {
        super(size, size);
        this.size = size;
        this.row1 = row1;
        this.scalar = scalar;
        this.row2 = row2;
    }

    public double get(int i, int j) {
        if (i == j) {
            if (row1 == row2) {
                return scalar;
            } else {
                return 1;
            }
        } else if (i == row2 && j == row1) {
            return scalar;
        } else {
            return 0;
        }
    }

    public double[] getRow(int i) {
        double[] row = new double[size];
        for (int j = 0; j < size; j++) {
            row[j] = get(i, j);
        }
        return row;
    }

    public double[] getCol(int j) {
        double[] col = new double[size];
        for (int i = 0; i < size; i++) {
            col[i] = get(i, j);
        }
        return col;
    }

    public double calc(double a, double b) { return a * scalar + b; }

    public int getFrom() { return row1; }

    public int getTo() { return row2; }

    public double getScalar() { return scalar; }

    public Matrix getMat() {
        double[][] mat = new double[size][size];
        for (int i = 0; i < size; i++) {
            mat[i][i] = get(i, i);
        }
        mat[row2][row1] = scalar;
        return new Matrix(mat);
    }

}
