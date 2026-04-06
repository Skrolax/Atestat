package com.socketprogramming.atestat;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private User user;
    private ArrayList<Service> services;
    ArrayList<Company> companies;

    @FXML
    ComboBox<Service> servicesComboBox;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            connectToDataBase();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        initializeServicesChoiceBox();
        initializeUser();

    }

    private void initializeServicesChoiceBox(){


        try {
           services = DatabaseAccess.getServices();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        servicesComboBox.getItems().setAll(services);
        servicesComboBox.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if(newValue != null){
                try {
                    companies = DatabaseAccess.getCompaniesBasedOnService(newValue.getServiceID());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            for(Company c : companies){
                System.out.println(c.toString());
            }
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

