package org.ensak.espace_citoyen.modele;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

public class Document {
    private String numero;
    private String nom;
    private Button document;

    public Document() {
        document = new Button();
        document.setStyle( "-fx-background-color: #FFFFFF;");
    }

    public Document(String numero, String nom) {
        this.numero = numero;
        this.nom = nom;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Button getDocument() {
        return document;
    }

    public void setDocument(Button document) {
        this.document = document;
    }
}
