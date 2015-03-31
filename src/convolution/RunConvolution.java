package convolution;

import java.util.ArrayList;

/**
 * Created by andrew on 3/31/15.
 */
public class RunConvolution {

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
            if (option.equals("-f")) {
                if (args.length != 2) {
                    System.out.println("Please include the path to the file");
                } else {
                    // TODO file stuff
                }
            } else if (option.equals("-h")) {
                printHelp();
            } else if (option.equals("-e") || option.equals("-ep")) {
                int length = 20;
                if (args.length == 2) {
                    length = Integer.parseInt(args[1]);
                }
                encode(length, option.equals("-ep"));
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
        BitMatrix y0 = new BitMatrix((Boolean[]) y0Raw.toArray());
        BitMatrix y1 = new BitMatrix((Boolean[]) y1Raw.toArray());

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

    public static void printHelp() {
        String s = "Here are your options:\n" +
//                "\t-i\tInteractive mode\n" +
                "\t-f\tRead from a file (include the path to the file)\n" +
                    "\t\tex) -f /path/to/file.dat\n" +
                "\t-e\tGenerate and encode a random binary stream" +
                "(include length, default = 20)\n" +
                    "\t\tex) -e 100\n" +
                "\t-ep\tSame as -e except also print out matrices A0 and A1\n" +
                "\t-h\tDisplay this help dialog\n";
        System.out.println(s);
    }

}
