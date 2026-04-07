package com.socketprogramming.atestat;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.controlsfx.control.Rating;

import java.sql.SQLException;
import java.util.ArrayList;

public class CompanyPageController {

    private MainController mainController;
    private Company company;
    private ArrayList<Review> reviews;

    @FXML
    VBox companyPageVBox;
    @FXML
    HBox companyContainerHBox;
    @FXML
    ScrollPane companyReviewScrollPane;
    @FXML
    VBox companyReviewVBox;


    public void setCompanyPage(MainController mainController, Company company, HBox companyContainer) throws SQLException {

        this.mainController = mainController;

        this.company = company;
        textLabel.setText(company.getName());

        companyContainerHBox.getChildren().addFirst(companyContainer);
        reviews = DatabaseAccess.getCompanyReviews(company.getCompanyID());
        for(Review review : reviews){
            companyReviewVBox.getChildren().add(createReviewContainer(review));
        }
        companyContainer.setOnMouseClicked(null);
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
    };


    @FXML
    Label textLabel;





}
