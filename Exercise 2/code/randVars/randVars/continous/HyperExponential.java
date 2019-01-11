package simulation.lib.randVars.continous;

import simulation.lib.randVars.RandVar;
import simulation.lib.rng.RNG;

/*
 * TODO Problem 2.3.2 - implement this class (section 3.2.4 in course syllabus)
 * !!! If an abstract class method does not make sense to be implemented in a particular RandVar class,
 * an UnsupportedOperationException should be thrown !!!
 *
 * Hyperexponential distributed random variable.
 */
public class HyperExponential extends RandVar {

	private double p1 = -1;
	private double p2 = -1;
	private double lambda1 = 0;
	private double lambda2 = 0;
	
	public HyperExponential(RNG rng) {
		super(rng);
		// TODO Auto-generated constructor stub
	}

	@Override
	public double getRV() {
		// TODO Auto-generated method stub
		if(p1 >= 0 && p2 >= 0 && lambda1 > 0 && lambda2 > 0){
			double u1 = rng.rnd();
			double u2 = rng.rnd();
			if(p1 >= u1){
				return -Math.log(u1)/lambda1;
			}else{
				return -Math.log(u2)/lambda2;
			}
		}else{
			throw new IllegalArgumentException("arguments are not set yet");
		}
	}

	@Override
	public double getMean() {
		// TODO Auto-generated method stub
		if(p1 >= 0 && p2 >= 0 && lambda1 > 0 && lambda2 > 0){
			return p1/lambda1 + p2/lambda2;
		}else{
			throw new IllegalArgumentException("arguments are not set yet");
		}
	}

	@Override
	public double getVariance() {
		// TODO Auto-generated method stub
		if(p1 >= 0 && p2 >= 0 && lambda1 > 0 && lambda2 > 0){
			return 2*p1/Math.pow(lambda1, 2) + 2*p2/Math.pow(lambda2, 2) - Math.pow(getMean(), 2); 
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
		double c = s/m;
		double q = (Math.pow(c, 2) - 1)/(Math.pow(c, 2) + 1);
		if (q >= 0){
			//p1 = 1/2 * (1 + Math.sqrt(q));
			//p2 = 1 - p1;
			//lambda1 = 2*p1/m;
			//lambda2 = 2*p2/m;
			lambda1 = 1/m * (1 - Math.sqrt(q));
			lambda2 = 1/m * (1 + Math.sqrt(q));
			if (lambda1 == lambda2){
				throw new IllegalArgumentException("lambda1 == lambda2 -> degenerated hyperexponential distribution");
			}
			p1 = (m*lambda1*lambda2 - lambda1)/(lambda2 - lambda1);
			p2 = 1 - p1;		
		}else{
			throw new IllegalArgumentException("no hyperexponential distribution can fulfill this");
		}
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return "HyperExponential";
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString() + "\nhyperparameters:\n\tp1: " + p1 + "\n\tlambda1: " + lambda1 + "\n\tp2: " + p2 + "\n\tlambda2: " + lambda2 + "\n";
	}
}
