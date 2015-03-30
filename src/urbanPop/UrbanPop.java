import utils.*;

/**
 * William Greenleaf, 3/24/2015
 * @version 1.0
 * Class that contains code for urban pop project.
 */ 
package urbanPop;
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
}