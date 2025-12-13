package model;

public class Camion extends Vehicule{
    private double volume;

    public Camion (String marque, String modele, double kilometrage, double tarifJournalier, double volume){
        super(marque, modele, kilometrage, tarifJournalier);
        this.volume = volume;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    @Override
    public double calculerPrixLocation( int jours){
        return super.getTarifJournalier() * jours + (this.volume*10);
    }
}
