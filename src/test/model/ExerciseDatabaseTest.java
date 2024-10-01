package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ExerciseDatabaseTest {

    @Test
    public void testAddUniqueExercises() {
        ExerciseDatabase db = new ExerciseDatabase();
        Exercise exercise1 = new Exercise("Push-ups", "Do a set of 10 push-ups.");
        Exercise exercise2 = new Exercise("Sit-ups", "Do a set of 20 sit-ups.");

        assertTrue(db.addExercise(exercise1));
        assertTrue(db.addExercise(exercise2));
        assertEquals(2, db.getExercises().size());
    }

    @Test
    public void testAddDuplicateExercise() {
        ExerciseDatabase db = new ExerciseDatabase();
        Exercise exercise1 = new Exercise("Push-ups", "Do a set of 10 push-ups.");
        Exercise exercise2 = new Exercise("Push-ups", "Another set of 10 push-ups.");

        assertTrue(db.addExercise(exercise1));
        assertFalse(db.addExercise(exercise2));
        assertEquals(1, db.getExercises().size());
    }

    @Test
    public void testGetExercises() {
        ExerciseDatabase db = new ExerciseDatabase();
        Exercise exercise = new Exercise("Push-ups", "Do a set of 10 push-ups.");
        db.addExercise(exercise);

        assertEquals(1, db.getExercises().size());
        assertEquals(exercise, db.getExercises().get(0));
    }
}
