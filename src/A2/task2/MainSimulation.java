import java.util.*;
import java.io.*;

public class MainSimulation extends GlobalSimulation {

	public static void main(String[] args) throws IOException {

		int NRUNS = 1000;
		ArrayList<Double> times = new ArrayList<Double>();
		ArrayList<Double> aveServiceTimes = new ArrayList<Double>();

		for (int i = 0; i < NRUNS; i++) {
			time = 0; // set time = 0 start of simulation.
			Event actEvent;
			State actState = new State(); // The state that shoud be used
			// Some events must be put in the event list at the beginning
			insertEvent(ARRIVAL, 0);
			insertEvent(MEASURE, 1);

			// work while there are either still people in queue or shift has ended
			while (time < workTime || actState.numberInQueue > 0) {
				actEvent = eventList.fetchEvent();
				time = actEvent.eventTime;
				actState.treatEvent(actEvent);
			}
			// add time at end of simulation
			times.add(time);
			aveServiceTimes.add(mean(actState.timesInSystem));
		}
		// Simulation has been run NRUNS times.

		// confidence interval for average time it takes to fill out prescription
		double meanServiceTime = mean(aveServiceTimes);
		double stdServiceTime = std(aveServiceTimes, meanServiceTime);
		double[] cIntervalServiceTime = { meanServiceTime - 1.96 * stdServiceTime/Math.sqrt(NRUNS),
				meanServiceTime + 1.96 * stdServiceTime/Math.sqrt(NRUNS) };

		System.out.println("Confidence interval (minutes): " + cIntervalServiceTime[0] + " - " + cIntervalServiceTime[1]);
		System.out.println("Mean prescription filling time : " + meanServiceTime);

		// confidence interval for total opening hours
		double meanTime = mean(times);
		double stdTime = std(times, meanTime);
		double[] cIntervalTime = { meanTime - 1.96 * stdTime/Math.sqrt(NRUNS), meanTime + 1.96 * stdTime/Math.sqrt(NRUNS) };

		int meanMinutes = (int) meanTime % 60;
		int meanHours = (int) meanTime / 60;
		System.out.println("Mean time work finished: " + (9 + meanHours) + ":" + meanMinutes);
		System.out.println(String.format("Confidence interval: %d : %d - %d : %d", 9 + (int) cIntervalTime[0] / 60,
				(int) cIntervalTime[0] % 60, 9 + (int) cIntervalTime[1] / 60, (int) cIntervalTime[1] % 60));

	}

	private static double mean(List<Double> list) {
		double totalTime = 0;
		for (Double x : list) {
			totalTime += x;
		}
		return totalTime / list.size();
	}

	private static double std(List<Double> list, double mean) {
		double SSE = 0;
		for (Double x : list) {
			SSE += Math.pow((x - mean), 2);
		}
		return Math.sqrt(SSE / list.size()); // std
	}
}