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
            return;
        }

        statusUpdateLabel.setText("");
        accountExists = DatabaseAccess.checkIfEmailExists(emailField.getText());

        // 1. Clean the container of all optional fields first to prevent duplicates
        mainContainer.getChildren().removeAll(
                initialLoginButton, usernameField, passwordField,
                reenterpasswordField, loginButton, registerButton, statusUpdateLabel
        );

        if(accountExists){
            loginLabel.setText("Login");
            // 2. Add only what is needed for Login
            mainContainer.getChildren().addAll(passwordField, loginButton, statusUpdateLabel);

            passwordField.setOnAction(event -> {
                try { login(); } catch (Exception e) { e.printStackTrace(); }
            });
            passwordField.requestFocus();
        }
        else {
            loginLabel.setText("Register");
            // 3. Add only what is needed for Register
            mainContainer.getChildren().addAll(usernameField, passwordField, reenterpasswordField, registerButton, statusUpdateLabel);

            passwordField.setOnAction(event -> reenterpasswordField.requestFocus());
            usernameField.requestFocus();
        }
    }

    @FXML
    public void register() throws SQLException, IOException {
        String email = emailField.getText();
        String password = passwordField.getText();
        String username = usernameField.getText();
        String reenter = reenterpasswordField.getText();

        if (!EmailValidator.isValid(email)) {
            statusUpdateLabel.setText("Invalid email format!");
            emailField.requestFocus();
            return;
        }
        if (username.isEmpty()) {
            statusUpdateLabel.setText("Must enter a username!");
            usernameField.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            statusUpdateLabel.setText("Must enter a password!");
            passwordField.requestFocus();
            return;
        }
        if (!password.equals(reenter)) {
            statusUpdateLabel.setText("Passwords are not the same");
            reenterpasswordField.requestFocus();
            return;
        }

        // Hash the password here or inside DatabaseAccess.registerUser
        user = DatabaseAccess.registerUser(email, password, username);
        if (user != null) {
            loadMainApp(user);
        } else {
            statusUpdateLabel.setText("Registration failed.");
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

        // COMBINED: Check email and move focus/change UI
        emailField.setOnAction(actionEvent -> {
            try {
                if(!emailField.getText().isEmpty()){
                    initialLogin();
                }
            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }
        });

        // These can stay here, they just won't do anything until the fields are added to the scene
        usernameField.setOnAction(e -> passwordField.requestFocus());
        passwordField.setOnAction(e -> reenterpasswordField.requestFocus());

        // For the login flow (password -> login)
        // We handle this inside initialLogin to avoid conflicts

        reenterpasswordField.setOnAction(e -> {
            try {
                register();
            } catch (SQLException | IOException ex) {
                ex.printStackTrace();
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
