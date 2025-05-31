/**
 * PROGRAM PURPOSE: Program to manage the user input and functionality of the Add New Internship popup window
 * @author Mika Collins
 * Date: 5/31/25
 */
package com.example.internshipapplicationtracker;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javafx.event.ActionEvent;
import javafx.stage.Stage;

public class AddNewInternshipController {

    // FXML references to UI elements
    @FXML private TextField textField_CompanyName;
    @FXML private TextField textField_TitleOfRole;
    @FXML private ChoiceBox<String> choiceBox_ApplicationStatus;
    @FXML private DatePicker datePicker_DateApplied;
    @FXML private TextField textField_Location;
    @FXML private ChoiceBox<String> choiceBox_Compensation;
    @FXML private ChoiceBox<String> choiceBox_Type;
    @FXML private TextField textField_ApplicationLink;
    @FXML private TextField textField_PortalPassword;
    @FXML private Button button_Cancel;
    @FXML private Button button_Confirm;

    // Lists to populate the various choice boxes with drop down options
    ObservableList<String> typeList = FXCollections.observableArrayList("On-site", "Hybrid", "Remote", "Unknown");
    ObservableList<String> statusList = FXCollections.observableArrayList("Not Applied", "Applied", "Accepted!", "Rejected", "Interview", "Pending");
    ObservableList<String> compensationList = FXCollections.observableArrayList("Paid", "Unpaid", "Unknown");

    /**
     * Method to initialize and populate the choice boxes upon opening the popup window
     */
    public void initialize() {
        choiceBox_Type.setItems(typeList);
        choiceBox_Type.setValue("Unknown");

        choiceBox_ApplicationStatus.setItems(statusList);
        choiceBox_ApplicationStatus.setValue("Not Applied");

        choiceBox_Compensation.setItems(compensationList);
        choiceBox_Compensation.setValue("Unknown");
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
     * Method to close the popup window, without saving data, upon clicking the cancel button
     * @param event The event of clicking the cancel button
     */
    @FXML
    private void buttonClickedCancel(ActionEvent event) {
        ((Stage)button_Cancel.getScene().getWindow()).close();
    }

    /**
     * Method to validate user input, create a new internship object, and close popup window once user is finished
     * @param event the event of clicking the confirm button
     */
    @FXML
    private void buttonClickedConfirm(ActionEvent event) {
        try {
            // Retrieving all input data from fields
            String companyName = textField_CompanyName.getText().trim();
            String roleTitle = textField_TitleOfRole.getText().trim();
            String status = choiceBox_ApplicationStatus.getValue();
            String dateApplied = (datePicker_DateApplied.getValue() != null) ? datePicker_DateApplied.getValue().toString() : "";
            String location = textField_Location.getText().trim();
            String compensation = choiceBox_Compensation.getValue();
            String type = choiceBox_Type.getValue();
            String link = textField_ApplicationLink.getText().trim();
            String portalPassword = textField_PortalPassword.getText().trim();

            // Validate to ensure all fields have been filled
            if (companyName.isEmpty() || roleTitle.isEmpty() || dateApplied.isEmpty() || status.isEmpty() || location.isEmpty() || compensation.isEmpty() || type.isEmpty() || link.isEmpty() || portalPassword.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Wait", "Please make sure to fill in all fields appropriately.");
                return;
            }

            // Create a new internship object and add it to the list
            Internship internship = new Internship(companyName, roleTitle, status, dateApplied, location, compensation, type, link, portalPassword);
            InternshipDataHandler.addInternship(internship);

            // Display confirmation and close popup window
            showAlert(Alert.AlertType.INFORMATION, "Done!", "New internship application was successfully added!");
            ((Stage)button_Confirm.getScene().getWindow()).close();

        } catch (Exception error) {
            error.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "An unexpected error has occurred. Please try again.");
        }
    }
}
