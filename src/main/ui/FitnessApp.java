package ui;

import model.Exercise;
import model.ExerciseDatabase;
import model.Goals;
import model.WeightRecord;
import model.WeightTracker;
import model.Workout;
import model.WorkoutList;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;


/**
 * The FitnessApp class serves as the main interface for the fitness tracking application.
 * It allows users to log workouts, add exercises to a database, set fitness goals, and track their weight over time.
 * The class provides a menu-driven interface for user interaction and
 * handles user inputs to perform various fitness-related operations.
 */
public class FitnessApp {
    private WorkoutList workoutList;
    private ExerciseDatabase exerciseDatabase;
    private Goals goals;
    private WeightTracker weightTracker;
    private Scanner scanner;

    private static final String JSON_STORE = "./data/fitnessApp.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: Initializes the application with default exercises, sets up an empty
    // workout list, goals, and weight tracker, and starts the application loop.
    public FitnessApp() {
        workoutList = new WorkoutList();
        exerciseDatabase = new ExerciseDatabase();
        goals = new Goals("", "");
        weightTracker = new WeightTracker();
        scanner = new Scanner(System.in);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        initializeExerciseDatabase();
        run();
    }

    public FitnessApp(boolean startConsole) {
        workoutList = new WorkoutList();
        exerciseDatabase = new ExerciseDatabase();
        goals = new Goals("", "");
        weightTracker = new WeightTracker();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        initializeExerciseDatabase();
        if (startConsole) {
            run(); // Only enter the console loop if startConsole is true
        }
    }


    // MODIFIES: this
    // EFFECTS: Adds default exercises to the exercise database.
    private void initializeExerciseDatabase() {
        exerciseDatabase.addExercise(new Exercise("Push-ups", "Do a set of 10 push-ups."));
        exerciseDatabase.addExercise(new Exercise("Running", "Run for 30 minutes at a moderate pace."));
    }

    // MODIFIES: this
    // EFFECTS: Runs the main application loop, displaying the
    // menu and processing user input until the user chooses to exit.
    public void run() {
        boolean keepRunning = true;
        while (keepRunning) {
            displayMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume line
            keepRunning = handleUserInput(choice);
        }
        System.exit(0);
    }

    // EFFECTS: Displays the main menu options to the user.
    private void displayMenu() {
        System.out.println("\nChoose an option:");
        System.out.println("1 - Log Workout");
        System.out.println("2 - View Workouts");
        System.out.println("3 - Add Exercise");
        System.out.println("4 - View Exercises");
        System.out.println("5 - Set Fitness Goal");
        System.out.println("6 - Log Weight");
        System.out.println("7 - View Weight Records");
        System.out.println("8 - Exit");
        System.out.println("9 - Load saved data");
        System.out.println("10 - Save Data");

    }

    // REQUIRES: choice to be an integer between 1 and 8.
    // MODIFIES: this
    // EFFECTS: Processes the user's menu choice and performs the corresponding action.
    // Returns false if the user chooses to exit, true otherwise.
    private boolean handleUserInput(int choice) {
        if (choice == 8) {
            saveBeforeExiting(); // Prompt user to save before exiting
            return false;
        }
        if (choice == 9) {
            loadFitnessAppData();
            return true; // Keep the application running after loading data
        }
        choiceHelper(choice);
        return true;
    }

    //    REQUIRES: choice to be an integer corresponding to a valid menu option (1-7, 10).
    //    MODIFIES: this (the current state of the FitnessApp object, which may include workoutList, exerciseDatabase,
    //    goals, weightTracker, or other parts of the application state depending on the user's choice).
    //    EFFECTS: Executes the method associated with the user's menu choice.
    //    This includes logging workouts, viewing workouts, adding exercises, viewing exercises,
    //    setting fitness goals, logging weight, viewing weight records, or saving the application data.
    //    Displays a message for invalid options.
    private void choiceHelper(int choice) {
        switch (choice) {
            case 1: logWorkout();
            break;
            case 2: viewWorkouts();
            break;
            case 3: addExercise();
            break;
            case 4: viewExercises();
            break;
            case 5: setFitnessGoal();
            break;
            case 6: logWeight();
            break;
            case 7: viewWeightRecords();
            break;
            case 10: saveFitnessAppData();
            break;
            default: System.out.println("Invalid option. Please try again.");
        }
    }

    // REQUIRES: User input for exercise type, duration, and intensity to be valid.
    // MODIFIES: this
    // EFFECTS: Logs a new workout based on user input.
    private void logWorkout() {
        System.out.println("Enter Exercise Type:");
        String type = scanner.nextLine();
        System.out.println("Enter Duration (in minutes):");
        int duration = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.println("Enter Intensity (Low, Medium, High):");
        String intensity = scanner.nextLine();

        Workout workout = new Workout(type, duration, intensity);
        workoutList.addWorkout(workout);
        System.out.println("Workout added successfully.");
    }

