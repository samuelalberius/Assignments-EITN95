package A2.task1;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.util.*;

class State extends GlobalSimulation{

	Random slump = new Random();
	File file;
	BufferedWriter bw;

	public int N = 1000;
	public int x = 100;
	public int lambda = 8;
	public int T = 1;
	public int M = 1000;

	public int noCustomers = 0;
	public int accumulated = 0;
	public int noMeasurements = 0;

	public State(int task) {
		switch (task) {
			case 1:
			break;
			case 2:
				x = 10;
				lambda = 80;
			break;
			case 3:
				x = 200;
				lambda = 4;
			break;
			case 4:
				N = 100;
				x = 10;
				lambda = 4;
				T = 4;
				M = 1000;
			break;
			case 5:
				N = 100;
				x = 10;
				lambda = 4;
				T = 1;
				M = 4000;
			break;
			case 6:
				N = 100;
				x = 10;
				lambda = 4;
				T = 4;
				M = 4000;
			break;
			default:
			break;
		}

		System.out.println("\nStarting simulation for task: " + task);
		System.out.println("Number of servers = " + N);
		System.out.println("Serving time = " + x);
		System.out.println("Mean arrival time = " + lambda);
		System.out.println("Time between measurements = " + T);
		System.out.println("Number of measurements = " + M + "\n");

		try {
			file = new File("measurements.txt");
			file.createNewFile();
            bw = new BufferedWriter(new FileWriter(file.getAbsoluteFile()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
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
	
	private void arrival() {
		if (noCustomers < N) {
			noCustomers++;
			insertEvent(READY, constDist(x));
		}
		insertEvent(ARRIVAL, expDist(lambda));
	}
	
	private void ready() {
		noCustomers--;
	}
	
	private void measure()	{
		accumulated += noCustomers;
		noMeasurements++;
		try {
			bw.write(noMeasurements + ", " + noCustomers + "\n");
		} catch (IOException e) {
			System.out.println("Error when writing to file");
			e.printStackTrace();
		}
		insertEvent(MEASURE, constDist(T));
	}

	private double expDist(double mean) {
		double y = - Math.log(1 - slump.nextDouble()) / mean;
		return time + y;
	}

	private double constDist(double mean) {
		return time + mean;
	}

	public void closeFile() {
		try {
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}