package service;

import exception.LocationException;
import model.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Agence {
    private ArrayList<Vehicule> parc = new ArrayList<>();
    private ArrayList<Client> clients = new ArrayList<>();

    //Gestion des Vehicules

    /**
     * Ajout Vehicule
     *
     * @param v
     */
    public void ajouterVehicule(Vehicule v) {
        parc.add(v);
    }

    /**
     * Suppression d'un vehicule
     *
     * @param v
     */
    public void supprimerVehicule(Vehicule v) {
        parc.remove(v);
    }

    /**
     * Rechercher un vehicule par son ID
     *
     * @param id
     * @return Vehicule
     */
    public Vehicule getVehiculeById(int id) {
        for (Vehicule v : parc) {
            if (v.getId() == id)
                return v;
        }
        return null;
    }

    /**
     * retourne tout le parc auto
     * @return une liste de Vehicule
     */
    public ArrayList<Vehicule> getTousLesVehicules (){
        return parc;
    }

    /**
     * retourne la liste des vehicules loués
     *
     * @return vehiculesDispo
     */
    public ArrayList<Vehicule> getVehiculesLoue() {
        ArrayList<Vehicule> vehiculesDispo = new ArrayList<>();
        for (Vehicule v : parc) {
            for (Client c : clients) {
                if (c.getVehiculeLoue() != null && c.getVehiculeLoue().getId() == v.getId())
                    vehiculesDispo.add(v);
            }
        }
        return vehiculesDispo;
    }

    public boolean estLoue(Vehicule v) {
        for (Client c : clients) {
            if (c.getVehiculeLoue() != null && c.getVehiculeLoue().getId() == v.getId()) {
                return true;
            }
        }
        return false;
    }


    /**
     * Louer un vehicule
     *
     * @param client
     * @param v
     * @throws LocationException
     */
    public void louerVehicule(Client client, Vehicule v) throws LocationException {
        // 1. Vérifier si le véhicule est déjà loué
        if(client.getVehiculeLoue() != null){
        for (Client c : clients) {
            if (c.getVehiculeLoue().getId() == v.getId()) {
                throw new LocationException("Le vehicule " + v.getModele() + " (ID: " + v.getId() + ") est deja loué");
            }
        }}

        //2. Verifier si le client à deja loué
        if (client.getVehiculeLoue() != null) {
            throw new LocationException("Le client " + client.getPrenom() + " " + client.getNom() + " (ID: " + client.getId() + ") a deja loué");
        }

        //3. Ajout du vehicule
        client.setVehiculeLoue(v);
        //On s'assure que le client est bien dans la liste de l'agence (si ce n'était pas le cas)
        if (!clients.contains(client)) {
            clients.add(client);
        }

        System.out.println("Location validée: " + client.getPrenom() + " -> " + v.getModele());

    }

    //Gestion des clients

    /**
     * Ajouter un client
     *
     * @param c
     */
    public void ajouterClient(Client c) {
        clients.add(c);
    }

    /**
     * Recherche Client via permis
     *
     * @param permis
     * @return Client
     */
    public Client getClientByPermis(String permis) {
        for (Client c : clients) {
            if (c.getNumeroPermis().equals(permis))
                return c;
        }
        return null;
    }

    /**
     * Retourne la liste de Clients
     * @return clients
     */
    public ArrayList <Client> getClients(){
        return clients;
    }

    /**
     * Supprime un client par son Id
     * @param id du client
     * @return true si la suppression est reussie et false sinon
     */
    public boolean supprimerClientParId (int id){
        Client remove = null;

        //recherche du client qui à deja un vehicule;
        for(Client c : clients){
            if(c.getId() == id){
                remove = c;
                break;
            }
        }

        //Suppression du client
        if(remove != null){
           if(remove.getVehiculeLoue() == null){
               clients.remove(remove);
               return true;
           } else {
               return false;
           }
        }

        //Si le client n'est pas trouvé
        return false;
    }

    /**
     * Calcul des revenus/jour
     *
     * @return revenu
     */
    public double calculerRevenuPotentiel() {
        double revenu = 0;
        for (Vehicule v : parc) {
            revenu = revenu + v.getTarifJournalier();
        }
        return revenu;
    }
    //TYPE;MARQUE;MODELE;KM;PRIX;SPECIFIQUE
    //Persistance de données

    /**
     * Sauvergarder les donnees de vehicules
     * Dans un fichier
     *
     * @param nomFichier
     */
    public void sauvegarderDonnees(String nomFichier) {
        try (
                FileWriter fileWriter = new FileWriter(nomFichier);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        ) {
            bufferedWriter.write("TYPE;MARQUE;MODELE;KM;PRIX;SPECIFIQUE");
            bufferedWriter.newLine();

            for (Vehicule v : parc) {
                StringBuilder ligne = new StringBuilder();
                if(v instanceof Voiture){
                    ligne.append("VOITURE");
                }
                else if(v instanceof Camion){
                    ligne.append("CAMION");
                }
                else {
                    ligne.append("VÉHICULE");
                }

                ligne.append(";").append(v.getMarque())
                        .append(";").append(v.getModele())
                        .append(";").append(v.getKilometrage())
                        .append(";").append(v.getTarifJournalier());

                if (v instanceof Voiture) {
                    ligne.append(";").append(((Voiture) v).getNbPlaces());
                } else if (v instanceof Camion) {
                    ligne.append(";").append(((Camion) v).getVolume());
                }



                bufferedWriter.write(ligne.toString());
                bufferedWriter.newLine();
            }

            System.out.println("Sauvegarde effectuée avec SUCCES !");
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde :" + e.getMessage());
        }

    }


}
