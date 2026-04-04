package com.socketprogramming.atestat;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    ComboBox<CompanyServices> servicesComboBox;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeServicesChoiceBox();
    }

    private void initializeServicesChoiceBox(){
        servicesComboBox.getItems().setAll(CompanyServices.values());
        servicesComboBox.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if(newValue != null){
                System.out.println(newValue.toString());
            }
        });
        servicesComboBox.getSelectionModel().clearSelection();
    }

}

