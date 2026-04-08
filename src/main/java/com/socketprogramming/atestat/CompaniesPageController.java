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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.controlsfx.control.Rating;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class CompaniesPageController implements Initializable {

    // FXML COMPONENTS

    @FXML
    ScrollPane companiesScrollPane;
    @FXML
    VBox companiesVBox;


    private MainController mainController;
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }




    private ArrayList<Company> companies;
    private ArrayList<Review> reviews;

    private HBox createCompanyContainer(Company company){

        HBox mainContainer = new HBox();
        VBox leftContainer = new VBox();
        VBox rightContainer = new VBox();

        ImageView imageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/photos/images.png"))));
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
            loadCompanyPage(company);
        });

        return mainContainer;
    }


    private FXMLLoader loadCompanyPage(Company company){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("company_page.fxml"));
        try {
            Parent view = loader.load();

            CompanyPageController companyPageController = loader.getController();
            companyPageController.setCompanyPage(this.mainController, company);
            mainController.appBorderPane.setCenter(view);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return loader;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setCompanies(ArrayList<Company> companies) throws IOException {

        this.companies = companies;
        for(Company company : companies){
            companiesVBox.getChildren().add(createCompanyContainer(company));
        }

    }

}
