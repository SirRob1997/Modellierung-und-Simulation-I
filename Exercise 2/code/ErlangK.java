package simulation.lib.randVars.continous;

import simulation.lib.randVars.RandVar;
import simulation.lib.rng.RNG;

/*
 * TODO Problem 2.3.2 - implement this class (section 3.2.3 in course syllabus)
 * !!! If an abstract class method does not make sense to be implemented in a particular RandVar class,
 * an UnsupportedOperationException should be thrown !!!
 *
 * Erlang-k distributed random variable.
 */
public class ErlangK extends RandVar {

	private int k = 0;
	private double lambda = 0;
	
	public ErlangK(RNG rng) {
		this(rng, 1, 1);
		// TODO Auto-generated constructor stub
	}
	
	public ErlangK(RNG rng, int k, double lambda){
		super(rng);
		this.k = k;
		this.lambda = lambda;
	}

	@Override
	public double getRV() {
		// TODO Auto-generated method stub
		if(k != 0 && lambda > 0){
			double u = 1.0;
			for (int i = 0; i < k; i++){
				u *= rng.rnd();
			}
			return -1/lambda * Math.log(u);
		}else{
			throw new IllegalArgumentException("arguments are not set yet");
		}
	}

	@Override
	public double getMean() {
		// TODO Auto-generated method stub
		if(k != 0 && lambda > 0){
			return k/lambda;
		}else{
			throw new IllegalArgumentException("arguments are not set yet");
		}
	}

	@Override
	public double getVariance() {
		// TODO Auto-generated method stub
		if(k != 0 && lambda > 0){
			return k/Math.pow(lambda, 2);
		}else{
			throw new IllegalArgumentException("arguments are not set yet");
		}
	}

	@Override
	public void setMean(double m) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("need more information, call setMeanAndStdDeviation instead");
	}

	@Override
	public void setStdDeviation(double s) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("need more information, call setMeanAndStdDeviation instead");
	}

	@Override
	public void setMeanAndStdDeviation(double m, double s) {
		// TODO Auto-generated method stub
		double kt = Math.pow(m/s, 2);
		if (Math.abs((int) kt - kt) < Math.pow(10, -16)){
			k = (int) kt;
			lambda = m/Math.pow(s, 2);
		}else{
			throw new IllegalArgumentException("no erlang distribution can fulfill this");
		}
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return "ErlangK";
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString() + "\nhyperparameters:\n\tk: " + k + "\n\tlambda: " + lambda + "\n";
	}		
}
