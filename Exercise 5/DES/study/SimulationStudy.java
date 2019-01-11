package study;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import simulation.lib.Simulator;
import simulation.lib.counter.*;
import simulation.lib.histogram.ContinuousHistogram;
import simulation.lib.histogram.DiscreteHistogram;
import simulation.lib.randVars.RandVar;
import simulation.lib.randVars.continous.ErlangK;
import simulation.lib.randVars.continous.Exponential;
import simulation.lib.randVars.continous.HyperExponential;
import simulation.lib.rng.StdRNG;
import simulation.lib.statistic.IStatisticObject;

/**
 * Represents a simulation study. Contains diverse counters for statistics and
 * program/simulator parameters. Starts the simulation.
 */
public class SimulationStudy {
	 /*
	 * TODO Problem 5.1 - Configure program arguments here
	 * TODO Problem 5.1.1 - nInit and lBatch
	 * TODO Problem 5.1.3 - Add attributes to configure your E[ST] and E[IAT] for the simulation
	 * Here you can set the different parameters for your simulation
	 * Note: Units are real time units (seconds).
	 * They get converted to simulation time units in setSimulationParameters.
	 */
	 // e.g. protected cNInit = ...
	 //protected cCvar = ... <- configuration Parameter for cVar[IAT]

	protected double alpha = 0.1;
	protected int cNInit = 1000;
	protected int lBatch = 1000;
	public static double cCvar = 0.5;
	public static double pSystemUtilization = 0.95;

	/**
	 * Main method
	 * 
	 * @param args
	 *            - none
	 */
	public static void main(String[] args) {
		double[] cVars = new double[]{0.5, 1.0, 2.0};
		for (int i = 0; i < 3; i ++){
			for (int j = 0; j < 19; j++){
				cCvar = cVars[i];
				pSystemUtilization = (double)(j+1)*0.05;
				/*
				 * create simulation object
				 */
				Simulator sim = new Simulator();
				/*
				 * run simulation
				 */
				sim.start();
				/*
				 * print out report
				 */
				sim.report();
				System.out.println("FINISHED: " + cCvar + " " + pSystemUtilization);
			}
		}
	}

	// PARAMETERS
	/**
	 * Turn on/off debug report in console.
	 */
	protected boolean isDebugReport = true;

	/**
	 * Turn on/off report in csv-files.
	 */
	protected boolean isCsvReport = false;

	/**
	 * inter arrival time of customers (in simulation time).
	 */
	public long interArrivalTime;

	/**
	 * service time of a customer (in simulation time).
	 */
	public long serviceTime;

	/**
	 * Number of customers for initialization.
	 */
	public long nInit;

	/**
	 * Length of batches.
	 */
	public long batchLength;

	/**
	 * Coefficient of variation.
	 */
	public double cVar;

	/**
	 * Random number generator for inter arrival times.
	 */
	public RandVar randVarInterArrivalTime;

	/**
	 * random number generator for service times
	 */
	public RandVar randVarServiceTime;

	// STATISTICS
	/**
	 * Map that contains all statistical relevant object such as counters and
	 * histograms.
	 */
	public HashMap<String, IStatisticObject> statisticObjects;

	/**
	 * Maximum queue size.
	 */
	public long maxQS;

	/**
	 * Minimum queue size.
	 */
	public long minQS;

	/**
	 * Number of batches in simulation.
	 */
	public long numBatches;

	/*
	 * TODO Problem 5.1 - naming your statistic objects
	 * Here you have to set some names (as Sting objects) for all your statistic objects
	 * They are later used to retrieve them from the dictionary
	 */
	// Strings used for receiving statisticobjects later in the dictionary.
	public String dtcWaitingTime = "discreteTimeCounterWaitingTime";
	public String dthWaitingTime = "discreteTimeHistogramWaitingTime";

	public String dtcServiceTime = "discreteTimeCounterServiceTime";
	public String dthServiceTime = "discreteTimeHistogramServiceTime";

	public String ctcQueueOccupancy = "continuousTimeCounterQueueOccupancy";
	public String cthQueueOccupancy = "continuousTimeHistogramQueueOccupancy";

	public String ctcServerUtilization = "continuousTimeCounterServerUtilization";
	public String cthServerUtilization = "continuousTimeHistogramServerUtilization";

