package Y86I0I;
import java.io.FileReader;
import java.util.Iterator;
import java.util.Map;
  
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;
  
public class ObjectY86I0I
{
    public static void main(String[] args) throws Exception 
    {
        Object obj = new JSONParser().parse(new FileReader("JSONY86I0I.json"));
          
        JSONObject jo = (JSONObject) obj;
        System.out.println(jo);
    }
}