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
public class StudyZone extends Application {
   public static void main(String[] args) {
      launch(args);
   }
   //
   @Override
   public void start(Stage primaryStage) {
/*
//***PROBABLY DELETE LATER***
      Label welcomeLabel = new Label("Welcome to 'Study Zone'");
      //
      HBox hbox = new HBox(welcomeLabel);
      //
      Scene scene = new Scene(hbox);
      //
      primaryStage.setScene(scene);
      //
      //Make button
      Button okBtn = new Button();
      okBtn.setText("OK");
      okBtn.setOnAction(new EventHandler<ActionEvent>() {
         @Override
         public void handle(ActionEvent event) {
            System.out.println("This button will eventualy make the window close");
         }
      });
      
      StackPane root = new StackPane();
      root.getChildren().add(okBtn);
      primaryStage.setScene(new Scene(root, 300, 250));
      //
      primaryStage.setTitle("Welcome to 'Study Zone'");
      //
      primaryStage.show();
      
*/
      
      // make grid
      GridPane grid = new GridPane();
      grid.setAlignment(Pos.CENTER);
      grid.setHgap(10);
      grid.setVgap(10);
      grid.setPadding(new Insets(25, 25, 25, 25));
      Scene scene2 = new Scene(grid, 300,275);
      primaryStage.setScene(scene2);
      //
      //add content
      //welcome
      Text scenetitle = new Text("Welcome to 'Study Zone'");
      scenetitle.setFont(Font.font("TimesNewRoman", FontWeight.NORMAL, 20));
      grid.add(scenetitle, 0, 0, 2, 1);
      //
      //search box label
      Label searchTerm = new Label("Search Term:");
      grid.add(searchTerm, 0, 1);
      //search box
      TextField searchTermField = new TextField();
      grid.add(searchTermField, 1, 1);
      //
      //search button
      Button search = new Button("Search");
      HBox hbSearch = new HBox(10);
      hbSearch.setAlignment(Pos.BOTTOM_LEFT);
      hbSearch.getChildren().add(search);
      grid.add(hbSearch, 1, 4);
      //add action to search button
      search.setOnAction(new EventHandler<ActionEvent>() {
         @Override
         public void handle(ActionEvent event) {
            System.out.println("This will eventualy give input to the API");
         }
      });
      //
      // show screen
      primaryStage.show();
   }
}