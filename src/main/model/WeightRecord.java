package model;


import org.json.JSONObject;

/**
 * Represents a record of a user's weight at a specific date.
 * This class is used to log and track changes in a user's weight over time,
 * storing both the date of the record and the recorded weight.
 */
public class WeightRecord {
    private String date;
    private double weight;

    // REQUIRES: date to be in the format "YYYY-MM-DD" and weight to be a positive number.
    // MODIFIES: this
    // EFFECTS: Constructs a WeightRecord object with the given date and weight.
    public WeightRecord(String date, double weight) {
        this.date = date;
        this.weight = weight;
    }

    //REQUIRES: jsonObject to contain a valid date string in the "YYYY-MM-DD"
    // format for the key "date" and a positive number for the key "weight".
    //MODIFIES: this (the current WeightRecord object)
    //EFFECTS: Constructs a WeightRecord object with the date and weight extracted from the given JSONObject.
    public WeightRecord(JSONObject jsonObject) {
        this.date = jsonObject.getString("date");
        this.weight = jsonObject.getDouble("weight");
    }

    // EFFECTS: Returns a JSONObject representing the WeightRecord object, including its date and weight.
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("date", date);
        json.put("weight", weight);
        return json;
    }

    // EFFECTS: Returns the date of this weight record.
    public String getDate() {
        return date;
    }

    // EFFECTS: Returns the weight of this weight record.
    public double getWeight() {
        return weight;
    }
}
