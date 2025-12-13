import exception.LocationException;
import model.*;
import service.Agence;
import ui.MainFrame;

import javax.swing.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws LocationException {

        //1 Creation du parc
        System.out.println("---Initialisation du parc---\n");
        Vehicule v1 = new Voiture ("Peugeot", "208", 15000, 30.0, 5);
        Vehicule v2 = new Voiture ("FERRARI", "2008", 15000, 30.0, 5);
        Vehicule c1 = new Camion( "CATERPILAR", "Sprinter", 80000, 50.0, 20.0);
        Vehicule c2 = new Camion( "SINOTRUCK", "Huilux", 80000, 50.0, 20.0);


        //2 Creation de l'agence
        Agence ag1 = new Agence ();
        ag1.ajouterVehicule (v1);
        ag1.ajouterVehicule (v2);
        ag1.ajouterVehicule (c1);
        ag1.ajouterVehicule (c2);

       //System.out.println("Revenu journalier potentiel : " + ag1.calculerRevenuPotentiel () + "€");

        //2 Creation client & test location
        Client client1 = new Client("Keuni", "Charly", "7777AAAA", c1);
        Client client2 = new Client("Albert", "Einstein", "6666545");
        Client client3 = new Client("Hamilton", "Lewis", "6666545");

        ag1.ajouterClient(client1);
        ag1.louerVehicule(client2,c2);


        System.out.println("--- Location simulée ---\n");
        System.out.println("Client " + client1.getNom() + " loue : " + client1.getVehiculeLoue().getModele());
        System.out.println("Client " + client2.getNom() + " loue : " + client2.getVehiculeLoue().getModele());

        //3 Affichage des véhicules dispo
        ArrayList<Vehicule> dispo = ag1.getVehiculesLoue();
        if (dispo.isEmpty()) {
            System.out.println("Aucun véhicule disponible.");
        } else {
            System.out.println("----Vehicules disponibles---");
            for (Vehicule v : dispo) {
                System.out.println("- " + v.getMarque() + " " + v.getModele() + " (ID: " + v.getId() + ")");
            }
        }

        // 4.SAUVEGARDE LES VEHICULES
       // ag1.sauvegarderDonnees("parc.csv");
       // 5.Test JFrame

        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame(ag1);
            frame.setVisible(true);
        });


    }
}