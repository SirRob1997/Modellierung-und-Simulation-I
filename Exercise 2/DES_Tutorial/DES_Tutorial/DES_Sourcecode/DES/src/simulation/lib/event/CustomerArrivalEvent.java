package simulation.lib.event;

import simulation.lib.Customer;
import study.SimulationState;

/**
 * Customer arrival event. Customer arrives at the single server queue simulator.
 */
public class CustomerArrivalEvent extends Event {
	private SimulationState state;

	/**
	 * Constructor
	 * @param eventTime The time of the event occurrence
	 */
	public CustomerArrivalEvent(SimulationState state, long eventTime) {
		super(eventTime);
		this.state = state;
	}

	/**
	 * @see Event#process()
	 */
	@Override
	public void process() {
		fireUpdateQueueOccupancyNotification();
		/*
		 * TODO Step 2.1 - create new Customer with the eventTime as arrivalTime:
		 */
		long eventTime = this.getTime();
		// Customer customer = ...

        /*
		 * TODO Step 2.1 - push a new event notification that there is a new customer arrival event
		 * Hint: firePushNewEventNotification expects a Class object (CustomerArrivalEvent, ServiceCompletionEvent or SimulationTerminationEvent)
		 */
        //firePushNewEventNotification(...);


		if (state.serverBusy == true) {
            /*
             * TODO Step 2.2 - the server is busy
             * Push the customer to the state.waitingCustomers list
             * And update state.queueSize
             */
		} else {
		    /*
		     * The server is not busy
             * TODO Step 2.3 - start the service of the new customer
             * Set state.customerInService to the newly created customer
             * Set customer.serviceStartTime to the eventTime
             */
			// state.customerInService = ...
            // customer.serviceStartTime = ...

			/*
             * TODO Step 2.3 - push a new service completion event
             * Push a service completion event to the event queue
             * Hint: firePushNewEventNotification expects a Class object (CustomerArrivalEvent, ServiceCompletionEvent or SimulationTerminationEvent)
             */
			//firePushNewEventNotification(...);

            /*
             * TODO Step 2.3 - set the serverBusy flag
             */
		}

		/*
         * TODO Step 2.4 - inform the simulator that it has to update its statistic objects
         * Hint: Use null as argument for the fireUpdateStatisticsNotification() method
         */
	}
}
