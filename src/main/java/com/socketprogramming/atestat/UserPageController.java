package com.socketprogramming.atestat;

import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.controlsfx.control.Rating;

import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserPageController {

    @FXML
    VBox userPageVBox;
    @FXML
    HBox userContainerHBox;
    @FXML
    VBox leftUserContainerVBox;
    @FXML
    ImageView leftUserContainerImageView;
    @FXML
    VBox rightUserContainerVBox;
    @FXML
    ScrollPane userReviewScrollPane;
    @FXML
    VBox userReviewVBox;

    MainController mainController;
    User user;
    ArrayList<Review> reviews;

    public void setUserPage(MainController mainController, User user) throws SQLException, MalformedURLException {

        this.mainController = mainController;
        this.user = user;

        rightUserContainerVBox.getChildren().addAll(
                new Label("User Name: " +  user.getName()),
                new Label("User Join Date: " + user.getJoinDate())
        );

        reviews = DatabaseAccess.getUserReviews(user.getID());
        for(Review review : reviews){
            userReviewVBox.getChildren().add(createReviewContainer(review));
        }

    }

    private VBox createReviewContainer(Review review){

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
    }


}
