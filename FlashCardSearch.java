/*This is the API in usage to be later implemented with the flash card program */

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class FlashCardSearch {

    private static final String API_KEY = "AIzaSyBlkhaalR6dxTwVDsWiMxn0CHPFvBsfY_k";
    private static final String SEARCH_ENGINE_ID = "d415f0d346923497a";
    private static final String SEARCH_QUERY = "Dog"; // Search query here

    public static void main(String[] args) throws IOException {
        String encodedQuery = URLEncoder.encode(SEARCH_QUERY, "UTF-8"); // Encode the search query

        String url = "https://www.googleapis.com/customsearch/v1?key=" + API_KEY +
            "&cx=" + SEARCH_ENGINE_ID +
            "&q=" + encodedQuery +
            "&num=3" +
            "&cr=countryUS" +
            "&hl=en" +
            "&gl=us" +
            "&fields=items(title,link,snippet)" +
            "&maxSnippetLength=-1"; // Use the encoded search query in the URL and set maxSnippetLength to -1 for maximum length

        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setRequestMethod("GET");

        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            String jsonResponse = response.toString();

            // Parse JSON using Gson
            JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();

            // Get the "items" array
            JsonArray itemsArray = jsonObject.getAsJsonArray("items");

            // Writing to a text file
            BufferedWriter writer = new BufferedWriter(new FileWriter("search_results.txt"));

            // Limit the loop to iterate over the first 3 items
            int count = 0;
            for (JsonElement element : itemsArray) {
                if (count >= 3) break; // Stop iterating after 3 items
                JsonObject item = element.getAsJsonObject();
                String title = item.get("title").getAsString();
                String link = item.get("link").getAsString();
                String snippet = item.get("snippet").getAsString();

                // Write the title, link, and snippet to the file
                writer.write("Title: " + title + "\n");
                writer.write("Link: " + link + "\n");
                writer.write("Snippet: " + snippet + "\n\n");
                count++;
            }
            writer.close(); // Close the writer
        } else {
            System.out.println("Request Failed!: " + responseCode);
        }
    }
}
