package convolution;

import utils.Vector;
/**
 * Created by andrew on 3/29/15.
 */
public class VectorAndCount {

    private final Vector v;
    private final int count;

    public VectorAndCount(Vector v, int count) {
        this.v = v;
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public Vector getVector() {
        return v;
    }

    public String toString() {
        return v.toString() + "\nIteration count = " + count;
    }
}
