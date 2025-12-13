package model;

public class Client {
    private static int cpt = 0;

    private int id;
    private String nom;
    private String prenom;
    private String numeroPermis;
    private Vehicule vehiculeLoue = null;

    //Constructeur
    public Client(String nom, String prenom, String numeroPermis, Vehicule vehiculeLoue) {
        this.id = ++cpt;
        this.nom = nom;
        this.prenom = prenom;
        this.numeroPermis = numeroPermis;
        this.vehiculeLoue = vehiculeLoue;
    }

    public Client(String nom, String prenom, String numeroPermis) {
        this.id = ++cpt;
        this.nom = nom;
        this.prenom = prenom;
        this.numeroPermis = numeroPermis;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNumeroPermis() {
        return numeroPermis;
    }

    public void setNumeroPermis(String numeroPermis) {
        this.numeroPermis = numeroPermis;
    }

    public Vehicule getVehiculeLoue() {
        return vehiculeLoue;
    }

    public void setVehiculeLoue(Vehicule vehiculeLoue) {
        this.vehiculeLoue = vehiculeLoue;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return getNom() + " " + getPrenom() + " (" + getNumeroPermis() + ")";
    }

}
