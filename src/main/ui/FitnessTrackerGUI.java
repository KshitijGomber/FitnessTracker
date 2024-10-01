package ui;

import model.EventLog;
import model.Event;
import java.util.List;
import java.util.ArrayList;
import model.Exercise;
import model.WeightRecord;
import model.Workout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

/**
 * FitnessTrackerGUI is the main user interface for the fitness tracking application.
 * It provides a graphical interface for users to interact with the application, allowing them to log workouts,
 * set fitness goals, log weight records, and view their progress.
 * It uses a CardLayout to switch between different views.
 */
public class FitnessTrackerGUI extends JFrame {
    private CardLayout cardLayout = new CardLayout();
    private JPanel cardPanel = new JPanel(cardLayout);
    private FitnessApp fitnessApp;

    private JPanel navigationPanel;
    private boolean isMainMenu = true;

    private DefaultListModel<Exercise> exerciseListModel = new DefaultListModel<>();
    private DefaultListModel<Workout> workoutListModel = new DefaultListModel<>();
    private DefaultListModel<String> weightRecordListModel = new DefaultListModel<>();


     //EFFECTS: Constructs the FitnessTrackerGUI, initializes the fitness application without console input,
     //prompts the user to load data, and sets up the splash screen and UI initialization.
    public FitnessTrackerGUI() {
        fitnessApp = new FitnessApp(false);
        // Initialize navigationPanel here to ensure it's done once
        navigationPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        navigationPanel.setVisible(false);
        add(navigationPanel, BorderLayout.SOUTH);

        promptLoadData();
        showSplashScreen(this::initializeUI);
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                printEventLog(); // Print the event log before exiting
                promptSaveDataBeforeExiting();
            }
        });
    }


    //MODIFIES: this
    //EFFECTS: Sets up the main application window, initializes the navigation
    // panel and content panels, and makes the window visible.
    private void initializeUI() {
        setTitle("Fitness Tracker");
        setSize(1519, 900);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        addBanner();
        initializeNavigation();
        initializeContentPanels();

        add(cardPanel, BorderLayout.CENTER);
        setVisible(true);
    }


    // MODIFIES: this
    // EFFECTS: Adds a banner image to the top of the application window.
    // The banner is loaded from a predefined location
    //          and scaled to fit the width of the window.
    private void addBanner() {
        ImageIcon bannerIcon = new ImageIcon("./data/banner.png");
        Image bannerImage = bannerIcon.getImage().getScaledInstance(1519, 274, Image.SCALE_SMOOTH);
        bannerIcon = new ImageIcon(bannerImage);
        JLabel bannerLabel = new JLabel(bannerIcon);
        add(bannerLabel, BorderLayout.NORTH);
    }



    // EFFECTS: Shows a confirmation dialog asking the user if they want to load data from a previous session.
    //          If the user confirms, loads the data and shows a message indicating success.
    private void promptLoadData() {
        int loadChoice = JOptionPane.showConfirmDialog(this,
                "Would you like to load previous session data?",
                "Load Data",
                JOptionPane.YES_NO_OPTION);
        if (loadChoice == JOptionPane.YES_OPTION) {
            fitnessApp.loadFitnessAppData();
            JOptionPane.showMessageDialog(this, "Data loaded successfully.");
        }
    }

    // EFFECTS: Shows a confirmation dialog asking the user if they want to save their
    //          current session data before exiting.
    //          If the user confirms, saves the data and shows a message indicating success.
    private void promptSaveDataBeforeExiting() {
        int saveChoice = JOptionPane.showConfirmDialog(this,
                "Would you like to save your data before exiting?",
                "Save Data",
                JOptionPane.YES_NO_OPTION);
        if (saveChoice == JOptionPane.YES_OPTION) {
            fitnessApp.saveFitnessAppData();
            JOptionPane.showMessageDialog(this, "Data saved successfully.");
        }
    }


    // MODIFIES: this
    // EFFECTS: Displays a splash screen for a brief period before executing the provided Runnable,
    //         typically to initialize the main application UI.
    private void showSplashScreen(Runnable onClosed) {
        JWindow splashScreen = new JWindow();

        ImageIcon imageIcon = new ImageIcon("./data/splash.png");
        Image image = imageIcon.getImage();
        Image resizedImage = image.getScaledInstance(1143, 583, Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(resizedImage);
        JLabel label = new JLabel(imageIcon);
        splashScreen.getContentPane().add(label);
        splashScreen.pack();
        splashScreen.setLocationRelativeTo(null);

        Timer timer = new Timer(2000, e -> {
            splashScreen.dispose();
            onClosed.run();
            ((Timer) e.getSource()).stop(); // Stop the timer to prevent further action events
        });
        timer.setRepeats(false); // Ensure the timer only runs once
        timer.start();
        splashScreen.setVisible(true);
    }



    //MODIFIES: this
    //EFFECTS: Creates and adds buttons to the navigation panel and assigns action listeners to them.
    private void initializeNavigation() {
        String[] buttonLabels = {
                "Log Workout", "Set Goals", "Log Weight", "Exercise Database",
                "Add Exercise", "Load Data", "Save Data", "Main Menu"
        };

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.addActionListener(this::switchCard);
            navigationPanel.add(button);
        }
        JButton printLogButton = new JButton("Print Event Log");
        printLogButton.addActionListener(e -> printEventLog());
        navigationPanel.add(printLogButton);
    }


    //    MODIFIES: this
    //    EFFECTS: Updates the application state based on the specified card name.
    private void updateCardState(String cardName) {
        isMainMenu = "Main Menu".equals(cardName);
        navigationPanel.setVisible(!isMainMenu);
        helperCard(cardName);
        if (!isMainMenu) {
            updateExerciseList();
            updateWorkoutList();
            updateWeightRecordList();
        }

        cardLayout.show(cardPanel, cardName);
    }

    // MODIFIES: this
    // EFFECTS: If the cardName is "Main Menu",
    //            the navigation panel is hidden; for other cards, the navigation panel is shown. Specific actions
    //            are taken based on the cardName, such as loading data, saving data, or updating list models.
    //            The card layout is updated to show the card specified by cardName.
    //            If cardName does not match any case,
    //            no action is taken.
    private void helperCard(String cardName) {
        switch (cardName) {
            case "Load Data":
                fitnessApp.loadFitnessAppData();
                JOptionPane.showMessageDialog(this, "Data loaded successfully.");
                break;
            case "Save Data":
                fitnessApp.saveFitnessAppData();
                JOptionPane.showMessageDialog(this, "Data saved successfully.");
                break;
            case "Exercise Database":
                updateExerciseList();
                break;
            case "View Workouts":
                updateWorkoutList();
                break;
            case "View Weight Records":
                updateWeightRecordList();
                break;
            case "Main Menu":
                break;
            default:
                break;
        }
    }


    //  MODIFIES: this
    //  EFFECTS: Changes the current view to the card specified by cardName. If cardName equals "Main Menu",
    //           hides the navigation panel; otherwise, makes it visible. Does nothing if cardName is not recognized.
    private void switchCard(String cardName) {
        updateCardState(cardName);
    }



    // MODIFIES: this
    // EFFECTS: Responds to an action event triggered by a button click by switching to the card specified
    //          by the action command associated with the event. This method is typically called by the action listeners
    //          attached to buttons in the navigation panel.
    private void switchCard(ActionEvent e) {
        String cardName = e.getActionCommand();
        //System.out.println("Navigating to: " + cardName); // For debugging
        updateCardState(cardName);
    }

    //MODIFIES: this
    //EFFECTS: Initializes and adds all the content panels to the card layout.
    private void initializeContentPanels() {
        cardPanel.add(createMainMenuPanel(), "Main Menu");
        cardPanel.add(createLogWorkoutPanel(), "Log Workout");
        cardPanel.add(createSetGoalsPanel(), "Set Goals");
        cardPanel.add(createLogWeightPanel(), "Log Weight");
        cardPanel.add(createViewExercisesPanel(), "Exercise Database");
        cardPanel.add(createAddExercisePanel(), "Add Exercise");
        cardPanel.add(createViewWorkoutsPanel(), "View Workouts");
        cardPanel.add(createViewWeightRecordsPanel(), "View Weight Records");
    }

    //EFFECTS: Creates and returns the main menu panel with buttons for all main actions.
    private JPanel createMainMenuPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 1, 10, 10));
        String[] buttons = {"Log Workout", "Set Goals", "Log Weight", "Exercise Database",
                "Add Exercise", "View Workouts", "View Weight Records", "Load Data", "Save Data"};
        for (String buttonLabel : buttons) {
            JButton button = new JButton(buttonLabel);
            button.addActionListener(e -> switchCard(new ActionEvent(button,
                    ActionEvent.ACTION_PERFORMED, buttonLabel)));
            panel.add(button);
        }
        return panel;
    }

    //EFFECTS: Creates and returns the panel for logging a new workout,
    // with fields for exercise type, duration, and intensity.
    private JPanel createLogWorkoutPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 10, 10));
        JTextField exerciseTypeField = new JTextField();
        JTextField durationField = new JTextField();
        JTextField intensityField = new JTextField();
        panel.add(new JLabel("Exercise Type:"));
        panel.add(exerciseTypeField);
        panel.add(new JLabel("Duration (minutes):"));
        panel.add(durationField);
        panel.add(new JLabel("Intensity:"));
        panel.add(intensityField);
        JButton logButton = new JButton("Log Workout");
        workoutPanelHelper(exerciseTypeField, durationField, intensityField, logButton);
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> switchCard("Main Menu"));
        panel.add(backButton);
        panel.add(logButton);

        return panel;
    }

    //MODIFIES: this
    //EFFECTS: Adds an action listener to the logButton that creates a new Workout object with input
    private void workoutPanelHelper(JTextField exerciseTypeField, JTextField durationField,
                                    JTextField intensityField, JButton logButton) {
        logButton.addActionListener(e -> {
            String type = exerciseTypeField.getText();
            int duration = Integer.parseInt(durationField.getText());
            String intensity = intensityField.getText();
            Workout workout = new Workout(type, duration, intensity);
            fitnessApp.getWorkoutList().addWorkout(workout);
            switchCard("Main Menu");
        });
    }

    //EFFECTS: Creates and returns the panel for setting fitness goals, with fields for goal type and description.
    private JPanel createSetGoalsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 10, 10));
        JTextField goalTypeField = new JTextField();
        JTextField descriptionField = new JTextField();
        panel.add(new JLabel("Goal Type:"));
        panel.add(goalTypeField);
        panel.add(new JLabel("Description:"));
        panel.add(descriptionField);
        JButton setGoalButton = new JButton("Set Goal");
        setGoalButton.addActionListener(e -> {
            String goalType = goalTypeField.getText();
            String description = descriptionField.getText();
            fitnessApp.setFitnessGoal(goalType, description);
            switchCard("Main Menu");
        });

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> switchCard("Main Menu"));
        panel.add(backButton);
        panel.add(setGoalButton);

        return panel;
    }

    //EFFECTS: Creates and returns the panel for logging a new weight record, with fields for date and weight.
    private JPanel createLogWeightPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 10, 10));

        JTextField dateField = new JTextField();
        JTextField weightField = new JTextField();

        panel.add(new JLabel("Date (YYYY-MM-DD):"));
        panel.add(dateField);
        panel.add(new JLabel("Weight (kg):"));
        panel.add(weightField);

        JButton logWeightButton = new JButton("Log Weight");
        logWeightButton.addActionListener(e -> {
            String date = dateField.getText();
            double weight = Double.parseDouble(weightField.getText());
            fitnessApp.logWeight(date, weight);
            switchCard("Main Menu");
        });

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> switchCard("Main Menu"));
        panel.add(backButton);
        panel.add(logWeightButton);

        return panel;
    }

    //EFFECTS: Creates and returns the panel for viewing the list of exercises from the exercise database.
    private JPanel createViewExercisesPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JList<Exercise> exerciseList = new JList<>(exerciseListModel);
        panel.add(new JScrollPane(exerciseList), BorderLayout.CENTER);
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> switchCard("Main Menu"));
        panel.add(backButton, BorderLayout.PAGE_END);
        return panel;
    }

    //EFFECTS: Creates and returns the panel for adding a new exercise to the
    // database, with fields for exercise name and instructions.
    private JPanel createAddExercisePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 10, 10));
        JTextField nameField = new JTextField();
        JTextField instructionsField = new JTextField();
        panel.add(new JLabel("Exercise Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Instructions:"));
        panel.add(instructionsField);
        JButton addButton = new JButton("Add Exercise");
        addButton.addActionListener(e -> {
            String name = nameField.getText();
            String instructions = instructionsField.getText();
            fitnessApp.getExerciseDatabase().addExercise(new Exercise(name, instructions));
            updateExerciseList();
            switchCard("Exercise Database");
        });
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> switchCard("Main Menu"));
        panel.add(backButton);
        panel.add(addButton);
        return panel;
    }


    //EFFECTS: Creates and returns the panel for viewing the list of logged workouts.
    private JPanel createViewWorkoutsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JList<Workout> workoutList = new JList<>(workoutListModel);
        workoutList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        panel.add(new JScrollPane(workoutList), BorderLayout.CENTER);
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> switchCard("Main Menu"));
        panel.add(backButton, BorderLayout.PAGE_END);
        return panel;
    }

    //EFFECTS: Creates and returns the panel for viewing the list of logged weight records.
    private JPanel createViewWeightRecordsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JList<String> weightRecordList = new JList<>(weightRecordListModel);
        panel.add(new JScrollPane(weightRecordList), BorderLayout.CENTER);
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> switchCard("Main Menu"));
        panel.add(backButton, BorderLayout.PAGE_END);
        return panel;
    }


    //MODIFIES: this
    //EFFECTS: Updates the list model for exercises with the latest data from the exercise database.
    private void updateExerciseList() {
        exerciseListModel.clear();
        for (Exercise exercise : fitnessApp.getExerciseDatabase().getExercises()) {
            exerciseListModel.addElement(exercise);
        }
    }


    //MODIFIES: this
    //EFFECTS: Updates the list model for workouts with the latest data from the workout list.
    private void updateWorkoutList() {
        workoutListModel.clear();
        for (Workout workout : fitnessApp.getWorkoutList().getWorkouts()) {
            workoutListModel.addElement(workout);
        }
    }

    //MODIFIES: this
    //EFFECTS: Updates the list model for weight records with the latest data from the weight tracker.
    private void updateWeightRecordList() {
        weightRecordListModel.clear();
        for (WeightRecord record : fitnessApp.getWeightTracker().getWeightRecords()) {
            weightRecordListModel.addElement("Date: " + record.getDate() + ", Weight: " + record.getWeight());
        }
    }


    //EFFECTS: prints event log for the application
    private void printEventLog() {
        EventLog eventLog = EventLog.getInstance();
        List<Event> eventList = new ArrayList<>();
        for (Event event : eventLog) {
            eventList.add(event);
        }
        StringBuilder logBuilder = new StringBuilder("Event Log:\n");
        for (Event event : eventList) {
            logBuilder.append(event.toString()).append("\n");
        }
        System.out.println(logBuilder.toString()); //print on console
        JOptionPane.showMessageDialog(this, logBuilder.toString(), "Event Log", JOptionPane.INFORMATION_MESSAGE);
    }

    //EFFECTS: Launches the FitnessTrackerGUI application.
    public static void main(String[] args) {
        SwingUtilities.invokeLater(FitnessTrackerGUI::new);

    }
}
