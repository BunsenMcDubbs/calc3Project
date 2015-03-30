package utils;
/**
 * William Greenleaf, 3/24/2015
 * Represents the result of the power method.
 * @version 1
 */
public class PowerResult {
    private Vector eiVect;
    private double eiNum;
    private int iterNum;
    
    /**
     * Creates a power result.
     * @param eiVect eigen vector
     * @param eiNum eigen number
     * @param iterNum number of iterations
     */
    public PowerResult(Vector eiVect, double eiNum, int iterNum) {
        this.eiVect = eiVect;
        this.eiNum = eiNum;
        this.iterNum = iterNum;
    }
    
    /**
     * Retrieves eigen vector
     * @return eigen vector
     */
    public Vector getEigenVector() {
        return eiVect;
    }
    
    /**
     * Retrieves eigen value
     * @return eigen value
     */
    public double getEigenValue() {
        return eiNum;
    }
    
    /**
     * Retrieves number of iterations
     * @return iteration number
     */
    public int getIterNum() {
        return iterNum;
    }
}