package org.ensak.espace_citoyen.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import org.ensak.espace_citoyen.Main;
import org.ensak.espace_citoyen.metier.beans.Procedure;
import org.ensak.espace_citoyen.metier.beans.ProcedureSave;
import org.ensak.espace_citoyen.metier.beansManager.LoginManager;
import org.ensak.espace_citoyen.metier.beansManager.ProcedureManager;
import org.ensak.espace_citoyen.modele.Document;
import org.ensak.espace_citoyen.utils.CastFrom;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LancerProcedureDepot implements Initializable {
    public Label nomProc;
    public TableView<Document> tableDocs;
    public TableColumn<Document,String> numero;
    public TableColumn<Document,String> nomDoc;
    public TableColumn<Document, Button> fichier;
    public Button retour;
    public Button lancer;
    public Label etat;
    public Label dateDebut;
    public Label numProc;
    public static int id =0;
    private ProcedureSave procedureSave = new ProcedureSave();
    private int lengthDoc;


    /**
     * methode qui sera exécutée au moment de l'affichage de la vue
     * Lancer procedures
     * @param location
     * @param resources
     */

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setCellValueFactory();
        load(id);
        procedureSave.setId_procedure(id);
        procedureSave.setCIN(LoginManager.dataCitoyen().getCin());
    }

    /**
     * cette methode permettra simplement de charger les données
     * de la base de données au tableview
     */
    public void load(int id)
    {
        ObservableList<Document> dataDocumentView = FXCollections.observableArrayList();
        Procedure procedure = ProcedureManager.getProcedureById(id);
        lengthDoc = procedure.getDocuments().size();
        procedureSave.setNom(procedure.getNom());
        nomProc.setText(procedure.getNom());
        for (org.ensak.espace_citoyen.metier.beans.Document document: procedure.getDocuments())
        {
            Document document1 = CastFrom.beansToModele(document);
            document1.getDocument().setOnAction(event -> {
                String chemin = null;
                FileChooser fileChooser = new FileChooser();
                List<File> files = fileChooser.showOpenMultipleDialog(null);
                for(int i=0; i < files.size();i++)
                    chemin = files.get(i).getAbsolutePath();
                org.ensak.espace_citoyen.metier.beans.Document document2 = new org.ensak.espace_citoyen.metier.beans.Document();
                document2.setNumero(document.getNumero());
                document2.setNom(document.getNom());
                document2.setUrl(chemin);
                procedureSave.getDocuments().add(document2);
                if(!chemin.isEmpty())
                    document1.getDocument().setStyle("-fx-background-color:green");
                else
                {
                    return;
                }
            });
            dataDocumentView.add(document1);
        }
        tableDocs.setItems(dataDocumentView);
    }


    private void setCellValueFactory(){
        numero.setCellValueFactory(new PropertyValueFactory<>("numero"));
        nomDoc.setCellValueFactory(new PropertyValueFactory<>("nom"));
        fichier.setCellValueFactory(new PropertyValueFactory<>("document"));
    }
    /**
     * il s'agit d'une methode static qu'un autre controller pourra utiliser pour
     * afficher la vue depot de documents
     */
    public static void lancerProcedure(int id) {
        setId(id);
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource(
                    "/views/lancerProcedureDepot.fxml"));
            MenuController.main.setCenter(loader.load());

        } catch (IOException e) {
            Logger.getLogger(LancerProcedureDepot.class.getName()).log(
                    Level.SEVERE, null, e);
        }
    }

    public void onRetour(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/views/lancerProcedureAccueil.fxml"));
            MenuController.main.setCenter(loader.load());

        } catch (IOException e) {
            Logger.getLogger(LancerProcedureDepot.class.getName()).log(Level.SEVERE, null, e);
        }
    }


    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        LancerProcedureDepot.id = id;
    }

    /**
     * methode permettant d'enregistrer une procedure
     * @param actionEvent
     * @throws JsonProcessingException
     */
    public void saveProcedure(ActionEvent actionEvent) throws JsonProcessingException {

        if (testSendAllDocuments5(procedureSave.getDocuments()) || testExist(procedureSave))
        {
            return;
        }
        else {
            int alert = JOptionPane.showConfirmDialog(null, "Voulez vous vraiment lancer cette procedure??" , "Enregistrer", JOptionPane.YES_NO_OPTION);
            if (alert == 0) {

                if (ProcedureManager.saveData(procedureSave)) {
                    JOptionPane.showMessageDialog(null, "votre procedure à été lancé");
                }
            }
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(Main.class.getResource("/views/lancerProcedureAccueil.fxml"));
                MenuController.main.setCenter(loader.load());

            } catch (IOException e) {
                Logger.getLogger(LancerProcedureDepot.class.getName()).log(Level.SEVERE, null, e);
            }
        }


    }

    /**
     * cette methode permet de tester si tous les documents ont été envoyés
     * @param documents
     * @return
     */
    private boolean testSendAllDocuments5(ArrayList<org.ensak.espace_citoyen.metier.beans.Document> documents)
    {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        int taille = documents.size();

        if(documents.isEmpty())
        {
            errors(errorAlert);
            return true;
        }
        else {
            for (org.ensak.espace_citoyen.metier.beans.Document document: documents)
            {
                if(document.getUrl() == null || ( taille != lengthDoc ))
                {
                     errors(errorAlert);
                     return true;
                }
            }
        }
        return  false;

    }

    private boolean testExist(ProcedureSave procedureSave)
    {
        if(ProcedureManager.isExistProcedure(procedureSave.getId_procedure()))
        {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorsExist(errorAlert);
            return true;
        }
        return false;
    }

    /**
     * methode qui gerer les exeptions relative a la vue
     * @param alert
     */
    private void errors(Alert alert)
    {
        alert.setHeaderText("ERROR ");
        alert.setContentText("Veillez upload tous les fichiers");
        alert.showAndWait();
    }
    private void errorsExist(Alert alert)
    {
        alert.setHeaderText("ERROR ");
        alert.setContentText("La procedure est cours impossible de l'enregister a nouveau");
        alert.showAndWait();
    }
}
