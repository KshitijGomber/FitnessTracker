package model;

import org.json.JSONObject;

/**
 * Represents a fitness goal with a specific type and a descriptive goal statement.
 * A Goals object can encapsulate various types of fitness objectives, such as weight loss or muscle gain,
 * along with a detailed description of the goal, e.g., "Lose 10 pounds" or "Gain 5 pounds of muscle".
 */
public class Goals {
    private String goalType;
    private String description;

    // REQUIRES: goalType and description to be non-null strings.
    // MODIFIES: this
    // EFFECTS: Constructs a Goals object with a given type and description.
    public Goals(String goalType, String description) {
        this.goalType = goalType;
        this.description = description;
    }

    //REQUIRES: jsonObject to contain non-null string values for keys "goalType" and "description".
    //MODIFIES: this (the current Goals object)
    //EFFECTS: Constructs a Goals object with goalType and description extracted from the provided JSONObject.
    public Goals(JSONObject jsonObject) {
        this.goalType = jsonObject.getString("goalType");
        this.description = jsonObject.getString("description");
    }

    //REQUIRES: jsonObject to contain non-null string values for keys "goalType" and "description".
    //MODIFIES: this (the goalType and description fields
    // of the current Goals object)
    //EFFECTS: Updates the goalType and description of the current
    // Goals object with values from the provided JSONObject.
    public void loadFromJson(JSONObject jsonObject) {
        this.goalType = jsonObject.getString("goalType");
        this.description = jsonObject.getString("description");
    }

    // EFFECTS: Returns a JSONObject representing the Goals object, including its goalType and description.
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("goalType", goalType);
        json.put("description", description);
        return json;
    }

    // EFFECTS: Returns the goal type.
    public String getGoalType() {
        return goalType;
    }

    // REQUIRES: goalType to be a non-null string.
    // MODIFIES: this
    // EFFECTS: Sets the goal's type to the given goalType.
    public void setGoalType(String goalType) {
        this.goalType = goalType;
    }

    // EFFECTS: Returns the goal description.
    public String getDescription() {
        return description;
    }

    // REQUIRES: description to be a non-null string.
    // MODIFIES: this
    // EFFECTS: Sets the goal's description to the given description.
    public void setDescription(String description) {
        this.description = description;
        EventLog.getInstance().logEvent(new Event("Goal set successfully."));
    }
}
