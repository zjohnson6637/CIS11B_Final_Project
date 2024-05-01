import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class FlashCardSearch {

    private static final String API_KEY = "AIzaSyBlkhaalR6dxTwVDsWiMxn0CHPFvBsfY_k";
    private static final String SEARCH_ENGINE_ID = "d415f0d346923497a";

    public static String searchAndRetrieveSnippet(String searchTerm) throws IOException {
        String encodedQuery = URLEncoder.encode(searchTerm, "UTF-8");

        String url = "https://www.googleapis.com/customsearch/v1?key=" + API_KEY +
                "&cx=" + SEARCH_ENGINE_ID +
                "&q=" + encodedQuery +
                "&num=1" + // Limit to 1 result (snippet)
                "&cr=countryUS" +
                "&hl=en" +
                "&gl=us" +
                "&fields=items(snippet)"; // Only retrieve snippet from search result

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

            return parseSnippet(jsonResponse); // Return the retrieved snippet
        } else {
            System.out.println("Search request failed: " + responseCode);
            return "Search request failed. Please try again.";
        }
    }

    private static String parseSnippet(String jsonResponse) {
        JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
        JsonArray items = jsonObject.getAsJsonArray("items");
        if (items != null && items.size() > 0) {
            JsonObject firstItem = items.get(0).getAsJsonObject();
            if (firstItem.has("snippet")) {
                return firstItem.get("snippet").getAsString();
            }
        }
        return "No snippet available"; // Default message if no snippet found
    }
}
