package utils;

public abstract class AbstractMatrix {

    protected final int height;
    protected final int width;

    public AbstractMatrix(int height, int width) {
        this.width = width;
        this.height = height;
    }

    public abstract double get(int i, int j);

    public Vector getRow(int i) {
        double[] row = new double[getCols()];
        for (int j = 0; j < getCols(); j++) {
            row[j] = get(i, j);
        }
        return new Vector(row);
    }

    public Vector getCol(int j) {
        double[] col = new double[getRows()];
        for (int i = 0; i < getRows(); i++) {
            col[i] = get(i, j);
        }
        return new Vector(col);
    }

    public int getHeight() { return height; }
    public int getRows() { return height; }
    public int getWidth() { return width; }
    public int getCols() { return width; }

    public double[][] cloneRaw() {
        double[][] mat = new double[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                mat[i][j] = get(i, j);
            }
        }
        return mat;
    }

    public Matrix getMat() {
        return new Matrix(cloneRaw());
    }

    /**
     * Gets String representation of matrix.
     * Columns separated by tabs, rows by new lines.
     * @return String representation of matrix.
     */
    public String toString() {
        String s = "";
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                s += String.format("%f\t", get(i, j));
            }
            s += "\n";
        }
        return s;
    }
}
