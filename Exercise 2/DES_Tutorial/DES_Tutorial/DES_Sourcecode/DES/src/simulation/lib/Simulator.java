package simulation.lib;

import simulation.lib.event.CustomerArrivalEvent;
import simulation.lib.event.Event;
import simulation.lib.event.IEventObserver;
import simulation.lib.event.ServiceCompletionEvent;
import simulation.lib.event.SimulationTerminationEvent;
import study.SimulationState;
import study.SimulationStudy;

/**
 * Main Simulator class for the discrete event simulation
 */
public class Simulator implements IEventObserver{
	private long simTimeInRealTime;
	private long now;
	private SortableQueue ec;
	private boolean stop;
	
	/**
	 * Contains simulator statistics and parameters
	 */
	private SimulationStudy sims;
	
	/**
	 * Contains simulator state.
	 */
	private SimulationState state;
	
	/**
	 * Constructor 
	 * Create event chain (ec), SimulationStudy- and SimulationState- objects
	 * Pushes first event (customer arrival event) to queue
	 */
	public Simulator() {
		// create event chain
		ec = new SortableQueue();
		sims = new SimulationStudy(this);
		state = new SimulationState(sims);
		// push the first customer arrival at t = 0
		pushNewEvent(new CustomerArrivalEvent(state, 0));
		// push the termination event at the simulationTime (max duration of simulation)
		pushNewEvent(new SimulationTerminationEvent(sims.simulationTime));
	}
	
	/**
	 * Sets the number of ticks in simulation time per unit in real time
	 * @param simTimeInRealTime number of ticks per unit in real time
	 */
	public void setSimTimeInRealTime(long simTimeInRealTime) {
		this.simTimeInRealTime = simTimeInRealTime;
	}
	
	/**
	 * Converts real time to sim time
	 * @param realTime units in real time
	 * @return units in sim time
	 * @throws Exception if sim time is out of range
	 */
	public long realTimeToSimTime(double realTime) throws NumberFormatException {
		/*
		 * TODO Step 4.1 - convert real time to simulation time
		 * Hint: multipy with this.simTimeInRealTime as conversion factor
		 * Round up conversion result (Use Math.ceil(...))
		 * Check if conversion result is greater than Long.MAX_VALUE and throw a new NumberFormatException if that's the case
		 */
		return 0;
	}
	
	/**
	 * Converts sim time to real time
	 * @param simTime units in sim time
	 * @return units in real time
	 */
	public double simTimeToRealTime(long simTime) {
		/*
		 * TODO Step 4.1 - convert simulation time to real time
		 * Hint: Again, use this.simTimeInRealTime as conversion factor
		 * Cast result to double
		 */
		return 0;
	}
	
	/**
	 * Starts the simulation
	 * @throws Exception is thrown when event order is invalid
	 */
	private void run() {
		while(!stop) {
			Event e = (Event) ec.popNextElement();
			if(e != null) {
				//check if event time is in the past
				if(e.getTime() < now) {
					throw new RuntimeException("Event time " + e.getTime()
							+ " smaller than current time " + now);
				}
				/*
				 * @TODO Step 4.2 - set the simulation time
				 * Hint: use e.getTime() to retrieve the current time
				 */
				//this.now = ...

				/*
				 * @TODO Step 4.2 - register the simulator as observer to get event notifications
				 * Hint: use e.register(IEventObserver obs)
				 */

				/*
				 * @TODO Step 4.2 - process event
				 * Hint: use e.process()
				 */

				/*
				 * @TODO Step 4.2 - unregister simulator from  event notifications
				 * Hint: use e.register(IEventObserver obs)
				 */
			} else {
				System.out.println("Event chain empty.");
				stop = true;
			}
		}
	}
	
	/**
	 * Pushes a new event into the event chain at the correct place
	 * @param e the new event
	 */
	private void pushNewEvent(Event e) {
		ec.pushNewElement(e);
	}
	
	/**
	 * Stops the simulator
	 */
	private void stop() {
		stop = true;
	}

    /**
     * Starts the simulation.
     */
	public void start() {
		stop = false;
		run();
	}
	
	/**
	 * Returns the current sim time
	 * @return current sim time
	 */
	public long getSimTime() {
	    return now;
	}
	
	/**
	 * Resets the simulator
	 */
	public void reset() {
		now = 0;
		stop = false;
		ec.clear();
	}
	
	/**
	 * Reports results.
	 */
	public void report() {
	    sims.report();
	}
	

	/**
	 * Handles update statistics event from IObservableEvent objects.
	 */
	public void updateStatisticsHandler(Object sender, Object arg) {
		if (sender.getClass() == CustomerArrivalEvent.class) {
			updateStatsCAE();
		}else if(sender.getClass() == ServiceCompletionEvent.class){
			updateStatsSCE((Customer) arg);
		}
	}
	
	/**
	 * Update statistics, fired from customer arrival event
	 */
	private void updateStatsCAE(){
		updateQueueSize();
	}

	/**
	 * Update statistics, fired from service completion event
	 * @param currentCustomer current customer
	 */
	private void updateStatsSCE(Customer currentCustomer){
		updateQueueSize();

		/*
		 * @TODO Step 4.2 - set the currentCustomer.serviceEndTime
		 * Hint: use this.getSimTime() to get the current Time
		 */
	}

	/**
	 * Updates the queue size variable in the simulation state
	 */
	private void updateQueueSize() {
		sims.minQS = state.queueSize < sims.minQS ? state.queueSize : sims.minQS;
		sims.maxQS = state.queueSize > sims.maxQS ? state.queueSize : sims.maxQS;
	}
	
	/**
	 * @see IEventObserver#updateQueueOccupancyHandler(Object sender)
	 */
	public void updateQueueOccupancyHandler(Object sender) {
		//To be implemented in later exercises!(Here you need Counter objects.)
	}

	/**
	 * @see IEventObserver#pushNewEventHandler(Class<?>)
	 */
	public void pushNewEventHandler(Class<?> c) {
		if (c == CustomerArrivalEvent.class) {
			/*
			 * @TODO Step 4.3 - create new CustomerArrivalEvent
			 * For the event time use the sum of the sims.interArrivalTime and the current simulation time (can be obtained by using getSimTime())
			 */
			// CustomerArrivalEvent cae = ...
			// pushNewEvent(cae);
		}else if(c == ServiceCompletionEvent.class){
			/*
			 * @TODO Step 4.3 - create new ServiceCompletionEvent
			 * For the event time use the sum of the sims.serviceTime and the current simulation time (can be obtained by using getSimTime())
			 */
			// ServiceCompletionEvent sce = ...
			// pushNewEvent(sce);
		}
	}

	/**
	 * @see IEventObserver#stopEventHandler(Object sender)
	 */
	public void stopEventHandler(Object sender) {
		stop();
	}
}
