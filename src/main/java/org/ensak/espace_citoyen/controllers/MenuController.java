package org.ensak.espace_citoyen.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.ensak.espace_citoyen.Main;
import org.ensak.espace_citoyen.metier.beans.Citoyen;
import org.ensak.espace_citoyen.metier.beansManager.LoginManager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MenuController implements Initializable {
    public Label cni;
    public Label nom;
    public Label prenom;
    public Button accueil;
    public ImageView Laccueil;
    public Button lancer;
    public ImageView Llancer;
    public Button mesProcedures;
    public ImageView Lprocedures;
    public static BorderPane main;
    public static Stage stage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.cni.setText(LoginManager.dataCitoyen().getCin());
        this.nom.setText(LoginManager.dataCitoyen().getNom());
        this.prenom.setText(LoginManager.dataCitoyen().getPrenom());
        activeBtn(accueil,Laccueil);
    }

    public static void Menu()
    {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/views/menu.fxml"));
            main = loader.load();

            FXMLLoader loader2  = new FXMLLoader();
            loader2.setLocation(Main.class.getResource("/views/accueil.fxml"));
            main.setCenter(loader2.load());

            Scene scene = new Scene(main);
            stage= new Stage();
            stage.setTitle("");
            stage.setScene(scene);
            stage.setResizable(true);
            //stage.setMaximized(true);
            stage.initStyle(StageStyle.DECORATED);
            stage.show();
            Main.MainStage.close();

        } catch (IOException e) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null,e);
        }

    }

    public void logOut(ActionEvent actionEvent) {
        Main.MainStage.show();
        stage.close();
    }

    public void OnAccueil(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/views/accueil.fxml"));
            main.setCenter(loader.load());
        } catch (IOException e) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null,e);
        }
        activeBtn(accueil,Laccueil);
    }

    public void OnLancer(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/views/lancerProcedureAccueil.fxml"));
            main.setCenter(loader.load());
        } catch (IOException e) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null,e);
        }
        activeBtn(lancer,Llancer);
    }

    public void onProceduresEnCours(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/views/mesProceduresAccueil.fxml"));
            main.setCenter(loader.load());
        } catch (IOException e) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null,e);
        }
        activeBtn(mesProcedures,Lprocedures);

    }
    private void activeBtn(Button btn , ImageView img) {

        Button[] btns = {accueil, lancer, mesProcedures};
        ImageView[] imgs = {Laccueil , Llancer , Lprocedures};

        for ( Button b : btns) {
            b.setStyle("-fx-background-color:#073763");
        }

        for(ImageView i : imgs)
            i.setStyle("-fx-fill : #969494");
        btn.setStyle(
                "-fx-background-color: #FF9900;"
        );

        img.setStyle("-fx-fill : #FFFFFF");
    }

}
