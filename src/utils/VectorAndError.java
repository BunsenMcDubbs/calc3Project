package utils;

/**
 * Created by andrew on 3/29/15.
 */
public class VectorAndError {

    private final Vector v;
    private final double err;

    public VectorAndError(Vector v, double e) {
        this.v = v;
        this.err = e;
    }

    public double getError() {
        return err;
    }

    public Vector getVector() {
        return v;
    }

    public String toString() {
        return v.toString() + " Matrix error = " + err;
    }
}
