package model;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WorkoutTest {

    @Test
    public void testWorkoutConstructorAndGetters() {
        Workout workout = new Workout("Running", 30, "Medium");
        assertEquals("Running", workout.getExerciseType());
        assertEquals(30, workout.getDuration());
        assertEquals("Medium", workout.getIntensity());
    }

    @Test
    public void testSetExerciseType() {
        Workout workout = new Workout("Running", 30, "Medium");
        workout.setExerciseType("Swimming");
        assertEquals("Swimming", workout.getExerciseType());
    }

    @Test
    public void testSetDuration() {
        Workout workout = new Workout("Running", 30, "Medium");
        workout.setDuration(45);
        assertEquals(45, workout.getDuration());
    }

    @Test
    public void testSetIntensity() {
        Workout workout = new Workout("Running", 30, "Medium");
        workout.setIntensity("High");
        assertEquals("High", workout.getIntensity());
    }

    @Test
    public void testToString() {
        Workout workout = new Workout("Running", 30, "Medium");
        String expectedToString = "Exercise Type: Running, Duration: 30 minutes, Intensity: Medium";
        assertEquals(expectedToString, workout.toString());
    }
}
