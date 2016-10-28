import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class that models the event list of an intersection
 *
 * @authors Ernst Roos & Pepijn Wissing
 */
public class EventList {

    /**
     * List containing all events currently in the event list
     *
     */
    private ArrayList<Event> list = new ArrayList();

    /**
     * Default constructor
     *
     */
    EventList() {

    }

    /**
     * Constructor for EventList that creates all events specified in the
     * arrival list as well as other relevant events.
     *
     * @param terminationTime The time at which the simulation is set to be
     * terminated.
     * @param arrivalList An ArrayList containing an array for each arrival
     * event. Each array must have the form [time from to].
     */
    EventList(double terminationTime, ArrayList<double[]> arrivalList) {
        for (int i = 0; i < arrivalList.size(); i++) {
            int[] lane = {(int) arrivalList.get(i)[1], (int) arrivalList.get(i)[2]};
            this.insert(new Event(1, arrivalList.get(i)[0], lane));
        }

        initializeOtherEvents(terminationTime);
    }

    /**
     * Constructor for EventList that generates all arrival events and other
     * relevant events based on the specified arrival rates.
     *
     * @param termintationTime The time at which the simulation is set to be
     * terminated.
     * @param arrivalRates A two-dimensional array that stores the arrival rate
     * for each lane.
     */
    EventList(double terminationTime, double[][] arrivalRates) {

        //Loop over all lanes
        for (int i = 0; i < arrivalRates.length; i++) {
            for (int j = 0; j < arrivalRates[i].length; j++) {
                int[] tmpArray = {i, j};

                //If the arrival rate is strictly positive
                if (arrivalRates[i][j] > 0) {
                    double time = 0;

                    //Generate events until the termination time
                    while (time < terminationTime) {
                        //Increase time by an exponentially distribued random variable
                        double expDistributedTime = Math.log(1 - Math.random()) / (-arrivalRates[i][j] / 60);
                        //Round and add to current time
                        time = time + Math.round(expDistributedTime * 10) / 10.0;
                        this.insert(new Event(1, time, tmpArray));
                    }
                }
            }
        }

        initializeOtherEvents(terminationTime);
    }

    /**
     * Creates non arrival events at the construction of the arrival list.
     *
     * @param terminationTime The time at which the simulation is to be
     * terminated.
     */
    void initializeOtherEvents(double terminationTime) {
        //Insert first end of color phase after first arrival
        this.insert(new Event(3, list.get(0).getTime()));

        //Insert termination event
        this.insert(new Event(4, terminationTime));
    }

    /**
     * Acquires the first event from the list and removes it
     *
     * @return The first event from the list
     */
    Event getNextEvent() {
        return list.remove(0);
    }

    /**
     * Schedules an arrival or departure event with the specified
     * characteristics
     *
     * @param type The TYPE of event.
     * @param time The time at which the event takes place.
     * @param lane The lane in which the event occurs.
     */
    void schedule(int type, double time, int[] lane) {
        if (type < 1 || type > 2) {
            throw new IllegalArgumentException("This method can only be used to schedule arrival and departure events");
        }

        this.insert(new Event(type, time, lane));
    }

    /**
     * Schedules an end of color phase event with the specified characteristics
     *
     * @param type The TYPE of event.
     * @param time The time at which the event takes place.
     */
    void schedule(int type, double time) {
        if (type != 3) {
            throw new IllegalArgumentException("This method can only be used to schedule an End of Color Phase event");
        }

        this.insert(new Event(type, time));
    }

    /**
     * Inserts an event into the list according to the ordering specified in
     * Event
     *
     * @param e The event to be inserted.
     */
    void insert(Event e) {
        int i = 0;
        while (i < list.size() && e.getTime() >= list.get(i).getTime()) {
            i++;
        }
        list.add(i, e);
    }

    /**
     * Method to check whether the list is empty
     *
     * @return True if the list is empty, false if it is not.
     */
    boolean isEmpty() {
        return list.isEmpty();
    }

    /**
     * Method to empty all contents of the list
     *
     */
    void empty() {
        list.removeAll(list);
    }

    /**
     * Method that deletes all events of the specified type.
     *
     * @param type The type event of which all instances must be deleted from
     * the list.
     */
    void deleteAllOfType(int type) {
        for (Event e : list) {
            if (e.getType() == type) {
                list.remove(e);
            }
        }
    }

