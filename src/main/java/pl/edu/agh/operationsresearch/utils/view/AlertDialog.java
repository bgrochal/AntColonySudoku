package pl.edu.agh.operationsresearch.utils.view;

import javafx.scene.control.Alert;

public class AlertDialog extends Alert {
    public AlertDialog(String title, String header, String content) {
        super(AlertType.INFORMATION);
        setTitle(title);
        setHeaderText(header);
        setContentText(content);
        showAndWait();
    }
}
