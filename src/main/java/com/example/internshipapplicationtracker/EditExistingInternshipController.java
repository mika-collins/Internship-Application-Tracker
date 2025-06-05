/**
 * PROGRAM PURPOSE: Program to manage internship edits/deletions and functionality of the Edit Existing Internship popup window
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

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class EditExistingInternshipController {

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
    @FXML private Button button_Delete;
    @FXML private Button button_Save;

    // Initialize lists to populate the choice box options
    ObservableList<String> typeList = FXCollections.observableArrayList("On-site", "Hybrid", "Remote", "Unknown");
    ObservableList<String> statusList = FXCollections.observableArrayList("Not Applied", "Accepted!", "Rejected", "Interviewing", "Pending");
    ObservableList<String> compensationList = FXCollections.observableArrayList("Paid", "Unpaid", "Unknown");

    // Currently selected internship and full internships list
    private Internship selectedInternship;
    private List<Internship> internshipList;

    /**
     * Method to set the current internship and list contents for the controller
     * @param internship the selected internship to be edited/deleted
     * @param internshipList the list of all internships for saving/deleting
     */
    public void setInternshipData(Internship internship, List<Internship> internshipList) {
        this.selectedInternship = internship;
        this.internshipList = internshipList;
        populateTextFields();
    }

    /**
     * Method to populate choice box options and UI fields with data from selected internship
     */
    public void populateTextFields() {

        // Retrieve and display previous internship data into text fields
        textField_CompanyName.setText(selectedInternship.getCompanyName());
        textField_TitleOfRole.setText(selectedInternship.getRoleTitle());
        choiceBox_ApplicationStatus.setValue(selectedInternship.getStatus());
        datePicker_DateApplied.setValue(LocalDate.parse(selectedInternship.getDateApplied()));
        textField_Location.setText(selectedInternship.getLocation());
        choiceBox_Compensation.setValue(selectedInternship.getCompensation());
        choiceBox_Type.setValue(selectedInternship.getType());
        textField_ApplicationLink.setText(selectedInternship.getLink());
        textField_PortalPassword.setText(selectedInternship.getPortalPassword());

        // Populate choice boxes
        choiceBox_Type.setItems(typeList);
        choiceBox_Compensation.setItems(compensationList);
        choiceBox_ApplicationStatus.setItems(statusList);
    }

    /**
     * Method save the updates to the selected internship upon clicking the "Save" button
     * @param event the event of clicking the "Save" button
     */
    @FXML
    private void buttonClickedSave(ActionEvent event) {
        try {
            // Autofill the UI fields with the previously entered information
            selectedInternship.setCompanyName(textField_CompanyName.getText());
            selectedInternship.setRoleTitle(textField_TitleOfRole.getText());
            selectedInternship.setStatus(choiceBox_ApplicationStatus.getValue());
            selectedInternship.setDateApplied(datePicker_DateApplied.getValue() != null ? datePicker_DateApplied.getValue().toString() : "");
            selectedInternship.setLocation(textField_Location.getText());
            selectedInternship.setCompensation(choiceBox_Compensation.getValue());
            selectedInternship.setType(choiceBox_Type.getValue());
            selectedInternship.setLink(textField_ApplicationLink.getText());
            selectedInternship.setPortalPassword(textField_PortalPassword.getText());

            // Save updated internship list to file and display confirmation to user
            InternshipDataHandler.saveToCSV(internshipList);
            showAlert(Alert.AlertType.INFORMATION, "Saved Changes!", "Changes to the internship application were successfully saved.");
            ((Stage) button_Save.getScene().getWindow()).close();

        } catch (IOException error) {
            error.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "File Error", "The changes could not be saved. Please try again.");

        } catch (Exception error) {
            error.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Sorry, an unexpected error has occurred.");
        }
    }

    /**
     * Method to remove the data of the selected internship upon clicking the "Delete" button
     * @param event the event of clicking the "Delete" button
     */
    @FXML
    private void buttonClickedDelete(ActionEvent event) {
        try {
            // Remove the selected internship and save updated list
            internshipList.remove(selectedInternship);
            InternshipDataHandler.saveToCSV(internshipList);

            // Display confirmation to user and close popup window
            showAlert(Alert.AlertType.INFORMATION, "Internship Deleted", "The selected internship application was successfully deleted.");
            ((Stage) button_Delete.getScene().getWindow()).close();

        } catch (Exception error) {
            error.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Unable to Delete Internship", "An unexpected error has occurred. Please try again.");
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
}
