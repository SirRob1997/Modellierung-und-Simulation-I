package study;

import simulation.lib.Simulator;

/**
 * Represents a simulation study. Contains diverse counters for statistics and
 * program/simulator parameters. Starts the simulation.
 */
public class SimulationStudy {
	// Configure program arguments here!!!
	// Note: Units are real time units (in seconds).
	// Get converted to simulation time units in setSimulationParameters.
	protected long cInterArrivalTime = 10;
	protected long cServiceTime = 11;
	protected long cSimulationTime=10000;

	/**
	 * Main method
	 *
	 * @param args
	 *            - none
	 */
	public static void main(String[] args) {
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
	}

	// PARAMETERS
	/**
	 * Turn on/off debug report in console.
	 */
	protected boolean isDebugReport = true;

	/**
	 * inter arrival time of customers (in simulation time).
	 */
	public long interArrivalTime;

	/**
	 * service time of a customer (in simulation time).
	 */
	public long serviceTime;

	// STATISTICS
	/**
	 * Maximum queue size.
	 */
	public long maxQS;

	/**
	 * Minimum queue size.
	 */
	public long minQS;

	/**
	 * Simulation time.
	 */
	public long simulationTime;

	private Simulator simulator;

	/**
	 * Constructor
	 *
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

		interArrivalTime = simulator.realTimeToSimTime(cInterArrivalTime);
		serviceTime = simulator.realTimeToSimTime(cServiceTime);
		simulationTime = simulator.realTimeToSimTime(cSimulationTime);
	}

	/**
	 * Initializes statistic objects. Adds them into Hashmap.
	 */
	private void initStatistics() {
		maxQS = Long.MIN_VALUE;
		minQS = Long.MAX_VALUE;

	}

	/**
	 * Report results. Print to console if isDebugReport = true. Print to csv
	 * files if isCsvReport = true. Note: Histogramms are only printed to csv
	 * files.
	 */
	public void report() {
		if (isDebugReport) {
			System.out.println("E[interArrivalTime] = " + simulator.simTimeToRealTime(interArrivalTime) + "\n"
					+ "E[serviceTime] = " + simulator.simTimeToRealTime(serviceTime) +"\n"
					+ "server utilization: "+ ((double) serviceTime / (double) interArrivalTime) + "\n");

			System.out.println("minimum queue size: " + minQS + "\n" + "maximum queue size: " + maxQS);
		}

	}
}
