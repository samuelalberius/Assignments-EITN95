package A1.task4;

import java.util.*;

// This class defines a simple queuing system with one server. It inherits Proc so that we can use time and the
// signal names without dot notation
class QS extends Proc{
	public Queue<Double> regs = new LinkedList<Double>();
	public Queue<Double> prios = new LinkedList<Double>();
	public Proc sendTo;
	public double prio, lambda;
	public double accReg = 0;
	public double accPrio = 0;
	public int noReg = 0;
	public int noPrio = 0;
	public double maxTime = 0;
	public int noReady = 0;
	double timeInQueue;
	Random slump = new Random();

	public int noInQ() {
		return prios.size() + regs.size();
	}
	public void TreatSignal(Signal x){
		switch (x.signalType){

			case ARRIVAL: {
				if (slump.nextDouble() < prio) {
					prios.add(time);
				} else {
					regs.add(time);
				}
				if (noInQ() == 1){
					SignalList.SendSignal(READY, this, time + (2.0 / lambda) * slump.nextDouble());
				}
			} break;

			case READY: {
				noReady++;
				if (prios.size() > 0) {
					timeInQueue = time - prios.remove();
					accPrio += timeInQueue;
					noPrio++;
				} else {
					timeInQueue = time - regs.remove();
					accReg += timeInQueue;
					noReg++;
				}
				if (timeInQueue > maxTime) maxTime = timeInQueue;
				if (sendTo != null){
					SignalList.SendSignal(ARRIVAL, sendTo, time);
				}
				if (noInQ() > 0){
					SignalList.SendSignal(READY, this, time + (2.0 / lambda) * slump.nextDouble());
				}
			} break;
		}
	}
}