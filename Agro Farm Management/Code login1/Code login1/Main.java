/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package login1;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Registration or Login");

        VBox vbox = new VBox(30);
        vbox.setAlignment(Pos.CENTER); 
        vbox.setPadding(new Insets(40));
        
    Label titleLabel = new Label("Agro Farm Management System");
    titleLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: #0000FF;"); 

        Button registerButton = new Button("Registration");
        registerButton.setStyle("-fx-font-size: 16px;"); 
        registerButton.setPrefWidth(150); 
        registerButton.setOnAction(e -> {
            Registration registration = new Registration();
            registration.start(new Stage());
        });

        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-font-size: 16px;"); 
        loginButton.setPrefWidth(150); 
        loginButton.setOnAction(e -> {
            Login1 login = new Login1();
            login.start(new Stage());
        });
        vbox.getChildren().addAll(titleLabel, registerButton, loginButton);

        Scene scene = new Scene(vbox, 500, 550);
        primaryStage.setScene(scene);

        primaryStage.show();
    }
   public static void main(String[] args) {
        launch(args);
    }
}
