package org.ensak.espace_citoyen.modele;

import javafx.scene.control.Button;

public class Procedure {
    private String numeroProcedure;
    private String nomProcedure;
    private String nomDivision;
    private Button lancerProcedure;

    public Procedure() {
        super();
        lancerProcedure = new Button("Lancer");
        lancerProcedure.setStyle("-fx-background-color:#FF9900");
    }

    public Procedure(String numeroProcedure, String nomProcedure, String nomDivision) {
        this.numeroProcedure = numeroProcedure;
        this.nomProcedure = nomProcedure;
        this.nomDivision = nomDivision;
    }

    public String getNumeroProcedure() {
        return numeroProcedure;
    }

    public void setNumeroProcedure(String numeroProcedure) {
        this.numeroProcedure = numeroProcedure;
    }

    public String getNomProcedure() {
        return nomProcedure;
    }

    public void setNomProcedure(String nomProcedure) {
        this.nomProcedure = nomProcedure;
    }

    public String getNomDivision() {
        return nomDivision;
    }

    public void setNomDivision(String nomDivision) {
        this.nomDivision = nomDivision;
    }

    public Button getLancerProcedure() {
        return lancerProcedure;
    }

    public void setLancerProcedure(Button lancerProcedure) {
        this.lancerProcedure = lancerProcedure;
    }
}
