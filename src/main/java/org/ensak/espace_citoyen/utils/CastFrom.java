package org.ensak.espace_citoyen.utils;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.ensak.espace_citoyen.modele.Document;
import org.ensak.espace_citoyen.modele.Procedure;
import org.ensak.espace_citoyen.modele.ProcedureLance;

public class CastFrom {

    public static Procedure beansToModele(org.ensak.espace_citoyen.metier.beans.Procedure procedure)
    {
        Procedure procedure1 = new Procedure();
        procedure1.setNumeroProcedure(String.valueOf(procedure.getNumero()));
        procedure1.setNomProcedure(procedure.getNom());
        procedure1.setNomDivision(procedure.getDivision().getNom());
        return procedure1;
    }

    public static Document beansToModele(org.ensak.espace_citoyen.metier.beans.Document document)
    {
        Image img = new Image("file:///C:/Users/Lenovo/Desktop/INSA/Candidature INSA/Favorites/upload.png");
        Document document1 = new Document();
        document1.setNom(document.getNom());
        document1.setNumero(document.getNumero());
        document1.getDocument().setText(document.getNom()+".png");
        ImageView im=new ImageView(img);
        im.setFitHeight(20);
        im.setFitWidth(30);
        document1.getDocument().setGraphic(im);
        return document1;
    }

    /**
     * tranformermer le beans d'une procedure lance en son modele
     * @param procedureLance
     * @return
     */

    public static ProcedureLance beansToModele(org.ensak.espace_citoyen.metier.beans.ProcedureLance procedureLance)
    {
        ProcedureLance procedureLance1 = new ProcedureLance();
        procedureLance1.setNumero(String.valueOf(procedureLance.getId_procedure()));
        procedureLance1.setNom(procedureLance.getNom());
        procedureLance1.setDateDebut(procedureLance.getDateDebut());
        procedureLance1.setDateFin(procedureLance.getDateFin());
        procedureLance1.setEtat(procedureLance.getEtat());
        String etat = procedureLance.getEtat();
        /**
         * en fp,ction de l'etat dans lequel jer suis je choisi un bouton
         * pour un traitement particulier
         */
        switch (etat)
        {
            case ("Terminé") :
                procedureLance1.getAction().setText("Remove");
                procedureLance1.getAction().setStyle("-fx-background-color:green");
                break;
            case ("En cours de Traitement"):
                procedureLance1.getAction().setText("Consulter");
                procedureLance1.getAction().setStyle("-fx-background-color: #FF9900");
                break;
            case ("Rejeté"):
                procedureLance1.getAction().setText("Remove");
                procedureLance1.getAction().setStyle("-fx-background-color: red");
                break;
            case ("En attente de traitement"):
                procedureLance1.getAction().setText("Consulter");
                procedureLance1.getAction().setStyle("-fx-background-color: #0adec5");
                break;
        }
        return procedureLance1;
    }
}
