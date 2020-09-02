package Web;

import static Constants.Constants.URL;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.swing.JOptionPane;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

@SuppressWarnings("unchecked")
public class WebConnector {
	
	public static void sendInfoToServer(String key, Object value) {
		// sends a json file to server (when the local player moved a figure)
		try {
			HttpURLConnection http = (HttpURLConnection) new URL(URL.getDirection()).openConnection();
			http.setRequestMethod("POST");
	        http.setDoOutput(true);
	        
	        JSONObject jsonObj = new JSONObject();
		    jsonObj.put(key, value);
		    
		    var jsonBytes = jsonObj.toJSONString().getBytes("utf-8");
	        http.setFixedLengthStreamingMode(jsonBytes.length);
	        http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
	        http.connect();
	        try(OutputStream os = http.getOutputStream()) {
	            os.write(jsonBytes);
	        }		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static JSONObject getInfoFromServer() {
		// gets a json object from server (constant update)
		String jsonStr = "";
		try {
			// get json string from server
			BufferedReader br = new BufferedReader(new InputStreamReader(new URL(URL.getDirection()).openConnection().getInputStream()));
			jsonStr = br.readLine();		
			// parse and return json object 
			JSONParser jsonParser = new JSONParser();
			
			return (JSONObject) jsonParser.parse(jsonStr);		
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showOptionDialog(null, "No internet access",
					"SYSTEM WARNING", JOptionPane.DEFAULT_OPTION, 
					JOptionPane.WARNING_MESSAGE, null, null, null);
		}		
		return null;
	}

	public static void resetServer() {
		try {
			// preparing connection
			HttpURLConnection http = (HttpURLConnection) new URL(URL.getDirection()).openConnection();
			http.setRequestMethod("POST");
	        http.setDoOutput(true);
	        
	        //writing json
	        JSONObject jsonObj = new JSONObject();
		    jsonObj.put("map", "none");
		    jsonObj.put("first", "none");
		    jsonObj.put("second", "none");
		    jsonObj.put("turn", "none");
		    
		    // sending to server
		    var jsonBytes = jsonObj.toJSONString().getBytes("utf-8");
	        http.setFixedLengthStreamingMode(jsonBytes.length);
	        http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
	        http.connect();
	        try(OutputStream os = http.getOutputStream()) {
	            os.write(jsonBytes);
	        }		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
