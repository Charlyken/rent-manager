package ui;

import model.Camion;
import model.Vehicule;
import model.Voiture;

import javax.swing.*;
import java.awt.*;

public class AddVehiculeDialog extends JDialog {

    private boolean validation = false;
    private Vehicule vehiculeCree = null;

    private JComboBox<String> typeCombo = new JComboBox<>(new String[]{"Voiture", "Camion"});
    private JTextField marqueField = new JTextField(10);
    private JTextField modeleField = new JTextField(10);
    private JTextField kmField = new JTextField(10);
    private JTextField prixField = new JTextField(10);
    private JTextField specField = new JTextField(10); //nb places ou volume
    private JLabel specLabel = new JLabel("Nb places:");

    public AddVehiculeDialog(JFrame parent){
        super(parent, "Nouveau Véhicule", true);
        this.setSize(350, 300);
        this.setLocationRelativeTo(parent);
        this.setLayout(new GridLayout(7, 2, 10, 10));

        // Ajout des Composants
        this.add(new JLabel("Type :"));
        this.add(typeCombo);
        this.add(new JLabel("Marque :"));
        this.add(marqueField);
        this.add(new JLabel("Modèle :"));
        this.add(modeleField);
        this.add(new JLabel("Kilométrage :"));
        this.add(kmField);
        this.add(new JLabel("Prix Journalier :"));
        this.add(prixField);
        this.add(specLabel);
        this.add(specField);

        JButton btnCancel = new JButton("Annuler");
        JButton btnOk = new JButton("Valider");

        this.add(btnCancel);
        this.add(btnOk);

        //Gestion du changement de type
        typeCombo.addActionListener(e -> {
            if(typeCombo.getSelectedItem().equals("Voiture")) {
                specLabel.setText("Nb Places :");
            } else {
                specLabel.setText("Volume (m3) :");
            }
        });

        //Actions : boutons
        btnCancel.addActionListener(e -> {
            this.dispose(); //ferme la fenetre
              });

        btnOk.addActionListener(e -> {
            try{
                String marque = marqueField.getText();
                String modele = modeleField.getText();
                double km = Double.parseDouble(kmField.getText());
                double prix = Double.parseDouble(prixField.getText());

                if(typeCombo.getSelectedItem().equals("Voiture")) {
                    int places = Integer.parseInt(specField.getText());
                    vehiculeCree = new Voiture(marque, modele, km, prix, places);
                } else {
                    double vol = Double.parseDouble(specField.getText());
                    vehiculeCree = new Camion(marque, modele, km, prix, vol);
                }

                validation = true;
                this.dispose();
            } catch (NumberFormatException ex){
                JOptionPane.showMessageDialog(this, "Erreur de saisie","Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public Vehicule getVehiculeCree(){
        return validation ? vehiculeCree : null;
    }
}
