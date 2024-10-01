package model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Manages a collection of Exercise objects.
 * This class serves as a repository for all exercises available in the fitness application,
 * allowing for the addition of new exercises and retrieval of the existing ones.
 * It ensures that each exercise has a unique name within the database.
 */
public class ExerciseDatabase {
    private List<Exercise> exercises;

    // EFFECTS: Constructs an empty ExerciseDatabase.
    public ExerciseDatabase() {
        this.exercises = new ArrayList<>();
    }

    // EFFECTS: Returns a JSONArray where each element is a JSONObject representation of an Exercise in the database.
    public JSONArray toJson() {
        JSONArray jsonArray = new JSONArray();
        for (Exercise exercise : exercises) {
            jsonArray.put(exercise.toJson());
        }
        return jsonArray;
    }

    // REQUIRES: jsonArray to be a non-null JSONArray where each element is a JSONObject that represents an Exercise.
    // MODIFIES: this (the exercises list in the current ExerciseDatabase object)
    // EFFECTS: Clears the current exercise list and populates it with Exercise objects
    // created from the JSONObjects in the given JSONArray.
    public void loadFromJson(JSONArray jsonArray) {
        exercises.clear(); // Clear the current list to avoid duplicating exercises
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Exercise exercise = new Exercise(jsonObject);
            exercises.add(exercise);
        }
    }

    // REQUIRES: exercise to be a non-null Exercise object.
    // MODIFIES: this
    // EFFECTS: Adds the given exercise to the exercise database. Do not add if exercise already exists.
    public boolean addExercise(Exercise exercise) {
        for (Exercise existingExercise : exercises) {
            if (existingExercise.getName().equalsIgnoreCase(exercise.getName())) {
                return false;
            }
        }
        exercises.add(exercise);
        EventLog.getInstance().logEvent(new Event("Exercise added."));
        return true;
    }

    // EFFECTS: Returns a new list containing all the exercises in the database.
    public List<Exercise> getExercises() {
        return new ArrayList<>(exercises);
    }
}
