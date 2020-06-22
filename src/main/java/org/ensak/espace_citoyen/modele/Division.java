package org.ensak.espace_citoyen.modele;

public class Division {
    private String numero;
    private String nom;

    public Division(String numero, String nom) {
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

    @Override
    public String toString() {
        return "Division :--->" + nom;
    }
}
