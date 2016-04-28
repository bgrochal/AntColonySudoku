package pl.edu.agh.operationsresearch.algorithm.controller;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import pl.edu.agh.operationsresearch.algorithm.model.AlgorithmCore;
import pl.edu.agh.operationsresearch.common.GridLoader;
import pl.edu.agh.operationsresearch.grid.controller.GridController;
import pl.edu.agh.operationsresearch.grid.model.Grid;
import pl.edu.agh.operationsresearch.utils.view.InvalidTextFieldException;
import pl.edu.agh.operationsresearch.utils.view.ValidatedTextField;

public class AlgorithmController {

    @FXML
    private ValidatedTextField antsTextField;

    @FXML
    private ValidatedTextField evaporationTextField;

    @FXML
    private ValidatedTextField cyclesTextField;

    @FXML
    private ValidatedTextField pheromonesTextField;
    
    private GridController gridCtrl;


    @FXML
    private void loadTest(ActionEvent actionEvent) {
        Grid grid = GridLoader.load(getFileName());
        
        if(grid != null){    
            antsTextField.setText("100");
            evaporationTextField.setText("0.9");
            cyclesTextField.setText("1000");
            pheromonesTextField.setText("1000");
            gridCtrl.setGrid(grid);
        }
    }
    
    private File getFileName(){
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open File");
        return chooser.showOpenDialog(antsTextField.getScene().getWindow());
    }

    @FXML
    private void startAlgorithm(ActionEvent actionEvent) {
        int antsNumber = 0;
        int pheromones = 0;
        int cycles = 0;
        double evaporationRate = 0;
        boolean valid = true;

        try {
            antsNumber = getAntsNumber();
            evaporationRate = getEvaporationRate();
            pheromones = getMaxPheromones();
            cycles = getCyclesNumber();
        } catch (InvalidTextFieldException e) {
            valid = false;
        }

        if (valid) {
            new AlgorithmCore(evaporationRate, antsNumber, pheromones, cycles, gridCtrl);
        }
    }
    
    public void setGridController(GridController ctrl){
        gridCtrl = ctrl;
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

    private int getCyclesNumber() throws InvalidTextFieldException {
        int cyclesNumber;

        try {
            cyclesNumber = Integer.parseInt(cyclesTextField.getText());
        } catch (NumberFormatException e) {
            throw new InvalidTextFieldException(cyclesTextField);
        }

        if (cyclesNumber <= 0) {
            throw new InvalidTextFieldException(cyclesTextField);
        }

        return cyclesNumber;
    }

    private int getMaxPheromones() throws InvalidTextFieldException {
        int pheromones;

        try {
            pheromones = Integer.parseInt(pheromonesTextField.getText());
        } catch (NumberFormatException e) {
            throw new InvalidTextFieldException(pheromonesTextField);
        }

        if (pheromones <= 0) {
            throw new InvalidTextFieldException(pheromonesTextField);
        }

        return pheromones;
    }

}
