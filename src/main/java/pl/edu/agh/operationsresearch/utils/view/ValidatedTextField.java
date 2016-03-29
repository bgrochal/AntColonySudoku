package pl.edu.agh.operationsresearch.utils.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class ValidatedTextField extends javafx.scene.control.TextField {

    public ValidatedTextField() {
        super();
        this.focusedProperty().addListener(new ValidatedTextFieldFocusListener(this));
    }

    public ValidatedTextField(String text) {
        super(text);
        this.focusedProperty().addListener(new ValidatedTextFieldFocusListener(this));
    }


    private class ValidatedTextFieldFocusListener implements ChangeListener<Boolean> {

        private ValidatedTextField textField;

        public ValidatedTextFieldFocusListener(ValidatedTextField textField) {
            this.textField = textField;
        }

        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            if(newValue) {
                textField.setStyle("-fx-background-color: white;");
            }
        }

    }

}
