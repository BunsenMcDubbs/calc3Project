package utils;
/**
 * William Greenleaf, 3/24/2015
 * Represents the result of the power method.
 * @version 1
 */
public class PowerResult {
    private Vector eiVect;
    private double eiNum;
    
    /**
     * Creates a power result.
     * @param eiVect eigen vector
     * @param eiNum eigen number
     */
    public PowerResult(Vector eiVect, double eiNum) {
        this.eiVect = eiVect;
        this.eiNum = eiNum;
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
}