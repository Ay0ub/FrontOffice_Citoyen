package org.ensak.espace_citoyen.metier.beans;

import java.util.ArrayList;
import java.util.Date;

public class ProcedureSave {
    private String CIN;
    private int id_procedure;
    private String nom;
    private String date;
    private String etat;
    private ArrayList<Document> documents;

    public ProcedureSave() {
        documents = new ArrayList<>();
    }

    public String getNom() {
        return nom;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCIN() {
        return CIN;
    }

    public void setCIN(String CIN) {
        this.CIN = CIN;
    }

    public int getId_procedure() {
        return id_procedure;
    }

    public void setId_procedure(int id_procedure) {
        this.id_procedure = id_procedure;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(ArrayList<Document> documents) {
        this.documents = documents;
    }
}
