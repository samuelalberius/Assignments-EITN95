package A1.task4;

import java.io.*;

//Denna klass �rver Global s� att man kan anv�nda time och signalnamnen utan punktnotation
//It inherits Proc so that we can use time and the signal names without dot notation


public class MainSimulation extends Global{

    public static void main(String[] args) throws IOException {

    	// Signallistan startas och actSignal deklareras. actSignal �r den senast utplockade signalen i huvudloopen nedan.
    	// The signal list is started and actSignal is declaree. actSignal is the latest signal that has been fetched from the 
    	// signal list in the main loop below.

    	Signal actSignal;
    	new SignalList();

    	//H�r nedan skapas de processinstanser som beh�vs och parametrar i dem ges v�rden.
    	// Here process instances are created (two queues and one generator) and their parameters are given values. 

    	QS Q1 = new QS();
    	Q1.sendTo = null;
		Q1.prio = 0.5;
		Q1.lambda = 1.0 / 240.0;

    	Gen Generator = new Gen();
    	Generator.lambda = 1.0 / 300.0; //Generator ska generera nio kunder per sekund  //Generator shall generate 9 customers per second
    	Generator.sendTo = Q1; //De genererade kunderna ska skickas till k�systemet QS  // The generated customers shall be sent to Q1

    	//H�r nedan skickas de f�rsta signalerna f�r att simuleringen ska komma ig�ng.
    	//To start the simulation the first signals are put in the signal list

    	SignalList.SendSignal(READY, Generator, time);
    	//SignalList.SendSignal(MEASURE, Q1, time);


    	// Detta �r simuleringsloopen:
    	// This is the main loop

    	while (Q1.noReady < 1000) {
    		actSignal = SignalList.FetchSignal();
    		time = actSignal.arrivalTime;
    		actSignal.destination.TreatSignal(actSignal);
    	}

    	//Slutligen skrivs resultatet av simuleringen ut nedan:
    	//Finally the result of the simulation is printed below:

		System.out.println("chance of prio: " + Q1.prio);
		System.out.println("number of regs: " + Q1.noReg);
		System.out.println("number of prio: " + Q1.noPrio);
		System.out.println("mean q-time for reg: " + 1.0 * Q1.accReg / Q1.noReg / 60.0 + " mins");
		System.out.println("mean q-time for prio: " + 1.0 * Q1.accPrio / Q1.noPrio / 60.0 + " mins");
		System.out.println("max q-time: " + 1.0 * Q1.maxTime / 60.0  + " mins");
    }
}