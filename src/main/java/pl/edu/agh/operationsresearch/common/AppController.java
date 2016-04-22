package pl.edu.agh.operationsresearch.common;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import pl.edu.agh.operationsresearch.algorithm.controller.AlgorithmController;
import pl.edu.agh.operationsresearch.grid.controller.GridController;

public class AppController implements Initializable {
    @FXML
    private GridController gridController; 
    
    @FXML
    private AlgorithmController algorithmController;

    public void initialize(URL arg0, ResourceBundle arg1) {
        algorithmController.setGridController(gridController);
    }

}
