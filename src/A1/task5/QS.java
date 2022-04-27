package A1.task5;

import java.util.*;

// This class defines a simple queuing system with one server. It inherits Proc so that we can use time and the
// signal names without dot notation
class QS extends Proc{
	
	public int numberInQueue = 0;
	public int accumulated = 0;
	public int noMeasurements = 0;

	Random slump = new Random();
	public Proc sendTo;

	public void TreatSignal(Signal x) {
		switch (x.signalType){
			case ARRIVAL: {
				numberInQueue++;
				if (numberInQueue == 1) {
					SignalList.SendSignal(READY, this, expDist(1 / 0.5));
				}
			} break;
			case READY: {
				numberInQueue--;
				if (sendTo != null) {
					SignalList.SendSignal(ARRIVAL, sendTo, time);
				}
				if (numberInQueue > 0) {
					SignalList.SendSignal(READY, this, expDist(1 / 0.5));
				}
			} break;
			case MEASURE: {
				noMeasurements++;
				accumulated += numberInQueue;
				SignalList.SendSignal(MEASURE, this, time + slump.nextDouble() * 5);
			}
		}
	}

	private double expDist(double lambda) {
		return time + Math.log(1 - slump.nextDouble()) / (-lambda);
	  }
}