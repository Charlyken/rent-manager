package ui;

import model.Client;

import javax.swing.*;
import java.awt.*;

public class AddClientDialog extends JDialog  {

    private boolean validation = false;
    private Client clientCree = null;

    private JTextField nomField = new JTextField(15);
    private JTextField prenomField = new JTextField(15);
    private JTextField permisField = new JTextField(15);

    public AddClientDialog (JFrame parent){
        super(parent,"Nouveau Client", true);
        this.setSize(300,200);
        this.setLayout(new GridLayout(4,2, 10, 10));

        this.add(new JLabel("Nom :"));
        this.add(nomField);
        this.add(new JLabel("Prénom :"));
        this.add(prenomField);
        this.add(new JLabel("N° Permis :"));
        this.add(permisField);

        JButton btnCancel = new JButton("Annuler");
        JButton btnOk = new JButton("Valider");

        this.add(btnCancel);
        this.add(btnOk);

        btnCancel.addActionListener(e -> this.dispose());

        btnOk.addActionListener(e -> {
            if(nomField.getText().isEmpty() || permisField.getText().isEmpty()){
                JOptionPane.showMessageDialog(this, "Nom et permis obligatoire !", "Erreur", JOptionPane.WARNING_MESSAGE);
                return;
            }
            clientCree = new Client(nomField.getText(),prenomField.getText(),permisField.getText());
            validation = true;
            this.dispose();
        });
    }

    public Client getClientCree(){
        return validation ? clientCree : null;
    }
}
