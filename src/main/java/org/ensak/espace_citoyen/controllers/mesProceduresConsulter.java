package org.ensak.espace_citoyen.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import org.ensak.espace_citoyen.Main;
import org.ensak.espace_citoyen.metier.beans.Etape;
import org.ensak.espace_citoyen.metier.beansManager.ProcedureManager;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class mesProceduresConsulter implements Initializable {

    public Label nomProc;
    public Label numProc;
    public Label dateDebut;
    public Label etat;
    private static String numero;
    private static String nom;
    private static String date;
    private static String state;
    public TabPane tabPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        etat.setText(state);
        dateDebut.setText(date);
        numProc.setText(numero);
        nomProc.setText(nom);
        // create a tabpane
        for (String s: ProcedureManager.getAllEtapesProcedure(Integer.valueOf(numero)))
        {
            Tab tab = new Tab(s);
            if(ProcedureManager.isEtapeActuelle(s))
            {
                tab.setClosable(false);
                tab.setDisable(false);
                tab.setStyle("-fx-background-color: #FF9900");
            }else
            {
                tab.setDisable(true);
                traitement(tab,s,Integer.valueOf(numero));
            }
            tabPane.getTabs().add(tab);
            tabPane.setTabMaxHeight(300);
            tabPane.setTabMaxWidth(300);
        }
    }

    private void traitement(Tab tab,String s,int num)
    {
        ArrayList<Etape> etapes = ProcedureManager.getEtapestraites(num);
        for (Etape etape : etapes)
        {
            if (etape.getNom().equals(s))
            {
                tab.setDisable(false);
                Label label = new Label(etape.getRapport());
                tab.setContent(label);
                tab.setStyle("-fx-background-color:green");
            }
        }
    }

    public void onRetour(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/views/mesProceduresAccueil.fxml"));
            MenuController.main.setCenter(loader.load());

        } catch (IOException e) {
            Logger.getLogger(mesProceduresConsulter.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public static void getValues(String numerop, String nomp, String datep, String etat)
    {
        nom = nomp;
        state = etat;
        date = datep;
        numero =numerop;
    }

}
