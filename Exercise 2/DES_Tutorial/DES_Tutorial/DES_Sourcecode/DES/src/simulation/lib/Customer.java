package simulation.lib;

import simulation.lib.event.SortableQueueItem;

/**
 * Represents the customer in the simulation.
 * A customer is the object that is served and queued by the simulator. 
 * This class helps to answer the following:
 * * Waiting time of the customer until service.
 * * Service time of the customer.
 */
public class Customer extends SortableQueueItem {
    
	/**
     * Arrival time of customer at the server.
     */
	public long arrivalTime;
	
	/**
	 * Service starting time of customer at the server.
	 */
    public long serviceStartTime;
    
    /**
     * Service ending time for customer at the server.
     */
    public long serviceEndTime;

    /**
     * Constructor.
     * @param arrivalTime arrival time
     */
    public Customer(long arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    /**
     * Compares the arrival time of this customer to the arrival time of another customer
     * Returns 0 if they arrive at the same time
     * Returns 1 if this customer arrives after the other customer (arrivalTime of otherCustomer is smaller)
     * Returns -1 if this customer arrives before the other customer (arrivalTime of otherCustomer is greater)
     * @return comparison result
     */
    @Override
    public int compareTo(SortableQueueItem o) {
        Customer otherCustomer = (Customer) o;

        /**
         * TODO Step 1 - compareTo
         * Implement this function according to the customer class diagram!
         * Hint: Take a look at the javadoc comment of this function
         */

        return 0;
    }

    /**
     * Calculates the time the customer waited in the queue.
     * @return waiting time
     */
    public long getTimeInQueue() {
        /**
         * TODO Step 1 - getTimeInQueue
         * Implement this function according to the customer class diagram!
         * Hint: The customer stays in the queue from his arrival time to the time he is served (serviceStartTime)
         */
        long timeInQueue = 0;
        return timeInQueue;
    }

    /**
     * Calculates the time the customer was in service.
     * @return service time
     */
    public long getTimeInService() {
        /**
         * TODO Step 1 - getTimeInService
         * Implement this function according to the customer class diagram!
         */
        long timeInService = 0;
        return timeInService;
    }
}
