package utils;

public abstract class AbstractMatrix {

    protected final int height;
    protected final int width;

    public AbstractMatrix(int height, int width) {
        this.width = width;
        this.height = height;
    }

    public abstract double get(int i, int j);
    public abstract double[] getCol(int i);
    public abstract double[] getRow(int i);
    public int getHeight() { return height; }
    public int getRows() { return height; }
    public int getWidth() { return width; }
    public int getCols() { return width; }

    /**
     * Gets String representation of matrix.
     * Columns separated by tabs, rows by new lines.
     * @return String representation of matrix.
     */
    public String toString() {
        String s = "";
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                s += String.format("%02.3f\t", get(i, j));
            }
            s += "\n";
        }
        return s;
    }
}
