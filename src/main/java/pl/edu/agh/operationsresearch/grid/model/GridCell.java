package pl.edu.agh.operationsresearch.grid.model;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import pl.edu.agh.operationsresearch.grid.controller.NewValuePopupController;

import java.io.IOException;

public class GridCell extends StackPane {

    private Text textValue;
    private int value;


    public GridCell() {
        value = 0;
        textValue = new Text("");
        textValue.setStyle("-fx-font-size: 20px;");

        this.setOnMouseClicked(new MouseClickOnGridHandler(this));
        this.getChildren().addAll(new Rectangle(50, 50, Color.valueOf("wheat")), textValue);
    }


    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
        updateView();
    }


    private void updateView() {
        if(value == 0) {
            textValue.setText("");
            return;
        }

        textValue.setText(String.valueOf(value));
    }


    private class MouseClickOnGridHandler implements EventHandler<MouseEvent> {

        private GridCell gridCell;

        public MouseClickOnGridHandler(GridCell gridCell) {
            this.gridCell = gridCell;
        }

        public void handle(MouseEvent event) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/pl/edu/agh/operationsresearch/grid/view/NewValuePopupView.fxml"));
            Stage popupStage = new Stage();
            Parent popupRoot = null;

            try {
                popupRoot = loader.load();
            } catch (IOException exc) {
                exc.printStackTrace();
            }

            if(popupRoot != null) {
                popupStage.setScene(new Scene(popupRoot));
            }

            NewValuePopupController popupController = loader.getController();
            popupController.setCurrentGridCell(gridCell);
            popupController.setPopupStage(popupStage);

            popupStage.show();
        }

    }

}
