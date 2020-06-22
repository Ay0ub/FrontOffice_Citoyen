package org.ensak.espace_citoyen.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.ensak.espace_citoyen.Main;
import org.ensak.espace_citoyen.metier.beans.Procedure;
import org.ensak.espace_citoyen.metier.beansManager.LoginManager;
import org.ensak.espace_citoyen.metier.beansManager.ProcedureManager;
import org.ensak.espace_citoyen.modele.Division;
import org.ensak.espace_citoyen.modele.ProcedureLance;
import org.ensak.espace_citoyen.utils.CastFrom;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class mesProceduresAccueil implements Initializable {

    public TextField textSearch;
    public Button onSearch;
    public TableView<ProcedureLance> tableProcedures;
    public TableColumn<ProcedureLance,String> numero;
    public TableColumn<ProcedureLance,String> nomProc;
    public TableColumn<ProcedureLance,String> dateDebut;
    public TableColumn<ProcedureLance,String> dateFin;
    public TableColumn<ProcedureLance,String> etat;
    public TableColumn<ProcedureLance,Button> actions;
    private ArrayList<org.ensak.espace_citoyen.metier.beans.ProcedureLance> procedures = new ArrayList<>();
    private ObservableList<org.ensak.espace_citoyen.modele.ProcedureLance> dataProcedureView = FXCollections.observableArrayList();
    private String jeton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setCellValueFactory();
        load();
    }
    /**
     * methode qui permet d'assigner les valeur de l'objet procedure
     * qui seront inserées a l'interieur du TableView
     */
    private void setCellValueFactory(){
        numero.setCellValueFactory(new PropertyValueFactory<>("numero"));
        nomProc.setCellValueFactory(new PropertyValueFactory<>("nom"));
        dateDebut.setCellValueFactory(new PropertyValueFactory<>("dateDebut"));
        dateFin.setCellValueFactory(new PropertyValueFactory<>("dateFin"));
        etat.setCellValueFactory(new PropertyValueFactory<>("etat"));
        actions.setCellValueFactory(new PropertyValueFactory<>("action"));
    }

    /**
     * cette methode permettra simplement de charger les données
     * de la base de données au tableview
     */
    public void load()
    {

        procedures = ProcedureManager.getAllProceduresLance(LoginManager.dataCitoyen().getCin());
        for(org.ensak.espace_citoyen.metier.beans.ProcedureLance procedure : procedures )
        {
            //On fait la conversion du metier ou modéle
            org.ensak.espace_citoyen.modele.ProcedureLance procedure1 = CastFrom.beansToModele(procedure);
            //Ajout de l'action sur le bouton
            traitement(procedure1);
            dataProcedureView.add(procedure1);//On l'ajoute dans le tableau
        }
        tableProcedures.setItems(dataProcedureView);
    }

    private void traitement(ProcedureLance procedureLance)
    {
        String etat = procedureLance.getEtat();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        /**
         * en fp,ction de l'etat dans lequel jer suis je choisi un bouton
         * pour un traitement particulier
         */
        switch (etat) {
            case ("Terminé"):
                procedureLance.getAction().setOnAction(event -> {
                    alert.setHeaderText("REPONSE");
                    alert.setContentText("Procedure terminée votre document vous sera envoyé par la poste");
                    alert.showAndWait();
                    ProcedureManager.removeProcedure(Integer.valueOf(procedureLance.getNumero()));
                });

                break;
            case ("En cours de Traitement"):
                procedureLance.getAction().setOnAction(event -> {
                    TextInputDialog jeton = new TextInputDialog("Entrer votre Jeton");
                    jeton.setTitle("JETON");
                    jeton.setHeaderText("Enter votre Jeton:");
                    jeton.setContentText("Numero de Jeton:");
                    Optional<String> result = jeton.showAndWait();
                    result.ifPresent(name->{
                        this.jeton = name;
                    });
                    mesProceduresConsulter.getValues(procedureLance.getNumero(),procedureLance.getNom(),
                            procedureLance.getDateDebut(),procedureLance.getEtat());
                    redirection(this.jeton,LoginManager.dataCitoyen().getCin());

                });
                break;
            case ("Rejeté"):
                procedureLance.getAction().setOnAction(event -> {
                    alert.setHeaderText("REPONSE");
                    alert.setContentText("Votre demande a été rejetée les motifs vous seront envoyés par mail");
                    alert.showAndWait();
                    ProcedureManager.removeProcedure(Integer.valueOf(procedureLance.getNumero()));
                });

                break;
            case ("En attente de traitement"):
                procedureLance.getAction().setOnAction(event -> {
                    alert.setHeaderText("REPONSE");
                    alert.setContentText("Veillez consuter votre boite mail un code vous sera envoyé");
                    alert.showAndWait();
                });
                break;
        }
    }

    private void redirection(String jeton,String cin)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        if (jeton.isEmpty())
        {
            alert.setHeaderText("REPONSE");
            alert.setContentText("Veillez entrer votre code");
            alert.showAndWait();
        }
        else {
            if(ProcedureManager.testJeton(jeton,cin))
            {
                try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(Main.class.getResource("/views/mesProceduresConsulter.fxml"));
                MenuController.main.setCenter(loader.load());
            } catch (IOException e) {
                Logger.getLogger(mesProceduresAccueil.class.getName()).log(Level.SEVERE, null,e);
            }
            }
            else
            {
                alert.setHeaderText("REPONSE");
                alert.setContentText("Ce code est incorrect");
                alert.showAndWait();
            }
        }
    }


    public void searchProcedure(ActionEvent actionEvent) {

        String procedureName  = this.textSearch.getText();
        procedures.clear();
        procedures = ProcedureManager.getAllProceduresLance(LoginManager.dataCitoyen().getCin());
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        if (procedureName.isEmpty())
        {
            errorAlert.setHeaderText("ERROR ");
            errorAlert.setContentText("Veillez entrer la procedure recherchée");
            errorAlert.showAndWait();
        }
        else {
            boolean exist = false;
            for(org.ensak.espace_citoyen.metier.beans.ProcedureLance procedure : procedures )
            {
                String nomp = procedure.getNom();
                if(nomp.equals(procedureName))
                {
                    exist = true;
                    dataProcedureView.clear();
                    //On fait la conversion du metier ou modéle
                    org.ensak.espace_citoyen.modele.ProcedureLance procedure1 = CastFrom.beansToModele(procedure);
                    traitement(procedure1);
                    dataProcedureView.add(procedure1);//On l'ajoute dans le tableau
                    break;
                }
            }
            if (!exist)
            {
                errorAlert.setHeaderText("REPONSE");
                errorAlert.setContentText("La procédure entrée est inexistante");
                errorAlert.showAndWait();
            }
        }
        tableProcedures.setItems(dataProcedureView);
    }
}
