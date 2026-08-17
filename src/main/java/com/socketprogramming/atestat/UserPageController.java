package com.socketprogramming.atestat;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class UserPageController implements Initializable {

    @FXML VBox userPageVBox;
    @FXML Label userNameLabel;
    @FXML HBox userContainerHBox;
    @FXML VBox leftUserContainerVBox;
    @FXML ImageView leftUserContainerImageView;
    @FXML Button changePhotoButton;
    @FXML TextField changeUsernameTextField;
    @FXML PasswordField changePasswordPasswordField;
    @FXML PasswordField reenterPasswordChangePasswordField;
    @FXML Button saveChangesButton;
    @FXML HBox reviewTitleHBox;
    @FXML Label reviewTitleLabel;
    @FXML ScrollPane userReviewScrollPane;
    @FXML VBox userReviewVBox;
    @FXML CheckBox togglePasswordCheckBox;
    @FXML CheckBox toggleRePasswordCheckBox;
    @FXML TextField toggledPasswordTextField;
    @FXML TextField toggledRePasswordTextField;
    @FXML HBox enterPasswordHBox;
    @FXML HBox reenterPasswordHBox;
    @FXML Label updateStatusLabel;

    MainController mainController;
    User user;
    ArrayList<Review> reviews;
    FileChooser fileChooser;
    byte[] imageByte;
    ArrayList<Review> userReviews;

    @FXML
    public void saveChanges() throws SQLException {
        boolean changeMade = false;
        if(imageByte != null) {
            try {
                DatabaseAccess.updateUserPhoto(user.getID(), imageByte);
                user.setPhoto(imageByte);
                changeMade = true;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if(!changeUsernameTextField.getText().isEmpty()){
            DatabaseAccess.updateUserName(user.getID(), changeUsernameTextField.getText());
            user.setName(changeUsernameTextField.getText());
            mainController.userProfileButton.setText(user.getName());
            userNameLabel.setText(user.getName() + "`s Profile Page");
            changeUsernameTextField.clear();
            changeMade = true;
        }
        if(!changePasswordPasswordField.getText().isEmpty() || !reenterPasswordChangePasswordField.getText().isEmpty()){
            if(!Objects.equals(changePasswordPasswordField.getText(), reenterPasswordChangePasswordField.getText())){
                updateStatusLabel.setText("Password don't match!");
                return;
            }
            DatabaseAccess.updateUserPassword(user.getID(), BCrypt.hashpw(changePasswordPasswordField.getText(), BCrypt.gensalt(12)));
            changePasswordPasswordField.clear();
            reenterPasswordChangePasswordField.clear();
            changeMade = true;
        }
        if(changeMade){
            updateStatusLabel.setText("Changes saved!");
        }
    }

    @FXML
    public void changeProfileImage() throws IOException {
        File image = fileChooser.showOpenDialog(new Stage());
        imageByte = PhotoManager.fileToByte(image);
        if(imageByte != null) {
            Platform.runLater(() -> {
                leftUserContainerImageView.setImage(PhotoManager.getImage(imageByte));
            });
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        fileChooser = new FileChooser();
        fileChooser.setTitle("Choose an image");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        toggledPasswordTextField.textProperty().bindBidirectional(changePasswordPasswordField.textProperty());
        toggledRePasswordTextField.textProperty().bindBidirectional(reenterPasswordChangePasswordField.textProperty());

        toggledPasswordTextField.visibleProperty().bind(togglePasswordCheckBox.selectedProperty());
        changePasswordPasswordField.visibleProperty().bind(togglePasswordCheckBox.selectedProperty().not());

        toggledRePasswordTextField.visibleProperty().bind(toggleRePasswordCheckBox.selectedProperty());
        reenterPasswordChangePasswordField.visibleProperty().bind(toggleRePasswordCheckBox.selectedProperty().not());

        toggledPasswordTextField.managedProperty().bind(toggledPasswordTextField.visibleProperty());
        changePasswordPasswordField.managedProperty().bind(changePasswordPasswordField.visibleProperty());

        toggledRePasswordTextField.managedProperty().bind(toggledRePasswordTextField.visibleProperty());
        reenterPasswordChangePasswordField.managedProperty().bind(reenterPasswordChangePasswordField.visibleProperty());

    }

    public void setUserPage(MainController mainController, User user) throws SQLException {

        this.mainController = mainController;
        this.user = user;
        leftUserContainerImageView.setImage(PhotoManager.getImage(DatabaseAccess.getUserPhoto(user.getID())));
        userNameLabel.setText(user.getName() + "`s Profile Page");

        userReviews = DatabaseAccess.getUserReviews(user.getID());
        for(Review review : userReviews){
            loadReviewContainer(review);
        }

    }

    public void loadReviewContainer(Review review){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("review_container.fxml"));
        try {
            Parent view = loader.load();
            ReviewContainerController reviewContainerController = loader.getController();
            reviewContainerController.setReviewContainer(review, this, this.mainController);
            userReviewVBox.getChildren().add(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
