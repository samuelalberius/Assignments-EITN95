import java.util.*;
import java.io.*;

class State extends GlobalSimulation{
	
	// Here follows the state variables and other variables that might be needed
	// e.g. for measurements
	public int numberInQueue = 0, accumulated = 0, noMeasurements = 0;
	private double lambda = 4.0/60; //4.0/60 rate per minute
	Random slump = new Random(); // This is just a random number generator
	
	//10+10*slump.nextDouble()
	// The following method is called by the main program each time a new event has been fetched
	// from the event list in the main loop. 
	public void treatEvent(Event x){
		switch (x.eventType){
			case ARRIVAL:
				arrival();
				break;
			case READY:
				ready();
				break;
			case MEASURE:
				measure();
				break;
		}
	}
	
	
	// The following methods defines what should be done when an event takes place. This could
	// have been placed in the case in treatEvent, but often it is simpler to write a method if 
	// things are getting more complicated than this.
	
	private void arrival(){
		if (time < workTime) { //only accept new orders if open
			if (numberInQueue == 0)
				insertEvent(READY, uniformDist());
			numberInQueue++;
			insertEvent(ARRIVAL, expDist(lambda));
		}
	}
	
	private void ready(){
		numberInQueue--;
		if (numberInQueue > 0)
			insertEvent(READY, uniformDist());
	}
	
	private void measure(){
		accumulated = accumulated + numberInQueue;
		noMeasurements++;
		insertEvent(MEASURE, time + slump.nextDouble()*10);
	}


	private double uniformDist() {
		return time + 10+10*slump.nextDouble();
	}

	private double expDist(double lambda) {
		double y = - Math.log(1 - slump.nextDouble()) / lambda;
		return time + y;
	}
}