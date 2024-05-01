import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Cards extends Application {

    // Fields for UI components and data storage
    private TextField titleField; // TextField for entering flashcard titles
    private TextField definitionField; // TextField for entering flashcard definitions
    private TextArea snippetField; // TextArea for displaying search result snippets
    private List<String> flashcards = new ArrayList<>(); // List to store flashcards
    private int currentCardIndex = 0; // Index of the currently displayed flashcard

    public static void main(String[] args) {
        launch(args); // Launch the JavaFX application
    }
   
    // Method to initialize and configure the primary stage of the application
    @Override
    public void start(Stage primaryStage) {
        // Create the main layout grid for the application
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25)); // Set padding around the grid
        Scene scene = new Scene(grid, 1000, 500); // Create a scene with specified dimensions
        primaryStage.setScene(scene); // Set the scene on the primary stage

        // Label for the front text entry box
        Label frontLabel = new Label("Front:");
        grid.add(frontLabel, 0, 0); // Add frontLabel to the grid

        // TextField for entering the term (title) of the flashcard
        titleField = new TextField("Enter Term");
        titleField.setFont(Font.font("TimesNewRoman", 30)); // Set font size and style
        grid.add(titleField, 1, 0); // Add titleField to the grid

        // Label for the back text entry box
        Label backLabel = new Label("Back:");
        grid.add(backLabel, 0, 1); // Add backLabel to the grid

        // TextField for entering the definition of the flashcard (initially visible)
        definitionField = new TextField("Enter Definition");
        definitionField.setFont(Font.font("TimesNewRoman", 15));
        grid.add(definitionField, 1, 1); // Add definitionField to the grid

        // Label for the snippet box
        Label snippetLabel = new Label("Search Results:");
        snippetLabel.setFont(Font.font("TimesNewRoman", 20));
        grid.add(snippetLabel, 0, 2); // Add snippetLabel to the grid

        // TextArea for displaying search result snippets
        snippetField = new TextArea();
        snippetField.setFont(Font.font("TimesNewRoman", 20));
        snippetField.setPrefHeight(150); // Set a larger preferred height
        snippetField.setEditable(false); // Make snippetField read-only
        snippetField.setWrapText(true); // Enable word wrapping
        snippetField.setStyle("-fx-text-alignment: left; -fx-alignment: top-left;");
        grid.add(snippetField, 1, 2, 3, 1); // Span across 3 columns

        // Buttons for navigating through flashcards
        Button leftArrow = new Button("<");
        leftArrow.setOnAction(e -> showPreviousCard());
        grid.add(leftArrow, 0, 3); // Add leftArrow button to the grid

        Button rightArrow = new Button(">");
        rightArrow.setOnAction(e -> showNextCard());
        grid.add(rightArrow, 1, 3); // Add rightArrow button to the grid

        // Button to toggle showing/hiding the flashcard definition
        Button showBack = new Button("Hide Definition");
        showBack.setOnAction(e -> {
            if (flashcards.isEmpty()) return;
            definitionField.setVisible(!definitionField.isVisible());
            showBack.setText(definitionField.isVisible() ? "Hide Definition" : "Show Definition");
        });
        grid.add(showBack, 2, 3); // Add showBack button to the grid

        // Button to save the current flashcard (title and definition) to a file
        Button saveButton = new Button("Save Card");
        saveButton.setOnAction(e -> {
            String title = titleField.getText();
            String definition = definitionField.getText();
            if (!flashcards.contains(title + ":" + definition)) {
                flashcards.add(title + ":" + definition);
                currentCardIndex = flashcards.size() - 1;
                displayCurrentCard();
                saveData(title, definition);
                clearFields();
            } else {
                System.out.println("Flashcard already exists.");
            }
        });
        grid.add(saveButton, 0, 4); // Add saveButton to the grid

        // Button to clear input fields
        Button newCard = new Button("New Card");
        newCard.setOnAction(e -> clearFields());
        grid.add(newCard, 1, 4); // Add newCard button to the grid

        // Button to initiate a search
        Button searchButton = new Button("Search");
        searchButton.setOnAction(e -> {
            String searchTerm = titleField.getText();
            try {
                String snippet = FlashCardSearch.searchAndRetrieveSnippet(searchTerm);
                snippetField.setText(snippet); // Update snippetField with the retrieved snippet
            } catch (IOException ex) {
                ex.printStackTrace();
                snippetField.setText("Search failed. Please try again.");
            }
        });
        grid.add(searchButton, 2, 4); // Add searchButton to the grid

        // Button to clear the search result box
        Button clearSnippetButton = new Button("Clear Snippet");
        clearSnippetButton.setOnAction(e -> {
            snippetField.setText(""); // Clear the text in snippetField
        });
        grid.add(clearSnippetButton, 3, 4); // Add clearSnippetButton to the grid

        primaryStage.show(); // Display the primary stage

        loadFlashcards(); // Load existing flashcards from file
        displayCurrentCard(); // Display the initial flashcard (if any)
    }

    // Method to save flashcard data to a file
    private void saveData(String title, String definition) {
        try (FileWriter writer = new FileWriter("flashcards.txt", true)) {
            writer.write(title + ":" + definition + "\n");
            System.out.println("Data saved successfully!");
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("Error saving data!");
        }
    }

    // Method to load flashcards from a file
    private void loadFlashcards() {
        try (BufferedReader reader = new BufferedReader(new FileReader("flashcards.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                flashcards.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to display the current flashcard on the UI
    private void displayCurrentCard() {
        if (!flashcards.isEmpty()) {
            String[] cardData = flashcards.get(currentCardIndex).split(":");
            if (cardData.length == 2) {
                titleField.setText(cardData[0]);
                definitionField.setText(cardData[1]);
            }
        }
    }

    // Method to show the next flashcard in the list
    private void showNextCard() {
        if (!flashcards.isEmpty()) {
            currentCardIndex = (currentCardIndex + 1) % flashcards.size();
            displayCurrentCard();
        }
    }

    // Method to show the previous flashcard in the list
    private void showPreviousCard() {
        if (!flashcards.isEmpty()) {
            currentCardIndex = (currentCardIndex - 1 + flashcards.size()) % flashcards.size();
            displayCurrentCard();
        }
    }

    // Method to clear input fields
    private void clearFields() {
        titleField.setText(""); // Clear the title field
        definitionField.setText(""); // Clear the definition field
        snippetField.setText(""); // Clear the snippet field
    }

    // Method to open a new secondary window for search functionality
    public void NewWindow() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        Stage secondaryStage = new Stage();
        secondaryStage.setTitle("StudyZone");
        Scene scene = new Scene(grid, 1000, 275);
        secondaryStage.setScene(scene);
        secondaryStage.show();
    }
}
