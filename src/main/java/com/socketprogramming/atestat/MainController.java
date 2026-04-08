package com.socketprogramming.atestat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
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

import java.io.IOException;
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
    private ArrayList<Service> services;

    @FXML
    ComboBox<Service> servicesComboBox;

    public FXMLLoader loadCompaniesPage() {
        FXMLLoader loader = new FXMLLoader();
        try {
            loader = new FXMLLoader(getClass().getResource("companies_page.fxml"));
            Parent view = loader.load();
            appBorderPane.setCenter(view);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return loader;
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
        servicesComboBox.setOnAction(event -> {
            Service selectedService = servicesComboBox.getSelectionModel().getSelectedItem();;

        });
        servicesComboBox.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if(newValue != null){

                FXMLLoader loader = loadCompaniesPage();
                CompaniesPageController companiesPageController = loader.getController();
                companiesPageController.setMainController(this);

                try {
                    companiesPageController.setCompanies(DatabaseAccess.getCompaniesBasedOnService(newValue.getServiceID()));
                } catch (SQLException | IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        servicesComboBox.getSelectionModel().clearSelection();
    }


    private void connectToDataBase() throws SQLException {
        DatabaseAccess.startConnection();
    }

}

