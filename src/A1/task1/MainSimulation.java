package A1.task1;

import java.io.*;

public class MainSimulation extends GlobalSimulation{
 
public static void main(String[] args) throws IOException {
    	
		int[] arrivalRates = {1, 2, 5};
		int measurmentsCap = 50000;
		
		for (int rate : arrivalRates) {
			Event actEvent;
			State actState = new State(); // The state that shoud be used
			insertEvent(ARRIVALQ1, 0);  
			insertEvent(MEASURE, 5);
			
			actState.arrivalTimeQ1 = rate;
			
			System.out.println("\nTask 1, q1 arrival time: " + rate);

			while (actState.noMeasurements < measurmentsCap) {
				actEvent = eventList.fetchEvent();
				time = actEvent.eventTime;
				actState.treatEvent(actEvent);
			}

			System.out.println("Mean no in Queue2: " + 1.0 *
			
			actState.accumulated / actState.noMeasurements);
			System.out.println("Rejection rate: " + 100 * (double) 
			
			actState.rejected / actState.arrived + "%\n");
		}
    }
}