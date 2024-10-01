package persistence;

import model.ExerciseDatabase;
import model.WorkoutList;
import model.Goals;
import model.WeightTracker;
import org.json.JSONObject;
import java.io.*;


/**
 * Facilitates the saving of the fitness application state to a JSON-formatted file.
 * /
/**
 * This class is adapted from the JsonWriter class of JsonSerializationDemo.
 */
// Represents a writer that writes JSON representation of workroom to file
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;


    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }


    // MODIFIES: this, file at destination
    // EFFECTS: writes JSON representation of ExerciseDatabase, WorkoutList, Goals, and WeightTracker to file
    public void write(ExerciseDatabase exerciseDatabase,
                      WorkoutList workoutList, Goals goals, WeightTracker weightTracker) throws FileNotFoundException {
        open();
        JSONObject json = new JSONObject();
        json.put("exerciseDatabase", exerciseDatabase.toJson());
        json.put("workoutList", workoutList.toJson());
        json.put("goals", goals.toJson());
        json.put("weightTracker", weightTracker.toJson());
        saveToFile(json.toString(TAB));
        close();
    }

    // MODIFIES: this, file at destination
    // EFFECTS: writes given string to file
    private void saveToFile(String json) {
        writer.print(json);
    }

    // MODIFIES: this
    // EFFECTS: closes the writer
    public void close() {
        writer.close();
    }
}
