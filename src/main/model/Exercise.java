package model;

import org.json.JSONObject;

/**
 * Represents an exercise with a name and a set of instructions.
 * An Exercise object encapsulates the details of a physical exercise,
 * including what the exercise is called and how it should be performed.
 */
public class Exercise {
    private String name;
    private String instructions;

    // REQUIRES: name and instructions to be non-null strings.
    // MODIFIES: this
    // EFFECTS: Constructs an Exercise object with a given name and instructions.
    public Exercise(String name, String instructions) {
        this.name = name;
        this.instructions = instructions;
    }

    //REQUIRES: jsonObject to contain non-null string values for keys "name" and "instructions".
    //MODIFIES: this (the current Exercise object)
    //EFFECTS: Constructs an Exercise object with name and instructions extracted from the given JSONObject.
    public Exercise(JSONObject jsonObject) {
        this.name = jsonObject.getString("name");
        this.instructions = jsonObject.getString("instructions");
    }


    // EFFECTS: Returns a JSONObject representing the Exercise object, including its name and instructions.
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("instructions", instructions);
        return json;
    }

    // EFFECTS: Returns the name of the exercise.
    public String getName() {
        return name;
    }

    // REQUIRES: name to be a non-null string.
    // MODIFIES: this
    // EFFECTS: Sets the exercise's name to the given name.
    public void setName(String name) {
        this.name = name;
    }

    // EFFECTS: Returns the instructions of the exercise.
    public String getInstructions() {
        return instructions;
    }

    // REQUIRES: instructions to be a non-null string.
    // MODIFIES: this
    // EFFECTS: Sets the exercise's instructions to the given instructions.
    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    // EFFECTS: Returns a string representation of the Exercise object.
    @Override
    public String toString() {
        return "Name: " + name + "\nInstructions: " + instructions;
    }
}
