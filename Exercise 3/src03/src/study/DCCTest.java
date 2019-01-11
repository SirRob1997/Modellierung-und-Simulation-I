package study;

import simulation.lib.counter.DiscreteConfidenceCounter;
import simulation.lib.randVars.RandVar;
import simulation.lib.randVars.continous.ErlangK;
import simulation.lib.randVars.continous.Exponential;
import simulation.lib.randVars.continous.Normal;
import simulation.lib.randVars.continous.HyperExponential;
import simulation.lib.rng.StdRNG;

/*
 * TODO Problem 3.1.3 and 3.1.4 - implement this class
 */
public class DCCTest {

    public static void main(String[] args) {
        testDCC();
    }

    public static void testDCC() {
        // TODO Auto-generated method stub
    	
    	double mean = 10;
    	double cvars[] = new double[]{0.25, 0.5, 1, 2, 4};
    	long seed = 2;
    	
    	int n_experiments = 500;
    	int n_samples[] = new int[]{5, 10, 50, 100};
    	
    	double significance_levels[] = new double[]{0.1, 0.05};
    	RandVar distributions[] = new RandVar[]{
	    			new Normal(new StdRNG(seed+(long)Math.pow(10, 5))), 
	    			new Exponential(new StdRNG(seed+(long)Math.pow(10, 5)), 1),
	    			new ErlangK(new StdRNG(seed+(long)Math.pow(10, 5)), 1, 1),
	    			new HyperExponential(new StdRNG(seed+(long)Math.pow(10, 5)))
	    	};
    	
	    for (RandVar distribution : distributions){
	    	
	    	for (double cvar : cvars){
	    	
	    		try{
	    			distribution.setMeanAndCvar(mean, cvar);
	    			
	    			for (double significance_level : significance_levels){
	    			
	    				for (int n_sample : n_samples){
	    				
	    					double fraction_of_correct_means = 0;
	    					
	    					String name = "Type:    " + distribution.getType() + "\n" + 
	    									"Cvar:    " + String.valueOf(cvar) + "\n" + 
	    									"alpha:   " + String.valueOf(significance_level) + "\n" + 
	    									"Samples: " + String.valueOf(n_sample);
	    					
	    					System.out.println(name);
	    					
	    					DiscreteConfidenceCounter dcc = new DiscreteConfidenceCounter(name, significance_level);
	    					
	    					for (int i = 0; i < n_experiments; i++){
	    						for (int j = 0; j < n_sample; j++){
	    							dcc.count(distribution.getRV());
	    						}
	    						double low = dcc.getLowerBound();
	    						double high = dcc.getUpperBound();
	    						// System.out.println(String.valueOf(low) + "\t" + String.valueOf(dcc.getMean()) + "\t" + String.valueOf(high));
	    						if (low <= mean && mean <= high){
	    							fraction_of_correct_means += 1.0;
	    						}
	    					}
	    					
	    					fraction_of_correct_means /= (double) n_experiments;
	    					System.out.println("Estimated Coverage: " + String.valueOf(fraction_of_correct_means) + "\n -----------------------------");
	    				
	    				}
	    				
	    			}
	    			
	    			
	    		}catch(Exception e){
	    			System.out.println(e);
	    		}
	    	}	
    	}
    }
}
