package convolution;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

import utils.*;

/**
 * Created by andrew on 3/31/15.
 */
public class PartTwo {

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("Part 2: Convolutional Codes");
        if (args.length == 0) {
            printHelp();
        } else {
            String option = args[0];
            if (option.equals("-iteration")) {
                if (args.length != 2) {
                    System.out.println("Please include the path to the file");
                } else {
                    try {
                        iteration(args[1]);
                    } catch (IOException e) {
                        System.out.printf("File at path %s could not be" +
                                " found%n", args[1]);
                    }
                }
            } else if (option.equals("-h")) {
                printHelp();
            } else if (option.equals("-e") || option.equals("-ep")) {
                int length = 20;
                if (args.length == 2) {
                    length = Integer.parseInt(args[1]);
                }
                encode(length, option.equals("-ep"));
            } else if (option.equals("-d")) {
                if (args.length != 2) {
                    System.out.println("Please include the path to the file");
                } else {
                    try {
                        decode(Files.readAllLines(
                                FileSystems.getDefault()
                                        .getPath(args[1])).get(0));
                    } catch (IOException e) {
                        System.out.printf("File at path %s could not be" +
                                " found%n", args[1]);
                    }
                }
            }
        }
    }

    public static void encode(int length, boolean printA) {
        BitMatrix x = Convolution.getRandomVector(length);
        BitMatrix y0 = Convolution.findy0(x);
        BitMatrix y1 = Convolution.findy1(x);
        System.out.printf("Random input of length %d%n", length);
        if (printA) {
            System.out.println("A0\n" + Convolution.getA0(length));
            System.out.println("A1\n" + Convolution.getA1(length));
        }
        System.out.println("x = " + x);
        System.out.print("y = { ");
        for (int i = 0; i < y0.getRows(); i++) {
            System.out.printf("%d%d ", (int) y0.get(i, 0), (int) y1.get(i, 0));
        }
        System.out.println("}");
    }

    public static void decode(String y) {
        ArrayList<Boolean> y0Raw = new ArrayList<Boolean>();
        ArrayList<Boolean> y1Raw = new ArrayList<Boolean>();
        for (int i = 0, ind = 0; i < y.length(); i++) {
            if (y.charAt(i) == '0') {
                if (ind % 2 == 0) {
                    y0Raw.add(false);
                } else {
                    y1Raw.add(false);
                }
                ind++;
            } else if (y.charAt(i) == '1') {
                if (ind % 2 == 0) {
                    y0Raw.add(true);
                } else {
                    y1Raw.add(true);
                }
                ind++;
            }
        }
        if (y0Raw.size() != y1Raw.size()) {
            System.out.println("The length of the input must be even " +
                    "(y0 and y1 must be of equal length)");
        }
        boolean[] y0Raw1 = new boolean[y0Raw.size()];
        boolean[] y1Raw1 = new boolean[y1Raw.size()];
        for (int i = 0; i < y0Raw.size(); i++) {
            y0Raw1[i] = y0Raw.get(i);
            y1Raw1[i] = y1Raw.get(i);
        }
        BitMatrix y0 = new BitMatrix(y0Raw1);
        BitMatrix y1 = new BitMatrix(y1Raw1);

        System.out.println("Solving with Jacobi iterations (with bit math)");
        System.out.println("y0 = " + y0);
        System.out.println("y0 -> x = " + Convolution.jacobiFindXFromY0(y0));
        System.out.println("y1 = " + y1);
        System.out.println("y1 -> x = " + Convolution.jacobiFindXFromY1(y1));

        System.out.println("\nSolving with Gauss Seidel iterations (with bit math)");
        System.out.println("y0 = " + y0);
        System.out.println("y0 -> x = " + Convolution.gaussSeidelFindXFromY0(y0));
        System.out.println("y1 = " + y1);
        System.out.println("y1 -> x = " + Convolution.gaussSeidelFindXFromY1(y1));
    }

    public static void iteration(String path) throws IOException {
        List<String> lines = Files.readAllLines(
                FileSystems.getDefault()
                        .getPath(path));

        int size = lines.get(0).split(" ").length - 1;
        double[][] aRaw = new double[size][size];
        double[] bRaw = new double[size];
        double[] x0Raw = new double[size];
        double tol = 0;

        for (int i = 0; i < size; i++) {
            String[] line = lines.get(i).split(" ");
            for (int j = 0; j < size; j++) {
                aRaw[i][j] = Double.parseDouble(line[j]);
            }
            bRaw[i] = Double.parseDouble(line[size]);
            x0Raw[i] = Double.parseDouble(lines.get(i + size));
        }
        tol = Double.parseDouble(lines.get(size * 2));

        Matrix a = new Matrix(aRaw);
        Vector b = new Vector(bRaw);
        Vector x0 = new Vector(x0Raw);
        System.out.println("Matrix A:\n" + a);
        System.out.println("Vector b: " + b);
        System.out.println("Vector x0: " + x0);
        System.out.println("Tolerance: " + tol);

        System.out.println("Solving for x with Jacobi iterations");
        try {
            System.out.println("xSol = " + Convolution.jacobi(a, b, x0, tol));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Solving for x with Gauss Seidel iterations");
        try {
            System.out.println("xSol = " + Convolution.gauss_seidel(a, b, x0, tol));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void printHelp() {
        String s = "Here are your options:\n" +
                "\t-iteration\tUse iteration methods (Jacobi and Gauss Seidel)" +
                "to solve of x in Ax = b, given A, b and a tolerance" +
                " (include the path to the file)\n" +
                    "\t\tex) -iterate /path/to/file.dat\n" +
                "\t-e\tGenerate and encode a random binary stream" +
                "(include length, default = 20)\n" +
                    "\t\tex) -e 100\n" +
                "\t-ep\tSame as -e except also print out matrices A0 and A1\n" +
                "\t-d\tDecode x from a given y (include path to the file)" +
                    "\n\t\tThe decoding will be done twice, once with Jacobi " +
                    "iterations and once with Gauss Seidel iterations." +
                    "\n\t\tNote: the bits are treated as bits which is faster " +
                    "and more memory efficient than approximating bits with " +
                    "floating point numbers\n" +
                "\t-h\tDisplay this help dialog\n";
        System.out.println(s);
    }

}
