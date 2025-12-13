package ui;

import model.Client;
import model.Vehicule;
import service.Agence;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class MainFrame extends JFrame {
    private Agence agence;
    private JTextArea vehiculeDisplay;
    private JTextArea clientDisplay;
    private JTextField searchClientField;

    //Constructeur
    public MainFrame(Agence agence) {
        this.agence = agence;

        //Configuration de la fenètre
        this.setTitle("FleetManager 2025");
        this.setSize(900, 600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        // Utilisation d'onglets pour separer les UI
        JTabbedPane tabbedPane = new JTabbedPane();

        //Onglet 1 : Gestion des vehicules
        JPanel vehiculePanel = createVehiculePanel();
        tabbedPane.addTab("Parc Automobile", vehiculePanel);

        //Onglet 2 : Gestion des Clients
        JPanel clientPanel = createClientPanel();
        tabbedPane.addTab("Gestion Clients", clientPanel);

        this.add(tabbedPane);

        //Initialisation
        refreshVehiculeDisplay();
        refreshClientDisplay("");
    }

    // --- ONGLET VEHICULES ---
    private JPanel createVehiculePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        //Zone d'affichage : Tableau de bord
        vehiculeDisplay = new JTextArea();
        vehiculeDisplay.setEditable(false);
        vehiculeDisplay.setFont(new Font("Monospaced", Font.PLAIN, 14));
        panel.add(new JScrollPane(vehiculeDisplay), BorderLayout.CENTER);

        //Zone haute : Barre d'outils
        JToolBar toolBar = new JToolBar();
        JButton btnAddVoiture = new JButton("Ajouter Voiture");
        JButton btnLouer = new JButton("Louer Véhicule");
        JButton btnSave = new JButton("Sauvegarder");
        JButton btnRefresh = new JButton("Rafraichir");

        toolBar.add(btnAddVoiture);
        toolBar.add(btnLouer);
        toolBar.addSeparator();
        toolBar.add(btnRefresh);
        toolBar.addSeparator();
        toolBar.add(btnSave);

        panel.add(toolBar, BorderLayout.NORTH);

        //Actions
        btnAddVoiture.addActionListener(e -> {
            AddVehiculeDialog dialog = new AddVehiculeDialog(this);
            dialog.setVisible(true);

            Vehicule v = dialog.getVehiculeCree();
            if (v != null) {
                agence.ajouterVehicule(v);
                this.refreshVehiculeDisplay();
                JOptionPane.showMessageDialog(this, "Véhicule ajouté avec succès");
            }


        });

        btnLouer.addActionListener(e -> {
            //verification des clients
            if (agence.getClients().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Aucun client enregisteé ! Créez en un", "Info", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            LocationVehiculeDialog dialog = new LocationVehiculeDialog(this, agence);
            dialog.setVisible(true);

            if (dialog.isLocationEffectuee()) {
                this.refreshVehiculeDisplay();
            }
        });


        btnSave.addActionListener(e -> {
            agence.sauvegarderDonnees("parcSwing.csv");
            JOptionPane.showMessageDialog(this, "Données sauvergardées");

        });

        btnRefresh.addActionListener(e -> {
            this.refreshVehiculeDisplay();
        });

        return panel;

    }

    private JPanel createClientPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Zone texte
        clientDisplay = new JTextArea();
        clientDisplay.setEditable(false);
        clientDisplay.setFont(new Font("Monospaced", Font.PLAIN, 14));
        panel.add(new JScrollPane(clientDisplay), BorderLayout.CENTER);

        // Barre de recherche et boutons
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchClientField = new JTextField(15);
        JButton btnSearch = new JButton("Rechercher");
        JButton btnAddClient = new JButton("Nouveau Client");
        JButton btnDeleteClient = new JButton("Supprimer Client (ID)"); // Suppression par ID

        topPanel.add(new JLabel("Recherche (Nom) : "));
        topPanel.add(searchClientField);
        topPanel.add(btnSearch);
        topPanel.add(new JSeparator(SwingConstants.VERTICAL));
        topPanel.add(btnAddClient);
        topPanel.add(btnDeleteClient);

        panel.add(topPanel, BorderLayout.NORTH);

        // Actions Clients
        btnSearch.addActionListener(e -> refreshClientDisplay(searchClientField.getText()));
        //Ajouter un client
        btnAddClient.addActionListener(e -> {
            AddClientDialog dialog = new AddClientDialog(this);
            dialog.setVisible(true);
            Client c = dialog.getClientCree();
            if (c != null) {
                agence.ajouterClient(c);
                refreshClientDisplay("");
            }
        });
        //Supprimer un client via son id
        btnDeleteClient.addActionListener(e -> {
            String idStr = JOptionPane.showInputDialog(this, "Entrez l'ID du client à supprimer :");
            if (idStr != null && !idStr.isEmpty()) {
                try {
                    int id = Integer.parseInt(idStr);
                    boolean deleted = agence.supprimerClientParId(id);
                    if (deleted) {
                        JOptionPane.showMessageDialog(this, "Client supprimé.");
                        refreshClientDisplay("");
                    } else {
                        JOptionPane.showMessageDialog(this, "Client introuvable ou a une location en cours.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "ID invalide.");
                }
            }
        });

        return panel;
    }

    // Methode d'affichages
    private void refreshVehiculeDisplay() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("%-5s | %-15s | %-15s | %-10s | %-10s\n", "ID", "MARQUE", "MODELE", "KM", "PRIXJ"));
        stringBuilder.append("----------------------------------------------------------------------\n");
        for (Vehicule v : agence.getTousLesVehicules()) {
            String statut = agence.estLoue(v) ? "[LOUE]" : "[DISPO]";
            stringBuilder.append(String.format("%-5d | %-15s | %-15s | %-10.1f | %-10.1f | %-10s\n",
                    v.getId(), v.getMarque(), v.getModele(), v.getKilometrage(), v.getTarifJournalier(), statut));
        }
        vehiculeDisplay.setText(stringBuilder.toString());
    }

    private void refreshClientDisplay(String filterNom) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-5s | %-15s | %-15s | %-20s | %-20s\n", "ID", "NOM", "PRENOM", "PERMIS", "VEHICULE LOUÉ"));
        sb.append("------------------------------------------------------------------------------------------\n");

        ArrayList<Client> clientsToDisplay = agence.getClients();

        // Filtrage simple (Recherche)
        if (!filterNom.isEmpty()) {
            clientsToDisplay = (ArrayList<Client>) clientsToDisplay.stream()
                    .filter(c -> c.getNom().toLowerCase().contains(filterNom.toLowerCase()))
                    .collect(Collectors.toList());
        }

        for (Client c : clientsToDisplay) {
            String vehiculeInfo = (c.getVehiculeLoue() != null) ? c.getVehiculeLoue().getModele() : "Aucun";
            sb.append(String.format("%-5d | %-15s | %-15s | %-20s | %-20s\n",
                    c.getId(), c.getNom(), c.getPrenom(), c.getNumeroPermis(), vehiculeInfo));
        }
        clientDisplay.setText(sb.toString());
    }


}
