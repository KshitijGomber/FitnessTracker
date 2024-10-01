package persistence;

import model.Exercise;
import model.Workout;

import model.ExerciseDatabase;
import model.WorkoutList;
import model.Goals;
import model.WeightTracker;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class is adapted from the JsonWriterTest class of JsonSerializationDemo.
 */

class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // Expected IOException, test passes
        }
    }

    @Test
    void testWriterEmptyFitnessApp() {
        try {
            JsonWriter writer = new JsonWriter("./data/testEmptyFitnessApp.json");
            writer.write(new ExerciseDatabase(), new WorkoutList(), new Goals("", ""), new WeightTracker());
            writer.close();

            JsonReader reader = new JsonReader("./data/testEmptyFitnessApp.json");
            ExerciseDatabase exerciseDatabase = new ExerciseDatabase();
            WorkoutList workoutList = new WorkoutList();
            Goals goals = new Goals("", "");
            WeightTracker weightTracker = new WeightTracker();

            reader.read(exerciseDatabase, workoutList, goals, weightTracker);

            assertTrue(exerciseDatabase.getExercises().isEmpty());
            assertTrue(workoutList.getWorkouts().isEmpty());
            assertEquals("", goals.getGoalType());
            assertEquals("", goals.getDescription());
            assertTrue(weightTracker.getWeightRecords().isEmpty());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralFitnessApp() {
        try {
            ExerciseDatabase exerciseDatabase = new ExerciseDatabase();
            exerciseDatabase.addExercise(new Exercise("Push-ups", "Do 3 sets of 10 push-ups."));
            WorkoutList workoutList = new WorkoutList();
            workoutList.addWorkout(new Workout("Running", 30, "Medium"));
            Goals goals = new Goals("Lose Weight", "Lose 5 kg in 2 months");
            WeightTracker weightTracker = new WeightTracker();
            weightTracker.logWeight("2023-01-01", 75);

            JsonWriter writer = new JsonWriter("./data/testGeneralFitnessApp.json");
            writer.write(exerciseDatabase, workoutList, goals, weightTracker);
            writer.close();

            JsonReader reader = new JsonReader("./data/testGeneralFitnessApp.json");
            exerciseDatabase = new ExerciseDatabase();
            workoutList = new WorkoutList();
            goals = new Goals("", "");
            weightTracker = new WeightTracker();

            reader.read(exerciseDatabase, workoutList, goals, weightTracker);

            assertFalse(exerciseDatabase.getExercises().isEmpty());
            assertEquals("Push-ups", exerciseDatabase.getExercises().get(0).getName());
            assertFalse(workoutList.getWorkouts().isEmpty());
            assertEquals("Running", workoutList.getWorkouts().get(0).getExerciseType());
            assertEquals("Lose Weight", goals.getGoalType());
            assertEquals("Lose 5 kg in 2 months", goals.getDescription());
            assertFalse(weightTracker.getWeightRecords().isEmpty());
            assertEquals("2023-01-01", weightTracker.getWeightRecords().get(0).getDate());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}