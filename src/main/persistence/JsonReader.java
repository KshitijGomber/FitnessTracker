package persistence;

import model.ExerciseDatabase;
import model.WorkoutList;
import model.Goals;
import model.WeightTracker;
import org.json.JSONObject;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * Handles reading and loading the application state from a JSON-formatted file.
 * /
/**
 * This class is adapted from the JsonReader class of JsonSerializationDemo.
 */

// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private String source;
    // EFFECTS: constructs reader to read from source file

    public JsonReader(String source) {
        this.source = source;
    }


    // MODIFIES: exerciseDatabase, workoutList, goals, weightTracker
    // EFFECTS: reads and parses the JSON data from the source file, and initializes
    // the exerciseDatabase, workoutList, goals, and weightTracker with the parsed data;
    // throws IOException if an error occurs reading data from the file
    public void read(ExerciseDatabase exerciseDatabase, WorkoutList workoutList,
                     Goals goals, WeightTracker weightTracker) throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        exerciseDatabase.loadFromJson(jsonObject.getJSONArray("exerciseDatabase"));
        workoutList.loadFromJson(jsonObject.getJSONArray("workoutList"));
        goals.loadFromJson(jsonObject.getJSONObject("goals"));
        weightTracker.loadFromJson(jsonObject.getJSONArray("weightTracker"));
    }

    // EFFECTS: reads source file as string and returns it; throws IOException if an error occurs reading from the file

    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }
}
