package com.socketprogramming.atestat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.controlsfx.control.Rating;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class CompaniesPageController implements Initializable {

    @FXML ScrollPane companiesScrollPane;
    @FXML VBox companiesVBox;

    private MainController mainController;
    private ArrayList<Company> companies;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    private HBox createCompanyContainer(Company company) throws SQLException {
        HBox mainContainer = new HBox();
        VBox leftContainer = new VBox();
        VBox rightContainer = new VBox();

        ImageView imageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/photos/defaulticonmouse.jpg"))));
        imageView.setFitWidth(330);
        imageView.setFitHeight(260);


        Label companyServices = new Label(company.getServicesString());
        companyServices.setWrapText(true);
        companyServices.setPrefWidth(400); // Set a reasonable width for the text to "hit" before wrapping
        companyServices.setMinWidth(0);   // Prevents it from pushing the HBox wider than the screen

// Force the rightContainer to cap the width
        rightContainer.setMaxWidth(500); // Adjust this number to fit your UI
        rightContainer.setMinWidth(0);
        HBox.setHgrow(rightContainer, Priority.ALWAYS);

        rightContainer.setMinWidth(0);
        Rating rating = new Rating(5);
        rating.setPartialRating(true);
        rating.setRating(DatabaseAccess.getCompanyTotalRating(company.getCompanyID()));
        rating.setMouseTransparent(true);
        rating.setFocusTraversable(false);
        leftContainer.getChildren().addAll(imageView, rating);

        Label companyName = new Label(company.getName());
        Label companyAddress = new Label(company.getAddress());
        companyServices.setWrapText(true);
        Label customerServiceEmail = new Label(company.getCustomerServiceEmail());
        Label customerServicePhoneNumber = new Label(company.getBusinessPhoneNumber());
        Hyperlink websiteLink = new Hyperlink(company.getWebsiteLink());
        Label moreDetails = new Label("Click for more details and reviews");

        rightContainer.getChildren().addAll(companyName, companyAddress, companyServices, customerServiceEmail, customerServicePhoneNumber, websiteLink, moreDetails);
        mainContainer.getChildren().addAll(leftContainer, rightContainer);

        mainContainer.setOnMouseClicked(mouseEvent -> {
            loadCompanyPage(company);
        });

        mainContainer.getStyleClass().add("company-container");
        leftContainer.getStyleClass().add("left-company-container");
        rightContainer.getStyleClass().add("right-company-container");

        return mainContainer;
    }

    private void loadCompanyPage(Company company) {

        mainController.getHistorySceneStack().push((Parent) mainController.appBorderPane.getCenter());
        mainController.getHistoryServiceStack().push(mainController.servicesComboBox.getSelectionModel().getSelectedItem());

        FXMLLoader loader = new FXMLLoader(getClass().getResource("company_page.fxml"));
        try {
            Parent view = loader.load();
            CompanyPageController companyPageController = loader.getController();
            companyPageController.setCompanyPage(this.mainController, company);
            mainController.appBorderPane.setCenter(view);
            mainController.backButton.setVisible(true);

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        companiesScrollPane.getStylesheets().add(getClass().getResource("/css/companiesPageStylesheet.css").toExternalForm());
        companiesVBox.setId("companies-vbox");
    }

    public void setCompanies(ArrayList<Company> companies) throws SQLException {
        this.companies = companies;
        companiesVBox.getChildren().clear();
        for (Company company : companies) {
            companiesVBox.getChildren().add(createCompanyContainer(company));
        }
    }
}