package persistence;

import model.Exercise;
import model.Goals;
import model.WeightRecord;
import model.Workout;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * This class is adapted from the JsonTest class of JsonSerializationDemo.
 */
public class JsonTest {
    protected void checkExercise(String name, String instructions, Exercise exercise) {
        assertEquals(name, exercise.getName());
        assertEquals(instructions, exercise.getInstructions());
    }

    protected void checkGoal(String goalType, String description, Goals goal) {
        assertEquals(goalType, goal.getGoalType());
        assertEquals(description, goal.getDescription());
    }

    protected void checkWeightRecord(String date, double weight, WeightRecord record) {
        assertEquals(date, record.getDate());
        assertEquals(weight, record.getWeight(), 0.01);
    }

    protected void checkWorkout(String exerciseType, int duration, String intensity, Workout workout) {
        assertEquals(exerciseType, workout.getExerciseType());
        assertEquals(duration, workout.getDuration());
        assertEquals(intensity, workout.getIntensity());
    }
}
