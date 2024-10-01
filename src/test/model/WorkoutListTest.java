package model;

import model.Workout;
import model.WorkoutList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WorkoutListTest {

    @Test
    public void testWorkoutConstructorAndGetters() {
        Workout workout = new Workout("Running", 30, "Medium");
        assertEquals("Running", workout.getExerciseType());
        assertEquals(30, workout.getDuration());
        assertEquals("Medium", workout.getIntensity());
    }

    @Test
    public void testAddAndRemoveWorkout() {
        WorkoutList list = new WorkoutList();
        Workout workout1 = new Workout("Running", 30, "Medium");
        Workout workout2 = new Workout("Swimming", 60, "High");
        list.addWorkout(workout1);
        list.addWorkout(workout2);

        assertEquals(2, list.getWorkouts().size());
        assertTrue(list.getWorkouts().contains(workout1));
        assertTrue(list.getWorkouts().contains(workout2));

        list.removeWorkout(workout1);
        assertEquals(1, list.getWorkouts().size());
        assertFalse(list.getWorkouts().contains(workout1));
        assertTrue(list.getWorkouts().contains(workout2));
    }
}
