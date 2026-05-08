package com.socketprogramming.atestat;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import javax.swing.*;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class AddReviewContainerController implements Initializable {

    @FXML TextArea addReviewTextArea;
    @FXML Button closeButton;

    private CompanyPageController companyPageController;
    private MainController mainController;
    private Button addReviewButton;

    public void setControllers(CompanyPageController companyPageController, MainController mainController){
        this.companyPageController = companyPageController;
        this.mainController = mainController;
        addReviewButton = companyPageController.addReviewButton;
        companyPageController.reviewTitleHBox.getChildren().removeLast();
    }

    @FXML
    public void closeReviewContainer(){
        companyPageController.companyReviewVBox.getChildren().removeFirst();
        companyPageController.reviewTitleHBox.getChildren().add(addReviewButton);
    }

    @FXML
    public void createReview() throws SQLException {
        if(addReviewTextArea.getText().isEmpty()){
            return;
        }
        companyPageController.companyReviewVBox.getChildren().removeFirst();
        DatabaseAccess.addReview(
                mainController.getUser().getID(),
                companyPageController.getCompany().getCompanyID(),
                addReviewTextArea.getText(),
                4.0F,
                true
        );
        Review review = DatabaseAccess.getCompanyReviews(companyPageController.getCompany().getCompanyID()).getLast();
        companyPageController.loadReviewContainer(review);
        companyPageController.reviewTitleHBox.getChildren().add(addReviewButton);
    }

    /*Review(int reviewID, int userID, String userName, int companyID, String companyName, String reviewText, float rating, boolean isAnonymous, LocalDateTime reviewDateTime, byte[] photo){
        this.reviewID = reviewID;
        this.userID = userID;
        this.userName = userName;
        this.companyID = companyID;
        this.companyName = companyName;
        this.reviewText = reviewText;
        this.rating = rating;
        this.isAnonymous = isAnonymous;
        this.userPhoto = photo;
        this.reviewDateTime = reviewDateTime;
    }*/

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addReviewTextArea.setWrapText(true);
        addReviewTextArea.textProperty().addListener((observable, oldValue, newValue) -> {
            int rowCount = newValue.split("\r\n|\r|\n", -1).length;
            addReviewTextArea.setPrefHeight(rowCount * 25 + 20);
        });
    }
}
