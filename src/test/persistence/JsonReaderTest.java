package persistence;

import model.ExerciseDatabase;
import model.WorkoutList;
import model.Goals;
import model.WeightTracker;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class is adapted from the JsonReaderTest class of JsonSerializationDemo.
 */
class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            reader.read(new ExerciseDatabase(), new WorkoutList(), new Goals("", ""), new WeightTracker());
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyFitnessApp() {
        JsonReader reader = new JsonReader("./data/testEmptyFitnessApp.json");
        try {
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
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralFitnessApp() {
        JsonReader reader = new JsonReader("./data/testGeneralFitnessApp.json");
        try {
            ExerciseDatabase exerciseDatabase = new ExerciseDatabase();
            WorkoutList workoutList = new WorkoutList();
            Goals goals = new Goals("", "");
            WeightTracker weightTracker = new WeightTracker();

            reader.read(exerciseDatabase, workoutList, goals, weightTracker);
            assertFalse(exerciseDatabase.getExercises().isEmpty());
            assertFalse(workoutList.getWorkouts().isEmpty());
            assertNotNull(goals.getGoalType());
            assertNotNull(goals.getDescription());
            assertFalse(weightTracker.getWeightRecords().isEmpty());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
