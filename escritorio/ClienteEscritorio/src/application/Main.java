package application;
	
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;


public class Main extends Application {
	String first_name = null;
    boolean is_admin = false;
    String last_name = null;
    String msg = null;
    String token = null;
    String user_name = null;
    
    @FXML
    VBox loginScreen;
    
    @FXML
    VBox sessionProfile;
    
	@FXML
	TextField usernameField;
	
	@FXML
	TextField passwordField;
	
	@FXML
	TextField nameField;
	
	@FXML
	TextField lastnameField;
	
	@FXML
	Label errorLabel;
	
	@FXML
	Label userLabel;
	
	@FXML
	Label isAdminText;
	
	/**
	 * Aquesta classe executa l'entorn gràfic del programa
	 * @author Raul Luque Craciun
	 * @param primaryStage L'escena que s'executarà
	 */
	@Override
	public void start(Stage primaryStage) {
		
		try {
			
			
			Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
			Scene scene = new Scene(root,800,600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			primaryStage.setTitle("PizzaLgust");
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch(Exception errorStart) {
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	/**
	 * Aquest mètode controla el login al clicar al botó de login
	 * @author Raul Luque Craciun
	 * @param e ActionEvent
	 */
	public void handleLogin(ActionEvent e) {
		try {
            URL url = new URL("http://localhost:5002/pizzalgust/login");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");

            connection.setRequestProperty("Content-Type", "application/json");

            connection.setDoOutput(true);

            String jsonInputString = "{\"email\": \""+usernameField.getText()+"\", \"password\": \""+passwordField.getText()+"\"}";

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            System.out.println("Login Code: " + responseCode);
            
            if (responseCode == 410) {
            	errorLabel.setText("Email inexistent o incorrecte");
            	usernameField.setText("");
            	passwordField.setText("");
            } else if (responseCode == 401) {
            	errorLabel.setText("Contrasenya incorrecta");
            	passwordField.setText("");
            }

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
                
                
                System.out.println("First Name: " + first_name);
                System.out.println("Last Name: " + last_name);
                System.out.println("User Name: " + user_name);
                System.out.println("Is Admin: " + is_admin);
                System.out.println("Message: " + msg);
                System.out.println("Token: " + token);
                
                userLabel.setText(user_name);
                nameField.setText(first_name);
                lastnameField.setText(last_name);
                
                if (is_admin) {
                	isAdminText.setVisible(true);
                } else {
                	isAdminText.setVisible(false);
                }
                
                passwordField.setText("");
                
                loginScreen.setVisible(false);
                sessionProfile.setVisible(true);
            }

            connection.disconnect();
        } catch (Exception errorHandleLogin) {
        }
	}
	
	/**
	 * Aquest mètode controla el logout al clicar el botó de logout
	 * @author Raul Luque Craciun
	 * @param e ActionEvent
	 */
	public void handleLogout(ActionEvent e) {
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
            System.out.println("Logout Code: " + responseCode);
            
            loginScreen.setVisible(true);
            sessionProfile.setVisible(false);
            
            connection.disconnect();
		} catch(Exception errorHandleLogout) {
		}
	}
}
