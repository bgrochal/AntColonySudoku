package pl.edu.agh.operationsresearch.algorithm.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import pl.edu.agh.operationsresearch.algorithm.model.AlgorithmCore;
import pl.edu.agh.operationsresearch.utils.view.InvalidTextFieldException;
import pl.edu.agh.operationsresearch.utils.view.ValidatedTextField;

public class AlgorithmController {

    @FXML
    private ValidatedTextField antsTextField;

    @FXML
    private ValidatedTextField evaporationTextField;

    // TODO: Add parameters if needed.

    @FXML
    private void startAlgorithm(ActionEvent actionEvent) {
        int antsNumber = 0;
        double evaporationRate = 0;
        boolean valid = true;

        try {
            antsNumber = getAntsNumber();
            evaporationRate = getEvaporationRate();
        } catch (InvalidTextFieldException e) {
            valid = false;
        }

        if (valid) {
            new AlgorithmCore(evaporationRate, antsNumber);
        }
    }

    private int getAntsNumber() throws InvalidTextFieldException {
        int antsNumber;

        try {
            antsNumber = Integer.parseInt(antsTextField.getText());
        } catch (NumberFormatException e) {
            throw new InvalidTextFieldException(antsTextField);
        }

        if (antsNumber <= 0) {
            throw new InvalidTextFieldException(antsTextField);
        }

        return antsNumber;
    }

    private double getEvaporationRate() throws InvalidTextFieldException {
        double evaporationRate;

        try {
            evaporationRate = Double
                    .parseDouble(evaporationTextField.getText());
        } catch (NumberFormatException e) {
            throw new InvalidTextFieldException(evaporationTextField);
        }
        if (!(evaporationRate > 0 && evaporationRate < 1)) {
            throw new InvalidTextFieldException(evaporationTextField);
        }

        return evaporationRate;
    }

}
