package com.example.internshipapplicationtracker;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class InternshipTrackerController {
    @FXML private Label label_InternshipApplicationTracker;
    @FXML private TextField textField_SearchByCompanyName;
    @FXML private Button button_AddApplication;
    @FXML private Button button_EditApplication;
    @FXML private TableView tableView_InternshipTable;
}