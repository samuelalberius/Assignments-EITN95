package A2.task1;

import java.io.*;

public class MainSimulation extends GlobalSimulation{
 
    public static void main(String[] args) throws IOException {

    	Event actEvent;

		// Choose task 1, 2, 3, 4, 5 or 6;
		int task = 1;
    	State actState = new State(task);

        insertEvent(ARRIVAL, 0);  
        insertEvent(MEASURE, actState.T);
        
    	while (actState.noMeasurements < actState.M) {
    		actEvent = eventList.fetchEvent();
    		time = actEvent.eventTime;
    		actState.treatEvent(actEvent);
    	}
		actState.closeFile();

		System.out.println("Mean number of customers: " + 1.0 * actState.accumulated / actState.M);
    }
}