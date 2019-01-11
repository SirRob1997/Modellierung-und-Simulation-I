package simulation.lib.randVars.continous;

import simulation.lib.randVars.RandVar;
import simulation.lib.rng.RNG;

/*
 * TODO Problem 2.3.2 - implement this class (section 3.2.1 in course syllabus)
 * !!! If an abstract class method does not make sense to be implemented in a particular RandVar class,
 * an UnsupportedOperationException should be thrown !!!
 *
 * Uniform distributed random variable.
 */
public class Uniform extends RandVar {

	private double low = Double.POSITIVE_INFINITY;
	private double high = Double.NEGATIVE_INFINITY;
	
	public Uniform(RNG rng) {
		this(rng, 0, 1);
		// TODO Auto-generated constructor stub
	}

	public Uniform(RNG rng, double low, double high){
		super(rng);
		if(low < high){
			this.low = low;
			this.high = high;
		}else{
			throw new IllegalArgumentException("no uniform distribution can fulfill this");
		}
	}
	
	@Override
	public double getRV() {
		// TODO Auto-generated method stub
		if(low != Double.POSITIVE_INFINITY || high != Double.NEGATIVE_INFINITY){
			return low + (high-low)*rng.rnd();
		}else{
			throw new IllegalArgumentException("arguments are not set yet");
		}
	}

	@Override
	public double getMean() {
		// TODO Auto-generated method stub
		if(low != Double.POSITIVE_INFINITY || high != Double.NEGATIVE_INFINITY){
			return (low + high)/2;
		}else{
			throw new IllegalArgumentException("arguments are not set yet");
		}
	}

	@Override
	public double getVariance() {
		// TODO Auto-generated method stub
		if(low != Double.POSITIVE_INFINITY || high != Double.NEGATIVE_INFINITY){
			return Math.pow((high - low), 2)/12;
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
		low = m - s * Math.sqrt(3.0);
		high = m + s * Math.sqrt(3.0);
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return "Uniform";
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString() + "\nhyperparameters:\n\tlow: " + low + "\n\thigh: " + high + "\n";
	}
	
}
