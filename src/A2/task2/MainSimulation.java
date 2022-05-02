import java.util.*;
import java.io.*;

public class MainSimulation extends GlobalSimulation {

	public static void main(String[] args) throws IOException {

		int NRUNS = 1000;
		ArrayList<Double> times = new ArrayList<Double>();

		for (int i = 0; i < NRUNS; i++) {
			time = 0; // set time = 0 start of simulation.
			Event actEvent;
			State actState = new State(); // The state that shoud be used
			// Some events must be put in the event list at the beginning
			insertEvent(ARRIVAL, 0);
			insertEvent(MEASURE, 5);

			// work while there are still people in queue or shift has ended
			while (time < workTime || actState.numberInQueue > 0) {
				actEvent = eventList.fetchEvent();
				time = actEvent.eventTime;
				actState.treatEvent(actEvent);
			}
			times.add(time);
		}
		// Simulation has been run NRUNS times.

		double totalTime = 0;
		for (Double x : times) {
			totalTime += x;
		}
		double meanTime = totalTime / NRUNS;

		double SSE = 0;
		for (Double x : times) {
			SSE += Math.pow((x - meanTime), 2);
		}
		double std = Math.sqrt(SSE / NRUNS);
		// System.out.println(SSE);
		double[] cInterval = { meanTime - 1.96 * std, meanTime + 1.96 * std };

		int meanMinutes = (int) meanTime % 60;
		int meanHours = (int) meanTime / 60;
		System.out.println("Mean time work finished: " + (9 + meanHours) + ":" + meanMinutes);
		System.out.println(String.format("Confidence interval: %d : %d - %d : %d", 9 + (int) cInterval[0] / 60,
				(int) cInterval[0] % 60, 9 + (int) cInterval[1] / 60, (int) cInterval[1] % 60));

		// times.forEach( (n) -> { if (n < 481) {
		// 	System.out.println(n); }} );


	}
}