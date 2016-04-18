package pl.edu.agh.operationsresearch.algorithm.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import pl.edu.agh.operationsresearch.algorithm.model.AlgorithmCore;
import pl.edu.agh.operationsresearch.grid.controller.GridController;
import pl.edu.agh.operationsresearch.grid.model.GridCell;
import pl.edu.agh.operationsresearch.utils.view.AlertDialog;
import pl.edu.agh.operationsresearch.utils.view.InvalidTextFieldException;
import pl.edu.agh.operationsresearch.utils.view.ValidatedTextField;

public class AlgorithmController {

    @FXML
    private ValidatedTextField antsTextField;

    @FXML
    private ValidatedTextField evaporationTextField;


    @FXML
    private void loadTest(ActionEvent actionEvent) {
        initializeTestGrid();
        antsTextField.setText("100");
        evaporationTextField.setText("0.9");
    }

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
            if(GridController.getInstance().isValid()){
                new AlgorithmCore(evaporationRate, antsNumber).start();
            } else {
                new AlertDialog("Error", "Invalid setup", "Entered sudoku setup is invalid.");
            }
        }
    }


    private void initializeTestGrid() {
        int[][] testGrid = new int[][] {
                {5, 3, 0, 0, 7, 0, 0, 0, 0},
                {6, 0, 0, 1, 9, 5, 0, 0, 0},
                {0, 9, 8, 0, 0, 0, 0, 6, 0},
                {8, 0, 0, 0, 6, 0, 0, 0, 3},
                {4, 0, 0, 8, 0, 3, 0, 0, 1},
                {7, 0, 0, 0, 2, 0, 0, 0, 6},
                {0, 6, 0, 0, 0, 0, 2, 8, 0},
                {0, 0, 0, 4, 1, 9, 0, 0, 5},
                {0, 0, 0, 0, 8, 0, 0, 7, 9}
        };

        /**
         {5, 3, 4, 6, 7, 8, 9, 1, 2},
         {6, 7, 2, 1, 9, 5, 3, 4, 8},
         {1, 9, 8, 3, 4, 2, 5, 6, 7},
         {8, 5, 9, 7, 6, 1, 4, 2, 3},
         {4, 2, 6, 8, 5, 3, 7, 9, 1},
         {7, 1, 3, 9, 2, 4, 8, 5, 6},
         {9, 6, 1, 5, 3, 7, 2, 8, 4},
         {2, 8, 7, 4, 1, 9, 6, 3, 5},
         {3, 4, 5, 2, 8, 6, 1, 7, 9}

         {5, 3, 0, 0, 7, 0, 0, 0, 0},
         {6, 0, 0, 1, 9, 5, 0, 0, 0},
         {0, 9, 8, 0, 0, 0, 0, 6, 0},
         {8, 0, 0, 0, 6, 0, 0, 0, 3},
         {4, 0, 0, 8, 0, 3, 0, 0, 1},
         {7, 0, 0, 0, 2, 0, 0, 0, 6},
         {0, 6, 0, 0, 0, 0, 2, 8, 0},
         {0, 0, 0, 4, 1, 9, 0, 0, 5},
         {0, 0, 0, 0, 8, 0, 0, 7, 9}
         **/

        GridCell[][] grid = GridController.getInstance().getSudokuGrid();
        for(int i=0; i<9; i++) {
            for(int j=0; j<9; j++) {
                grid[i][j].setValue(testGrid[j][i]);
            }
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
            evaporationRate = Double.parseDouble(evaporationTextField.getText());
        } catch (NumberFormatException e) {
            throw new InvalidTextFieldException(evaporationTextField);
        }
        if (!(evaporationRate > 0 && evaporationRate < 1)) {
            throw new InvalidTextFieldException(evaporationTextField);
        }

        return evaporationRate;
    }

}
