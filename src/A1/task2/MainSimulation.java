package A1.task2;

import java.io.IOException;

public class MainSimulation extends GlobalSimulation{
 
    public static void main(String[] args) throws IOException {
    	Event actEvent;
    	State actState = new State(); // The state that shoud be used
    	// Some events must be put in the event list at the beginning
        insertEvent(ARRIVAL_A, 0);  
        insertEvent(MEASURE, 0.1);
        
        // The main simulation loop
    	while (actState.noMeasurements < 1000){
    		actEvent = eventList.fetchEvent();
 
    		time = actEvent.eventTime;
    		actState.treatEvent(actEvent);
    	}
    	
    	// Printing the result of the simulation, in this case a mean value
    	System.out.println(1.0*actState.accumulated/actState.noMeasurements);
		actState.W.close();
    }
}