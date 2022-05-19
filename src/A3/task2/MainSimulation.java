import java.util.*;
import java.io.*;

//Denna klass �rver Global s� att man kan anv�nda time och signalnamnen utan punktnotation
//It inherits Proc so that we can use time and the signal names without dot notation


public class MainSimulation extends Global{

    public static void main(String[] args) throws IOException {

		//Takes a long time to run for large value of NRUNS,
		//since the simulation is run for each velocity, total 3*NRUNS runs.
		int NRUNS = 1000;
		

		//get random coordinates for students.
		int[][] coords = getNRandomCoordinates(numberOfStudents);
		//2 m/s, 4 m/s, U(1,7) m/s.
		double[] velocities = {2,4,-1};
		//run once for each velocity
		for (int i = 0; i < 3; i++) {
			double velocity = velocities[i];
			//use same students to run the simulation NRUNS times.
			ArrayList<Double> completionTimes = new ArrayList<Double>();
			SimpleFileWriter filew = new SimpleFileWriter("xvalues"+i+".m", false);
			SimpleFileWriter filew2 = new SimpleFileWriter("yvalues"+i+".m", false);
			HashMap<Double,Integer> frequencyMap = new HashMap<Double,Integer>();

			for (int j = 0; j < NRUNS; j++) {
				ArrayList<Student> students = generateStudents(coords);
				runSimulation(students,velocity);
				completionTimes.add(time);
				//System.out.println(time);
				updateFrequencyMap(students,frequencyMap);
			}
			for (Double k : frequencyMap.keySet()) {
				//System.out.println(k + " = " + frequencyMap.get(k));
				filew.println(String.valueOf(k));
				filew2.println(String.valueOf(frequencyMap.get(k)));
			}
			filew.close();
			filew2.close();
			double meanTime = mean(completionTimes);
			double stdTime = std(completionTimes,meanTime);
			double[] cIntervalTime = { meanTime - 1.96 * stdTime/Math.sqrt(NRUNS), meanTime + 1.96 * stdTime/Math.sqrt(NRUNS) };
			System.out.println(cIntervalTime[0]/60 + " - " + cIntervalTime[1]/60);
			System.out.println(meanTime/60);

	}
    }






	private static void updateFrequencyMap(ArrayList<Student> students, HashMap<Double, Integer> frequencyMap) {
		for (Student s : students) {
			double[] meetingTimes = s.meetingTimes;
			for (int i = 0; i < numberOfStudents; i++) {
				if (i != s.id) {
					if (frequencyMap.containsKey(meetingTimes[i])) {
						frequencyMap.put(meetingTimes[i],frequencyMap.get(meetingTimes[i])+1);
					} else {
						frequencyMap.put(meetingTimes[i],1);
					}
				}
			}
		}
	}

	private static ArrayList<Student> runSimulation(ArrayList<Student> students,double velocity) {
		time = 0;

    	Signal actSignal;
    	new SignalList();

		
    	Manager m = new Manager(students,velocity);
 
		SignalList.SendSignal(MOVE,m,time);

    	while (!m.everyOneTalkedToEveryone()){
    		actSignal = SignalList.FetchSignal();
    		time = actSignal.arrivalTime;
    		actSignal.destination.TreatSignal(actSignal);
    	}
		
		return students;
	} 

	private static ArrayList<Student> generateStudents(int[][] coords) {
		ArrayList<Student> students = new ArrayList<Student>();
		for (int i = 0; i < numberOfStudents; i++) {
			students.add(new Student(coords[i][0],coords[i][1],i));
		}
		return students;
	}


	private static int[][] getNRandomCoordinates(int N) {
		Random rand = new Random();
		int[][] randomCoordinates = new int[N][2];
		for (int i = 0; i < N; i++) {
			randomCoordinates[i][0] = rand.nextInt(WIDTH); 
			randomCoordinates[i][1] = rand.nextInt(HEIGHT); 
		}
		return randomCoordinates;
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
		return Math.sqrt(SSE / list.size());
	}

	
}