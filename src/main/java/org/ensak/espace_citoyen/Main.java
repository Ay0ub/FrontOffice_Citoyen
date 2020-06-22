package org.ensak.espace_citoyen;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * JavaFX App
 */
public class Main extends Application {

    public static Stage MainStage;
    @Override
    public void start(Stage stage) throws Exception {
        MainStage = stage;
        Parent root = FXMLLoader.load(getClass().getResource("/views/login.fxml"));
        MainStage.setTitle("Citoyen Login");
        MainStage.setScene(new Scene(root, 500, 200));
        MainStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}