	public String dtcBatchWaitingTime = "discreteTimeCounterBatchWaitingTime";
	public String tempdtcBatchWaitingTime = "temporaryDiscreteTimeCounterBatchWaitingTime";

	public String dtcBatchServiceTime = "discreteTimeCounterBatchServiceTime";
	public String tempdtcBatchServiceTime = "temporaryDiscreteTimeCounterBatchServiceTime";

	public String ccDummyWaitingTime = "confidenceCounterDummy";
	public String ccreBatchWaitingTime = "confidenceCounterWithRelativeErrorBatchWaitingTime";
	public String ccreWaitingTime = "confidenceCounterWithRelativeErrorWaitingTime";

	public long numWaitingTimeExceeds5TimesServiceTime;
	public long numBatchWaitingTimeExceeds5TimesBatchServiceTime;
	public long numWaitingTimeExceeds0;

	public String dtacBatchWaitingTime = "discreteTimeAutocorrelationCounterBatchWaitingTime";

	private Simulator simulator;

	/**
	 * Constructor
	 * @param sim Simulator instance.
	 */
	public SimulationStudy(Simulator sim) {
		simulator = sim;
		simulator.setSimTimeInRealTime(1000);
		setSimulationParameters();
		initStatistics();
	}

	/**
	 * Sets simulation parameters, converts real time to simulation time if
	 * needed.
	 */
	private void setSimulationParameters() {

		/*
		 * TODO Problem 5.1.1 - Set simulation parameters
		 * Hint: Take a look at the attributes of this class which have no usages yet (This may be indicated by your IDE)
		 */
		this.nInit = cNInit;
		this.batchLength = lBatch;
		this.cVar = cCvar;


		/*
		 * TODO Problem 5.1.2 - Create random variables for IAT and ST
		 * You may use different random variables for this.randVarInterArrivalTime, since Cvar[IAT] = {0.5, 1, 2}
		 * You can use this.cVar as a configuration parameter for Cvar[IAT]
		 * !!! Make sure to use StdRNG objects with different seeds !!!
		 */

		StdRNG rng1 = new StdRNG(System.currentTimeMillis());
		double mean1 = 1.0/pSystemUtilization;
		this.randVarInterArrivalTime = new Exponential(rng1, mean1);

		StdRNG rng2 = new StdRNG(System.currentTimeMillis() + 1);
		double mean2 = 1.0;
		this.randVarServiceTime = new Exponential(rng2, mean2);
	}

	/**
	 * Initializes statistic objects. Adds them into Hashmap.
	 */
	private void initStatistics() {
		maxQS = Long.MIN_VALUE;
		minQS = Long.MAX_VALUE;

		// Init numBatches
		numBatches = 0;

		statisticObjects = new HashMap<>();
		statisticObjects.put(dtcWaitingTime, new DiscreteCounter("waiting time/customer"));
		statisticObjects.put(dthWaitingTime, new DiscreteHistogram("waiting_time_per_customer", 80, 0, 80));

		statisticObjects.put(dtcServiceTime, new DiscreteCounter("service time/customer"));
		statisticObjects.put(dthServiceTime, new DiscreteHistogram("service_time_per_customer", 80, 0, 80));

		statisticObjects.put(ctcQueueOccupancy, new ContinuousCounter("queue occupancy/time", simulator));
		statisticObjects.put(cthQueueOccupancy,
				new ContinuousHistogram("queue_occupancy_over_time", 80, 0, 80, simulator));

		statisticObjects.put(ctcServerUtilization, new ContinuousCounter("server utilization/time", simulator));
		statisticObjects.put(cthServerUtilization,
				new ContinuousHistogram("server_utilization_over_time", 80, 0, 80, simulator));

		/*
		 * TODO Problem 5.1.1 - Create a DiscreteConfidenceCounterWithRelativeError
		 * In order to check later if the simulation can be terminated according to the condition
		 */

		statisticObjects.put(ccDummyWaitingTime, new DiscreteCounter("dummywaitingtime for reset task 1"));
		statisticObjects.put(ccreBatchWaitingTime, new DiscreteConfidenceCounterWithRelativeError("batch means waiting time task 1", alpha));

		/*
		 * TODO Problem 5.1.4 - Create counter to calculate the mean waiting time with batch means method
		 */
		/*
		 * TODO Problem 5.1.4 - Provide means to keep track of E[WT] > 5 * E[ST]
		 * !!! This is also called "waiting probability" in the sheet !!!
		 */
		/*
		 * TODO Problem 5.1.4 - Create confidence counter for individual waiting time samples
		 */
		statisticObjects.put(ccreWaitingTime, new DiscreteConfidenceCounterWithRelativeError("single waiting time task 4", alpha));

		/*
		 * TODO Problem 5.1.4 - Create confidence counter for to count waiting times with batch means method
		 */
		statisticObjects.put(tempdtcBatchWaitingTime, new DiscreteCounter("temporary waiting time task 4"));
		statisticObjects.put(dtcBatchWaitingTime, new DiscreteConfidenceCounterWithRelativeError("batch means waiting time task 4", alpha));

		statisticObjects.put(tempdtcBatchServiceTime, new DiscreteCounter("temporary service time task 4"));
		statisticObjects.put(dtcBatchServiceTime, new DiscreteConfidenceCounterWithRelativeError("batch means service time task 4", alpha));
		/*
		 * TODO Problem 5.1.5 - Create a DiscreteAutocorrelationCounter for batch means
		 */
		statisticObjects.put(dtacBatchWaitingTime, new DiscreteAutocorrelationCounter("batch means autocorrelation", 100));
	}


