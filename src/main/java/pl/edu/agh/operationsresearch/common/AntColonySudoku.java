package pl.edu.agh.operationsresearch.common;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AntColonySudoku extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        prepareWindow(primaryStage);
    }

    private void prepareWindow(Stage primaryStage) throws IOException {
        primaryStage.setTitle("Ant Colony Sudoku Solver");

        Parent root = FXMLLoader.load(getClass().getResource(
                "view/MainWindowView.fxml"));
        primaryStage.setScene(new Scene(root, 800, 500));
        primaryStage.show();
    }

}
