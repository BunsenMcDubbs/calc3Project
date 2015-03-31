package convolution;

/**
 * Created by andrew on 3/29/15.
 */
public class BitMatrixAndCount {

    private final BitMatrix v;
    private final int count;

    public BitMatrixAndCount(BitMatrix v, int count) {
        this.v = v;
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public BitMatrix getVector() {
        return v;
    }

    public String toString() {
        return v.toString() + "\nIteration count = " + count;
    }
}
