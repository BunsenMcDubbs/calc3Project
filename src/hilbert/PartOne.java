package hilbert;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

import utils.*;

/**
 * Created by andrew on 3/29/15.
 */
public class PartOne {

    public static void main(String[] args) {
        System.out.println("Part 1: The Hilbert Matrix");
        if (args.length == 0 || args[0].equals("-h")) {
            printHelp();
        } else if (args[0].equals("-hilberts")) {
            hilberts();
        } else if (args.length == 2){
            String path = args[1];
            String opt = args[0];
            if (opt.equals("-lu") || opt.equals("-all")) {
                Matrix a = readMatrixFromFile(path);
                lu(a);
            }
            if (opt.equals("-house") || opt.equals("-qr")
                    || opt.equals("-all")) {
                Matrix a = readMatrixFromFile(path);
                householders(a);
            }
            if (opt.equals("-givens") || opt.equals("-qr")
                    || opt.equals("-all")) {
                Matrix a = readMatrixFromFile(path);
                givens(a);
            }
            if (opt.equals("-solve")) {
                Object[] temp = readAugmentedMatrixFromFile(args[1]);
                Matrix a1 = (Matrix) temp[0];
                Vector b = (Vector) temp[1];
                System.out.println("\nSolving for x with LU factorization:");
                VectorAndError luX = Hilbert.solve_lu_b(a1, b);
                System.out.println(solveVia(luX, a1, b, "LU"));

                System.out.println("\nSolving for x with QR factorization (Householders):");
                VectorAndError qrHX = Hilbert.solve_qr_b(a1, b);
                System.out.println(solveVia(qrHX, a1, b, "QR"));


                System.out.println("\nSolving for x with QR factorization (Givens):");
                VectorAndError qrGX = Hilbert.solve_qr_b_givens(a1, b);
                System.out.println(solveVia(qrGX, a1, b, "QR"));
            }
        } else {
            System.out.println("Your command was not valid or you forgot" +
                    " to specify a path to an input file");
        }
    }

    private static Matrix readMatrixFromFile(String fileName) {
        List<String> lines = null;
        try {
            lines = Files.readAllLines(FileSystems.getDefault().getPath(fileName));
        } catch (IOException e) {
            System.out.println("File not found at path: " + fileName);
            return null;
        }
        int size = lines.get(0).split(" ").length;
        double[][] raw = new double[size][size];
        for (int i = 0; i < size; i++) {
            String[] line = lines.get(i).split(" ");
            for (int j = 0; j < size; j++) {
                System.out.println(line.length);
                raw[i][j] = Double.parseDouble(line[j]);
            }
        }
        Matrix mat = new Matrix(raw);
        System.out.println("Your matrix is:\n" + mat);
        return mat;
    }

    private static Object[] readAugmentedMatrixFromFile(String fileName) {
        List<String> lines = null;
        try {
            lines = Files.readAllLines(FileSystems.getDefault().getPath(fileName));
        } catch (IOException e) {
            System.out.println("File not found at path: " + fileName);
            return null;
        }
        int size = lines.get(0).split(" ").length - 1;
        double[][] aRaw = new double[size][size];
        double[] bRaw = new double[size];
        for (int i = 0; i < size; i++) {
            String[] line = lines.get(i).split(" ");
            for (int j = 0; j < size; j++) {
                aRaw[i][j] = Double.parseDouble(line[j]);
            }
            bRaw[i] = Double.parseDouble(line[size]);
        }
        Matrix a = new Matrix(aRaw);
        Vector b = new Vector(bRaw);
        System.out.println("Your matrix (A) is:\n" + a);
        System.out.println("Your vector (b) is: " + b);
        return new Object[]{a, b};
    }

    private static void lu(AbstractMatrix a) {
        MatFact qr = Hilbert.qr_fact_househ(a);
        Matrix q = qr.getA();
        Matrix r = qr.getB();
        double err = qr.getError();
        System.out.println("Performing forwards and backwards substitution" +
                "to find an LU decomposition.");
        System.out.println("This is L:\n" + q);
        System.out.println("This is U:\n" + r);
        System.out.printf(
                "%e is the error in the matrix (|LU-A|)%n%n", err);
    }

