package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.*;

//Represents a schedule including events and and their corresponding times
public class Schedule extends Observable implements Writable {

    public String name;
    public ArrayList<String> schedule;
    public ArrayList<Event> events;

    public Schedule(String name) {
        this.name = name;
        schedule = new ArrayList<>();
        events = new ArrayList<>();
    }

    //MODIFIES: this
    //EFFECTS: adds given event at given time to the schedule, and adds given event to events
    public void schedule(Event event) {
        String scheduling = event.getName() + " at " + event.getTime();
        schedule.add(scheduling);
        events.add(event);
    }

    //REQUIRES: string is in the format "HH:MM:SS"
    //EFFECTS: produces true if the time given is in times
    public int isSameTime(String time) {
        for (int i = 0; i < events.size(); i++) {
            Event e = events.get(i);
            if (time.equals(e.getTime())) {
                return i;
            }
        }
        return -1;
    }

    //REQUIRES: schedule is not empty
    //MODIFIES: this
    //EFFECTS: removes ith element from schedule
    public void removeEvent(int i) {
        if (schedule.size() > 0) {
            schedule.remove(i);
            events.remove(i);
        }
    }

    //EFFECTS: returns the ith element in schedule
    public String get(int i) {
        return schedule.get(i);
    }

    //EFFECTS: returns the ith event in schedule
    public String getEvent(int i) {
        return events.get(i).getName();
    }

    //EFFECTS: returns the name of the schedule
    public String getName() {
        return name;
    }

    //EFFECTS: returns the the size of schedule
    public int length() {
        return schedule.size();
    }

    //EFFECTS: returns the list of events
    public List<Event> getEventList() {
        return Collections.unmodifiableList(events);
    }

    /**
     * Adds an observer to the alarm
     * @param o the observer to be added
     */
    public void addScheduleObserver(Observer o) {
        this.addObserver(o);
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("events", eventsToJson());
        return json;
    }

    // EFFECTS: returns lists in this schedule as a JSON array
    private JSONArray eventsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Event e : events) {
            jsonArray.put(e.toJson());
        }
        return jsonArray;
    }
}