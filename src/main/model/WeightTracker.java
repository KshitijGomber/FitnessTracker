package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Manages a list of WeightRecord objects to track a user's weight over time.
 * This class allows for logging new weight records and provides functionality
 * to retrieve the history of weight records. It ensures the user's weight tracking
 * is organized and accessible for review and analysis.
 */
public class WeightTracker {
    private List<WeightRecord> weightRecords;

    // EFFECTS: Constructs an empty WeightTracker.
    public WeightTracker() {
        this.weightRecords = new ArrayList<>();
    }

    //EFFECTS: Returns a JSONArray where each element is a JSONObject representation of a WeightRecord in the tracker.
    public JSONArray toJson() {
        JSONArray jsonArray = new JSONArray();
        for (WeightRecord record : weightRecords) {
            jsonArray.put(record.toJson());
        }
        return jsonArray;
    }

    // REQUIRES: jsonArray to be a non-null JSONArray where each element is a JSONObject that represents a WeightRecord.
    //MODIFIES: this (the weightRecords list in the current WeightTracker object)
    //EFFECTS: Clears the current weight records list and populates it with WeightRecord
    // objects created from the JSONObjects in the given JSONArray.
    public void loadFromJson(JSONArray jsonArray) {
        weightRecords.clear(); // Clear the current list to avoid duplicating records
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            WeightRecord record = new WeightRecord(jsonObject);
            weightRecords.add(record);
        }
    }

    // REQUIRES: date to be in the format "YYYY-MM-DD" and weight to be a positive number.
    // MODIFIES: this
    // EFFECTS: Adds a new weight record to the tracker with the given date and weight.
    public void logWeight(String date, double weight) {
        weightRecords.add(new WeightRecord(date, weight));
        EventLog.getInstance().logEvent(new Event("Weight logged."));
    }

    // EFFECTS: Returns a new list containing all the weight records in this tracker.
    public List<WeightRecord> getWeightRecords() {
        return new ArrayList<>(weightRecords);
    }
}
