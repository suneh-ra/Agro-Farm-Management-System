
package login1;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Login1 extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Login Page");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(50, 50, 50, 50));
        grid.setVgap(10);
        grid.setHgap(10);

        Label usernameLabel = new Label("Username:");
        GridPane.setConstraints(usernameLabel, 0, 0);

        TextField usernameInput = new TextField();
        GridPane.setConstraints(usernameInput, 1, 0);

        Label passwordLabel = new Label("Password:");
        GridPane.setConstraints(passwordLabel, 0, 1);

        PasswordField passwordInput = new PasswordField();
        GridPane.setConstraints(passwordInput, 1, 1);

        Label roleLabel = new Label("Select Role:");
        GridPane.setConstraints(roleLabel, 0, 2);

        ChoiceBox<String> roleChoiceBox = new ChoiceBox<>();
        roleChoiceBox.getItems().addAll("Farm Worker", "Farm Manager", "Accountant");
        roleChoiceBox.setValue("Farm Worker");
        GridPane.setConstraints(roleChoiceBox, 1, 2);

        Button loginButton = new Button("Login");
        GridPane.setConstraints(loginButton, 1, 3);
        loginButton.setOnAction(e -> login(usernameInput.getText(), passwordInput.getText(), roleChoiceBox.getValue(), primaryStage));

        grid.getChildren().addAll(usernameLabel, usernameInput, passwordLabel, passwordInput, roleLabel, roleChoiceBox, loginButton);

        Scene scene = new Scene(grid, 400, 400);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private void login(String username, String password, String role, Stage primaryStage) {
    boolean loginSuccessful = checkLoginCredentials(username, password, role);

    if (loginSuccessful) {
        System.out.println("Login successful!");
        switch (role) {
            case "Farm Worker":
                new WorkerBoard(primaryStage).showDashboard();
                break;
            case "Farm Manager":
                 new ManagerBoard(primaryStage).showDashboard();
                break;
            case "Accountant":
                 new AccountantBoard(primaryStage).showDashboard();
                break;
        }
    } else {
        System.out.println("Login failed!");
        showAlert("Login Failed", "Invalid username, password, or role.");
    }
}

    
    private boolean checkLoginCredentials(String username, String password, String role) {
    String filePath = "registration_data3.txt";

    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
        String line;
        boolean foundUsername = false;
        boolean foundPassword = false;
        boolean foundRole = false;
        
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(": ");
            if (parts.length == 2) {
                String key = parts[0].trim();
                String value = parts[1].trim();
                
                if (key.equals("Username") && value.equals(username)) {
                    foundUsername = true;
                }
                
                if (foundUsername && key.equals("Password") && value.equals(password)) {
                    foundPassword = true;
                }
                
                if (foundPassword && key.equals("Role") && value.equals(role)) {
                    foundRole = true;
                }
            }
        }
        
        return foundUsername && foundPassword && foundRole;
        
    } catch (IOException e) {
        e.printStackTrace();
        
    }

    return false;
}

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
