package com.socketprogramming.atestat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.controlsfx.control.Rating;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CompanyPageController implements Initializable {

    private MainController mainController;

    public Company getCompany() {
        return company;
    }
    public void setCompany(Company company) {
        this.company = company;
    }

    private Company company;
    private ArrayList<Review> reviews;
    private UserPageController userPageController;

    // Main Containers
    @FXML VBox companyPageVBox;
    @FXML HBox companyContainerHBox;
    @FXML VBox leftCompanyContainerVBox;
    @FXML HBox rightCompanyContainerHBox;
    @FXML ScrollPane companyReviewScrollPane;
    @FXML VBox companyReviewVBox;

    // Media and Rating
    @FXML ImageView leftCompanyContainerImageView;
    @FXML Label leftContainerRatingLabel;

    // Company Information (Column 1)
    @FXML Label companyNameLabel;
    @FXML Label addressLabel;
    @FXML Hyperlink websiteHyperlink;
    @FXML Label foundedDateLabel;

    // Contact Information (Column 2)
    @FXML Label businessEmailLabel;
    @FXML Label businessPhoneLabel;
    @FXML Label customerEmailLabel;
    @FXML Label customerPhoneLabel;

    @FXML Label reviewTitleLabel;
    @FXML Button addReviewButton;

    @FXML HBox reviewTitleHBox;




    public void setCompanyPage(MainController mainController, Company company) throws SQLException, MalformedURLException {

        this.mainController = mainController;
        this.company = company;

        companyNameLabel.setText(company.getName());
        addressLabel.setText(addressLabel.getText() + company.getAddress());
        websiteHyperlink.setText(websiteHyperlink.getText() + company.getWebsiteLink());
        foundedDateLabel.setText(foundedDateLabel.getText() + company.getCompanyFoundedDate());
        businessEmailLabel.setText(businessEmailLabel.getText() + company.getBusinessEmail());
        businessPhoneLabel.setText(businessPhoneLabel.getText() + company.getBusinessPhoneNumber());
        customerEmailLabel.setText(customerEmailLabel.getText() + company.getCustomerServiceEmail());
        customerPhoneLabel.setText(customerPhoneLabel.getText() + company.getCustomerServicePhoneNumber());

        leftContainerRatingLabel.setText("TOTAL RATING: " + String.format("%.1f", DatabaseAccess.getCompanyTotalRating(company.getCompanyID())));

        reviews = DatabaseAccess.getCompanyReviews(company.getCompanyID());
        for(Review review : reviews){
            User user = DatabaseAccess.getUser(review.getUserID());
            loadReviewContainer(review);
        }

    }


    public FXMLLoader loadReviewContainer(Review review){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("review_container.fxml"));
        try {
            Parent view = loader.load();
            ReviewContainerController reviewContainerController = loader.getController();
            reviewContainerController.setReviewContainer(review, this, this.mainController);
            companyReviewVBox.getChildren().add(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loader;
    }

    @FXML
    private void addReview() throws SQLException {
        companyReviewScrollPane.setVvalue(0.0);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("add_review_container.fxml"));
        try {
            Parent view = loader.load();
            AddReviewContainerController addReviewContainerController = loader.getController();
            addReviewContainerController.setControllers(this, this.mainController);
            companyReviewVBox.getChildren().addFirst(view);
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*VBox mainContainer = new VBox();
        mainContainer.getStylesheets().add(getClass().getResource("/css/addReviewContainerStylesheet.css").toExternalForm());
        mainContainer.minHeight(40);
        mainContainer.setPrefWidth(1000);
        mainContainer.setPrefHeight(120);
        mainContainer.setId("mainContainer");

        HBox topContainer = new HBox();
        TextField addReviewTextField = new TextField();
        Button closeContainerButton = new Button("X");
        addReviewTextField.setText("Enter your review");

        addReviewTextField.setPrefWidth(950);


        HBox bottomContainer = new HBox();
        Label ratingLabel = new Label();
        ratingLabel.setText("Rating: ");
        Rating rating = new Rating(5);
        Button sendButton = new Button("Send");

        topContainer.getChildren().addAll(addReviewTextField, closeContainerButton);
        bottomContainer.getChildren().addAll(ratingLabel, rating, sendButton);
        mainContainer.getChildren().addAll(topContainer, bottomContainer);

        companyReviewVBox.getChildren().addFirst(mainContainer);*/

    }

   /* private FXMLLoader loadUserPage(User user){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("user_page.fxml"));
        try {
            Parent view = loader.load();

            UserPageController userPageController = loader.getController();
            //userPageController.setUserPage(this.mainController, user);
            mainController.appBorderPane.setCenter(view);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return loader;
    }*/

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        companyPageVBox.getStylesheets().add(getClass().getResource("/css/companyPageStylesheet.css").toExternalForm());
    }
}
