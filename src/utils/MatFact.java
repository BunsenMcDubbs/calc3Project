package utils;

/**
 * Created by andrew on 3/23/15.
 */
public class MatFact {

    private final Matrix a, b;
    private final double error;

    public MatFact(Matrix a, Matrix b, double error) {
        this.a = a;
        this.b = b;
        this.error = error;
    }

    public Matrix getA() { return a; }
    public Matrix getB() { return b; }
    public double getError() { return error; }
}