    /**
     * Method that deletes all events of the specified type.
     *
     * @param type The type event of which all instances must be deleted from
     * the list. Only accepts 1 or 2.
     */
    void deleteAllOfTypeLane(int type, int[] lane) {
        if (type == 1 || type == 2) {
            for (Event e : list) {
                if (e.getType() == type && e.getLane()[0] == lane[0] && e.getLane()[1] == lane[1]) {
                    list.remove(e);
                }
            }
        } else {
            throw new IllegalArgumentException("This type of event cannot be deleted by this method.");

        }
    }

    /**
     * Accessor method for list
     *
     * @return The list of events.
     */
    public ArrayList<Event> getList() {
        return list;
    }

    /**
     * Method that prints the specified number of events
     * @param numberOfEvents Amount of events that will be printed
     */
    public void printNextEvents(int numberOfEvents) {
        for (int i = 0; i < numberOfEvents; i++) {
            System.out.println(list.get(i));
        }
    }    
    
    /**
     * Method that returns a string representation of the object.
     *
     * @return String representation of the object.
     */
    @Override
    public String toString() {
        String toReturn = "";
        for (int i = 0; i < list.size(); i++) {
            toReturn = toReturn + list.get(i).toString() + "\n";
        }

        return toReturn;
    }
}

/**
 * Class that models an event at an intersection
 *
 * @author Ernst Roos & Pepijn Wissing
 */
class Event {

    /**
     * Describes the TYPE of event: <br>
     * 1 - Arrival of a car <br>
     * 2 - Departure of a car <br>
     * 3 - End of a color phase <br>
     * 4 - Termination of the run
     */
    final int TYPE;

    /**
     * Time the event is scheduled for
     *
     */
    private double time;

    /**
     * Lane in which the event takes place
     *
     */
    private int[] lane;

    /**
     * Constructor for events for which a lane is irrelevant
     *
     * @param type The type of event you want to create.
     * @param time The time at which the event occurs.
     */
    Event(int type, double time) {
        if (type == 1 || type == 2) {
            throw new IllegalArgumentException("An arrival or departure event must have a specified lane.");
        }
        this.TYPE = type;
        this.time = time;
    }

    /**
     * Constructor for events for which a lane is relevant
     *
     * @param type The type of event you want to create.
     * @param time The time at which the event occurs.
     * @param lane The lane in which the event occurs.
     */
    Event(int type, double time, int[] lane) {
        this.TYPE = type;
        this.time = time;
        this.lane = lane;
    }

    /**
     * Method that assesses if this event should precede the event passes as
     * argument
     *
     * @param e The event with which this instance is compared.
     * @return True if this instance precedes e, false otherwise.
     */
    public boolean precedes(Event e) {
        //An event precedes another if it happens earlier
        if (this.time < e.getTime()) {
            return true;
        } //If both events occur at the same time, the events are processed by TYPE
        else if (this.time == e.getTime()) {
            return this.TYPE < e.getType();
        } else {
            return false;
        }
    }

    /**
     * Accessor for TYPE
     *
     * @return The type of this instance.
     */
    public int getType() {
        return TYPE;
    }

    /**
     * Accessor for time
     *
     * @return The at which this instance occurs.
     */
    public double getTime() {
        return time;
    }

    /**
     * Accessor for lane
     *
     * @return The lane in which this instance occurs.
     */
    public int[] getLane() {
        return lane;
    }

    /**
     * Method that returns a String representation of the object.
     *
     * @return A string representation of the object.
     */
    @Override
    public String toString() {
        String toReturn = "";
        switch (this.TYPE) {
            case 1:
                toReturn = "Arrival \t" + time + "\t" + "[" + lane[0] + ", " + lane[1] + "]";
                break;
            case 2:
                toReturn = "Departure \t" + time + "\t" + "[" + lane[0] + ", " + lane[1] + "]";
                break;
            case 3:
                toReturn = "End of color phase \t" + time + "\t" + "[" + lane[0] + ", " + lane[1] + "]";
                break;
            case 4:
                toReturn = "Termination \t" + time + "\t" + "[" + lane[0] + ", " + lane[1] + "]";
                break;
        }
        return toReturn;
    }


}