	/**
	 * Report results. Print to console if isDebugReport = true. Print to csv
	 * files if isCsvReport = true. Note: Histogramms are only printed to csv
	 * files.
	 */
	public void report() {
		String sd = new SimpleDateFormat("yyyyMMdd_HHmmss_").format(new Date(System.currentTimeMillis()));
		String destination = sd + this.getClass().getSimpleName();

		if (isCsvReport) {
			File file = new File(destination);
			file.mkdir();
			for (IStatisticObject so : statisticObjects.values()) {
				so.csvReport(destination);
			}
		}
		if (isDebugReport) {
			/*
			 * TODO Problem 5.1 - Output reporting information!
			 * Print your statistic objects which are needed to answer the questions in the exercise sheet
			 */
			for(IStatisticObject so : statisticObjects.values()){
				System.out.println(so.report());
			}
			try{
				FileWriter writer = new FileWriter("./data/data.txt", true);
				DiscreteConfidenceCounterWithRelativeError a = (DiscreteConfidenceCounterWithRelativeError) statisticObjects.get(ccreWaitingTime);
				DiscreteConfidenceCounterWithRelativeError b = (DiscreteConfidenceCounterWithRelativeError) statisticObjects.get(ccreBatchWaitingTime);
				DiscreteAutocorrelationCounter c = (DiscreteAutocorrelationCounter) statisticObjects.get(dtacBatchWaitingTime);

				String s = String.valueOf(cVar) + "\t" +
							String.valueOf(pSystemUtilization) + "\t" +
							String.valueOf(a.getMean()) + "\t" +
						String.valueOf(a.getLowerBound()) + "\t" +
						String.valueOf(a.getUpperBound()) + "\t" +
						String.valueOf((double)numWaitingTimeExceeds5TimesServiceTime/(double)(simulator.getStateNumberOfSamples() - nInit) + "\t" +
						String.valueOf(b.getMean()) + "\t" +
						String.valueOf(b.getLowerBound()) + "\t" +
						String.valueOf(b.getUpperBound()) + "\t" +
						String.valueOf((double)numBatchWaitingTimeExceeds5TimesBatchServiceTime/(double)simulator.getStateNumberOfBatches())) + "\t";
				for (int i = 0; i  <= (c.getNumSamples() < c.getMaxLag() ? c.getNumSamples() : c.getMaxLag()); i++){
					s += c.getAutoCorrelation(i)+ "\t";
				}
				s += "\n";
				writer.append(s);
				writer.flush();
				writer.close();
			}catch (Exception e){
				System.out.println(e);
			}

		}

		System.out.println("numWaitingTimeExceeds5TimesServiceTime " + numWaitingTimeExceeds5TimesServiceTime);
		System.out.println("numBatchWaitingTimeExceeds5TimesBatchServiceTime " + numBatchWaitingTimeExceeds5TimesBatchServiceTime);
		System.out.println("numWaitingTimeExceeds0 " + numWaitingTimeExceeds0);

	}
}
