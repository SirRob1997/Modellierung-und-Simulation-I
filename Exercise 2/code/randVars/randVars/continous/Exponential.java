/**
 * 
 */
package simulation.lib.randVars.continous;

import simulation.lib.randVars.RandVar;
import simulation.lib.rng.RNG;

/*
 * TODO Problem 2.3.2 - implement this class (section 3.2.2 in course syllabus)
 * !!! If an abstract class method does not make sense to be implemented in a particular RandVar class,
 * an UnsupportedOperationException should be thrown !!!
 *
 * Exponential distributed random variable.
 */
public class Exponential extends RandVar {

	private double lambda = 0;
	
	public Exponential(RNG rng) {
		this(rng, 1);
		// TODO Auto-generated constructor stub
	}
	
	public Exponential(RNG rng, double lambda){
		super(rng);
		this.lambda = lambda;
	}

	@Override
	public double getRV() {
		// TODO Auto-generated method stub
		if(lambda > 0){
			return -Math.log(rng.rnd())/lambda;
		}else{
			throw new IllegalArgumentException("argument is not set yet");
		}
	}

	@Override
	public double getMean() {
		// TODO Auto-generated method stub
		if(lambda > 0){
			return 1/lambda;
		}else{
			throw new IllegalArgumentException("argument is not set yet");
		}
	}

	@Override
	public double getVariance() {
		// TODO Auto-generated method stub
		if(lambda > 0){
			return 1/Math.pow(lambda, 2);
		}else{
			throw new IllegalArgumentException("argument is not set yet");
		}
	}

	@Override
	public void setMean(double m) {
		// TODO Auto-generated method stub
		lambda = 1/m;
	}

	@Override
	public void setStdDeviation(double s) {
		// TODO Auto-generated method stub
		lambda = 1/Math.pow(s, 2);
	}

	@Override
	public void setMeanAndStdDeviation(double m, double s) {
		// TODO Auto-generated method stub
		double l1 = 1/m;
		double l2 = 1/Math.pow(s, 2);
		if (l1 == l2){
			lambda = l1;
		}else{
			throw new IllegalArgumentException("no exponential distribution can fulfill this");
		}
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return "Exponential";
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString() + "\nhyperparameters:\n\tlambda: " + lambda + "\n";
	}
	
}
