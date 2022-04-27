package A1.task3;

import java.util.LinkedList;
import java.util.Random;

class State extends GlobalSimulation{
	
	public int numberInQueue1 = 0;
	public int numberInQueue2 = 0;
	public int accumulated = 0;
	public int noMeasurements = 0;
	public int arrived = 0;

	public double arrivalTimeQ1 = 0.1;

	public double accumulatedTimeInSystem = 0;
	public double numberOfTimes = 0;

	LinkedList<Double> times = new LinkedList<Double>();

	Random slump = new Random();
	
	public void treatEvent(Event x) {
		switch (x.eventType) {
			case ARRIVALQ1:
				arrivalQ1();
				break;
			case READYQ1:
				readyQ1();
				break;
			case READYQ2:
				readyQ2();
				break;
			case MEASURE:
				measure();
				break;
		}
	}
	
	private void arrivalQ1() {
		arrived++;
		numberInQueue1++;
		if (numberInQueue1 == 1) {
			insertEvent(READYQ1, expDist(1));
		}
		insertEvent(ARRIVALQ1, expDist(arrivalTimeQ1));
		// insert current-time into FIFO
		times.add(time);
	}

	private void readyQ1() {
		numberInQueue1--;
		if (numberInQueue1 > 0) {
			insertEvent(READYQ1, expDist(1));
		}
		numberInQueue2++;
		if (numberInQueue2 == 1) {
			insertEvent(READYQ2, expDist(1));
		}
	}

	private void readyQ2() {
		numberInQueue2--;
		if (numberInQueue2 > 0) {
			insertEvent(READYQ2, expDist(1));
		}
		// POLL from fifo for first time
		accumulatedTimeInSystem += (time - times.poll());
		numberOfTimes++;
	}
	
	private void measure(){
		accumulated += numberInQueue2 + numberInQueue1;
		noMeasurements++;
		// measure again in exp(5) seconds
		insertEvent(MEASURE, expDist(5));
	}

	private double expDist(double mean) {
		double y = - Math.log(1 - slump.nextDouble()) * mean;
		return time + y;
	}
 }
