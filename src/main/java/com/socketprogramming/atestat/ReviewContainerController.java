package com.socketprogramming.atestat;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.time.format.DateTimeFormatter;

public class ReviewContainerController {

    public Review getReview() {
        return review;
    }
    public void setReview(Review review) {
        this.review = review;
    }
    public CompanyPageController getCompanyPageController() {
        return companyPageController;
    }
    public void setCompanyPageController(CompanyPageController companyPageController) {
        this.companyPageController = companyPageController;
    }

    private Review review;
    private User user;
    private CompanyPageController companyPageController;
    private MainController mainController;

    @FXML VBox reviewItemContainer;
    @FXML Label usernameLabel;
    @FXML Label ratingLabel;
    @FXML Label reviewTextLabel;
    @FXML Label reviewDateLabel;

    public void setReviewContainer(Review review, CompanyPageController companyPageController, MainController mainController){
        if(review.isAnonymous()){
            usernameLabel.setText("Anonymous user");
        }
        else{
            usernameLabel.setText(review.getUserName());
        }
        ratingLabel.setText("Rating: " + review.getRating());
        reviewTextLabel.setText(review.getReviewText());
        reviewDateLabel.setText("Date: " + review.getReviewDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
    }

}

