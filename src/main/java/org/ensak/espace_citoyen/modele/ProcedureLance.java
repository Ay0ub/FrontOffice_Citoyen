package org.ensak.espace_citoyen.modele;

import javafx.scene.control.Button;

public class ProcedureLance {
    private String numero;
    private String nom;
    private String dateDebut;
    private String dateFin;
    private String etat;
    private Button action;

    public ProcedureLance() {
        super();
        action = new Button();
    }

    public ProcedureLance(String numero, String nom, String dateDebut, String dateFin, String etat) {
        this.numero = numero;
        this.nom = nom;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.etat = etat;
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

    public String getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    public String getDateFin() {
        return dateFin;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public Button getAction() {
        return action;
    }

    public void setAction(Button action) {
        this.action = action;
    }
}
