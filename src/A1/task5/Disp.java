package A1.task5;

import java.util.Random;

public class Disp extends Proc {

    int accumulated = 0;
    int nonEmptyAccumulated = 0;
    int noMeasure = 0;
    int index = 0;

    // WHAT METHOD CHOOSES QS FOR INCOMMING CUSTOMER
    int method = 2;

    Random slump = new Random();
    QS[] list;

    public Disp() {
        list = new QS[5];
        for (int i = 0; i < 5; i++) {
            list[i] = new QS();
        }
    }

    @Override
    public void TreatSignal(Signal x) {
        switch(x.signalType) {
            case ARRIVAL: {
                switch(method) {
                    case 1: {
                        QS q = randomQS();
                        SignalList.SendSignal(ARRIVAL, q, time);
                    } break;
                    case 2: {
                        QS q = roundRobin();
                        SignalList.SendSignal(ARRIVAL, q, time);
                    } break;
                    case 3: {
                        QS q = leastNumber();
                        SignalList.SendSignal(ARRIVAL, q, time);
                    } break;
                }
            } break;
            case MEASURE: {
                noMeasure++;
                accumulated += totalNumberInQueue();
                SignalList.SendSignal(MEASURE, this, time + slump.nextDouble() * 100);
            }
        }
    }

    private QS randomQS() {
        return list[Math.abs(slump.nextInt()) % 5];
    }

    private QS roundRobin() {
        return list[index++ % 5];
    }

    private QS leastNumber() {
        QS ret = list[0];
        for (QS q : list) {
            if (q.numberInQueue < ret.numberInQueue) ret = q;
        }
        return ret;
    }

    private int totalNumberInQueue() {
        int ret = 0;
        for (QS q : list) {
            ret += q.numberInQueue;
        }
        return ret;
    }
}
