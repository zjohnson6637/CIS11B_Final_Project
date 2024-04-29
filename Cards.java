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
//

public class Cards extends Application {

   private boolean isAnswerVisible = false;//show/hide answer

   public static void main(String[] args) {
      launch(args);
   }
   // Make Grid
   @Override
   public void start(Stage primaryStage) {
      GridPane grid = new GridPane();
      grid.setAlignment(Pos.CENTER);
      grid.setHgap(10);
      grid.setVgap(10);
      grid.setPadding(new Insets(25, 25, 25, 25));
      Scene scene = new Scene(grid, 500, 500);
      primaryStage.setScene(scene);
      //front of card
      Text term = new Text("Scientific name for 'Dog'" + ":");
      term.setFont(Font.font("TimesNewRoman", FontWeight.BOLD, 30));
      grid.add(term, 0, 0, 1, 1);
      //back
      Text deff = new Text("Canis familiaris");
      deff.setFont(Font.font("TimesNewRoman", FontWeight.NORMAL, 15));
      grid.add(deff, 0, 1, 1, 1);
      deff.setVisible(false);
      //button
      Button showBack = new Button("Reveal Answer");
      showBack.setOnAction(act -> {
         isAnswerVisible = !isAnswerVisible;
         deff.setVisible(isAnswerVisible);
         showBack.setText(isAnswerVisible ? "Hide Answer" : "Show Answer");
      });
      grid.add(showBack, 0, 2, 3, 1);
      //show
      primaryStage.show();
   }
}