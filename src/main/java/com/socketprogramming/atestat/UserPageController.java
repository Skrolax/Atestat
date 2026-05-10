package com.socketprogramming.atestat;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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


    MainController mainController;
    User user;
    ArrayList<Review> reviews;
    FileChooser fileChooser;
    byte[] imageByte;

    @FXML
    public void changeProfileImage() throws IOException {
        File image = fileChooser.showOpenDialog(new Stage());
        imageByte = PhotoManager.fileToByte(image);
        if(imageByte != null) {
            Platform.runLater(() -> {
                try {
                    DatabaseAccess.updateUserPhoto(user.getID(), imageByte);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
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
    }

    public void setUserPage(MainController mainController, User user) throws SQLException {

        this.mainController = mainController;
        this.user = user;
        leftUserContainerImageView.setImage(PhotoManager.getImage(DatabaseAccess.getUserPhoto(user.getID())));

    }

   /* private VBox createReviewContainer(Review review){

        VBox mainContainer = new VBox();
        HBox topContainer = new HBox();
        Label reviewText = new Label(review.getReviewText());
        ImageView userPhoto = new ImageView();
        Label userName;
        Rating rating;

        if(review.isAnonymous()){
            userName = new Label("Anonymous user");
            rating = new Rating();

        }
        else {
            userName = new Label(review.getUserName());
            rating = new Rating();
        }
        Label timeDate = new Label(review.getReviewDateTime().toString());

        topContainer.getChildren().addAll(userPhoto, userName, rating, timeDate);

        mainContainer.getChildren().addAll(topContainer, reviewText);

        return mainContainer;
    }*/


}
