package study;

import simulation.lib.counter.DiscreteCounter;
import simulation.lib.randVars.RandVar;
import simulation.lib.randVars.continous.ErlangK;
import simulation.lib.randVars.continous.Exponential;
import simulation.lib.randVars.continous.HyperExponential;
import simulation.lib.randVars.continous.Uniform;
import simulation.lib.rng.StdRNG;

/*
 * TODO Problem 2.3.3 and 2.3.4[Bonus] - implement this class
 * You can call your test from the main-method in SimulationStudy
 */
public class RandVarTest {

	public static void main(String[] args){
		testRandVars();
	}
	
    public static void testRandVars() {
        // TODO Auto-generated method stub
    	long currentTimeMillis = System.currentTimeMillis();
    	StdRNG random = new StdRNG(currentTimeMillis);
    	RandVar t0 = new Uniform(random);
    	try{
        	t0.setMeanAndCvar(1.0, 0.1);
        	System.out.println(t0.toString());
    		testDist("u(1/0.1)", t0);
    	}catch(Exception e){
    		System.out.println("\n"+e);
    	}
    	RandVar t1 = new Uniform(random);
    	try{
        	t1.setMeanAndCvar(1.0, 1.0);
        	System.out.println(t1.toString());
    		testDist("u(1/1)", t1);
    	}catch(Exception e){
    		System.out.println("\n"+e);
    	}
    	RandVar t2 = new Uniform(random);
    	try{
        	t2.setMeanAndCvar(1.0, 2.0);
        	System.out.println(t2.toString());
    		testDist("u(1/2)", t2);
    	}catch(Exception e){
    		System.out.println("\n"+e);
    	}
    	RandVar t3 = new Exponential(random);
    	try{
        	t3.setMeanAndCvar(1.0, 0.1);
        	System.out.println(t3.toString());
    		testDist("e(1/0.1)", t3);
    	}catch(Exception e){
    		System.out.println("\n"+e);
    	}
    	RandVar t4 = new Exponential(random);
    	try{
        	t4.setMeanAndCvar(1.0, 1.0);
        	System.out.println(t4.toString());
    		testDist("e(1/1)", t4);
    	}catch(Exception e){
    		System.out.println("\n"+e);
    	}
    	RandVar t5 = new Exponential(random);
    	try{
        	t5.setMeanAndCvar(1.0, 2.0);
        	System.out.println(t5.toString());
    		testDist("e(1/2)", t5);
    	}catch(Exception e){
    		System.out.println("\n"+e);
    	}
    	RandVar t6 = new ErlangK(random);
    	try{
        	t6.setMeanAndCvar(1.0, 0.1);
        	System.out.println(t6.toString());
    		testDist("k(1/0.1)", t6);
    	}catch(Exception e){
    		System.out.println("\n"+e);
    	}
    	RandVar t7 = new ErlangK(random);
    	try{
        	t7.setMeanAndCvar(1.0, 1.0);
        	System.out.println(t7.toString());
    		testDist("k(1/1)", t7);
    	}catch(Exception e){
    		System.out.println("\n"+e);
    	}
    	RandVar t8 = new ErlangK(random);
    	try{
        	t8.setMeanAndCvar(1.0, 2.0);
        	System.out.println(t8.toString());
    		testDist("k(1/2)", t8);
    	}catch(Exception e){
    		System.out.println("\n"+e);
    	}
    	RandVar t9 = new HyperExponential(random);
    	try{
        	t9.setMeanAndCvar(1.0, 0.1);
        	System.out.println(t9.toString());
    		testDist("h(1/0.1)", t9);
    	}catch(Exception e){
    		System.out.println("\n"+e);
    	}
    	RandVar t10 = new HyperExponential(random);
    	try{
        	t10.setMeanAndCvar(1.0, 1.0);
        	System.out.println(t10.toString());
    		testDist("h(1/1)", t10);
    	}catch(Exception e){
    		System.out.println("\n"+e);
    	}
    	RandVar t11 = new HyperExponential(random);
    	try{
        	t11.setMeanAndCvar(1.0, 2.0);
        	System.out.println(t11.toString());
    		testDist("h(1/2)", t11);
    	}catch(Exception e){
    		System.out.println("\n"+e);
    	}
    }
    
    public static void testDist(String testName, RandVar testDist){
    	DiscreteCounter dc = new DiscreteCounter(testName);
    	for (int i = 0; i < Math.pow(10, 6); i++){
    		double r = testDist.getRV();
    		dc.count(r);
    	}
    	System.out.println(dc.report());
    }
}
