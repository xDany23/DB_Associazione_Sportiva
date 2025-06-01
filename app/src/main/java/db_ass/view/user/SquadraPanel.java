package db_ass.view.user;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.AbstractDocument;

import db_ass.data.Persona;
import db_ass.data.Squadra;
import db_ass.data.TipoSquadra;
import db_ass.view.LimitDocumentFilter;
import db_ass.view.Menu;

public final class SquadraPanel {
    
    private Menu menu;
    private Persona persona;

    public JPanel setUp(Persona persona, Menu menu) {

        this.persona = persona;
        this.menu = menu;
        
        String[] sports = {"Calcetto", "Padel", "Tennis singolo", "Tennis doppio"};

        JPanel squadre = new JPanel();
        squadre.setLayout(new BoxLayout(squadre, BoxLayout.Y_AXIS));

        //titolo
        JLabel squadreTitolo = new JLabel("Le mie squadre", SwingConstants.CENTER);
        squadreTitolo.setFont(new Font("Arial", Font.BOLD, 30));
        squadreTitolo.setAlignmentX(Component.CENTER_ALIGNMENT);
        squadreTitolo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        squadre.add(squadreTitolo);

        //pannello principale Squadra
        JPanel contentSquadra = new JPanel();
        contentSquadra.setLayout(new BoxLayout(contentSquadra, BoxLayout.Y_AXIS));
        JScrollPane scrollSquadre = new JScrollPane(contentSquadra);
        squadre.add(scrollSquadre, BorderLayout.CENTER);

        //dati da compilare sotto
        JPanel datiSquadra = new JPanel();
        datiSquadra.setLayout(new GridLayout(10, 2, 2, 0));
        JLabel codiceSquadraLabel = new JLabel("Codice Squadra: ");
        JTextField codiceSquadraField = new JTextField(16);
        JLabel nomeSquadraLabel = new JLabel("Nome squadra: ");
        JTextField nomeSquadraField = new JTextField(16);
        JLabel tipoSquadraLabel = new JLabel("Tipo: ");
        JComboBox<String> tipoSquadraBox = new JComboBox<>(sports);
        JLabel componente1Label = new JLabel("Componente 1: ");
        JTextField componente1Field = new JTextField(16);
        ((AbstractDocument) componente1Field.getDocument()).setDocumentFilter(new LimitDocumentFilter(16));
        JLabel componente2Label = new JLabel("Componente 2: ");
        JTextField componente2Field = new JTextField(16);
        ((AbstractDocument) componente2Field.getDocument()).setDocumentFilter(new LimitDocumentFilter(16));
        JLabel componente3Label = new JLabel("Componente 3: ");
        JTextField componente3Field = new JTextField(16);
        ((AbstractDocument) componente3Field.getDocument()).setDocumentFilter(new LimitDocumentFilter(16));
        JLabel componente4Label = new JLabel("Componente 4: ");
        JTextField componente4Field = new JTextField(16);
        ((AbstractDocument) componente4Field.getDocument()).setDocumentFilter(new LimitDocumentFilter(16));
        JLabel componente5Label = new JLabel("Componente 5: ");
        JTextField componente5Field = new JTextField(16);
        ((AbstractDocument) componente5Field.getDocument()).setDocumentFilter(new LimitDocumentFilter(16));
        JButton creaNuovaSquadraButton = new JButton("Crea");
        JButton mieSquadreButton = new JButton("Le mie squadre");
        JButton cercaSquadraButton = new JButton("Cerca");
        datiSquadra.add(codiceSquadraLabel);
        datiSquadra.add(codiceSquadraField);
        datiSquadra.add(nomeSquadraLabel);
        datiSquadra.add(nomeSquadraField);
        datiSquadra.add(tipoSquadraLabel);
        datiSquadra.add(tipoSquadraBox);
        datiSquadra.add(componente1Label);
        datiSquadra.add(componente1Field);
        datiSquadra.add(componente2Label);
        datiSquadra.add(componente2Field);
        datiSquadra.add(componente3Label);
        datiSquadra.add(componente3Field);
        datiSquadra.add(componente4Label);
        datiSquadra.add(componente4Field);
        datiSquadra.add(componente5Label);
        datiSquadra.add(componente5Field);
        datiSquadra.add(creaNuovaSquadraButton);
        datiSquadra.add(mieSquadreButton);
        datiSquadra.add(cercaSquadraButton);

        //aggiungo un ActionListener per vedere tutte le squadre dell'utente
        mieSquadreButton.addActionListener(e -> {
            codiceSquadraField.setText(""); // per bellezza
            nomeSquadraField.setText("");
            componente1Field.setText("");
            componente2Field.setText("");
            componente3Field.setText("");
            componente4Field.setText("");
            componente5Field.setText("");
            List<Squadra> list = this.menu.getController().allTeamsOfUser(persona);
            if (list.isEmpty()) {
                contentSquadra.removeAll();
                contentSquadra.add(new JLabel("Non fai parte di nessuna squadra"));
            } else {
                contentSquadra.removeAll();
                contentSquadra.add(new JLabel("LE TUE SQUADRE:"));
                contentSquadra.add(Box.createVerticalStrut(10));
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).tipo.equals(TipoSquadra.TENNIS_SINGOLO)) {
                        contentSquadra.add(new JLabel("Codice Squadra: " + list.get(i).codiceSquadra + ", " +
                                                    "Nome: " + list.get(i).nome + ", " + 
                                                    "Tipo: " + list.get(i).tipo + ", " +
                                                    "Componente 1: " + list.get(i).componente1.nome + list.get(i).componente1.cognome)
                                        );
                    } else if (list.get(i).tipo.equals(TipoSquadra.TENNIS_DOPPIO) || list.get(i).tipo.equals(TipoSquadra.PADEL)) {
                        contentSquadra.add(new JLabel("Codice Squadra: " + list.get(i).codiceSquadra + ", " +
                                                    "Nome: " + list.get(i).nome + ", " + 
                                                    "Tipo: " + list.get(i).tipo + ", " +
                                                    "Componente 1: " + list.get(i).componente1.nome + list.get(i).componente1.cognome + ", " +
                                                    "Componente 2: " + list.get(i).componente2.nome + list.get(i).componente2.cognome)
                                        );
                    } else {
                        contentSquadra.add(new JLabel("Codice Squadra: " + list.get(i).codiceSquadra + ", " +
                                                    "Nome: " + list.get(i).nome + ", " + 
                                                    "Tipo: " + list.get(i).tipo + ", " +
                                                    "Componente 1: " + list.get(i).componente1.nome + list.get(i).componente1.cognome + ", " +
                                                    "Componente 2: " + list.get(i).componente2.nome + list.get(i).componente2.cognome + ", " +
                                                    "Componente 3: " + list.get(i).componente3.nome + list.get(i).componente3.cognome + ", " +
                                                    "Componente 4: " + list.get(i).componente4.nome + list.get(i).componente4.cognome + ", " +
                                                    "Componente 5: " + list.get(i).componente5.nome + list.get(i).componente5.cognome + ", ")
                                        );
                    }
                }
            }
            contentSquadra.revalidate();
            contentSquadra.repaint();
        });

        //aggiungo un ActionListener al bottone per cercare una determinata squadra
        cercaSquadraButton.addActionListener(e -> {
            Squadra team;
            String codice = codiceSquadraField.getText();
            nomeSquadraField.setText("");
            componente1Field.setText("");
            componente2Field.setText("");
            componente3Field.setText("");
            componente4Field.setText("");
            componente5Field.setText("");
            if (codice.isEmpty()) {
                JOptionPane.showMessageDialog(
                    null, 
                    "Necessario inserire il codice della squadra", 
                    "Campi mancanti", 
                    JOptionPane.WARNING_MESSAGE);
            } else {
                team = this.menu.getController().findTeam(Integer.parseInt(codice));
                contentSquadra.removeAll();
                if (team == null) {
                    contentSquadra.add(new JLabel("La squadra selezionata non esiste"));
                } else {
                    contentSquadra.add(new JLabel("ECCO LA SQUADRA CERCATA:"));
                    contentSquadra.add(Box.createVerticalStrut(10));
                    if (team.tipo.equals(TipoSquadra.TENNIS_SINGOLO)) {
                        contentSquadra.add(new JLabel(  "Nome: " + team.nome + ", " + 
                                                    "Tipo: " + team.tipo + ", " +
                                                    "Componente 1: " + team.componente1.nome + team.componente1.cognome)
                        );
                    } else if (team.tipo.equals(TipoSquadra.PADEL) || team.tipo.equals(TipoSquadra.TENNIS_DOPPIO)) {
                        contentSquadra.add(new JLabel(  "Nome: " + team.nome + ", " + 
                                                    "Tipo: " + team.tipo + ", " +
                                                    "Componente 1: " + team.componente1.nome + team.componente1.cognome + ", " +
                                                    "Componente 2: " + team.componente2.nome + team.componente2.cognome)
                    );
                    } else {
                    contentSquadra.add(new JLabel(  "Nome: " + team.nome + ", " + 
                                                    "Tipo: " + team.tipo + ", " +
                                                    "Componente 1: " + team.componente1.nome + team.componente1.cognome + ", " +
                                                    "Componente 2: " + team.componente2.nome + team.componente2.cognome + ", " +
                                                    "Componente 3: " + team.componente3.nome + team.componente3.cognome + ", " +
                                                    "Componente 4: " + team.componente4.nome + team.componente4.cognome + ", " +
                                                    "Componente 5: " + team.componente5.nome + team.componente5.cognome + ", ")
                    );
                    }
                }
                contentSquadra.revalidate();
                contentSquadra.repaint();
            }
        });

        //aggiungo un ActionListener al bottone per la creazione di una squadra
        creaNuovaSquadraButton.addActionListener(e -> {
            String codice = codiceSquadraField.getText();
            String nome = nomeSquadraField.getText();
            TipoSquadra tipo = (tipoSquadraBox.getSelectedIndex() == 0)
                        ? TipoSquadra.CALCETTO 
                        : (tipoSquadraBox.getSelectedIndex() == 1)
                        ? TipoSquadra.PADEL
                        : (tipoSquadraBox.getSelectedIndex() == 2)
                        ? TipoSquadra.TENNIS_SINGOLO
                        : TipoSquadra.TENNIS_DOPPIO;
            String p1 = componente1Field.getText();
            String p2 = componente2Field.getText();
            String p3 = componente3Field.getText();
            String p4 = componente4Field.getText();
            String p5 = componente5Field.getText();
            if (!codice.isEmpty()) {
                JOptionPane.showMessageDialog(
                    null, 
                    "Il codice della squadra non serve per la creazione, viene assegnato automaticamente", 
                    "Campi non necessari", 
                    JOptionPane.WARNING_MESSAGE);
            } else if (nome.isEmpty()) {
                JOptionPane.showMessageDialog(
                    null, 
                    "Necessario inserire il nome della squadra", 
                    "Campi mancanti", 
                    JOptionPane.WARNING_MESSAGE);
            } else if (!p1.isEmpty() && this.menu.getController().findPersona(p1) == null) {
                JOptionPane.showMessageDialog(
                    null, 
                    "Il componente 1 inserito non esiste", 
                    "Campi errati", 
                    JOptionPane.WARNING_MESSAGE);
            } else if (!p2.isEmpty() && this.menu.getController().findPersona(p2) == null) {
                JOptionPane.showMessageDialog(
                    null, 
                    "Il componente 2 inserito non esiste", 
                    "Campi errati", 
                    JOptionPane.WARNING_MESSAGE);
            } else if (!p3.isEmpty() && this.menu.getController().findPersona(p3) == null) {
                JOptionPane.showMessageDialog(
                    null, 
                    "Il componente 3 inserito non esiste", 
                    "Campi errati", 
                    JOptionPane.WARNING_MESSAGE);
            } else if (!p4.isEmpty() && this.menu.getController().findPersona(p4) == null) {
                JOptionPane.showMessageDialog(
                    null, 
                    "Il componente 4 inserito non esiste", 
                    "Campi errati", 
                    JOptionPane.WARNING_MESSAGE);
            } else if (!p5.isEmpty() && this.menu.getController().findPersona(p5) == null) {
                JOptionPane.showMessageDialog(
                    null, 
                    "Il componente 5 inserito non esiste", 
                    "Campi errati", 
                    JOptionPane.WARNING_MESSAGE);
            } else {
                contentSquadra.removeAll();
                if (this.menu.getController().createNewTeam(nome, 
                                                            tipo, 
                                                            this.menu.getController().findPersona(p1), 
                                                            this.menu.getController().findPersona(p2), 
                                                            this.menu.getController().findPersona(p3), 
                                                            this.menu.getController().findPersona(p4), 
                                                            this.menu.getController().findPersona(p5)) == 0) {
                                                                contentSquadra.add(new JLabel("Qualcosa è andato storto, assicurati che il numero di persone inserite corrisponda con il numero di giocatori dello sport. Altrimenti è probabile che per quello sport esista già una squadra con quel nome"));
                                                            }
                else {
                    contentSquadra.add(new JLabel("Squadra registrata con successo"));
                }
                contentSquadra.revalidate();
                contentSquadra.repaint();
            }
        });

        //aggiungo al pannello principale
        squadre.add(datiSquadra, BorderLayout.WEST);

        return squadre;
    }

}
