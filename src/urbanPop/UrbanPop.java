package urbanPop;

import utils.*;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;

/**
 * William Greenleaf, 3/24/2015
 * @version 1.0
 * Class that contains code for urban pop project.
 */ 
public class UrbanPop {
    /**
     * Calculates largest eigen value and corresponding vector by power method.
     * @param a input matrixVectorMultiply
     * @param tol Error tolerance
     * @param approx Approximate eigen vector
     * @return Power Result object with the eigen vector, eigen value, and iteration
     */
    public static PowerResult power_method(Matrix a, double tol, Vector approx)
        throws PowerDoesNotConvergeException {

        int i = 0;
        Vector newApprox = LinearAlgebra.matrixVectorMultiply(a, approx);
        double eiValue1;
        double eiValue2;
        double testEi1;
        double testEi2;
        Vector eiVector;
        int maxIterations = 100000;
        do {
            i++;
            eiValue1 = newApprox.getMax();
            eiVector = newApprox.divideComponents(eiValue1);
            newApprox = LinearAlgebra.matrixVectorMultiply(a, eiVector);
            eiValue2 = newApprox.getMax();
            eiVector = newApprox.divideComponents(eiValue2);
            testEi2 = eiValue2;
            testEi1 = eiValue1;
        } while (Math.abs((Math.abs(testEi1) - Math.abs(testEi2))) > tol && i < maxIterations);
        if (i == maxIterations){
            throw new PowerDoesNotConvergeException("The eigen value did not converge.");
        }
        return new PowerResult(eiVector, eiValue2, i);
    }
    
    /**
     * Main method. Accepts 2-3 arguments.
     * Will default to vector with all ones for no input.
     * For power method, enter "-power" and then 
     * the path of an augmented matrix .dat file.
     * Optionally enter your error tolerance.
     */
    public static void main(String[] args) {
        System.out.println("Part 3: Urban Population Dynamics");
        if (args.length == 0 || args[0].equals("-h")) {
            printHelp();
        } else if (args.length == 3){
            double tol = Double.parseDouble(args[2]);
            String path = args[1];
            String opt = args[0];
            if (opt.equals("-power")) {
                Object[] temp = readAugmentedMatrixFromFile(args[1]);
                Matrix a1 = (Matrix) temp[0];
                Vector b = (Vector) temp[1];
                System.out.println("\nSolving for dominant eigenvalue");
                try {
                    PowerResult result = power_method(a1, tol, b);
                    System.out.println("Your eigen vector is:\n" + result.getEigenVector());
                    System.out.println("Your eigen value is: " + result.getEigenValue());
                    System.out.println("The number of iterations was: " + result.getIterNum());
                } catch(PowerDoesNotConvergeException e2) {
                    System.out.println("The eigen value does not converge.");
                }
            }
        } else if (args.length == 2){
            double tol = 0.00000001;
            String path = args[1];
            String opt = args[0];
            if (opt.equals("-power")) {
                Object[] temp = readAugmentedMatrixFromFile(args[1]);
                Matrix a1 = (Matrix) temp[0];
                Vector b = (Vector) temp[1];
                System.out.println("\nSolving for dominant eigenvalue");
                try {
                    PowerResult result = power_method(a1, tol, b);
                    System.out.println("Your eigen vector is:\n" + result.getEigenVector());
                    System.out.println("Your eigen value is: " + result.getEigenValue());
                    System.out.println("The number of iterations was: " + result.getIterNum());
                } catch(PowerDoesNotConvergeException e2) {
                    System.out.println("The eigen value does not converge.");
                }
            } else {
            System.out.println("Your command was not valid or you forgot" +
                    " to specify a path to an input file");
        }
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
    
    private static void printHelp() {
        String s = "These are your options:\n" +
                "\t-power\t Do power method\n " +
                "\t-h\t Print this help message\n";
        System.out.println(s);
    }
}