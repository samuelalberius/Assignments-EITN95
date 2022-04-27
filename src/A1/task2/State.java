package A1.task2;

import java.util.*;

class State extends GlobalSimulation{
	
	// Here follows the state variables and other variables that might be needed
	// e.g. for measurements
	public int accumulated = 0, noMeasurements = 0;

	private int noJobsA = 0;
	private int noJobsB = 0;

	private double lambda = 150;
	private double x_a = 0.002;
	private double x_b = 0.004;
	private int d = 1;

	Random slump = new Random(); // This is just a random number generator
	SimpleFileWriter W = new SimpleFileWriter("number1.m", false);
	// The following method is called by the main program each time a new event has been fetched
	// from the event list in the main loop. 
	public void treatEvent(Event x){
		switch (x.eventType){
			case ARRIVAL_A:
				arrivalA();
				break;
			case ARRIVAL_B:
				arrivalB();
				break;
			case READY_A:
				readyA();
				break;
			case READY_B:
				readyB();
				break;	
			case MEASURE:
				measure();
				break;
		}
	}
	
	// The following methods defines what should be done when an event takes place. This could
	// have been placed in the case in treatEvent, but often it is simpler to write a method if 
	// things are getting more complicated than this.
	
	private void arrivalA() {
		noJobsA++;
		if (noJobsA == 1 && noJobsB == 0) {
			insertEvent(READY_A, constDist(x_a));
		}
		// keep adding JobA to queue
		insertEvent(ARRIVAL_A, expDist(lambda));
	}

	private void arrivalB() {
		noJobsB++;
		if (noJobsB == 1 && noJobsA == 0) {
			insertEvent(READY_B, constDist(x_b));
		}
	}
	
	private void readyA() {
		noJobsA--;
		// insert connection-termination job const(d) time from now
		insertEvent(ARRIVAL_B, constDist(d));
		//insertEvent(ARRIVAL_B, expDist(1.0));
		if (noJobsB > 0) {
			insertEvent(READY_B, constDist(x_b));
		} else if (noJobsA > 0) {
			insertEvent(READY_A, constDist(x_a));
		}
	}

	private void readyB() {
		noJobsB--;
		if (noJobsB > 0) {
			insertEvent(READY_B, constDist(x_b));
		} else if (noJobsA > 0) {
			insertEvent(READY_A, constDist(x_a));
		}
	}

	private void measure() {
		accumulated = accumulated+noJobsA + noJobsB;
		//System.out.println(accumulated);
		noMeasurements++;
		// measure again const(0.1) time from now
		W.println(String.valueOf(noJobsA+noJobsB));
		insertEvent(MEASURE, constDist(0.1));
	}
	
	// Inversion method to go from normal distribution to exponential distribution
	private double expDist(double lambda) {
		//double lambda = 1.0 / mean;
		double y = - Math.log(1 - slump.nextDouble())/lambda;
		return time + y;
	}

	private double constDist(double lambda) {
		return time + lambda;
	}
}