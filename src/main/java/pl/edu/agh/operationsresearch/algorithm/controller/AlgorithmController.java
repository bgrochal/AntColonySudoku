package pl.edu.agh.operationsresearch.algorithm.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import pl.edu.agh.operationsresearch.algorithm.model.AlgorithmCore;
import pl.edu.agh.operationsresearch.utils.view.ValidatedTextField;

public class AlgorithmController {

    @FXML
    private ValidatedTextField antsTextField;

    @FXML
    private ValidatedTextField evaporationTextField;

    //TODO: Add parameters if needed.


    @FXML
    private void startAlgorithm(ActionEvent actionEvent) {
        int antsNumber;
        double evaporationRate;

        try {
            antsNumber = Integer.parseInt(antsTextField.getText().trim());
            if(antsNumber <= 0) {
                throw new NumberFormatException();
            }
        } catch(NumberFormatException exc) {
            antsTextField.setStyle("-fx-background-color: red;");
            antsNumber = 0;
        }

        try {
            evaporationRate = Double.parseDouble(evaporationTextField.getText().trim());
            if(!(evaporationRate > 0 && evaporationRate < 1)) {
                throw new NumberFormatException();
            }
        } catch(NumberFormatException exc) {
            evaporationTextField.setStyle("-fx-background-color: red;");
            evaporationRate = 0;
        }

        if(antsNumber != 0 && evaporationRate != 0) {
            new AlgorithmCore(evaporationRate, antsNumber);
        }
    }

}
