/**
 *
 * @author hp
 */
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Registration extends Application {

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Registration Form");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setVgap(10);
        grid.setHgap(10);

        Label nameLabel = new Label("Full Name:");
        GridPane.setConstraints(nameLabel, 0, 0);

        TextField nameInput = new TextField();
        GridPane.setConstraints(nameInput, 1, 0);

        Label usernameLabel = new Label("Username:");
        GridPane.setConstraints(usernameLabel, 0, 1);

        TextField usernameInput = new TextField();
        GridPane.setConstraints(usernameInput, 1, 1);

        Label phoneLabel = new Label("Phone Number:");
        GridPane.setConstraints(phoneLabel, 0, 2);

        TextField phoneInput = new TextField();
        GridPane.setConstraints(phoneInput, 1, 2);

        Label emailLabel = new Label("Email Address:");
        GridPane.setConstraints(emailLabel, 0, 3);

        TextField emailInput = new TextField();
        GridPane.setConstraints(emailInput, 1, 3);

        Label passwordLabel = new Label("Password:");
        GridPane.setConstraints(passwordLabel, 0, 4);

        PasswordField passwordInput = new PasswordField();
        GridPane.setConstraints(passwordInput, 1, 4);

        Label roleLabel = new Label("Role:");
        GridPane.setConstraints(roleLabel, 0, 5);

        ChoiceBox<String> roleChoiceBox = new ChoiceBox<>();
        roleChoiceBox.getItems().addAll("Farm Worker", "Farm Manager", "Accountant");
        roleChoiceBox.setValue("Farm Worker");
        GridPane.setConstraints(roleChoiceBox, 1, 5);

        Button registerButton = new Button("Register");
        GridPane.setConstraints(registerButton, 1, 6);
        registerButton.setOnAction(e -> register(nameInput.getText(), usernameInput.getText(), phoneInput.getText(), emailInput.getText(), passwordInput.getText(), roleChoiceBox.getValue()));

        grid.getChildren().addAll(nameLabel, nameInput, usernameLabel, usernameInput, phoneLabel, phoneInput, emailLabel, emailInput, passwordLabel, passwordInput, roleLabel, roleChoiceBox, registerButton);

        Scene scene = new Scene(grid, 500, 550);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private void register(String name, String username, String phone, String email, String password, String role) {

        System.out.println("Registration successful!");
        System.out.println("Name: " + name);
        System.out.println("Username: " + username);
        System.out.println("Phone Number: " + phone);
        System.out.println("Email Address: " + email);
        System.out.println("Password: " + password);
        System.out.println("Role: " + role);
        
        saveToFile(name, username, phone, email, password, role);

        primaryStage.close();
        showMainPage();
    }

    private void saveToFile(String name, String username, String phone, String email, String password, String role) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("registration_data3.txt", true))) {
            writer.write("Name: " + name + "\n");
            writer.write("Username: " + username + "\n");
            writer.write("Phone Number: " + phone + "\n");
            writer.write("Email Address: " + email + "\n");
            writer.write("Password: " + password + "\n");
            writer.write("Role: " + role + "\n\n");
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Unable to save registration information to file.");
        }
    }

    private void showMainPage() {
        Main mainPage = new Main();
        mainPage.start(new Stage());
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
