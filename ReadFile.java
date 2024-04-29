import java.io.*;
//
public class ReadFile {
   public ReadFile(){}
   public void returnResults() throws Exception {
      File results = new File("search_results.txt");
      BufferedReader br = new BufferedReader(new FileReader(results));
      String line;
      while ((line = br.readLine()) != null) {
         System.out.println(line);
      }
      br.close();
   }
}