    private static void householders(AbstractMatrix a) {
        MatFact qr = Hilbert.qr_fact_househ(a);
        Matrix q = qr.getA();
        Matrix r = qr.getB();
        double err = qr.getError();
        System.out.println("Performing Householders Reflections to " +
                "find a QR decomposition.");
        System.out.println("This is Q:\n" + q);
        System.out.println("This is R:\n" + r);
        System.out.printf(
                "%e is the error in the matrix (|QR-A|)%n%n", err);
    }

    private static void givens(AbstractMatrix a) {
        MatFact qr = Hilbert.qr_fact_givens(a);
        Matrix q = qr.getA();
        Matrix r = qr.getB();
        double err = qr.getError();
        System.out.println("Performing Givens Rotations to " +
                "find a QR decomposition.");
        System.out.println("This is Q:\n" + q);
        System.out.println("This is R:\n" + r);
        System.out.printf(
                "%e is the error in the matrix (|QR-A|)%n%n", err);
    }

    private static void printHelp() {
        String s = "These are your options:\n" +
                "\t-hilberts\tPrint out Hilbert Matrices and Factorizations from 2x2 " +
                "to 20x20\n" +
                "\t-lu\tPerform LU decomposition on a matrix (include file path)\n" +
                "\t-house\tPerform Householders reflections to get the QR decomposition" +
                "of a matrix (include file path)\n" +
                "\t-givens\tPerform Givens Rotations to get the QR decomposition of a" +
                "matrix (include file path)\n" +
                "\t-qr\tPerform both Householders reflections and Givens Rotations " +
                "to find the QR decomposition of a matrix (include file path)\n" +
                "\t-solve\tSolve for x in Ax = b given an augmented matrix A|b\n" +
                "\t-h\tPrint this help message\n";
        System.out.println(s);
    }

    private static void hilberts() {
        String s = "Solving for x in Hx = b with various size " +
                "Hilbert matrices and different factorization strategies.";
        System.out.println(s);
        String s1 = "With n from n = 2 to n = 20, we will solve for x in Hx = b " +
                "where H is a Hilbert matrix of size n by n and " +
                "vector b = 0.1^n/3(1, 1, ... 1)";
        System.out.println(s1);
        for (int n = 2; n <= 20; n++) {
            System.out.println("\n*** n = " + n + " ***\n");
            Hilbert h = new Hilbert(n);
//            System.out.println("Hilbert Matrix:\n" + h);
            Vector b = makeB(n);
            System.out.println("b = " + b);

            System.out.println("\nSolving for x with LU factorization:");
            VectorAndError luX = Hilbert.solve_lu_b(h, b);
            System.out.println(solveHilbertVia(luX, h, b, "LU"));

            System.out.println("\nSolving for x with QR factorization (Householders):");
            VectorAndError qrHX = Hilbert.solve_qr_b(h, b);
            System.out.println(solveHilbertVia(qrHX, h, b, "QR"));


            System.out.println("\nSolving for x with QR factorization (Givens):");
            VectorAndError qrGX = Hilbert.solve_qr_b_givens(h, b);
            System.out.println(solveHilbertVia(qrGX, h, b, "QR"));
        }
    }

    private static String solveHilbertVia (VectorAndError x, AbstractMatrix h,
                                    Vector b, String method) {
        String s = "Solved x = " + x.getVector();
        double vecErr = LinearAlgebra.vectorSubtract(
                LinearAlgebra.matrixVectorMultiply(h, x.getVector()), b)
                .getMagnitude();
        s += "\n||Hx(" + method.toLowerCase() + ") - b|| = " + vecErr;
        s += "\n||" + method + " - H|| = " + x.getError();
        return s;
    }

    private static String solveVia (VectorAndError x, AbstractMatrix h,
                                    Vector b, String method) {
        String s = "Solved x = " + x.getVector();
        double vecErr = LinearAlgebra.vectorSubtract(
                LinearAlgebra.matrixVectorMultiply(h, x.getVector()), b)
                .getMagnitude();
        s += "\n||Ax(" + method.toLowerCase() + ") - b|| = " + vecErr;
        s += "\n||" + method + " - A|| = " + x.getError();
        return s;
    }

    private static Vector makeB(int n) {
        double x = Math.pow(.1, n / 3.);
        double[] b = new double[n];
        for (int i = 0; i < n; i++) {
            b[i] = x;
        }
        return new Vector(b);
    }

}
