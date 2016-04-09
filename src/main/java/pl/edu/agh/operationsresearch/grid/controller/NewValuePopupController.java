package pl.edu.agh.operationsresearch.grid.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import pl.edu.agh.operationsresearch.grid.model.GridCell;
import pl.edu.agh.operationsresearch.utils.view.InvalidTextFieldException;
import pl.edu.agh.operationsresearch.utils.view.ValidatedTextField;

public class NewValuePopupController {

	@FXML
	private ValidatedTextField textField;

	private GridCell currentGridCell;
	private Stage popupStage;

	@FXML
	private void handleOKButtonClick(ActionEvent actionEvent) {
		int value = 0;
		boolean valid = true;

		if (textField.getText().trim().length() != 0) {
			try {
				value = getNewValue();
			} catch (InvalidTextFieldException e) {
				valid = false;
			}
		}

		if (valid) {
			currentGridCell.setValue(value);
			popupStage.close();
		}
	}

	@FXML
	private void handleCancelButtonClick(ActionEvent actionEvent) {
		popupStage.close();
	}

	private int getNewValue() throws InvalidTextFieldException {
		int value;

		try {
			value = Integer.parseInt(textField.getText());
		} catch (NumberFormatException e) {
			throw new InvalidTextFieldException(textField);
		}

		if (!(value > 0 && value < 10)) {
			throw new InvalidTextFieldException(textField);
		}

		return value;
	}

	public void setPopupStage(Stage popupStage) {
		this.popupStage = popupStage;
	}

	public void setCurrentGridCell(GridCell currentGridCell) {
		this.currentGridCell = currentGridCell;
	}

}
