package ui;

import exception.LocationException;
import model.Client;
import model.Vehicule;
import service.Agence;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class LocationVehiculeDialog extends JDialog {

    private Agence agence;
    private boolean locationEffectuee = false;

    private JComboBox<Client> clientCombo;
    private JComboBox<Vehicule> vehiculeCombo;

    public LocationVehiculeDialog(JFrame parent, Agence agence) {
        super(parent, "Louer un Véhicule", true);
        this.agence = agence;

        this.setSize(400, 250);
        this.setLocationRelativeTo(parent);
        this.setLayout(new GridLayout(4, 2, 10, 10));

        //Initialisation des données
        ArrayList<Client> clients = agence.getClients();
        clientCombo = new JComboBox<>(clients.toArray(new Client[0]));

        //Vehicules dispo
        ArrayList<Vehicule> dispo = agence.getTousLesVehicules();
        vehiculeCombo = new JComboBox<>(dispo.toArray(new Vehicule[0]));

        //UI
        this.add(new JLabel("Selectioner un client :"));
        this.add(clientCombo);
        this.add(new JLabel("Selectionner Véhicule :"));
        this.add(vehiculeCombo);
        this.add(new JLabel(""));
        this.add(new JLabel(""));

        JButton btnCancel = new JButton("Annuler");
        JButton btnValider = new JButton("Valider Location");

        this.add(btnCancel);
        this.add(btnValider);

        //Actions

        btnCancel.addActionListener(e -> this.dispose());

        btnValider.addActionListener(e -> {
            Client selectedClient = (Client) clientCombo.getSelectedItem();
            Vehicule selectedVehicule = (Vehicule) vehiculeCombo.getSelectedItem();

            if (selectedClient == null || selectedClient == null) {
                JOptionPane.showMessageDialog(this, "Veuillez selectionner un client et un vehicule", "Erreur", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                //Essaie de location
                agence.louerVehicule(selectedClient, selectedVehicule);
                locationEffectuee = true;
                JOptionPane.showMessageDialog(this, "Location enregistré avec succès !");
                this.dispose();
            } catch (LocationException ex) {
                JOptionPane.showMessageDialog(this, "Erreur Location : " + ex.getMessage(), "Echec", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public boolean isLocationEffectuee() {
        return locationEffectuee;
    }
}
