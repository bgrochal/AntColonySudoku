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

    private GridCell[][] sudokuGrid;

    private static final int GRID_SIZE = 9;

    public void initialize(URL location, ResourceBundle resources) {
        sudokuGrid = new GridCell[GRID_SIZE][GRID_SIZE];

        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                sudokuGrid[i][j] = new GridCell();
                sudokuGridPane.add(sudokuGrid[i][j], i, j);
            }
        }
    }


    public GridCell[][] getSudokuGrid() {
        return sudokuGrid;
    }

}
