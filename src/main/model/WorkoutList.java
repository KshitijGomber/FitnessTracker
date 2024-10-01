package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages a collection of Workout objects.
 * This class acts as a container for all logged workouts in the fitness application,
 * supporting operations such as adding new workouts, removing workouts, and retrieving the list of all workouts.
 */
public class WorkoutList {
    private List<Workout> workouts;

    // EFFECTS: Constructs an empty WorkoutList.
    public WorkoutList() {
        this.workouts = new ArrayList<>();
    }

    // EFFECTS: Returns a JSONArray where each element is a JSONObject representing a Workout in the list.
    public JSONArray toJson() {
        JSONArray jsonArray = new JSONArray();
        for (Workout workout : workouts) {
            jsonArray.put(workout.toJson());
        }
        return jsonArray;
    }

    // REQUIRES: jsonArray to be a non-null JSONArray where each element is a JSONObject that represents a Workout.
    // MODIFIES: this (the workouts list in the current WorkoutList object)
    // EFFECTS: Clears the current workouts list and populates it with Workout
    // objects created from the JSONObjects in the given JSONArray.
    public void loadFromJson(JSONArray jsonArray) {
        workouts.clear(); // Clear the current list to avoid duplicating workouts
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Workout workout = new Workout(jsonObject);
            workouts.add(workout);
        }
    }

    // REQUIRES: workout to be a non-null Workout object.
    // MODIFIES: this
    // EFFECTS: Adds the given workout to the list of workouts.
    public void addWorkout(Workout workout) {
        workouts.add(workout);
        EventLog.getInstance().logEvent(new Event("Workout added."));
    }

    // REQUIRES: workout to be a non-null Workout object that is already in the list.
    // MODIFIES: this
    // EFFECTS: Removes the given workout from the list of workouts, if it exists.
    public boolean removeWorkout(Workout workout) {
        return workouts.remove(workout);
    }

    // EFFECTS: Returns a new list containing all the workouts in this list.
    public List<Workout> getWorkouts() {
        return new ArrayList<>(workouts);
    }
}
