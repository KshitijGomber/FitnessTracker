package model;

import org.json.JSONObject;

/**
 * Represents a single workout session, including the type of exercise, duration, and intensity.
 * A Workout object encapsulates all relevant details of a workout, making it possible
 * to log diverse types of workouts with varying durations and intensities.
 */
public class Workout {
    private String exerciseType;
    private int duration;
    private String intensity;

    // REQUIRES: exerciseType to be a non-null string, duration to be a positive integer,
    //              and intensity to be one of "Low", "Medium", or "High".
    // MODIFIES: this
    // EFFECTS: Constructs a Workout object with the given exercise type, duration, and intensity.
    public Workout(String exerciseType, int duration, String intensity) {
        this.exerciseType = exerciseType;
        this.duration = duration;
        this.intensity = intensity;
    }


    //REQUIRES: jsonObject to contain a non-null string for exerciseType, a positive integer for duration,
    // and a string that is one of "Low", "Medium", or "High" for intensity.
    //MODIFIES: this (the current Workout object)
    //EFFECTS: Constructs a Workout object by extracting and setting the exercise
    // type, duration, and intensity from the given JSONObject.
    public Workout(JSONObject jsonObject) {
        this.exerciseType = jsonObject.getString("exerciseType");
        this.duration = jsonObject.getInt("duration");
        this.intensity = jsonObject.getString("intensity");
    }

    // EFFECTS: Returns a JSONObject that represents the current state of the Workout
    // object, including its exercise type, duration, and intensity.
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("exerciseType", exerciseType);
        json.put("duration", duration);
        json.put("intensity", intensity);
        return json;
    }

    // EFFECTS: Returns the exercise type of this workout.
    public String getExerciseType() {
        return exerciseType;
    }

    // REQUIRES: exerciseType to be a non-null string.
    // MODIFIES: this
    // EFFECTS: Sets the exercise type of this workout to the given exerciseType.
    public void setExerciseType(String exerciseType) {
        this.exerciseType = exerciseType;
    }

    // EFFECTS: Returns the duration of this workout in minutes.
    public int getDuration() {
        return duration;
    }

    // REQUIRES: duration to be a positive integer.
    // MODIFIES: this
    // EFFECTS: Sets the duration of this workout to the given duration.
    public void setDuration(int duration) {
        this.duration = duration;
    }

    // EFFECTS: Returns the intensity level of this workout.
    public String getIntensity() {
        return intensity;
    }

    // REQUIRES: intensity to be one of "Low", "Medium", or "High".
    // MODIFIES: this
    // EFFECTS: Sets the intensity level of this workout to the given intensity.
    public void setIntensity(String intensity) {
        this.intensity = intensity;
    }

    // EFFECTS: Returns a string representation of the Workout object.
    @Override
    public String toString() {
        return "Exercise Type: " + exerciseType + ", Duration: " + duration + " minutes, Intensity: " + intensity;
    }
}
