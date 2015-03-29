package hilbert;

import utils.LinearAlgebra;
import utils.Vector;
import utils.VectorAndError;

/**
 * Created by andrew on 3/29/15.
 */
public class RunHilbert {

    public static void main(String... args) {
        String s = "Part 1: Solving for x in Hx = b with various size " +
                "Hilbert matrices and different factorization strategies.";
        System.out.println(s);
        String s1 = "With n from n = 2 to n = 20, we will solve for x in Hx = b " +
                "where H is a Hilbert matrix of size n by n and " +
                "vector b = 0.1^n/3(1, 1, ... 1)";
        System.out.println(s1);
        for (int n = 2; n <= 20; n++) {
            System.out.println("\n*** n = " + n + " ***\n");
            Hilbert h = new Hilbert(n);
            System.out.println("Hilbert Matrix:\n" + h);
            Vector b = makeB(n);
            System.out.println("b = " + b);

            System.out.println("\nSolving for x with LU factorization:");
            VectorAndError luX = Hilbert.solve_lu_b(h, b);
            System.out.println(solveVia(luX, h, b, "LU"));

            System.out.println("\nSolving for x with QR factorization (Householders):");
            VectorAndError qrHX = Hilbert.solve_qr_b(h, b);
            System.out.println(solveVia(qrHX, h, b, "QR"));


            System.out.println("\nSolving for x with QR factorization (Givens):");
            VectorAndError qrGX = Hilbert.solve_qr_b_givens(h, b);
            System.out.println(solveVia(qrGX, h, b, "QR"));
        }
    }

    public static String solveVia (VectorAndError x, Hilbert h, Vector b, String method) {
        String s = "Solved x = " + x.getVector();
        double vecErr = LinearAlgebra.vectorSubtract(
                LinearAlgebra.matrixVectorMultiply(h, x.getVector()), b)
                .getMagnitude();
        s += "\n||Hx(" + method.toLowerCase() + ") - b|| = " + vecErr;
        s += "\n||" + method + " - H|| = " + x.getError();
        return s;
    }

    public static Vector makeB(int n) {
        double x = Math.pow(.1, n / 3.);
        double[] b = new double[n];
        for (int i = 0; i < n; i++) {
            b[i] = x;
        }
        return new Vector(b);
    }

}
