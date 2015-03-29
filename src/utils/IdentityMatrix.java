package utils;

/**
 * Created by andrew on 3/28/15.
 */
public class IdentityMatrix extends AbstractMatrix {

    protected int size;

    public IdentityMatrix(int size) {
        super(size, size);
        this.size = size;
    }

    public double get(int i, int j) {
        return i == j ? 1 : 0;
    }

    @Override
    public double[][] cloneRaw() {
        double[][] mat = new double[size][size];
        for (int i = 0; i < size; i++) {
            mat[i][i] = 1;
        }
        return mat;
    }

}
