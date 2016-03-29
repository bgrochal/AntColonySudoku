package pl.edu.agh.operationsresearch.grid.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import pl.edu.agh.operationsresearch.grid.model.GridCell;

import java.net.URL;
import java.util.ResourceBundle;

public class GridController implements Initializable {

    @FXML
    private GridPane sudokuGridPane;

    private GridCell[] sudokuGrid;


    public void initialize(URL location, ResourceBundle resources) {
        sudokuGrid = new GridCell[81];

        for(int i=0; i<81; i++) {
            sudokuGrid[i] = new GridCell();
            sudokuGridPane.add(sudokuGrid[i], i%9, i/9);
        }
    }


    public GridCell[] getSudokuGrid() {
        return sudokuGrid;
    }

}
