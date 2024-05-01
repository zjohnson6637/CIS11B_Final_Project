import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class StudyZone2 extends Application {

    private static final String API_KEY = "AIzaSyBlkhaalR6dxTwVDsWiMxn0CHPFvBsfY_k";
    private static final String SEARCH_ENGINE_ID = "d415f0d346923497a";

    @Override
    public void start(Stage primaryStage) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Scene scene = new Scene(grid, 400, 275);

        Text scenetitle = new Text("Welcome to 'Study Zone'");
        scenetitle.setId("welcome-text");
        grid.add(scenetitle, 0, 0, 2, 1);

        Label searchTerm = new Label("Search Term:");
        grid.add(searchTerm, 0, 1);

        TextField searchTermField = new TextField();
        grid.add(searchTermField, 1, 1);

        Button searchBtn = new Button("Search");
        grid.add(searchBtn, 1, 2);

        Text searchResults = new Text();
        grid.add(searchResults, 0, 3, 2, 1);

        searchBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String searchQuery = searchTermField.getText();
                if (!searchQuery.isEmpty()) {
                    try {
                        String encodedQuery = URLEncoder.encode(searchQuery, "UTF-8");
                        String url = "https://www.googleapis.com/customsearch/v1?key=" + API_KEY +
                                "&cx=" + SEARCH_ENGINE_ID +
                                "&q=" + encodedQuery +
                                "&num=3" +
                                "&cr=countryUS" +
                                "&hl=en" +
                                "&gl=us" +
                                "&fields=items(title,link,snippet)" +
                                "&maxSnippetLength=-1";

                        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
                        conn.setRequestMethod("GET");

                        int responseCode = conn.getResponseCode();
                        if (responseCode == HttpURLConnection.HTTP_OK) {
                            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                            StringBuilder response = new StringBuilder();
                            String inputLine;
                            while ((inputLine = in.readLine()) != null) {
                                response.append(inputLine);
                            }
                            in.close();

                            JsonObject jsonObject = JsonParser.parseString(response.toString()).getAsJsonObject();
                            JsonArray itemsArray = jsonObject.getAsJsonArray("items");

                            StringBuilder resultText = new StringBuilder();
                            for (JsonElement element : itemsArray) {
                                JsonObject item = element.getAsJsonObject();
                                String title = item.get("title").getAsString();
                                String link = item.get("link").getAsString();
                                String snippet = item.get("snippet").getAsString();

                                resultText.append("Title: ").append(title).append("\n");
                                resultText.append("Link: ").append(link).append("\n");
                                resultText.append("Snippet: ").append(snippet).append("\n\n");
                            }

                            searchResults.setText(resultText.toString());
                        } else {
                            searchResults.setText("Request Failed!: " + responseCode);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        primaryStage.setScene(scene);
        primaryStage.setTitle("Study Zone");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