    // EFFECTS: Displays all logged workouts, if any.
    private void viewWorkouts() {
        if (workoutList.getWorkouts().isEmpty()) {
            System.out.println("No workouts logged yet.");
            return;
        }
        System.out.println("Logged Workouts:");
        for (Workout workout : workoutList.getWorkouts()) {
            System.out.println(workout);
        }
    }


    // REQUIRES: User input for exercise name and instructions to be non-empty.
    // MODIFIES: this
    // EFFECTS: Adds a new exercise to the exercise database based on user input. Shows error message if already exists.
    private void addExercise() {
        System.out.println("Enter Exercise Name:");
        String name = scanner.nextLine();
        System.out.println("Enter Instructions:");
        String instructions = scanner.nextLine();

        Exercise exercise = new Exercise(name, instructions);
        boolean addedSuccessfully = exerciseDatabase.addExercise(exercise);

        if (addedSuccessfully) {
            System.out.println("Exercise added successfully.");
        } else {
            System.out.println("Exercise already exists.");
        }
    }

    // EFFECTS: Displays all exercises in the exercise database, if any.
    private void viewExercises() {
        if (exerciseDatabase.getExercises().isEmpty()) {
            System.out.println("No exercises in database.");
            return;
        }
        System.out.println("Available Exercises:");
        for (Exercise exercise : exerciseDatabase.getExercises()) {
            System.out.println(exercise);
        }
    }

    // REQUIRES: User input for goal type and description to be non-empty.
    // MODIFIES: this
    // EFFECTS: Sets a new fitness goal based on user input.
    private void setFitnessGoal() {
        System.out.println("Enter goal type (e.g., Weight Loss, Muscle Gain):");
        String type = scanner.nextLine();
        System.out.println("Enter goal description (e.g., Lose 10 pounds):");
        String description = scanner.nextLine();

        goals.setGoalType(type);
        goals.setDescription(description);
        System.out.println("Fitness goal set successfully.");
    }

    public void setFitnessGoal(String goalType, String description) {
        goals.setGoalType(goalType);
        goals.setDescription(description);
    }

    // REQUIRES: User input for date to be in the format "YYYY-MM-DD" and weight to be a positive number.
    // MODIFIES: this
    // EFFECTS: Logs a new weight record based on user input.
    private void logWeight() {
        System.out.println("Enter date (YYYY-MM-DD):");
        String date = scanner.nextLine();
        System.out.println("Enter weight:");
        double weight = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        weightTracker.logWeight(date, weight);
        System.out.println("Weight logged successfully.");
    }

    public void logWeight(String date, double weight) {
        weightTracker.logWeight(date, weight);
    }

    // EFFECTS: Displays all logged weight records and the current fitness goal, if any.
    private void viewWeightRecords() {
        // Check and print the fitness goal
        if (goals.getGoalType().isEmpty() && goals.getDescription().isEmpty()) {
            System.out.println("No fitness goal set.");
        } else {
            System.out.println("Fitness Goal: " + goals.getGoalType() + " - " + goals.getDescription());
        }

        List<WeightRecord> records = weightTracker.getWeightRecords();
        if (records.isEmpty()) {
            System.out.println("No weight records found.");
        } else {
            System.out.println("\nWeight Records:");
            for (WeightRecord record : records) {
                System.out.println("Date: " + record.getDate() + ", Weight: " + record.getWeight());
            }

        }
    }




    //MODIFIES: this (the application state may be modified if the user chooses to save data before exiting)
    //EFFECTS: Prompts the user to save their data before exiting the application.
    // Exits the application after handling the user's choice.
    private void saveBeforeExiting() {
        System.out.println("Would you like to save your data before exiting? (yes/no)");
        String input = scanner.nextLine().trim().toLowerCase();
        if (input.equals("yes")) {
            saveFitnessAppData();
        }
        System.out.println("Exiting FitnessApp. Goodbye!");
    }

    // MODIFIES: External data storage
    //EFFECTS: Saves the current state of the FitnessApp,
    // including exerciseDatabase, workoutList, goals, and weightTracker, to a file.
    // Prints a message indicating the success of the operation or an error if the file cannot be found.
    protected void saveFitnessAppData() {
        try {
            jsonWriter.write(exerciseDatabase, workoutList, goals, weightTracker);
            System.out.println("Data saved to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }


    //REQUIRES: The file specified by JSON_STORE exists and is in the correct format.
    //MODIFIES: this (exerciseDatabase, workoutList, goals, and weightTracker are modified based on the loaded data)
    //EFFECTS: Loads the state of the FitnessApp from a file, updating the exerciseDatabase,
    // workoutList, goals, and weightTracker with the loaded data.
    // Prints a message indicating the success of the operation or an error if there is an issue with reading the file.
    protected void loadFitnessAppData() {
        try {
            jsonReader.read(exerciseDatabase, workoutList, goals, weightTracker);
            System.out.println("Data loaded from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }




    // Getters for GUI access
    public WorkoutList getWorkoutList() {
        return workoutList;
    }

    public ExerciseDatabase getExerciseDatabase() {
        return exerciseDatabase;
    }

    public Goals getGoals() {
        return goals;
    }

    public WeightTracker getWeightTracker() {
        return weightTracker;
    }






}
