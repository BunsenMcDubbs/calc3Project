package hilbert;

import javafx.scene.shape.HLineTo;
import utils.LinearAlgebra;
import utils.Matrix;
import utils.Result;
import utils.Vector;

/**
 * Created by andrew on 3/28/15.
 */
public class Test {

    public static void main(String... args) {
        hilbertQRHouseTest();
    }

    public static void hilbertQRHouseTest() {
        Hilbert a = new Hilbert(4);
        Matrix mat = a.getMat();

        System.out.println("A\n" + mat);
        Result r = Hilbert.qr_fact_househ(mat);
        System.out.println("Q\n" + r.getA());
        System.out.println();
        System.out.println("R\n" + r.getB());

        System.out.println("Q x R = A?");
        System.out.println(LinearAlgebra.matrixMultiply(r.getA(), r.getB()));

        System.out.println("Solve for b (Ax = b)");
        Vector b = new Vector(new double[] {0.0464159, 0.0464159, 0.0464159, 0.0464159});

        System.out.println("solved x = " + Hilbert.solve_qr_b(mat, b));
    }

    public static void hilbertLUTest() {
        Hilbert a = new Hilbert(4);
        Matrix mat = a.getMat();

        System.out.println("A\n" + mat);
        Result r = Hilbert.lu_fact(mat);
        System.out.println("L\n" + r.getA());
        System.out.println();
        System.out.println("U\n" + r.getB());

        System.out.println("Solve for b (Ax = b)");
        Vector b = new Vector(new double[] {0.0464159, 0.0464159, 0.0464159, 0.0464159});

        System.out.println("solved x = " + Hilbert.solve_lu_b(mat, b));
    }

    public static void sanityCheckLU() {
        double[][] testMat = new double[][] {
                {1, 2, 4},
                {2, 3, 5},
                {3, 7, 16}
        };
        Matrix mat = new Matrix(testMat);

        System.out.println("A\n" + mat);
        Result r = Hilbert.lu_fact(mat);
        System.out.println("L\n" + r.getA());
        System.out.println();
        System.out.println("U\n" + r.getB());
        System.out.println("L x U = A?");
        System.out.println(LinearAlgebra.matrixMultiply(r.getA(), r.getB()));

        System.out.println("L inverse");
        Matrix lInv = Hilbert.inverseDown(r.getA());
        System.out.println(lInv);
        System.out.println("U inverse");
        Matrix uInv = Hilbert.inverseUp(r.getB());
        System.out.println(uInv);

        System.out.println("Solve for b (Ax = b)");
        double[] testX = new double[] {1, 2, 3};
        Vector x = new Vector(testX);
        System.out.println("actual x = " + x);
        Vector b = LinearAlgebra.matrixVectorMultiply(mat, x);
        System.out.println("Ax = b = " + b);

        Vector temp = LinearAlgebra.matrixVectorMultiply(lInv, b);
        System.out.println("(lInv) * b = temp = " + temp);
        System.out.println("(uInv) * temp = " + LinearAlgebra.matrixVectorMultiply(uInv, temp));

        System.out.println("solved x = " + Hilbert.solve_lu_b(mat, b));
    }
}
