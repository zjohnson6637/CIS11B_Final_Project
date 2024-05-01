import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;


public class NewFile {
   public void list(String listName) {
      try (FileWriter writer = new FileWriter(listName)) {
         writer.write("this is the second file");
      } catch (IOException ex) {
         ex.printStackTrace();
         System.out.println("Error saving data!");
      }
   }
}