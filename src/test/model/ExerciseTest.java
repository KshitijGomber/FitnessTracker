package model;

import model.Exercise;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExerciseTest {

    @Test
    public void testExerciseConstructorAndGetters() {
        Exercise exercise = new Exercise("Push-ups", "Do a set of 10 push-ups.");
        assertEquals("Push-ups", exercise.getName());
        assertEquals("Do a set of 10 push-ups.", exercise.getInstructions());
    }

    @Test
    public void testSetters() {
        Exercise exercise = new Exercise("Push-ups", "Do a set of 10 push-ups.");
        exercise.setName("Sit-ups");
        exercise.setInstructions("Do a set of 20 sit-ups.");
        assertEquals("Sit-ups", exercise.getName());
        assertEquals("Do a set of 20 sit-ups.", exercise.getInstructions());
    }

    @Test
    public void testToString() {
        Exercise exercise = new Exercise("Push-ups", "Do a set of 10 push-ups.");
        String expected = "Name: Push-ups\nInstructions: Do a set of 10 push-ups.";
        assertEquals(expected, exercise.toString());
    }
}
