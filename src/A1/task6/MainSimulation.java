package A1.task6;

import java.util.*;
import java.io.*;

public class MainSimulation {
 
    public static void main(String[] args) throws IOException {
		
		int nbrComponents = 5;
        Random slump = new Random();
		int runs = 1000;
		int count = 0;
		double DONE = 10.0;
		ArrayList<Double> times = new ArrayList<Double>();
		double totalTime = 0;
		boolean finished = false;
		
        // The main simulation loop
    	while (count < runs){
			//add components for this run
			ArrayList<Double> components = new ArrayList<Double>(nbrComponents);
			for (int i = 0; i < nbrComponents; i++) {
				components.add(1 + 4 * slump.nextDouble());
			}
			System.out.println("Components this run: " + components);

			//loop until all components are DONE
			finished = false;
			while (!finished) {
				double smallestTime = Collections.min(components);
				int smallestIndex = components.indexOf(smallestTime);

				if (smallestIndex == 0) {
					components.set(0,DONE);
					components.set(1,DONE);
					components.set(4,DONE);
				} else if (smallestIndex == 2) {
					components.set(2,DONE);
					components.set(3,DONE);
				} else {
					components.set(smallestIndex,DONE);
				}

				boolean allFinished = new HashSet<>(components).size() <= 1;
				if (allFinished) {
					finished = true;
					times.add(smallestTime);
					totalTime += smallestTime;
					System.out.println("Time this run: " + smallestTime);
				}
			}	
			count++;
    	}
    	
    	// Printing the result of the simulation, in this case a mean value
    	System.out.println("mean time: " + totalTime/count);
	
    }
}