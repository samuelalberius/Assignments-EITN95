import java.util.*;
import java.io.*;

// This class defines a simple queuing system with one server. It inherits Proc so that we can use time and the
// signal names without dot notation
class Manager extends Proc{
	Random rand = new Random();
	ArrayList<Student> students;
	double velocity;

	public Manager(ArrayList<Student> students, double velocity) {
		this.students = students;
		this.velocity = velocity;
	}



	public void TreatSignal(Signal x){
		switch (x.signalType){


			//move students, check which ones have same x,y
			case MOVE:{
				//move students
				for (Student s : students) {
					if (!s.inMeeting) {
						s.step();
					}
				}

				//check for meetings
				handleMeetings(students);
				//make students move again after 1/velocity seconds.
				if (velocity > 0) {
					SignalList.SendSignal(MOVE, this, time + 1/velocity);
				} else {
					//random velocity
					SignalList.SendSignal(MOVE,this,time + 1 / (double) uniformDist(1, 7));
				}
			
			} break;
		}
	}

	private void handleMeetings(ArrayList<Student> students) {
		for (int i = 0; i < students.size(); i++) {
			for (int j = 0; j < students.size(); j++) {
				if (i != j) {
					Student s1 = students.get(i);
					Student s2 = students.get(j);
					if (sameCoordinate(s1,s2) & !inMeeting(s1,s2)) {
						s1.inMeeting = true;
						s1.inMeetingWith = s2.id;
						s2.inMeeting = true;
						s2.inMeetingWith = s1.id;
						SignalList.SendSignal(ENDMEETING, s1, time + tt);
						SignalList.SendSignal(ENDMEETING, s2, time + tt);
					}
				}
			}
		}
	}

	private boolean inMeeting(Student s1, Student s2) {
		return s1.inMeeting || s2.inMeeting;
	}



	private boolean sameCoordinate(Student s1, Student s2) {
		return (s1.coord.x == s2.coord.x) & (s1.coord.y == s2.coord.y);
	}

	public boolean everyOneTalkedToEveryone() {
		int talkedToEveryone = 0;
		for (Student s: students) {
			if (s.talkedToEveryone()) {
				talkedToEveryone += 1;
			}
		}
		return talkedToEveryone == numberOfStudents;
	}

	private int uniformDist(int low, int high) {
        return rand.nextInt(high-low+1)+low;
    }

}