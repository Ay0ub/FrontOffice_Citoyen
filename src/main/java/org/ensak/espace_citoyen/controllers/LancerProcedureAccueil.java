package org.ensak.espace_citoyen.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.ensak.espace_citoyen.metier.beansManager.ProcedureManager;
import org.ensak.espace_citoyen.modele.Division;
import org.ensak.espace_citoyen.modele.Procedure;
import org.ensak.espace_citoyen.utils.CastFrom;

import java.net.URL;
import java.nio.FloatBuffer;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class LancerProcedureAccueil implements Initializable {
    public ComboBox<Division> division;
    public TextField textSearch;
    public Button onSearch;
    public TableView<Procedure> tableProcedures;
    public TableColumn<Procedure, Button> lancer;
    public TableColumn<Procedure, String> nomDiv;
    public TableColumn<Procedure,String> nomProc;
    public TableColumn<Procedure,String> numero;
    ArrayList<org.ensak.espace_citoyen.metier.beans.Procedure> procedures = new ArrayList<>();
    ObservableList<Procedure> dataProcedureView = FXCollections.observableArrayList();


    /**
     * methode d'entrée du controlleur lancer procedure.
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setCellValueFactory();
        load();
        onChangeDivision();
    }
    /**
     * methode qui permet d'assigner les valeur de l'objet procedure
     * qui seront inserées a l'interieur du TableView
     */
    private void setCellValueFactory(){
        numero.setCellValueFactory(new PropertyValueFactory<>("numeroProcedure"));
        nomProc.setCellValueFactory(new PropertyValueFactory<>("nomProcedure"));
        nomDiv.setCellValueFactory(new PropertyValueFactory<>("nomDivision"));
        lancer.setCellValueFactory(new PropertyValueFactory<>("lancerProcedure"));
    }

    /**
     * cette methode permettra simplement de charger les données
     * de la base de données au tableview
     */
    public void load()
    {
        dataProcedureView.clear();
        procedures.clear();
        ObservableList<Division> dataDivisions = division.getItems();
        procedures = ProcedureManager.getAllProcedures();
        for(org.ensak.espace_citoyen.metier.beans.Procedure procedure : procedures )
        {
            Division division = new Division(String.valueOf(procedure.getDivision().getId()),
                    procedure.getDivision().getNom());
            dataDivisions.add(division);
            //On fait la conversion du metier ou modéle
            Procedure procedure1 = CastFrom.beansToModele(procedure);
            //Ajout de l'action sur le bouton
            procedure1.getLancerProcedure().setOnAction(event ->
                    LancerProcedureDepot.lancerProcedure(procedure.getNumero()));
            dataProcedureView.add(procedure1);//On l'ajoute dans le tableau
        }
        tableProcedures.setItems(dataProcedureView);
    }

    /**
     * cette methode permet de pouvoir afficher les procedure en fonction de la division
     * a laquelle la procedure appartient
     * @param division elle prend en paramettre la division
     */
    private void loadDivision(Division division)
    {
        dataProcedureView.clear();
        procedures.clear();
        procedures = ProcedureManager.getProceduresDivision(division.getNumero());
        for(org.ensak.espace_citoyen.metier.beans.Procedure procedure : procedures )
        {
            Procedure procedure1 = CastFrom.beansToModele(procedure);
            //Ajout de l'action sur le bouton
            procedure1.getLancerProcedure().setOnAction(event ->
                    LancerProcedureDepot.lancerProcedure(procedure.getNumero()));
            dataProcedureView.add(procedure1);//On l'ajoute dans le tableau
        }
        tableProcedures.setItems(dataProcedureView);
    }

    /**
     * cette methode represente l'action qui sera effectué lorsque
     * l'utilisateur fera un choix dans le comboBox des divisions
     */

    public void onChangeDivision()
    {
        this.division.valueProperty().addListener((ov, oldDivision, newDivision) -> {
                   //On charge les nouvelles informations
                   loadDivision(newDivision);
              });
    }

    /**
     * l'action qui sera effectiuée lorsua l'utilisateur
     * voudra faire le recherche d'une procedure en la saisissant dns la textfield
     * @param actionEvent
     */

    public void searchProcedure(ActionEvent actionEvent) {
        String procedureName  = this.textSearch.getText();
        procedures.clear();
        procedures = ProcedureManager.getAllProcedures();
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        if (procedureName.isEmpty())
        {
            errorAlert.setHeaderText("ERROR ");
            errorAlert.setContentText("Veillez entrer la procedure recherchée");
            errorAlert.showAndWait();
        }
        else {
            boolean exist = false;
            for(org.ensak.espace_citoyen.metier.beans.Procedure procedure : procedures )
            {
                String nomp = procedure.getNom();
                if(nomp.equals(procedureName))
                {
                    exist = true;
                    dataProcedureView.clear();
                    //On fait la conversion du metier ou modéle
                    Procedure procedure1 = CastFrom.beansToModele(procedure);
                    //Ajout de l'action sur le bouton
                    procedure1.getLancerProcedure().setOnAction(event ->
                            LancerProcedureDepot.lancerProcedure(procedure.getNumero()));
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
