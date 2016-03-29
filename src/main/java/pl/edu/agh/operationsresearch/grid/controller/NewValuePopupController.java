package pl.edu.agh.operationsresearch.grid.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import pl.edu.agh.operationsresearch.grid.model.GridCell;
import pl.edu.agh.operationsresearch.utils.view.ValidatedTextField;

public class NewValuePopupController {

    @FXML
    private ValidatedTextField newValueTextField;

    private GridCell currentGridCell;
    private Stage popupStage;


    @FXML
    private void handleOKButtonClick(ActionEvent actionEvent) {
        int newValue;

        if(newValueTextField.getText().trim().length() == 0) {
            currentGridCell.setValue(0);
            popupStage.close();
        }

        try {
            newValue = Integer.parseInt(newValueTextField.getText().trim());
            if(!(newValue > 0 && newValue < 10)) {
                throw new NumberFormatException();
            }
        } catch(NumberFormatException exc) {
            newValueTextField.setStyle("-fx-background-color: red;");
            newValue = 0;
        }

        if(newValue != 0) {
            currentGridCell.setValue(newValue);
            popupStage.close();
        }
    }

    @FXML
    private void handleCancelButtonClick(ActionEvent actionEvent) {
        popupStage.close();
    }


    public void setPopupStage(Stage popupStage) {
        this.popupStage = popupStage;
    }

    public void setCurrentGridCell(GridCell currentGridCell) {
        this.currentGridCell = currentGridCell;
    }

}
