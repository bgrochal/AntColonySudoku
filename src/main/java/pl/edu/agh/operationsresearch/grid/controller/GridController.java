package pl.edu.agh.operationsresearch.grid.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import pl.edu.agh.operationsresearch.grid.model.Grid;
import pl.edu.agh.operationsresearch.grid.model.GridCell;

public class GridController implements Initializable {
    @FXML
    private GridPane gridPane;

    private GridCell[][] gridCells;
    
    private Grid grid;

    public void initialize(URL location, ResourceBundle resources) {
        gridCells = new GridCell[Grid.GRID_SIZE][Grid.GRID_SIZE];
        grid = new Grid();

        for (int i = 0; i < Grid.GRID_SIZE; i++) {
            for (int j = 0; j < Grid.GRID_SIZE; j++) {
                gridCells[i][j] = new GridCell(this, i, j);
                gridPane.add(gridCells[i][j], i, j);
            }
        }
    }
    
    public void setGrid(Grid grid){
        int value;
        this.grid = grid;
        
        for (int i = 0; i < Grid.GRID_SIZE; i++) {
            for (int j = 0; j < Grid.GRID_SIZE; j++) {
                value = grid.get(i, j);
                gridCells[i][j].setValue(value);
            }
        }
    }
    
    public Grid getGrid(){
        return grid;
    }
    
    public boolean set(int row, int col, int value){
        if(grid.set(row, col, value)){
            gridCells[row][col].setValue(value);
            return true;
        } else {
            return false;
        }
    }
}
