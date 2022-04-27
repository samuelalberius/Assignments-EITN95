package A1.task3;

import java.io.*;

public class MainSimulation extends GlobalSimulation{
 
public static void main(String[] args) throws IOException {
    	
		double[] arrivalRates = {2, 1.5, 1.1};
		int measurmentsCap = 50000;
		
		for (double rate : arrivalRates) {
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

			System.out.println("Mean no in queueing system: " + 1.0 * actState.accumulated / actState.noMeasurements);
			System.out.println("Mean time in queueing system: " + actState.accumulatedTimeInSystem / actState.numberOfTimes);
		}
    }
}