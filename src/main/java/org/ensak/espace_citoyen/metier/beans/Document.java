package org.ensak.espace_citoyen.metier.beans;

import javafx.stage.FileChooser;

public class Document {
    private String numero;
    private String nom;
    private String url;


    public Document() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Document(String numero, String nom, String url) {
        this.numero = numero;
        this.nom = nom;
        this.url = url;
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
}
