package com.socketprogramming.atestat;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.controlsfx.control.Rating;

import javax.swing.*;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;


public class AddReviewContainerController implements Initializable {

    @FXML TextArea addReviewTextArea;
    @FXML Button closeButton;
    @FXML Rating ratingElement;
    @FXML Label reviewStatusLabel;
    @FXML CheckBox isAnonymousCheckBox;

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
            reviewStatusLabel.setText("Cannot send an empty review.");
            return;
        }
        if(ratingElement.getRating() == 0){
            reviewStatusLabel.setText("You must rate the company!");
            return;
        }
        if(addReviewTextArea.getText().isEmpty() && ratingElement.getRating() == 0){
            reviewStatusLabel.setText("Must enter a review and a rating.");
            return;
        }
        companyPageController.companyReviewVBox.getChildren().removeFirst();
        DatabaseAccess.addReview(
                mainController.getUser().getID(),
                companyPageController.getCompany().getCompanyID(),
                addReviewTextArea.getText(),
                (float) ratingElement.getRating(),
                isAnonymousCheckBox.isSelected()
        );
        Review review = DatabaseAccess.getCompanyReviews(companyPageController.getCompany().getCompanyID()).getLast();
        companyPageController.leftContainerRatingLabel.setText("TOTAL RATING: " + String.format("%.1f", DatabaseAccess.getCompanyTotalRating(companyPageController.getCompany().getCompanyID())));
        companyPageController.loadReviewContainer(review);
        companyPageController.reviewTitleHBox.getChildren().add(addReviewButton);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ratingElement.setRating(0);
        ratingElement.setMax(5);
        addReviewTextArea.setWrapText(true);
        addReviewTextArea.textProperty().addListener((observable, oldValue, newValue) -> {
            int rowCount = newValue.split("\r\n|\r|\n", -1).length;
            addReviewTextArea.setPrefHeight(rowCount * 25 + 20);
        });
        addReviewTextArea.setTextFormatter(new TextFormatter<String>(change ->
                change.getControlNewText().length() <= 400 ? change : null));
    }
}
