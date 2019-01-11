package simulation.lib.counter;

/**
 * This class implements a discrete time confidence counter
 */
public class DiscreteConfidenceCounter extends DiscreteCounter{
    /*
     * TODO Problem 3.1.2 - implement this class according to the given class diagram!
     * Hint: see section 4.4 in course syllabus
     */

    /*	Row 1: degrees of freedom
     *  Row 2: alpha 0.01
     *  Row 3: alpha 0.05
     *  Row 4: alpha 0.10
     */
	
	private double alpha = 0.05;
	double b;
	double g;
	
    private double tAlphaMatrix[][] = new double[][]{
            {		1, 		2, 		3,		4, 5, 6, 7, 8, 9, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 1000000},
            {  63.657, 	9.925, 	5.841,	4.604, 4.032, 3.707, 3.499, 3.355, 3.250, 3.169, 2.845, 2.750, 2.704, 2.678, 2.660, 2.648, 2.639, 2.632, 2.626, 2.576},
            {  12.706, 	4.303,	3.182, 	2.776, 2.571, 2.447, 2.365, 2.306, 2.262, 2.228, 2.086, 2.042, 2.021, 2.009, 2.000, 1.994, 1.990, 1.987, 1.984, 1.960},
            {	6.314, 	2.920, 	2.353, 	2.132, 2.015, 1.943, 1.895, 1.860, 1.833, 1.812, 1.725, 1.697, 1.684, 1.676, 1.671, 1.667, 1.664, 1.662, 1.660, 1.645}};

    public DiscreteConfidenceCounter(String variable){
    	super(variable);
    	System.out.println(tAlphaMatrix[2][1]);
    }
    
    public DiscreteConfidenceCounter(String variable, double alpha){
    	super(variable);
    	this.alpha = alpha;
    }
    
    public DiscreteConfidenceCounter(String variable, String type, double alpha){
    	super(variable, type);
    	this.alpha = alpha;
    }
    
	public double getT(long degsOfFreedom){
    	double dflow = 0;
    	double dfhigh = 0;
    	double tlow = 0;
    	double thigh = 0;
    	
    	double t_alpha = Double.NEGATIVE_INFINITY;
    	
    	int row_id = getRow();
    	
    	for (int i = 0; i < 20; i++){
    		if(tAlphaMatrix[0][i] == degsOfFreedom){
    			return tAlphaMatrix[row_id][i];
    		}
    	}
		if(tAlphaMatrix[0][19] < degsOfFreedom){
			return tAlphaMatrix[row_id][19];
		}
		for (int i = 18; i >=0; i--){
			//System.out.println(i);
    		if(tAlphaMatrix[0][i] <= degsOfFreedom && degsOfFreedom < tAlphaMatrix[0][i+1]){
    			dflow = tAlphaMatrix[0][i];
    			dfhigh = tAlphaMatrix[0][i+1];
    			tlow = tAlphaMatrix[row_id][i];
    			thigh = tAlphaMatrix[row_id][i+1];
    			return linearInterpol(dflow, dfhigh, tlow, thigh, degsOfFreedom);
    		}
    	}
		return t_alpha;
    }
    
	private double linearInterpol(double dflow, double dfhigh, double tlow, double thigh, long degsOfFreedom){
    	return tlow + (thigh - tlow)/(dfhigh - dflow) * ((double) degsOfFreedom - dflow);
    }
    
	private int getRow(){
    	if(alpha < 0.05){
    		return 1;
    	}else if(0.05 <= alpha  && alpha < 0.10){
    		return 2;
    	}else{
    		return 3;
    	}
    }
    
    public double getLowerBound(){
    	this.b = getBound();
    	return getMean() - b; //getBound();
    }
    
    public double getUpperBound(){
    	return getMean() + getBound();
    }
    
    public double getBound(){
    	g = Math.sqrt(getVariance()/getNumSamples());
    	return getT(getNumSamples()-1)*g;
    }
    
    public void _report(){
    	System.out.println(this.report());
    }
    
    /**
     * @see Counter#report()
     * Uncomment this function when you have implemented this class for reporting.
     */
    @Override
    public String report() {
        String out = super.report();
        out += ("" + "\talpha = " + alpha + "\n" +
                "\tt(1-alpha/2) = " + getT(getNumSamples() - 1) + "\n" +
                "\tlower bound = " + getLowerBound() + "\n" +
                "\tupper bound = " + getUpperBound());
        return out;
    }

    /**
     * @see Counter#csvReport(String)
     * Uncomment this function when you have implemented this class for reporting.
     */
    @Override
    public void csvReport(String outputdir) {
        String content = observedVariable + ";" + getNumSamples() + ";" + getMean() + ";" + getVariance() + ";" + getStdDeviation() + ";" +
                getCvar() + ";" + getMin() + ";" + getMax() + ";" + alpha + ";" + getT(getNumSamples() - 1) + ";" + getLowerBound() + ";" +
                getUpperBound() + "\n";
        String labels = "#counter ; numSamples ; MEAN; VAR; STD; CVAR; MIN; MAX;alpha;t(1-alpha/2);lowerBound;upperBound\n";
        writeCsv(outputdir, content, labels);
    }

}
