package urbanPop;
import utils.*;
/**
 * Test method that calculates values of the leslie iteration matrix.
 */
public class LeslieTest {
    public static void main(String[] args) {
        double[] x = {2.1, 1.9, 1.8, 2.1, 2.0, 1.7, 1.2, 0.9, 0.5};
        Vector distr = new Vector(x);
        double[][] testcomp = new double[9][9];
        testcomp[0][1] = 1.2;
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
        double[][] testcomp2 = a.cloneRaw();
        testcomp2[0][1] = 0.6;
        Matrix a2 = new Matrix(testcomp2);
        
        //If this is true, will do question 4's iterations.
        //Else it will do question 2's iterations.
        if (true) {
            System.out.println(distr.getSum() * 100000);
            for( int i = 0; i < 2; i++) {
                System.out.println("20" + (i+1) + "0");
                distr = LinearAlgebra.matrixVectorMultiply(a, distr);
                System.out.println(distr);
                System.out.println(distr.getSum() * 100000);
                System.out.println();
            }
            for( int i2 = 2; i2 < 5; i2++) {
                System.out.println("20" + (i2+1) + "0");
                distr = LinearAlgebra.matrixVectorMultiply(a2, distr);
                System.out.println(distr);
                System.out.println(distr.getSum() * 100000);
                System.out.println();
            }
        } else {
            for( int i = 0; i < 5; i++) {
                System.out.println("20" + (i+1) + "0");
                distr = LinearAlgebra.matrixVectorMultiply(a, distr);
                System.out.println(distr);
                System.out.println(distr.getSum() * 100000);
                System.out.println();
            }
        }
    }
}