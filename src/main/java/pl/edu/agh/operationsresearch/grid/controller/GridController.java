package pl.edu.agh.operationsresearch.grid.controller;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import pl.edu.agh.operationsresearch.grid.model.GridCell;

public class GridController implements Initializable {

    private static final int GRID_SIZE = 9;

    @FXML
    private GridPane sudokuGridPane;

    private GridCell[][] sudokuGrid;
    
    private static GridController instance = null;
    
    public static GridController getInstance(){
        return instance;
    }

    public void initialize(URL location, ResourceBundle resources) {
        sudokuGrid = new GridCell[GRID_SIZE][GRID_SIZE];

        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                sudokuGrid[i][j] = new GridCell();
                sudokuGridPane.add(sudokuGrid[i][j], i, j);
            }
        }
        
        instance = this;
    }

    public boolean isValid() {
        
        for (int i = 0; i < 9; i++) {
            if (!validateRow(i))
                return false;
        }

        for (int i = 0; i < 9; i++) {
            if (!validateColumn(i))
                return false;
        }

        for (int i = 0; i < 9; i++) {
            if (!validateSquare(i % 3, (i / 3) * 3))
                return false;
        }

        return true;
    }

    private boolean validateRow(int n) {
        List<Integer> values = new LinkedList<Integer>();
        int i, j, value;

        j = n;

        for (i = 0; i < GRID_SIZE; i++) {
            value = sudokuGrid[i][j].getValue();

            if (value != 0) {
                if (values.contains(value))
                    return false;
                values.add(value);
            }
        }

        return true;
    }

    private boolean validateColumn(int n) {
        List<Integer> values = new LinkedList<Integer>();
        int i, j, value;

        i = n;

        for (j = 0; j < GRID_SIZE; j++) {
            value = sudokuGrid[i][j].getValue();

            if (value != 0) {
                if (values.contains(value))
                    return false;
                values.add(value);
            }
        }

        return true;
    }

    private boolean validateSquare(int i, int j) {
        int map[][] = { { 0, 0 }, { 1, 0 }, { 2, 0 }, { 0, 1 }, { 1, 1 },
                { 2, 1 }, { 0, 2 }, { 1, 2 }, { 2, 2 } };
        int offset[];

        List<Integer> values = new LinkedList<Integer>();
        int value;

        for (int k = 0; k < GRID_SIZE; k++) {
            offset = map[k];
            value = sudokuGrid[i + offset[0]][j + offset[1]].getValue();

            if (value != 0) {
                if (values.contains(value))
                    return false;
                values.add(value);
            }
        }

        return true;
    }

    public GridCell[][] getSudokuGrid() {
        return sudokuGrid;
    }

}
