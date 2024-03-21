package application;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.Test;

public class MainTest {
	String first_name = null;
    boolean is_admin = false;
    String last_name = null;
    String msg = null;
    String token = null;
    String user_name = null;
	@Test
	public void testServer() {
		try {
			URL url = new URL("http://localhost:5002/pizzalgust/test");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");

            connection.setRequestProperty("Content-Type", "application/json");

            connection.setDoOutput(true);

            String name = "Raul";
            
            String jsonInputString = "{\"nombre\": \""+name+"\"}";
            
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            assertEquals(200, responseCode);
            connection.disconnect();
		} catch (Exception e) {
			
		}
	}
	
	@Test
	public void testLoginLogoutHandlers() {
		try {
			URL url = new URL("http://localhost:5002/pizzalgust/login");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");

            connection.setRequestProperty("Content-Type", "application/json");

            connection.setDoOutput(true);

            String email = "pparker@newyork.com";
            String password = "spiderman";
            
            String jsonInputString = "{\"email\": \""+email+"\", \"password\": \""+password+"\"}";
            
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            assertEquals(200, responseCode);
            
            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String line;
                StringBuilder response = new StringBuilder();
                while ((line = br.readLine()) != null) {
                    response.append(line);
                }
                
                String json = response.toString();
                String[] pairs = json.replaceAll("[{}\"]", "").split(",");
                
                for (String pair : pairs) {
                    String[] keyValue = pair.split(":");
                    String key = keyValue[0].trim();
                    String value = keyValue[1].trim();

                    switch (key) {
                        case "first_name":
                            first_name = value;
                            break;
                        case "is_admin":
                            is_admin = Boolean.parseBoolean(value);
                            break;
                        case "last_name":
                            last_name = value;
                            break;
                        case "msg":
                            msg = value;
                            break;
                        case "token":
                            token = value;
                            break;
                        case "user_name":
                            user_name = value;
                            break;
                        default:
                            break;
                    }
                }
            }
            connection.disconnect();
		} catch (Exception e) {
			
		}
		
		try {
			URL url = new URL("http://localhost:5002/pizzalgust/logout");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");

            connection.setRequestProperty("Content-Type", "application/json");

            connection.setDoOutput(true);

            String jsonInputString = "{\"token\": \""+token+"\"}";
            
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            assertEquals(200, responseCode);
            connection.disconnect();
		} catch(Exception errorHandleLogout) {
		}
	}
}