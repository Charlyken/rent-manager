package model;

public class Voiture extends Vehicule {
    private int nbPlaces;

    //Constructeur
    public Voiture(String marque, String modele, double kilometrage, double tarifJournalier, int nbPlaces){
        super(marque, modele, kilometrage, tarifJournalier);
        this.nbPlaces = nbPlaces;
    }

    public int getNbPlaces() {
        return nbPlaces;
    }

    public void setNbPlaces(int nbPlaces) {
        this.nbPlaces = nbPlaces;
    }

    @Override
    public double calculerPrixLocation( int jours){
        return super.getTarifJournalier()*jours;
    }
}
