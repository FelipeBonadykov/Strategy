package Web;

import static Constants.Constants.URL;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.JOptionPane;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class WebConnector {
	
	public static void sendInfoToServer(String key, String value) {
		// sends a json file to server (when the local player moved a figure)
		try {
			URL url = new URL(URL.getDirection());
			URLConnection urlCon = url.openConnection();
			HttpURLConnection http = (HttpURLConnection)urlCon;
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
			URL url2 = new URL(URL.getDirection());
			BufferedReader br = new BufferedReader(new InputStreamReader(url2.openConnection().getInputStream()));
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
		WebConnector.sendInfoToServer("map", "none");
		WebConnector.sendInfoToServer("first", "none");
		WebConnector.sendInfoToServer("second", "none");
	}
	
}
