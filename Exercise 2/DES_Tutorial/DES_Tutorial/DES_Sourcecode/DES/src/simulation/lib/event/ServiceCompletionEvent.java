package simulation.lib.event;
import simulation.lib.Customer;
import study.SimulationState;

/**
 * Service completion event.
 * Customer's service is finished.
 */
public class ServiceCompletionEvent extends Event {
	private SimulationState state;
	/**
	 * Constructor
	 * @param state simulation state
	 * @param eventTime The time of the event occurrence
	 */
	public ServiceCompletionEvent(SimulationState state, long eventTime) {
		super(eventTime);
		this.state = state;
	}

	/**
	 * @see Event#process()
	 */
	@Override
	public void process() {
		// Get the current customer in service
		Customer currentCustomer = state.customerInService;

		// Check queue size
		if (state.queueSize > 0) {
			/*
             * TODO Step 3.1 - the queue contains customers
             * Set the next customer as state.customerInService (Hint: use state.waitingCustomer.popNextElement() and cast it to Customer)
             * Set the serviceStartTime of nextCustomer to the eventTime (Hint: use this.getTime())
             */
			// Customer nextCustomer = ...

			//state.customerInService = ...

			/*
             * TODO Step 3.1 - push a new service completion event to the event chain
             * Hint: firePushNewEventNotification expects a Class object (CustomerArrivalEvent, ServiceCompletionEvent or SimulationTerminationEvent)
             */
			//firePushNewEventNotification(...);

			/*
             * TODO Step 3.1 - update state.queueSize
             * One customer was removed from the queue and is now in service
             */
		} else {
			/*
             * TODO Step 3.2 - the queue is empty
             * Set the state.serverBusy flag to false
             * Set the state.customerInService to null
             */
		}

		/*
		 * TODO Step 3.3 - update statistics
		 * Increase sample counter with state.increaseNumSamplesByOne()
		 * Call fireUpdateStatisticsNotification(Object arg) (Hint: use currentCustomer as argument)
		 */
	}
}
