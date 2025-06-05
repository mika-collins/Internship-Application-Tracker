/**
 * PROGRAM PURPOSE: Program to manage table display and UI interaction of the Internship Application Tracker main dashboard
 * @author Mika Collins
 * Date: 5/31/25
 */
package com.example.internshipapplicationtracker;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.*;
import java.util.List;

public class InternshipTrackerController {

    // FXML references to UI elements
    @FXML private Label label_InternshipApplicationTracker;
    @FXML private TextField textField_SearchByCompanyName;
    @FXML private Button button_AddApplication;
    @FXML private Button button_EditApplication;
    @FXML private TableView<Internship> tableView_InternshipTable;
    @FXML private TableColumn<Internship, String> tableColumn_CompanyName;
    @FXML private TableColumn<Internship, String> tableColumn_RoleTitle;
    @FXML private TableColumn<Internship, String> tableColumn_Status;
    @FXML private TableColumn<Internship, String> tableColumn_DateApplied;
    @FXML private TableColumn<Internship, String> tableColumn_Location;
    @FXML private TableColumn<Internship, String> tableColumn_Compensation;
    @FXML private TableColumn<Internship, String> tableColumn_Type;
    @FXML private TableColumn<Internship, String> tableColumn_Link;
    @FXML private TableColumn<Internship, String> tableColumn_PortalPassword;

    // Observable list to store internships for the table display
    private ObservableList<Internship> internshipsList = FXCollections.observableArrayList();

    // Flags to prevent multiple pop up windows from being open at one time
    public static boolean addPopupIsOpen = false;
    public static boolean editPopupIsOpen = false;

    /**
     * Method to initialize the table values and load the saved internship data from csv file
     */
    @FXML
    public void initialize() {
        tableColumn_CompanyName.setCellValueFactory(new PropertyValueFactory<>("companyName"));
        tableColumn_RoleTitle.setCellValueFactory(new PropertyValueFactory<>("roleTitle"));
        tableColumn_Status.setCellValueFactory(new PropertyValueFactory<>("status"));
        tableColumn_DateApplied.setCellValueFactory(new PropertyValueFactory<>("dateApplied"));
        tableColumn_Location.setCellValueFactory(new PropertyValueFactory<>("location"));
        tableColumn_Compensation.setCellValueFactory(new PropertyValueFactory<>("compensation"));
        tableColumn_Type.setCellValueFactory(new PropertyValueFactory<>("type"));
        tableColumn_Link.setCellValueFactory(new PropertyValueFactory<>("link"));
        tableColumn_PortalPassword.setCellValueFactory(new PropertyValueFactory<>("portalPassword"));

        // Load existing data into the table
        loadInternshipsFromFile();
        tableView_InternshipTable.setItems(internshipsList);

        // Filter internship table by company name search
        textField_SearchByCompanyName.textProperty().addListener((observable, oldValue, newValue) -> {
            filterTableBySearch(newValue);
        });

        // Color code table column based on status
        setStatusColumnColors();
    }

    /**
     * Method to load the saved internship data from a CSV file using InternshipDataHandler.java
     */
    public void loadInternshipsFromFile() {
        // Clear potential existing data
        internshipsList.clear();

        try {
            List<Internship> loadedInternships = InternshipDataHandler.loadInternships();
            internshipsList.addAll(loadedInternships);

        } catch (Exception error) {
            error.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Unable to load internships from CSV file.");
        }
    }

    /**
     * Method to show an alert notifications for feedback and error handling
     * @param type the type of the alert to be displayed
     * @param title The title of the alert window
     * @param content The content of the message to be displayed in the alert.
     */
    @FXML
    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.getButtonTypes().setAll(ButtonType.OK);
        alert.showAndWait();
    }

    /**
     * Method to handle and open the popup window upon clicking the "Add Application" button
     * @param event the event of clicking the "Add Application" button
     */
    @FXML
    private void buttonClickedAddApplication(ActionEvent event) {
        // Check if popup window is already opened, only allowed to open once
        if (!addPopupIsOpen) {
            addPopupIsOpen = true;

            try {
                // Load the popup window and wait until user is finished to close
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Add New Internship.fxml"));
                Parent root = loader.load();

                Stage stage = new Stage();
                stage.setTitle("Add New Internship");
                stage.setScene(new Scene(root));
                stage.showAndWait();

                // Reload file data to update table after popup is closed
                addPopupIsOpen = false;
                loadInternshipsFromFile();

            } catch (IOException error) {
                error.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error", "An unexpected error has occurred. Please try again.");
            }
        }
    }

    /**
     * Method to handle and open the popup window upon clicking the "Edit Application" button
     * @param event the event of clicking the "Edit Application" button
     */
    @FXML
    private void buttonClickedEditApplication(ActionEvent event) {
        Internship selectedInternship = tableView_InternshipTable.getSelectionModel().getSelectedItem();

        // Validate that the user has selected a single internship to edit
        if (selectedInternship == null) {
            showAlert(Alert.AlertType.ERROR, "No Internship Selected", "Please select an internship application to edit.");
            return;
        }

        // Check if popup window is already opened, only allowed to open once
        if (!editPopupIsOpen) {
            editPopupIsOpen = true;

            try {
                // Load the popup window and wait until user is finished to close
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Edit Existing Internship.fxml"));
                Parent root = loader.load();

                // Pass the selected internship to the controller for editing
                EditExistingInternshipController controller = loader.getController();
                controller.setInternshipData(selectedInternship, internshipsList);

                Stage stage = new Stage();
                stage.setTitle("Edit Existing Internship");
                stage.setScene(new Scene(root));
                stage.showAndWait();

                // Reload file data to update table after popup is closed
                editPopupIsOpen = false;
                loadInternshipsFromFile();

            } catch (IOException error) {
                error.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error", "An unexpected error has occurred. Please try again.");
            }
        }
    }

    /**
     * Method to filter the table based on the company name searched by the user
     * @param searchText the text provided by the user within the search text field (i.e. company name)
     */
    private void filterTableBySearch(String searchText) {

        // Set the table to display all internships if no search input
        if (searchText == null || searchText.isEmpty()) {
            tableView_InternshipTable.setItems((internshipsList));
            return;
        }

        // New list to keep track of all filtered internships
        ObservableList<Internship> filteredList = FXCollections.observableArrayList();

        // Loop to check all internships for search matches, append to filtered list
        for (Internship internship : internshipsList) {
            if (internship.getCompanyName().toLowerCase().contains(searchText.toLowerCase())) {
                filteredList.add(internship);
            }
        }

        // Filter table display
        tableView_InternshipTable.setItems(filteredList);
    }

    /**
     * Method to color code the status column within the table depending on the application status type
     */
    private void setStatusColumnColors() {
        tableColumn_Status.setCellFactory(column -> new TableCell<Internship, String>() {

            @Override
            protected void updateItem(String status, boolean empty) {
                super.updateItem(status, empty);

                if (empty || status == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(status);
                    setStyle(""); // reset first

                    switch (status) {
                        case "Accepted!":
                            setStyle("-fx-background-color: #b9f6ca;"); // set green
                            break;
                        case "Pending":
                            setStyle("-fx-background-color: #fff59d;"); // set yellow
                            break;
                        case "Interviewing":
                            setStyle("-fx-background-color: #fff59d;"); // set yellow
                            break;
                        case "Rejected":
                            setStyle("-fx-background-color: #ffcdd2;"); // set red
                            break;
                        default:
                            break;
                    }
                }
            }
        });
    }
}