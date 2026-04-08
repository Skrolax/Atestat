package com.socketprogramming.atestat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.controlsfx.control.Rating;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.ArrayList;

public class CompanyPageController {

    private MainController mainController;
    private Company company;
    private ArrayList<Review> reviews;
    private UserPageController userPageController;

    @FXML
    VBox companyPageVBox;
    @FXML
    HBox companyContainerHBox;
    @FXML
    VBox leftCompanyContainerVBox;
    @FXML
    ImageView leftCompanyContainerImageView;
    @FXML
    Label leftContainerRatingLabel;
    @FXML
    VBox rightCompanyContainerVBox;

    @FXML
    ScrollPane companyReviewScrollPane;
    @FXML
    VBox companyReviewVBox;




    public void setCompanyPage(MainController mainController, Company company) throws SQLException, MalformedURLException {

        this.mainController = mainController;
        this.company = company;

        leftContainerRatingLabel.setText("NO RATING YET");
        rightCompanyContainerVBox.getChildren().addAll(
            new Label("Company Name: " +  company.getName()),
            new Label("Company Founded Date: " + company.getCompanyFoundedDate()),
            new Label("Company Address: " + company.getAddress()),
            new Hyperlink("Company Website: " + company.getWebsiteLink()),
            new Label("Business Email: " + company.getBusinessEmail()),
            new Label("Customer Service Email: " + company.getCustomerServiceEmail()),
            new Label("Business Phone Number: " + company.getBusinessPhoneNumber()),
            new Label("Customer Service Phone Number: " + company.getBusinessPhoneNumber())
        );

        reviews = DatabaseAccess.getCompanyReviews(company.getCompanyID());
        for(Review review : reviews){
            companyReviewVBox.getChildren().add(createReviewContainer(review));
        }

    }


     private VBox createReviewContainer(Review review) throws SQLException {

        User user = DatabaseAccess.getUser(review.getUserID());
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


            userName.setOnMouseClicked(mouseEvent -> {
                loadUserPage(user);
            });
        }

        Label timeDate = new Label(review.getReviewDateTime().toString());

        topContainer.getChildren().addAll(userPhoto, userName, rating, timeDate);

        mainContainer.getChildren().addAll(topContainer, reviewText);

        return mainContainer;
    }

    private FXMLLoader loadUserPage(User user){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("user_page.fxml"));
        try {
            Parent view = loader.load();

            UserPageController userPageController = loader.getController();
            userPageController.setUserPage(this.mainController, user);
            mainController.appBorderPane.setCenter(view);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return loader;
    }

}
