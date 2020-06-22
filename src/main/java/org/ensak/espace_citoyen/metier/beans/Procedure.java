package org.ensak.espace_citoyen.metier.beans;

import java.util.ArrayList;

public class Procedure {
    private int numero;
    private String nom;
    private Division division;
    private ArrayList<Document> documents;

    public Procedure() {
    }

    public Procedure(int numero, String nom, Division division, ArrayList<Document> documents) {
        this.numero = numero;
        this.nom = nom;
        this.division = division;
        this.documents = documents;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Division getDivision() {
        return division;
    }

    public void setDivision(Division division) {
        this.division = division;
    }

    public ArrayList<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(ArrayList<Document> documents) {
        this.documents = documents;
    }
}
