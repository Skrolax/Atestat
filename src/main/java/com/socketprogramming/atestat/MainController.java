package com.socketprogramming.atestat;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.controlsfx.control.Rating;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    BorderPane appBorderPane;
    ScrollPane companyContainerScrollPane;
    private VBox companyContainerVBox = new VBox();
    private VBox companyProfilePageVBox = new VBox();
    private ArrayList<HBox> companyContainer;

    private User user;
    private ArrayList<Service> services;

    private ArrayList<Company> companies;
    private ArrayList<Review> reviews = new ArrayList<>();


    @FXML
    ComboBox<Service> servicesComboBox;


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

    private VBox createCompanyProfilePage(HBox companyContainer){
        VBox companyProfilePage = new VBox();
        VBox reviewContainer = new VBox();
        for(Review review : reviews){
            reviewContainer.getChildren().add(createReviewContainer(review));
        }
        companyProfilePage.getChildren().addAll(companyContainer, reviewContainer);
        return companyProfilePage;
    }

    private HBox createCompanyContainer(Company company){

        HBox mainContainer = new HBox();
        VBox leftContainer = new VBox();
        VBox rightContainer = new VBox();

        ImageView imageView = new ImageView(new Image(Objects.requireNonNull(UI.class.getResourceAsStream("/photos/images.png"))));
        Rating rating = new Rating(5);
        leftContainer.getChildren().addAll(imageView, rating);

        Label companyName = new Label(company.getName());
        Label companyAddress = new Label(company.getAddress());
        Label companyServices = new Label(company.getServicesString());
        Label customerServiceEmail = new Label(company.getCustomerServiceEmail());
        Label customerServicePhoneNumber = new Label(company.getBusinessPhoneNumber());
        Hyperlink websiteLink = new Hyperlink(company.getWebsiteLink());
        Label moreDetails = new Label("Click for more details and reviews");

        rightContainer.getChildren().addAll(companyName, companyAddress, companyServices, customerServiceEmail,customerServicePhoneNumber, websiteLink, moreDetails);
        mainContainer.getChildren().addAll(leftContainer, rightContainer);

        mainContainer.setOnMouseClicked(mouseEvent -> {
            disableCompanyContainerVBox();

            try {
                reviews.clear();
                reviews = DatabaseAccess.getCompanyReviews(company.getCompanyID());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            companyProfilePageVBox = createCompanyProfilePage(mainContainer);
            companyContainerScrollPane.setContent(companyProfilePageVBox);
        });

        return mainContainer;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            connectToDataBase();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        initializeCompanyContainerScrollPane();


        initializeServicesChoiceBox();
        initializeUser();

    }

    private void disableCompanyContainerVBox(){
        companyContainerVBox.setDisable(true);
        companyContainerVBox.setVisible(false);
    }
    private void enableCompanyContainerVBox(){
        companyContainerVBox.setDisable(false);
        companyContainerVBox.setVisible(true);
    }


    private void initializeCompanyContainerScrollPane(){
        companyContainerScrollPane = new ScrollPane();
        appBorderPane.setCenter(companyContainerScrollPane);
        companyContainerScrollPane.setFitToWidth(true);
        companyContainerScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
    }

    private void initializeServicesChoiceBox(){

        try {
           services = DatabaseAccess.getServices();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        servicesComboBox.getItems().setAll(services);
        servicesComboBox.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {

            enableCompanyContainerVBox();
            if(newValue != null){
                try {
                    companyContainerVBox.getChildren().clear();
                    companies = DatabaseAccess.getCompaniesBasedOnService(newValue.getServiceID());

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

            companyContainer = new ArrayList<>();
            companyContainerScrollPane.setContent(companyContainerVBox);
            for(Company company : companies){
                companyContainerVBox.getChildren().add(createCompanyContainer(company));
            }
            companyContainerScrollPane.setContent(companyContainerVBox);
        });

        servicesComboBox.getSelectionModel().clearSelection();
    }


    private void initializeUser(){
        //TODO
    }

    private void connectToDataBase() throws SQLException {
        DatabaseAccess.startConnection();
    }

}

