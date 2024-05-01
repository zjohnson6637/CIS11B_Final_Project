import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
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
    private String fileName = "default list";
    private TextField titleField;
    private TextField definitionField;
    private List<String> flashcards = new ArrayList<>(); // List to store flashcards
    private int currentCardIndex = 0; // Index of the currently displayed flashcard

    public static void main(String[] args) {
        launch(args); // Launch the JavaFX application
    }
   
    // Make Grids
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
        // TextField for entering the term (title) of the flashcard
        titleField = new TextField("Enter Term");
        titleField.setFont(Font.font("TimesNewRoman", 30)); // Set font size and style
        grid.add(titleField, 0, 0, 1, 1); // Add titleField to the grid

        // TextField for entering the definition of the flashcard (initially hidden)
        definitionField = new TextField("Enter Definition");
        definitionField.setFont(Font.font("TimesNewRoman", 15));
        grid.add(definitionField, 0, 1, 1, 1);
        definitionField.setVisible(true); // Initially hide the definition field

        // Button to toggle showing/hiding the definition of the current flashcard
        Button showBack = new Button("Hide Definition");
        showBack.setOnAction(e -> {
            if (flashcards.isEmpty()) return;

            if (!definitionField.isVisible()) {
                // Show definition when button is clicked
                definitionField.setVisible(true);
                showBack.setText("Hide Definition"); // Change button text to indicate hiding option
            } else {
                // Hide definition when button is clicked again
                definitionField.setVisible(false);
                showBack.setText("Show Definition"); // Change button text back to original
            }
        });
        grid.add(showBack, 0, 2, 3, 1); // Add showBack button to the grid

        // Button to save the current flashcard (title and definition) to a file
        Button saveButton = new Button("Save Card");
        saveButton.setOnAction(e -> {
            String title = titleField.getText();
            String definition = definitionField.getText();

            // Check if the flashcard (title and definition) already exists in the list
            if (!flashcards.contains(title + ":" + definition)) {
                // Add the new flashcard to the list only if it's not a duplicate
                flashcards.add(title + ":" + definition);
                
                // Point to the newly added flashcard in the list
                currentCardIndex = flashcards.size() - 1;
                
                // Display the newly added flashcard
                displayCurrentCard();
                
                // Save the flashcard data to the file
                saveData(title, definition);
                
                // Clear input fields after saving
                clearFields();
            } else {
                // Inform the user that the flashcard already exists
                System.out.println("Flashcard already exists.");
            }
        });
        grid.add(saveButton, 0, 3, 3, 1); // Add saveButton to the grid

        // Buttons for navigating through the list of flashcards
        Button leftArrow = new Button("<");
        leftArrow.setOnAction(e -> showPreviousCard()); // Show the previous flashcard
        grid.add(leftArrow, 1, 3); // Add leftArrow button to the grid

        Button rightArrow = new Button(">");
        rightArrow.setOnAction(e -> showNextCard()); // Show the next flashcard
        grid.add(rightArrow, 2, 3); // Add rightArrow button to the grid
        
        //New flashcard button
        Button newCard = new Button("New Card");
        newCard.setOnAction(e -> clearFields()); 
        grid.add(newCard, 3, 3);
        //search button
        Button search = new Button("Search");  
        search.setOnAction(e -> NewWindow());       
        grid.add(search, 3, 4);
        //
        primaryStage.show(); // Display the primary stage
        // Load existing flashcards from file when the application starts
        loadFlashcards();
        // Display the initial flashcard (if any)
        displayCurrentCard();
        
        //New List text field
        TextField listField = new TextField("Enter list name");
        grid.add(listField, 2, 2);
        //New List Button
        
        Button newList = new Button("New List");
        newList.setOnAction(e -> {
         fileName = listField.getText();
         newFile(fileName);
         clearFields();
         });
        grid.add(newList, 3, 5);
    }

    // Method to save a flashcard (title and definition) to a file
    private void saveData(String title, String definition) {
        try (FileWriter writer = new FileWriter(fileName, true)) {
            writer.write(title + ":" + definition + "\n"); // Write flashcard data to file
            System.out.println("Data saved successfully!");
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("Error saving data!");
        }
    }

    // Method to load flashcards from a file
    private void loadFlashcards() {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                flashcards.add(line); // Add each line (flashcard) from file to the list
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
                titleField.setText(cardData[0]); // Set title of current flashcard
                definitionField.setText(cardData[1]); // Set definition of current flashcard
            }
        }
    }

    // Method to show the next flashcard in the list
    private void showNextCard() {
        if (!flashcards.isEmpty()) {
            currentCardIndex = (currentCardIndex + 1) % flashcards.size();
            displayCurrentCard(); // Display the next flashcard
        }
    }

    // Method to show the previous flashcard in the list
    private void showPreviousCard() {
        if (!flashcards.isEmpty()) {
            currentCardIndex = (currentCardIndex - 1 + flashcards.size()) % flashcards.size();
            displayCurrentCard(); // Display the previous flashcard
        }
    }

    // Method to clear input fields after saving a flashcard
    private void clearFields() {
        titleField.setText(""); // Clear the title field
        definitionField.setText(""); // Clear the definition field
    }
    // Method to add a new Stage
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
   //Method to add a new file
   public void newFile(String listName) {
      try (FileWriter writer = new FileWriter(listName)) {
      } catch (IOException ex) {
         ex.printStackTrace();
         System.out.println("Error saving data!");
      }
   }
}
