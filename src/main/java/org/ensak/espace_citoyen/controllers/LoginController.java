package org.ensak.espace_citoyen.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.ensak.espace_citoyen.metier.beansManager.LoginManager;

public class LoginController {
    @FXML
    public TextField cni;

    public void login(ActionEvent actionEvent) {
        String cin = cni.getText();
        if (LoginManager.validation(cin))
        {
            MenuController.Menu();
        }
        else
        {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            if (cin.isEmpty()) {

                errorAlert.setHeaderText("Aucune CIN");
                errorAlert.setContentText("Veillez entrer votre CIN");
                errorAlert.showAndWait();
            }
            else
            {
                errorAlert.setHeaderText("Mauvaise CIN");
                errorAlert.setContentText("Veillez entrer la bonne CIN");
                errorAlert.showAndWait();
            }
        }

    }

    public void exit(ActionEvent actionEvent) {
        System.exit(0);
    }
}
