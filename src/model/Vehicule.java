package model;

public abstract class Vehicule {
    private static int cpt = 0;

    private int id;
    private String marque;
    private String modele;
    private double kilometrage;
    private double tarifJournalier;

    // Constructeur
    public Vehicule (String marque, String modele, double kilometrage, double tarifJournalier){
        this.id = ++cpt;
        this.modele = modele;
        this.marque = marque;
        this.kilometrage = kilometrage;
        this.tarifJournalier = tarifJournalier;
    }

    // Getters et Setters

    public int getId() {
        return id;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getModele() {
        return modele;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public double getKilometrage() {
        return kilometrage;
    }

    public void setKilometrage(double kilometrage) {
        if(kilometrage >= this.kilometrage){
           this.kilometrage = kilometrage;
        }
    }

    public double getTarifJournalier() {
        return tarifJournalier;
    }

    public void setTarifJournalier(double tarifJournalier) {
        this.tarifJournalier = tarifJournalier;
    }

    abstract double calculerPrixLocation (int jours);

    @Override
    public String toString() {
        return getMarque() + " " + getModele() + " (ID : " + getId() + ")";
    }
}
