package urbanPop;
import utils.*;
/**
 * Tests the power method with a matrix.
 * Has the values for the leslie matrix in part 3.
 * shows generally how to use power method.
 * Note that PowerDoesNotConverge is a checked exception.
 */
public class PowerTest {
    public static void main(String[] args) {
        double[][] testcomp = new double[9][9];
        double[] testVecomp = {2.1, 1.9, 1.8, 2.1, 2.0, 1.7, 1.2, 0.9, 0.5};
        Vector testVec = new Vector(testVecomp);
        //testcomp[0][1] = 1.2;
        testcomp[0][1] = 0.6;
        testcomp[0][2] = 1.1;
        testcomp[0][3] = 0.9;
        testcomp[0][4] = 0.1;
        testcomp[1][0] = 0.7;
        testcomp[2][1] = 0.85;
        testcomp[3][2] = 0.9;
        testcomp[4][3] = 0.9;
        testcomp[5][4] = 0.88;
        testcomp[6][5] = 0.8;
        testcomp[7][6] = 0.77;
        testcomp[8][7] = 0.40;
        Matrix a = new Matrix(testcomp);
        try {
            PowerResult test = UrbanPop.power_method(a, 0.00000001, testVec);
            System.out.println(test.getEigenVector());
            System.out.println(test.getEigenValue());
            System.out.println(test.getIterNum());
        } catch (PowerDoesNotConvergeException e) {
            System.out.println(e.getMessage());
        }
    }
}