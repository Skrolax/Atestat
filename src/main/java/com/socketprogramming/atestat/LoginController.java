package com.socketprogramming.atestat;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    private boolean accountExists;
    private User user;

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }


    @FXML VBox mainContainer;
    @FXML Label loginLabel;
    @FXML TextField emailField;
    @FXML TextField usernameField;
    @FXML PasswordField passwordField;
    @FXML TextField reenterpasswordField;
    @FXML Button initialLoginButton;
    @FXML Button loginButton;
    @FXML Button registerButton;
    @FXML Label statusUpdateLabel;

    @FXML
    public void initialLogin() throws SQLException, IOException {
        if(!EmailValidator.isValid(emailField.getText())){
            statusUpdateLabel.setText("Invalid email format!");
            System.out.println("da");
            return;
        }
        statusUpdateLabel.setText("");
        mainContainer.getChildren().remove(statusUpdateLabel);
        accountExists = DatabaseAccess.checkIfEmailExists(emailField.getText());
        if(accountExists){
            loginLabel.setText("Login");
            mainContainer.getChildren().remove(initialLoginButton);
            mainContainer.getChildren().addAll(passwordField, loginButton, statusUpdateLabel);
            emailField.setOnAction(event -> passwordField.requestFocus());
            passwordField.requestFocus();
            passwordField.setOnAction(event -> {
                try {
                    login();
                } catch (SQLException | IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        else{
            loginLabel.setText("Register");
            mainContainer.getChildren().remove(initialLoginButton);
            mainContainer.getChildren().addAll(usernameField, passwordField, reenterpasswordField, registerButton, statusUpdateLabel);
        }
    }

    @FXML
    public void register() throws SQLException, IOException {
        if(DatabaseAccess.checkIfEmailExists(emailField.getText())){
            login();
        }
        else{
            if(!EmailValidator.isValid(emailField.getText())){
                statusUpdateLabel.setText("Invalid email format!");
                return;
            }
            if(!Objects.equals(passwordField.getText(), reenterpasswordField.getText())){
                statusUpdateLabel.setText("Passwords are not the same");
                reenterpasswordField.requestFocus();
                return;
            }
            if(passwordField.getText().isEmpty()){
                statusUpdateLabel.setText("Must enter a password!");
                return;
            }
            if(usernameField.getText().isEmpty()){
                statusUpdateLabel.setText("Must enter an username!");
                return;
            }
            user = DatabaseAccess.registerUser(emailField.getText(), passwordField.getText(), usernameField.getText());
            loadMainApp(user);
        }
    }

    @FXML
    public void login() throws SQLException, IOException {
        if(!EmailValidator.isValid(emailField.getText())){
            statusUpdateLabel.setText("Invalid email format!");
            return;
        }
        if(DatabaseAccess.checkIfEmailExists(emailField.getText())){
            user = DatabaseAccess.attemptLogin(emailField.getText(), passwordField.getText());
            if (user != null) {
                loadMainApp(user);
            } else {
                statusUpdateLabel.setText("Wrong password.");
                passwordField.clear();
            }
        }
        else{
            register();
        }
    }

    public void loadMainApp(User user) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main.fxml"));
        Parent view = fxmlLoader.load();
        Stage mainStage = (Stage) loginLabel.getScene().getWindow();
        MainController mainController = fxmlLoader.getController();
        mainController.setUser(user);
        mainController.userProfileButton.setText(user.getName());
        Scene scene = new Scene(view);
        mainStage.setScene(scene);
        mainStage.setResizable(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loginLabel.setText("Enter your email");
        initialLoginButton.setText("Check");
        emailField.setOnAction(actionEvent -> {
            try {
                if(emailField.getText().isEmpty()){
                    return;
                }
                initialLogin();
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        });
        mainContainer.getChildren().removeAll(usernameField, passwordField, reenterpasswordField, loginButton, registerButton);
        try {
            DatabaseAccess.startConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
