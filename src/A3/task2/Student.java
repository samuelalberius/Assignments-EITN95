import java.util.ArrayList;
import java.util.Random;

public class Student extends Proc{
    
    //directions = 0,1,2,3,4,5,6,7,8 clockwise
    int id; //0-numberofstudents-1
    int inMeetingWith = -1; //not in meeting = -1;
    private Random rand = new Random();
    public int direction;
    public Coordinate coord;
    private int tilesLeft = uniformDist(1,10);
    public boolean inMeeting = false;
    public double[] meetingTimes = new double[numberOfStudents];


    int[] directions = new int[]{ 0,1,2,3,4,5,6,7 };

    public Student(int x, int y, int id) {
        this.coord = new Coordinate(x,y);
        this.direction = getRandomValidDirection();
        this.id = id;
    }


    @Override
    public void TreatSignal(Signal x) {
        switch (x.signalType){
        case ENDMEETING: {
            meetingTimes[inMeetingWith] += tt;
            inMeeting = false;
            inMeetingWith = -1;
        } break;
    }
}

    public boolean talkedToEveryone() {
        int nbrTalkedTo = 0;
        for (int i = 0; i < numberOfStudents; i++) {
            if ((i != id) & (meetingTimes[i] > 0)) {
                nbrTalkedTo++;
            }
        }
        return nbrTalkedTo == numberOfStudents-1;
    }

    public void step() {
        if(tilesLeft == 0) {
            direction = getRandomValidDifferentDirection(direction);
            tilesLeft = uniformDist(1,10);
        }
        //if about to move out of bounds.
        if(!isInBounds(move(direction))) {
            direction = getRandomValidDirection();
        }
        Coordinate new_coord = move(direction);
        coord.x = new_coord.x;
        coord.y = new_coord.y;
        tilesLeft--;
    }



    private int uniformDist(int low, int high) {
        return rand.nextInt(high-low+1)+low;
    }

    private Coordinate move(int direction) {
        int x = coord.x;
        int y = coord.y;
        switch(direction) {
            //up
            case 0:
                y += 1;
                break;
            //diag up right
            case 1: 
                y += 1;
                x += 1;
                break;
            //right
            case 2:
                x += 1;
                break;
            //diag down right
            case 3:
                y -= 1;
                x += 1;
                break;
            //down
            case 4:
                y -= 1;
                break;
            //diag down left
            case 5:
                y -= 1;
                x -= 1;
                break;
            //left
            case 6:
                x -= 1;
                break;
            //diag up left
            case 7:
                y += 1;
                x += 1;
                break;
        }
        return new Coordinate(x,y);
    }


    private ArrayList<Integer> validDirections() {
        ArrayList<Integer> validDirections = new ArrayList<Integer>();
        for(int d : directions) {
            if (isInBounds(move(d))) {
                validDirections.add(d);
            };
        }
        return validDirections;
    }


    private int getRandomValidDifferentDirection(int d) {
        ArrayList<Integer> validDirections = validDirections();
        ArrayList<Integer> newValidDirections = new ArrayList<Integer>();
        for (int d1 : validDirections) {
            if (d1 != d) {
                newValidDirections.add(d1);
            }
        }
        int new_d = newValidDirections.get(rand.nextInt(newValidDirections.size()));
        return new_d;
    }


    private int getRandomValidDirection() {
        ArrayList<Integer> validDirections = validDirections();
        int d = validDirections.get(rand.nextInt(validDirections.size()));
        return d;
    }

    private boolean isInBounds(Coordinate c) {
        return (c.x < Global.WIDTH & c.x >= 0) & (c.y < Global.HEIGHT & c.y >= 0);
    }






}
