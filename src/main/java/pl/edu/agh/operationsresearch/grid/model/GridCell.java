package pl.edu.agh.operationsresearch.grid.model;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import pl.edu.agh.operationsresearch.grid.controller.GridController;
import pl.edu.agh.operationsresearch.grid.controller.NewValuePopupController;

import java.io.IOException;

public class GridCell extends StackPane implements EventHandler<MouseEvent> {

    private Text value;
    private GridController ctrl;
    private int row;
    private int col;

    public GridCell(GridController ctrl, int row, int col) {
        value = new Text("");
        value.setStyle("-fx-font-size: 20px;");

        this.ctrl = ctrl;
        this.row = row;
        this.col = col;

        this.setOnMouseClicked(this);
        this.getChildren().addAll(
                new Rectangle(50, 50, Color.valueOf("wheat")), value);
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void setValue(int value) {
        if (value == 0) {
            this.value.setText("");
        } else {
            this.value.setText(String.valueOf(value));
            this.value.setFill(selectFontColor());
        }
    }

    private Paint selectFontColor() {
        Paint paint;

        switch(this.value.getText()) {
            case "1":
                paint = Paint.valueOf("LIMEGREEN");
                break;
            case "2":
                paint = Paint.valueOf("DARKTURQUOISE");
                break;
            case "3":
                paint = Paint.valueOf("PERU");
                break;
            case "4":
                paint = Paint.valueOf("PURPLE");
                break;
            case "5":
                paint = Paint.valueOf("DEEPPINK");
                break;
            case "6":
                paint = Paint.valueOf("BLUE");
                break;
            case "7":
                paint = Paint.valueOf("CRIMSON");
                break;
            case "8":
                paint = Paint.valueOf("DARKGREEN");
                break;
            case "9":
                paint = Paint.valueOf("DIMGRAY");
                break;
            default:
                paint = Paint.valueOf("BLACK");
        }

        return paint;
    }

    public void handle(MouseEvent event) {
        FXMLLoader loader = new FXMLLoader(
                getClass()
                        .getResource(
                                "/pl/edu/agh/operationsresearch/grid/view/NewValuePopupView.fxml"));
        Stage popupStage = new Stage();
        Parent popupRoot = null;

        try {
            popupRoot = loader.load();
        } catch (IOException exc) {
            exc.printStackTrace();
        }

        if (popupRoot != null) {
            popupStage.setScene(new Scene(popupRoot));
        }

        NewValuePopupController popupController = loader.getController();
        popupController.setParameters(value.getText(), ctrl, row, col);
        popupController.setPopupStage(popupStage);

        popupStage.show();
    }
}
