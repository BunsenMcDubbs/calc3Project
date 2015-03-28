package utils;
/**
 * William Greenleaf, 3/24/2015
 * @version 0.1
 */
public class PowerResult {
    private Vector eiVect;
    private double eiNum;
    
    public PowerResult(Vector eiVect, double eiNum) {
        this.eiVect = eiVect;
        this.eiNum = eiNum;
    }
    
    public Vector getEigenVector() {
        return eiVect;
    }
    
    public double getEigenValue() {
        return eiNum;
    }
}