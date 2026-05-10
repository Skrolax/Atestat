package com.socketprogramming.atestat;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.skin.ComboBoxBaseSkin;
import javafx.scene.control.skin.ComboBoxListViewSkin;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Stack;

public class MainController implements Initializable {

    @FXML BorderPane appBorderPane;
    @FXML ImageView homeIconImageView;
    @FXML Button userProfileButton;
    @FXML Button backButton;
    @FXML HBox navbarHBox;
    @FXML ComboBox<Service> servicesComboBox;

    private ArrayList<Service> services;
    private User user;

    public Stack<Parent> getHistorySceneStack() {
        return historySceneStack;
    }
    public void setHistorySceneStack(Stack<Parent> historySceneStack) {
        this.historySceneStack = historySceneStack;
    }

    private Stack<Parent> historySceneStack = new Stack<>();

    public Stack<Service> getHistoryServiceStack() {
        return historyServiceStack;
    }
    public void setHistoryServiceStack(Stack<Service> historyServiceStack) {
        this.historyServiceStack = historyServiceStack;
    }

    private Stack<Service> historyServiceStack = new Stack<>();

    private boolean navigationInProgress = false;

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public FXMLLoader loadCompaniesPage() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("companies_page.fxml"));
        try {
            Parent view = loader.load();
            appBorderPane.setCenter(view);
            backButton.setVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loader;
    }

    boolean onUserPage = false;

    @FXML
    public void loadUserPage() {

        if (onUserPage) {
            return;
        }

        if (appBorderPane.getCenter() != null) {
            historySceneStack.push((Parent) appBorderPane.getCenter());
            historyServiceStack.push(servicesComboBox.getSelectionModel().getSelectedItem());
        }

        navigationInProgress = true;
        try {
            servicesComboBox.getSelectionModel().clearSelection();
            servicesComboBox.setSkin(new ComboBoxListViewSkin<>(servicesComboBox));
        } finally {
            navigationInProgress = false;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("user_page.fxml"));
        try {
            Parent view = loader.load();

            UserPageController userPageController = loader.getController();
            userPageController.setUserPage(this, this.user);

            appBorderPane.setCenter(view);
            backButton.setVisible(true);

            onUserPage = true;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void goBack() {
        if (historySceneStack.isEmpty()) {
            return;
        }

        navigationInProgress = true;
        onUserPage = false;

        try {
            Parent previousView = historySceneStack.pop();
            Service previousService = historyServiceStack.pop();

            appBorderPane.setCenter(previousView);

            if (previousService == null) {
                servicesComboBox.getSelectionModel().clearSelection();
                servicesComboBox.setSkin(new ComboBoxListViewSkin<>(servicesComboBox));
            } else {
                servicesComboBox.getSelectionModel().select(previousService);
            }

            if (historySceneStack.isEmpty()) {
                backButton.setVisible(false);
                servicesComboBox.setSkin(new ComboBoxListViewSkin<>(servicesComboBox));
            }
        } finally {
            navigationInProgress = false;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        backButton.setVisible(false);
        initializeServicesChoiceBox();
        Platform.runLater(() -> appBorderPane.requestFocus());
        appBorderPane.setOnMouseClicked(event -> appBorderPane.requestFocus());
    }

    private void initializeServicesChoiceBox() {
        try {
            services = DatabaseAccess.getServices();
            servicesComboBox.getItems().setAll(services);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        servicesComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (!navigationInProgress && newValue != null) {

                historySceneStack.push((Parent) appBorderPane.getCenter());
                historyServiceStack.push(oldValue);
                onUserPage = false;

                FXMLLoader loader = loadCompaniesPage();
                if (loader != null) {
                    CompaniesPageController companiesPageController = loader.getController();
                    companiesPageController.setMainController(this);

                    try {
                        companiesPageController.setCompanies(
                                DatabaseAccess.getCompaniesBasedOnService(newValue.getServiceID())
                        );
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

}