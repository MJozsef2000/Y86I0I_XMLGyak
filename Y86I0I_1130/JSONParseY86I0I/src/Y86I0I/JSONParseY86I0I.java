package Y86I0I;

import java.text.ParseException;
import java.util.Iterator;
import java.util.Map;
import java.io.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JSONParseY86I0I {

	public static void main(String[] args) throws ParseException, FileNotFoundException, IOException, org.json.simple.parser.ParseException {
        
        Object obj = new JSONParser().parse(new FileReader("vizsgakJSONY86I0I.json"));
        JSONObject jo = (JSONObject) obj;
        try {
            FileWriter myWriter = new FileWriter("JSONWRITER.txt");
            JSONArray ja = (JSONArray) jo.get("vizsgak");
            System.out.println(ja);
		    Iterator itr = ja.iterator();
	          
		    while (itr.hasNext()) 
	        {
		    	Iterator<Map.Entry> itr1 = ((Map) itr.next()).entrySet().iterator();
	            while (itr1.hasNext()) {
	                Map.Entry pair = itr1.next();
	                myWriter.write(pair.getKey() + " : " + pair.getValue());
	                myWriter.write("\n");
	            }
	        }
		    myWriter.close();
        } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		}

	}
}