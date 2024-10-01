package model;
import model.Goals;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GoalsTest {

    @Test
    public void testGoalsConstructorAndGetters() {
        Goals goals = new Goals("Weight Loss", "Lose 10 pounds");
        assertEquals("Weight Loss", goals.getGoalType());
        assertEquals("Lose 10 pounds", goals.getDescription());
    }

    @Test
    void testGoalsConstructorWithJSONObject() {
        // Create a JSONObject representing a goal
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("goalType", "Gain Muscle");
        jsonObject.put("description", "Gain 5 kg of muscle in 3 months");

        // Use the JSONObject to create a Goals instance
        Goals goals = new Goals(jsonObject);

        // Assert that the Goals instance has the expected values
        assertEquals("Gain Muscle", goals.getGoalType());
        assertEquals("Gain 5 kg of muscle in 3 months", goals.getDescription());
    }

    @Test
    public void testSetters() {
        Goals goals = new Goals("Weight Loss", "Lose 10 pounds");
        goals.setGoalType("Muscle Gain");
        goals.setDescription("Gain 5 pounds of muscle");
        assertEquals("Muscle Gain", goals.getGoalType());
        assertEquals("Gain 5 pounds of muscle", goals.getDescription());
    }
}
