import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.*;
import java.io.*;

public class Cards extends Application {

  private boolean isAnswerVisible = true;//show/hide answer

  public static void main(String[] args) {
    launch(args);
  }

  // Make Grid
  @Override
  public void start(Stage primaryStage) throws Exception {
    GridPane grid = new GridPane();
    grid.setAlignment(Pos.CENTER);
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(25, 25, 25, 25));
    Scene scene = new Scene(grid, 1000, 500);
    primaryStage.setScene(scene);

    // Front of card/Editable title
    TextField titleField = new TextField("Enter Term");
    titleField.setFont(Font.font("TimesNewRoman", FontWeight.BOLD, 30));
    grid.add(titleField, 0, 0, 1, 1);

    // Back of card/Editable definition (initially hidden)
    TextField definitionField = new TextField("Enter Definition");
    definitionField.setFont(Font.font("TimesNewRoman", FontWeight.NORMAL, 15));
    grid.add(definitionField, 0, 1, 1, 1);
    definitionField.setVisible(true);

    // Button
    Button showBack = new Button("Hide Answer");
    showBack.setOnAction(act -> {
      isAnswerVisible = !isAnswerVisible;
      definitionField.setVisible(isAnswerVisible);
      showBack.setText(isAnswerVisible ? "Hide Answer" : "Show Answer");
    });
    grid.add(showBack, 0, 2, 3, 1);

    // Save Button
    Button saveButton = new Button("Save Card");
    saveButton.setOnAction(act -> {
      String title = titleField.getText();
      String definition = definitionField.getText();
      saveData(title, definition);
    });
    grid.add(saveButton, 0, 3, 3, 1);
    //arrow keys
    Button leftArrow = new Button("<");
    leftArrow.setOnAction(act -> {
       try (BufferedReader buffRead = new BufferedReader(new FileReader("flashcards.txt"))) {
           String line;
           while ((line = buffRead.readLine()) != null) {
               System.out.println(line);
           }
       } catch (IOException e) {
           e.printStackTrace(); // Handle the exception appropriately
       }
      });
   grid.add(leftArrow, 1, 3, 1, 1);
   //
   Button rightArrow = new Button(">");
   rightArrow.setOnAction(act -> {
      try(BufferedReader buffRead = new BufferedReader(new FileReader("flashcards.txt"))) {
           String line;
           while ((line = buffRead.readLine()) != null) {
               System.out.println(line);
           }
       } catch (IOException e) {
           e.printStackTrace(); // Handle the exception appropriately
       }
      });
   grid.add(rightArrow, 2, 3, 1, 1);
    //
    primaryStage.show();
  }

  // Method to save data to a text file
  private void saveData(String title, String definition) {
    try {
      // Create a FileWriter object with desired file path
      FileWriter writer = new FileWriter("flashcards.txt", true);
      writer.write(title + ":" + definition + "\n");
      writer.close();
      System.out.println("Data saved successfully!");
    } catch (IOException e) {
      e.printStackTrace();
      System.out.println("Error saving data!");
    }
  }
}
