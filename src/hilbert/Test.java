package hilbert;

import utils.*;

/**
 * This is a testing class
 */
public class Test {

    public static void main(String... args) {
        sanityCheckQRHouse();
    }

    public static void hilbertQRGivensTest() {
        System.out.println("Testing QR Givens Rotations");
        Hilbert mat = new Hilbert(4);

        System.out.println("A\n" + mat);
        MatFact r = Hilbert.qr_fact_givens(mat);
        System.out.println("Q\n" + r.getA());
        System.out.println("R\n" + r.getB());

        System.out.println("Q x R = A?");
        System.out.println(LinearAlgebra.matrixMultiply(r.getA(), r.getB()));

        System.out.println("Solve for x (Ax = b)");
        Vector b = new Vector(new double[] {0.0464159, 0.0464159, 0.0464159, 0.0464159});

        VectorAndError xsol = Hilbert.solve_qr_b_givens(mat, b);
        System.out.println("solved x = " + xsol.getVector());
        System.out.println("matrix error = " + xsol.getError());
        Vector axsol = LinearAlgebra.matrixVectorMultiply(mat, xsol.getVector());
        Vector diff = LinearAlgebra.vectorSubtract(axsol, b);
        System.out.println("Ax(sol) = " + axsol);
        System.out.println("b =       " + b);
        System.out.println("diff =    " + diff);
        System.out.println(diff.getMagnitude());
    }

    public static void hilbertQRHouseTest() {
        System.out.println("Testing QR Householders Reflections");
        Hilbert mat = new Hilbert(4);

        System.out.println("A\n" + mat);
        MatFact r = Hilbert.qr_fact_househ(mat);
        System.out.println("Q\n" + r.getA());
        System.out.println("R\n" + r.getB());

        System.out.println("Q x R = A?");
        System.out.println(LinearAlgebra.matrixMultiply(r.getA(), r.getB()));

        System.out.println("Solve for x (Ax = b)");
        Vector b = new Vector(new double[] {0.0464159, 0.0464159, 0.0464159, 0.0464159});

        VectorAndError xsol = Hilbert.solve_qr_b(mat, b);
        System.out.println("solved x = " + xsol.getVector());
        System.out.println("matrix error = " + xsol.getError());
        Vector axsol = LinearAlgebra.matrixVectorMultiply(mat, xsol.getVector());
        Vector diff = LinearAlgebra.vectorSubtract(axsol, b);
        System.out.println("Ax(sol) = " + axsol);
        System.out.println("b =       " + b);
        System.out.println("diff =    " + diff);
        System.out.println(diff.getMagnitude());
    }

    public static void hilbertLUTest() {
        System.out.println("Testing LU Factorization");
        Hilbert mat = new Hilbert(4);

        System.out.println("A\n" + mat);
        MatFact r = Hilbert.lu_fact(mat);
        System.out.println("L\n" + r.getA());
        System.out.println();
        System.out.println("U\n" + r.getB());

        System.out.println("Solve for x (Ax = b)");
        Vector b = new Vector(new double[] {0.0464159, 0.0464159, 0.0464159, 0.0464159});

        VectorAndError xsol = Hilbert.solve_lu_b(mat, b);
        System.out.println("solved x = " + xsol.getVector());
        System.out.println("matrix error = " + xsol.getError());
        Vector axsol = LinearAlgebra.matrixVectorMultiply(mat, xsol.getVector());
        Vector diff = LinearAlgebra.vectorSubtract(axsol, b);
        System.out.println("Ax(sol) = " + axsol);
        System.out.println("b =       " + b);
        System.out.println("diff =    " + diff);
        System.out.println(diff.getMagnitude());
    }

    public static void sanityCheckLU() {
        double[][] testMat = new double[][] {
                {1, 2, 4},
                {2, 3, 5},
                {3, 7, 16}
        };
        Matrix mat = new Matrix(testMat);

        System.out.println("A\n" + mat);
        MatFact r = Hilbert.lu_fact(mat);
        System.out.println("L\n" + r.getA());
        System.out.println();
        System.out.println("U\n" + r.getB());
        System.out.println("L x U = A?");
        System.out.println(LinearAlgebra.matrixMultiply(r.getA(), r.getB()));

        System.out.println("Solve for b (Ax = b)");
        double[] testX = new double[] {1, 2, 3};
        Vector x = new Vector(testX);
        System.out.println("actual x = " + x);
        Vector b = LinearAlgebra.matrixVectorMultiply(mat, x);
        System.out.println("Ax = b = " + b);

        System.out.println("solved x = " + Hilbert.solve_lu_b(mat, b));
    }

    public static void sanityCheckQRHouse() {
        double[][] testMat = new double[][] {
                {2, 0, 1},
                {1, 3, 2},
                {0, 2, 3}
        };
        Matrix mat = new Matrix(testMat);

        System.out.println("A\n" + mat);
        MatFact r = Hilbert.qr_fact_househ(mat);
        System.out.println("Q\n" + r.getA());
        System.out.println();
        System.out.println("R\n" + r.getB());
        System.out.println("Q x R = A?");
        System.out.println(LinearAlgebra.matrixMultiply(r.getA(), r.getB()));

        System.out.println("Solve for b (Ax = b)");
        double[] testX = new double[] {1, 2, 3};
        Vector x = new Vector(testX);
        System.out.println("actual x = " + x);
        Vector b = LinearAlgebra.matrixVectorMultiply(mat, x);
        System.out.println("Ax = b = " + b);

        Matrix qt = Hilbert.transpose(r.getA());
        Vector qtb = LinearAlgebra.matrixVectorMultiply(qt, b);
        System.out.println(Hilbert.backwardSub(r.getB(), qtb));
    }
}
