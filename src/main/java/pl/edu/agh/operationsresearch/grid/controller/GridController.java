package pl.edu.agh.operationsresearch.grid.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import pl.edu.agh.operationsresearch.grid.model.Grid;
import pl.edu.agh.operationsresearch.grid.model.GridCell;
import pl.edu.agh.operationsresearch.grid.model.GridConstants;

public class GridController implements Initializable {
    @FXML
    private GridPane gridPane;

    private GridCell[][] gridCells;

    private Grid grid;

    public void initialize(URL location, ResourceBundle resources) {
        gridCells = new GridCell[GridConstants.GRID_SIZE][GridConstants.GRID_SIZE];
        grid = new Grid();

        for (int row = 0; row < GridConstants.GRID_SIZE; row++) {
            for (int col = 0; col < GridConstants.GRID_SIZE; col++) {
                gridCells[row][col] = new GridCell(this, row, col);
                gridPane.add(gridCells[row][col], col, row);
            }
        }

        setGridStyle();
    }

    private void setGridStyle() {
        int lastCoordinate = GridConstants.GRID_SIZE - 1;

        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                String borderColorStyle = "-fx-border-color: gray";
                gridCells[3*i][3*j].setStyle("-fx-border-width: 2 0 0 2;" + borderColorStyle);
                gridCells[3*i][3*j + 1].setStyle("-fx-border-width: 2 0 0 0;" + borderColorStyle);
                gridCells[3*i][3*j + 2].setStyle("-fx-border-width: 2 2 0 0;" + borderColorStyle);
                gridCells[3*i + 1][3*j].setStyle("-fx-border-width: 0 0 0 2;" + borderColorStyle);
                gridCells[3*i + 1][3*j + 1].setStyle("-fx-border-width: 0 0 0 0;");
                gridCells[3*i + 1][3*j + 2].setStyle("-fx-border-width: 0 2 0 0;" + borderColorStyle);
                gridCells[3*i + 2][3*j].setStyle("-fx-border-width: 0 0 2 2;" + borderColorStyle);
                gridCells[3*i + 2][3*j + 1].setStyle("-fx-border-width: 0 0 2 0;" + borderColorStyle);
                gridCells[3*i + 2][3*j + 2].setStyle("-fx-border-width: 0 2 2 0;" + borderColorStyle);
            }
        }
    }

    public void setGrid(Grid grid) {
        int value;
        this.grid = grid;

        for (int row = 0; row < GridConstants.GRID_SIZE; row++) {
            for (int col = 0; col < GridConstants.GRID_SIZE; col++) {
                value = grid.get(row, col);
                gridCells[row][col].setValue(value);
            }
        }
    }

    public Grid getGrid() {
        return grid;
    }

    public boolean set(int row, int col, int value) {
        if (grid.set(row, col, value)) {
            gridCells[row][col].setValue(value);
            return true;
        } else {
            return false;
        }
    }
}
