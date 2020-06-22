package org.ensak.espace_citoyen.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.ensak.espace_citoyen.Main;
import org.ensak.espace_citoyen.metier.beansManager.LoginManager;
import org.ensak.espace_citoyen.metier.beansManager.ProcedureManager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AccueilController implements Initializable {
    public Button lancer;
    public Label nbProcedures;
    public Label nbTerminees;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nbProcedures.setText(ProcedureManager.nombreproceduresLances(LoginManager.dataCitoyen().getCin())+" Procédures Lancées");
        nbTerminees.setText(ProcedureManager.nombreDeProceduresTermine(LoginManager.dataCitoyen().getCin())+" Procédures Terminées");
    }

    public void OnLancer(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/views/mesProceduresAccueil.fxml"));
            MenuController.main.setCenter(loader.load());

        } catch (IOException e) {
            Logger.getLogger(AccueilController.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
