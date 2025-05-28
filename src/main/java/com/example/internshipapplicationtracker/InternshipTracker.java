/**
 * PROGRAM PURPOSE: Program to serve as the entry point for the Internship Application Tracker
 * @author Mika Collins
 * Date: 5/28/25
 */
package com.example.internshipapplicationtracker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class InternshipTracker extends Application {

    /**
     * Method to start the JavaFX application and load the FXML file
     * @param stage the main stage for this program
     * @throws Exception error handling in case the fxml does not load successfully
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Internship Application Tracker.fxml"));

        Scene scene = new Scene(root);
        stage.setTitle("Internship Application Tracker");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Main method to serve as an entry point and launcher for the program
     * @param args arguments passed to the application
     */
    public static void main(String[] args) {
        launch();
    }
}