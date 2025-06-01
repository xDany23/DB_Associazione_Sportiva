package db_ass.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;

import db_ass.data.Campo;
import db_ass.data.Corso;
import db_ass.data.Giorno;
import db_ass.data.LezionePrivata;
import db_ass.data.Persona;
import db_ass.data.RisultatiTorneo;
import db_ass.data.Sport;
import db_ass.data.Squadra;
import db_ass.data.TipoSquadra;
import db_ass.data.Torneo;

public class UserPage {
    
    private Menu menu;
    private JFrame mainFrame;
    private Persona persona;

    public UserPage(Menu menu, JFrame mainFrame, Persona persona) {
        this.menu = menu;
        this.mainFrame = mainFrame;
        this.persona = persona;
    }

    public JFrame setUp() {
        JTabbedPane main = new JTabbedPane();

        JPanel backPanel = new JPanel();
        backPanel.setLayout(new BoxLayout(backPanel, BoxLayout.Y_AXIS));

        JPanel userPanel = new JPanel(new GridLayout(4,1,2,0));
        userPanel.setMaximumSize(new Dimension(500, 30));
        userPanel.setAlignmentX(JPanel.CENTER_ALIGNMENT);

        //opzioni per scelte a tendina
        String[] orari = {"15:00:00", "16:30:00", "18:00:00"};
        String[] sports = {"Calcetto", "Padel", "Tennis singolo", "Tennis doppio"};

        //PAGINE DEL MAIN
        //PANNELLO SQUADRA  
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
                                                            Integer.parseInt(codice), 
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
        

        //PANNELLO TORNEI
        JPanel tornei = new JPanel();
        tornei.setLayout(new BoxLayout(tornei, BoxLayout.Y_AXIS));

        //titolo
        JLabel torneiTitolo = new JLabel("I miei tornei", SwingConstants.CENTER);
        torneiTitolo.setFont(new Font("Arial", Font.BOLD, 30));
        torneiTitolo.setAlignmentX(Component.CENTER_ALIGNMENT);
        torneiTitolo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        tornei.add(torneiTitolo);

        //pannello principale Tornei
        JPanel contentTornei = new JPanel();
        contentTornei.setLayout(new BoxLayout(contentTornei, BoxLayout.Y_AXIS));
        JScrollPane scrollTornei = new JScrollPane(contentTornei);
        tornei.add(scrollTornei, BorderLayout.CENTER);

        //dati da compilare sotto
        JPanel datiTorneo = new JPanel();
        datiTorneo.setLayout(new GridLayout(5, 2, 2, 0));
        JLabel codiceTorneoLabel = new JLabel("Codice Torneo: ");
        JTextField codiceTorneoField = new JTextField(16);
        JLabel codiceSquadraTorneoLabel = new JLabel("Codice Squadra: ");
        JTextField codiceSquadraTorneoField = new JTextField(16);
        JLabel tipoTorneoLabel = new JLabel("Tipo: ");
        JComboBox<String> tipoTorneoBox = new JComboBox<>(sports);
        JButton registrationButtonTorneo = new JButton("Iscrivi squadra");
        JButton tuttiPartecipantiButton = new JButton("Mostra partecipanti");
        JButton tuttePartiteButton = new JButton("Mostra partite");
        JButton torneiPartecipabili = new JButton("Tornei partecipabili");
        datiTorneo.add(codiceTorneoLabel);
        datiTorneo.add(codiceTorneoField);
        datiTorneo.add(codiceSquadraTorneoLabel);
        datiTorneo.add(codiceSquadraTorneoField);
        datiTorneo.add(tipoTorneoLabel);
        datiTorneo.add(tipoTorneoBox);
        datiTorneo.add(registrationButtonTorneo);
        datiTorneo.add(tuttiPartecipantiButton);
        datiTorneo.add(tuttePartiteButton);
        datiTorneo.add(torneiPartecipabili);

        //aggiungo un ActionListener per il bottone dell'iscrizione al torneo
        registrationButtonTorneo.addActionListener(e -> {
            String codiceTorneo = codiceTorneoField.getText();
            String codiceSquadra = codiceSquadraTorneoField.getText();
            if (codiceSquadra.isEmpty() || codiceTorneo.isEmpty()) {
                JOptionPane.showMessageDialog(
                    null, 
                    "E' necessario inserire sia il codice Torneo che il codice Squadra per l'iscrizione", 
                    "Campi mancanti", 
                    JOptionPane.WARNING_MESSAGE);
            } else if (!this.menu.getController().findTeam(Integer.parseInt(codiceSquadra)).componente1.equals(persona) ||
                       !this.menu.getController().findTeam(Integer.parseInt(codiceSquadra)).componente2.equals(persona) ||
                       !this.menu.getController().findTeam(Integer.parseInt(codiceSquadra)).componente3.equals(persona) ||
                       !this.menu.getController().findTeam(Integer.parseInt(codiceSquadra)).componente4.equals(persona) ||
                       !this.menu.getController().findTeam(Integer.parseInt(codiceSquadra)).componente5.equals(persona)) {
                JOptionPane.showMessageDialog(
                    null, 
                    "Non fai parte della squadra inserita! Riprovare", 
                    "Campi errati", 
                    JOptionPane.WARNING_MESSAGE);
            } else if (this.menu.getController().findTournament(Integer.parseInt(codiceTorneo)) == null) {
                JOptionPane.showMessageDialog(
                    null, 
                    "Il torneo inserito non esiste! Riprovare", 
                    "Campi errati", 
                    JOptionPane.WARNING_MESSAGE);
            } else if (!this.menu.getController().findTournament(Integer.parseInt(codiceTorneo)).tipo.equals(this.menu.getController().findTeam(Integer.parseInt(codiceSquadra)).tipo)) {
                JOptionPane.showMessageDialog(
                    null, 
                    "La squadra che hai inserito non è dello stesso tipo del torneo!", 
                    "Campi errati", 
                    JOptionPane.WARNING_MESSAGE);
            } else if (this.menu.getController().tournamentsEnterable(this.menu.getController().findTeam(Integer.parseInt(codiceSquadra)).tipo).contains(this.menu.getController().findTournament(Integer.parseInt(codiceTorneo)))) { //controllo se il torneo è joinable oppure no
                JOptionPane.showMessageDialog(
                    null, 
                    "Il torneo che hai inserito purtroppo non accetta piú iscrizioni", 
                    "Torneo non disponibile", 
                    JOptionPane.WARNING_MESSAGE);
            } else {
                contentTornei.removeAll();
                if (this.menu.getController().enterTournament(Integer.parseInt(codiceTorneo), Integer.parseInt(codiceSquadra)) == 0) {
                    contentTornei.add(new JLabel("C'è stato un errore nella registrazione, riprovare"));
                } else {
                    contentTornei.add(new JLabel("Iscrizione effettuata con successo!"));
                }
                contentTornei.revalidate();
                contentTornei.repaint();
            }
        });

        //aggiungo un ActionListener al bottone per visualizzare tutti i partecipanti di un torneo
        tuttiPartecipantiButton.addActionListener(e -> {
            List<Persona> output = new ArrayList<>();
            String codiceTorneo = codiceTorneoField.getText();
            codiceSquadraTorneoField.setText("");
            if (codiceTorneo.isEmpty()) {
                JOptionPane.showMessageDialog(
                    null, 
                    "E' necessario inserire il codice Torneo per visualizzare i partecipanti di un torneo", 
                    "Campi mancanti", 
                    JOptionPane.WARNING_MESSAGE);
            } else if (this.menu.getController().findTournament(Integer.parseInt(codiceTorneo)) == null) {
                JOptionPane.showMessageDialog(
                    null, 
                    "Il torneo selezionato non esiste", 
                    "Campi errati", 
                    JOptionPane.WARNING_MESSAGE);
            } else {
                contentTornei.removeAll();
                output = this.menu.getController().findAllPartecipants(Integer.parseInt(codiceTorneo));
                if (output.isEmpty()) {
                    contentTornei.add(new JLabel("Non ci sono ancora iscritti al torneo selezionato"));
                } else {
                    contentTornei.add(new JLabel("PARTECIPANTI:"));
                    contentTornei.add(Box.createVerticalStrut(10));
                    for (int i = 0; i < output.size(); i++) {
                        contentTornei.add(new JLabel("Nome: " + output.get(i).nome + output.get(i).cognome + ", " + 
                                                    "CF: " + output.get(i).cf + ", " + 
                                                    "Email: " + output.get(i).email));
                    }
                    contentTornei.revalidate();
                    contentTornei.repaint();
                }
            }
        });

        //aggiungo un ActionListener per il bottone per visualizzare tutte le partite di un torneo
        tuttePartiteButton.addActionListener(e -> {
            List<RisultatiTorneo> output = new ArrayList<>();
            String codiceTorneo = codiceTorneoField.getText();
            codiceSquadraTorneoField.setText("");
            if (codiceTorneo.isEmpty()) {
                JOptionPane.showMessageDialog(
                    null, 
                    "E' necessario inserire il codice Torneo per visualizzare i partecipanti di un torneo", 
                    "Campi mancanti", 
                    JOptionPane.WARNING_MESSAGE);
            } else if (this.menu.getController().findTournament(Integer.parseInt(codiceTorneo)) == null) {
                JOptionPane.showMessageDialog(
                    null, 
                    "Il torneo selezionato non esiste", 
                    "Campi errati", 
                    JOptionPane.WARNING_MESSAGE);
            } else {
                contentTornei.removeAll();
                output = this.menu.getController().visualizeAllTournamentMatches(Integer.parseInt(codiceTorneo));
                output.sort((a,b) -> Integer.compare(a.codicePartita, b.codicePartita));
                if (output.isEmpty()) {
                    contentTornei.add(new JLabel("Per ora non ci sono ancora partite in programma per questo torneo"));
                } else {
                    contentTornei.add(new JLabel("PARTITE DEL TORNEO " + codiceTorneo));
                    contentTornei.add(Box.createVerticalStrut(10));
                    for (int i = 0; i < output.size(); i = i + 2) {
                        contentTornei.add(new JLabel("Codice partita: " + output.get(i).codicePartita + ", " + 
                                                    "[" + output.get(i).nomeSquadra + "(" + output.get(i).codiceSquadra + "), " 
                                                    + output.get(i + 1).nomeSquadra + "(" + output.get(i + 1).codiceSquadra + ")]: " +
                                                    output.get(i).punteggio + " - " + output.get(i + 1).punteggio));
                    }
                }
                contentTornei.revalidate();
                contentTornei.repaint();
            }
        });

        //aggiungo un ActionListener per il bottone per visualizzare tutti i tornei partecipabili
        torneiPartecipabili.addActionListener(e -> {
            TipoSquadra tipo;
            List<Torneo> output = new ArrayList<>();
            codiceSquadraTorneoField.setText("");
            codiceTorneoField.setText("");
            if (tipoTorneoBox.getSelectedIndex() == 0) {
                tipo = TipoSquadra.CALCETTO;
            } else if (tipoTorneoBox.getSelectedIndex() == 1) {
                tipo = TipoSquadra.PADEL;
            } else if (tipoTorneoBox.getSelectedIndex() == 2) {
                tipo = TipoSquadra.TENNIS_SINGOLO;
            } else {
                tipo = TipoSquadra.TENNIS_DOPPIO;
            }
            contentTornei.removeAll();
            if (output.isEmpty()) {
                contentTornei.add(new JLabel("Putroppo per questo sport non ci sono tornei previsti per ora"));
            } else {
                contentTornei.add(new JLabel("TORNEI PREVISTI:"));
                contentTornei.add(Box.createVerticalStrut(10));
                for (int i = 0; i < output.size(); i++) {
                    contentTornei.add(new JLabel("Codice torneo: " + output.get(i).codiceTorneo + ", " + 
                                                "Nome: " + output.get(i).nome + ", " +
                                                "Data di Svolgimento: " + output.get(i).dataSvolgimento + ", " +
                                                "Premio: " + output.get(i).premio + ", " + 
                                                "Max partecipanti: " + output.get(i).massimoPartecipanti + ", " +
                                                "Quota d'iscrizione: " + output.get(i).quotaIscrizione));
                }
            }
        });

        //aggiungo il pannello dei dati dentro a quello principale dei tornei
        tornei.add(datiTorneo, BorderLayout.WEST);

        //PANNELLO ISCRIZIONI
        JPanel iscrizione = new JPanel();
        iscrizione.setLayout(new BoxLayout(iscrizione, BoxLayout.Y_AXIS));

        //titolo
        JLabel iscrizioneTitolo = new JLabel("Iscrizioni", SwingConstants.CENTER);
        iscrizioneTitolo.setFont(new Font("Arial", Font.BOLD, 30));
        iscrizioneTitolo.setAlignmentX(Component.CENTER_ALIGNMENT);
        iscrizioneTitolo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        iscrizione.add(iscrizioneTitolo);

        //pannello principale
        JPanel contentIscrizioni = new JPanel();
        contentIscrizioni.setLayout(new BoxLayout(contentIscrizioni, BoxLayout.Y_AXIS));
        JScrollPane scrollLezioni = new JScrollPane(contentIscrizioni);
        iscrizione.add(scrollLezioni, BorderLayout.CENTER);

        //dati da compilare sotto
        JPanel datiIscrizione = new JPanel();
        datiIscrizione.setLayout(new GridLayout(6,2,2,0));
        JLabel numCampoLabel = new JLabel("Numero campo: ");
        JTextField numCampoField = new JTextField(16);
        JLabel orarioInizioLabel = new JLabel("Orario inizio: ");
        JComboBox<String> orarioInizioBox = new JComboBox<>(orari);
        JLabel dataLabel = new JLabel("Data: ");
        JTextField dataField = new JTextField(16);
        JLabel sportLabel = new JLabel("Sport: ");
        JComboBox<String> sportBox = new JComboBox<>(sports);
        JButton buttonIscrizione = new JButton("Iscrivimi");
        JButton buttonRicerca = new JButton("Cerca");
        JButton buttonCercaSpazio = new JButton("Trova spazio");
        JButton creaLezioneButton = new JButton("Inserisci Lezione");
        datiIscrizione.add(numCampoLabel);
        datiIscrizione.add(numCampoField);;
        datiIscrizione.add(orarioInizioLabel);
        datiIscrizione.add(orarioInizioBox);
        datiIscrizione.add(dataLabel);
        datiIscrizione.add(dataField);
        datiIscrizione.add(sportLabel);
        datiIscrizione.add(sportBox);
        datiIscrizione.add(buttonIscrizione);
        datiIscrizione.add(buttonRicerca);
        datiIscrizione.add(buttonCercaSpazio);
        datiIscrizione.add(creaLezioneButton);

        //aggiungo un ActionListener sul bottone Cerca
        buttonRicerca.addActionListener(e -> {
            List<LezionePrivata> lezioni = new ArrayList<>();
            String orario = orarioInizioBox.getSelectedItem().toString();
            String data = dataField.getText();
            Sport sport = (sportBox.getSelectedIndex() == 0)
                        ? Sport.CALCETTO 
                        : (sportBox.getSelectedIndex() == 1)
                        ? Sport.PADEL
                        : (sportBox.getSelectedIndex() == 2)
                        ? Sport.TENNIS
                        : Sport.TENNIS;
            if (!numCampoField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(
                    null, 
                    "Il numero del campo non serve per la ricerca", 
                    "Dati non necessari", 
                    JOptionPane.WARNING_MESSAGE);
            } else if (orario.isEmpty() || data.isEmpty()) {
                JOptionPane.showMessageDialog(
                    null, 
                    "Campi mancanti per la ricerca (Orario o Data)", 
                    "Campi mancanti", 
                    JOptionPane.WARNING_MESSAGE);
            } else {
                lezioni = this.menu.getController().findjoinableLesson(data, orario, sport);
                if (lezioni.isEmpty()) {
                    contentIscrizioni.removeAll();
                    contentIscrizioni.add(new JLabel("Non ci sono lezioni disponibili, prova in altre occasioni..."));
                } else {
                    contentIscrizioni.removeAll();
                    for (int i = 0; i < lezioni.size(); i++) {
                        contentIscrizioni.add(new JLabel("Campo: " + lezioni.get(i).numeroCampo + ", " +
                                                        "Orario Inizio: " + lezioni.get(i).orarioInizio + ", " +
                                                        "Data Svolgimento: " + lezioni.get(i).dataSvolgimento + ", " +
                                                        "Prezzo: " + lezioni.get(i).prezzo + ", " +
                                                        "Allenatore: " + lezioni.get(i).allenatore.nome + " " + lezioni.get(i).allenatore.cognome));
                    }
                }
            }
            contentIscrizioni.revalidate();
            contentIscrizioni.repaint();
        });

        //aggiungo un ActionListener sul bottone dell'iscrizione
        buttonIscrizione.addActionListener(e -> {
            String orario = orarioInizioBox.getSelectedItem().toString();
            String data = dataField.getText();
            Sport sport = (sportBox.getSelectedIndex() == 0)
                        ? Sport.CALCETTO 
                        : (sportBox.getSelectedIndex() == 1)
                        ? Sport.PADEL
                        : (sportBox.getSelectedIndex() == 2)
                        ? Sport.TENNIS
                        : Sport.TENNIS;
            LocalDate str = LocalDate.parse(data);
            String giornoProva = str.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ITALIAN);
            Giorno giorno = (giornoProva.equals("lunedì"))
                            ? Giorno.LUNEDI
                            : (giornoProva.equals("martedì"))
                            ? Giorno.MARTEDI
                            : (giornoProva.equals("mercoledì"))
                            ? Giorno.MERCOLEDI
                            : (giornoProva.equals("giovedì")) 
                            ? Giorno.GIOVEDI
                            : (giornoProva.equals("venerdì"))
                            ? Giorno.VENERDI
                            : (giornoProva.equals("sabato"))
                            ? Giorno.SABATO
                            : Giorno.DOMENICA;
            String numCampo = numCampoField.getText();
            if (data.isEmpty() || numCampo.isEmpty()) {
                JOptionPane.showMessageDialog(
                    null, 
                    "Dati mancanti per la prenotazione", 
                    "Campi mancanti", 
                    JOptionPane.WARNING_MESSAGE);
            } else if (giorno.equals(Giorno.SABATO) || giorno.equals(Giorno.DOMENICA)) {
                JOptionPane.showMessageDialog(
                    null, 
                    "Le lezioni private NON si svolgono di sabato o di domenica", 
                    "Campi invalidi", 
                    JOptionPane.WARNING_MESSAGE);
            } else {
                if (this.menu.getController().joinLesson(persona, Integer.parseInt(numCampo), giorno, orario, data, sport) == 0) {
                    contentIscrizioni.removeAll();
                    contentIscrizioni.add(new JLabel("Purtroppo la lezione selezionata non esiste o è già piena"));
                } else {
                    contentIscrizioni.removeAll();
                    contentIscrizioni.add(new JLabel("Sei stato iscritto alla lezione con successo!"));
                }
            }
            contentIscrizioni.revalidate();
            contentIscrizioni.repaint();
        });

        //aggiungo un ActionLister al bottone per cercare spazio per delle lezioni
        buttonCercaSpazio.addActionListener(e -> {
            List<Campo> campi = new ArrayList<>();
            String orario = orarioInizioBox.getSelectedItem().toString();
            String data = dataField.getText();
            Sport sport = (sportBox.getSelectedIndex() == 0)
                        ? Sport.CALCETTO 
                        : (sportBox.getSelectedIndex() == 1)
                        ? Sport.PADEL
                        : (sportBox.getSelectedIndex() == 2)
                        ? Sport.TENNIS
                        : Sport.TENNIS;
            LocalDate str = LocalDate.parse(data);
            String giornoProva = str.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ITALIAN);
            Giorno giorno = (giornoProva.equals("lunedì"))
                            ? Giorno.LUNEDI
                            : (giornoProva.equals("martedì"))
                            ? Giorno.MARTEDI
                            : (giornoProva.equals("mercoledì"))
                            ? Giorno.MERCOLEDI
                            : (giornoProva.equals("giovedì")) 
                            ? Giorno.GIOVEDI
                            : (giornoProva.equals("venerdì"))
                            ? Giorno.VENERDI
                            : (giornoProva.equals("sabato"))
                            ? Giorno.SABATO
                            : Giorno.DOMENICA;
            if (data.isEmpty()) {
                JOptionPane.showMessageDialog(
                    null, 
                    "Dati mancanti per la prenotazione", 
                    "Campi mancanti", 
                    JOptionPane.WARNING_MESSAGE);
            } else if (giorno.equals(Giorno.SABATO) || giorno.equals(Giorno.DOMENICA)) {
                JOptionPane.showMessageDialog(
                    null, 
                    "Le lezioni private NON si svolgono di sabato o di domenica", 
                    "Campi invalidi", 
                    JOptionPane.WARNING_MESSAGE);
            } else {
                campi = this.menu.getController().findSpaceForNewLesson(sport, orario, giorno, data);
                if (campi.isEmpty()) {
                    contentIscrizioni.removeAll();
                    contentIscrizioni.add(new JLabel("Putroppo in questa data non ci sono campi disponibili"));
                } else {
                    contentIscrizioni.removeAll();
                    contentIscrizioni.add(new JLabel("Campi disponibli: "));
                    contentIscrizioni.add(Box.createVerticalStrut(10));
                    for (int i = 0; i < campi.size(); i++) {
                        contentIscrizioni.add(new JLabel("Campo: " + campi.get(i).numeroCampo));
                    }
                }
            }
            contentIscrizioni.revalidate();
            contentIscrizioni.repaint();
        });

        //aggiungo un ActionListener per il creare una nuova lezione
        creaLezioneButton.addActionListener(e -> {
            String orario = orarioInizioBox.getSelectedItem().toString();
            String data = dataField.getText();
            Sport sport = (sportBox.getSelectedIndex() == 0)
                        ? Sport.CALCETTO 
                        : (sportBox.getSelectedIndex() == 1)
                        ? Sport.PADEL
                        : (sportBox.getSelectedIndex() == 2)
                        ? Sport.TENNIS
                        : Sport.TENNIS;
            LocalDate str = LocalDate.parse(data);
            String giornoProva = str.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ITALIAN);
            Giorno giorno = (giornoProva.equals("lunedì"))
                            ? Giorno.LUNEDI
                            : (giornoProva.equals("martedì"))
                            ? Giorno.MARTEDI
                            : (giornoProva.equals("mercoledì"))
                            ? Giorno.MERCOLEDI
                            : (giornoProva.equals("giovedì")) 
                            ? Giorno.GIOVEDI
                            : (giornoProva.equals("venerdì"))
                            ? Giorno.VENERDI
                            : (giornoProva.equals("sabato"))
                            ? Giorno.SABATO
                            : Giorno.DOMENICA;
            String numCampo = numCampoField.getText();
            if (data.isEmpty() || numCampo.isEmpty()) {
                JOptionPane.showMessageDialog(
                    null, 
                    "Dati mancanti per la prenotazione", 
                    "Campi mancanti", 
                    JOptionPane.WARNING_MESSAGE);
            } else if (giorno.equals(Giorno.SABATO) || giorno.equals(Giorno.DOMENICA)) {
                JOptionPane.showMessageDialog(
                    null, 
                    "Le lezioni private NON si svolgono di sabato o di domenica", 
                    "Campi invalidi", 
                    JOptionPane.WARNING_MESSAGE);
            } else if (this.menu.getController().findFreeTrainer(data, orario) == null) {
                JOptionPane.showMessageDialog(
                    null, 
                    "Purtoppo non ci sono allenatori disponibili, prova in altre date o altri orari", 
                    "Allenatore non disponibile", 
                    JOptionPane.WARNING_MESSAGE);
            } else {
                contentIscrizioni.removeAll();
                contentIscrizioni.add(new JLabel("Lezione inserita con successo!"));
                this.menu.getController().createNewLesson(Integer.parseInt(numCampo), giorno, orario, data, sport, 30.00, persona);
            }
            contentIscrizioni.revalidate();
            contentIscrizioni.repaint();
        });

        //aggiungo il pannello dei dati delle iscrizioni dentro al pannello delle iscrizioni
        iscrizione.add(datiIscrizione, BorderLayout.WEST);


        //PANNELLO PRENOTAZIONE
        JPanel prenotazione = new JPanel();
        prenotazione.setLayout(new BoxLayout(prenotazione, BoxLayout.Y_AXIS));
        
        //titolo
        JLabel prenotazioneTitolo = new JLabel("Prenotazioni", SwingConstants.CENTER);
        prenotazioneTitolo.setFont(new Font("Arial", Font.BOLD, 30));
        prenotazioneTitolo.setAlignmentX(Component.CENTER_ALIGNMENT);
        prenotazioneTitolo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        prenotazione.add(prenotazioneTitolo);


        //PANNELLO CORSO
        JPanel corso = new JPanel();
        corso.setLayout(new BoxLayout(corso, BoxLayout.Y_AXIS));

        //titolo
        JLabel corsoTitolo = new JLabel("Corsi", SwingConstants.CENTER);
        corsoTitolo.setFont(new Font("Arial", Font.BOLD, 30));
        corsoTitolo.setAlignmentX(Component.CENTER_ALIGNMENT);
        corsoTitolo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        corso.add(corsoTitolo);

        //pannello principale
        JPanel contentCorsi = new JPanel();
        contentCorsi.setLayout(new BoxLayout(contentCorsi, BoxLayout.Y_AXIS));
        JScrollPane scrollCorsi = new JScrollPane(contentCorsi);
        corso.add(scrollCorsi, BorderLayout.CENTER);

        //dati da compilare
        JPanel datiCorso = new JPanel();
        datiCorso.setLayout(new GridLayout(3,2,2,0));
        JLabel codiceCorsoLabel = new JLabel("Codice corso: ");
        JTextField codiceCorsoField = new JTextField(16);
        JButton joinCorsoButton = new JButton("Iscrizione");
        JButton ricercaCorsiButton = new JButton("Ricerca");
        JButton corsiPiuAttiviButton = new JButton("Corsi piú frequentati");
        JButton corsiUtenteButton = new JButton("I miei corsi");
        datiCorso.add(codiceCorsoLabel);
        datiCorso.add(codiceCorsoField);
        datiCorso.add(joinCorsoButton);
        datiCorso.add(ricercaCorsiButton);
        datiCorso.add(corsiPiuAttiviButton);
        datiCorso.add(corsiUtenteButton);

        //aggiungo un ActionListener sul bottone per cercare se un corso è attivo
        ricercaCorsiButton.addActionListener(e -> {
            String codice = codiceCorsoField.getText();
            if (codice.isEmpty()) {
                JOptionPane.showMessageDialog(
                    null, 
                    "Codice corso mancante", 
                    "Campi mancanti", 
                    JOptionPane.WARNING_MESSAGE);
            } else {
                Corso output;
                output = this.menu.getController().findActiveCourse(Integer.parseInt(codice));
                if (output == null) {
                    contentCorsi.removeAll();
                    contentCorsi.add(new JLabel("Il corso cercato non è attivo o non esiste"));
                } else {
                    contentCorsi.removeAll();
                    contentCorsi.add(new JLabel("Il corso cercato è il seguente ed è attivo"));
                    contentCorsi.add(new JLabel("Data di fine: " + output.dataFine + ", " +
                                                "Sport praticato: " + output.sportPraticato + ", " + 
                                                "Prezzo: " + output.prezzo + ", " + 
                                                "Allenatore: " + output.allenatore.nome + " " + output.allenatore.cognome));
                }
            }
            contentCorsi.revalidate();
            contentCorsi.repaint();
        });

        //aggiungo un ActionListener sul bottone per joinare un corso
        joinCorsoButton.addActionListener(e -> {
            String codice = codiceCorsoField.getText();
            if (codice.isEmpty()) {
                JOptionPane.showMessageDialog(
                    null, 
                    "Codice corso mancante", 
                    "Campi mancanti", 
                    JOptionPane.WARNING_MESSAGE);
            } else {
                if (this.menu.getController().joinCourse(persona, Integer.parseInt(codice)) == 1) {
                    contentCorsi.removeAll();
                    contentCorsi.add(new JLabel("Iscrizione al corso effettuata!"));
                } else {
                    contentCorsi.removeAll();
                    contentCorsi.add(new JLabel("Purtroppo qualcosa è andato storto, controlla di non essere già iscritto a questo corso"));
                }
            }
            contentCorsi.revalidate();
            contentCorsi.repaint();
        });

        //aggiungo un ActionListener per cercare i corsi con piu partecipanti
        corsiPiuAttiviButton.addActionListener(e -> {
            codiceCorsoField.setText("");   //per bellezza
            List<Corso> output = this.menu.getController().findMostActiveCourses();
            if (output.isEmpty()) {
                contentCorsi.removeAll();
                contentCorsi.add(new JLabel("Non sono presenti corsi in questo momento"));
            } else {
                contentCorsi.removeAll();
                contentCorsi.add(new JLabel("I CORSI PIU ATTIVI"), SwingConstants.CENTER);
                for (int i = 0; i < output.size(); i++) {
                    contentCorsi.add(new JLabel("Codice corso: " + output.get(i).codiceCorso + ", " +
                                                "Data di fine: " + output.get(i).dataFine + ", " +
                                                "Sport praticato: " + output.get(i).sportPraticato + ", " + 
                                                "Prezzo: " + output.get(i).prezzo + ", " + 
                                                "Allenatore: " + output.get(i).allenatore.nome + " " + output.get(i).allenatore.cognome));
                }
            }
            contentCorsi.revalidate();
            contentCorsi.repaint();
        });

        //aggiungo un ActionListener per visualizzare i corsi dell'utente
        corsiUtenteButton.addActionListener(e -> {
            List<Corso> output = this.menu.getController().allCoursesOfUser(persona);
            if (output.isEmpty()) {
                contentCorsi.removeAll();
                contentCorsi.add(new JLabel("Non sei iscritto a nessun corso"));
            } else {
                contentCorsi.removeAll();
                contentCorsi.add(new JLabel("I MIEI CORSI"), SwingConstants.CENTER);
                for (int i = 0; i < output.size(); i++) {
                    contentCorsi.add(new JLabel("Codice corso: " + output.get(i).codiceCorso + ", " +
                                                "Data di fine: " + output.get(i).dataFine + ", " +
                                                "Sport praticato: " + output.get(i).sportPraticato + ", " +  
                                                "Allenatore: " + output.get(i).allenatore.nome + " " + output.get(i).allenatore.cognome));
                }
            }
            contentCorsi.revalidate();
            contentCorsi.repaint();
        });

        //aggiungo al pannello corsi
        corso.add(datiCorso, BorderLayout.WEST);


        //aggiungo al main
        main.addTab("Squadre", squadre);
        main.addTab("Tornei", tornei);
        main.addTab("Iscrizioni", iscrizione);
        main.addTab("Prenotazioni", prenotazione);
        main.addTab("Corsi", corso);
        

        //campi dello userPanel
        JLabel title = new JLabel("Informazioni personali", SwingConstants.CENTER);
        JLabel nomeLabel = new JLabel("Nome: " + persona.nome);
        JLabel cognomeLabel = new JLabel("Cognome: " + persona.cognome);
        JLabel emailLabel = new JLabel("Email: "+ persona.email);
        nomeLabel.setAlignmentX(JLabel.RIGHT);
        cognomeLabel.setAlignmentX(JLabel.RIGHT);
        emailLabel.setAlignmentX(JLabel.RIGHT);
        userPanel.add(title);
        userPanel.add(Box.createVerticalStrut(20));
        userPanel.add(nomeLabel);
        userPanel.add(cognomeLabel);
        userPanel.add(emailLabel);



        //bottone indietro
        JButton backButton = new JButton("Indietro");
        backButton.setAlignmentX(JButton.LEFT_ALIGNMENT);
        backButton.setAlignmentY(JButton.TOP_ALIGNMENT);
        backPanel.add(backButton);
        backButton.addActionListener(e -> {
            this.back();
        });


        //aggiungo tutto al mainFrame
        mainFrame.getContentPane().add(main, BorderLayout.CENTER);
        mainFrame.getContentPane().add(backPanel, BorderLayout.WEST);
        mainFrame.getContentPane().add(userPanel, BorderLayout.EAST);
        mainFrame.setResizable(false);
        mainFrame.setVisible(true);
        mainFrame.addWindowListener(
            (WindowListener) new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    //onClose.run();
                    System.exit(0);
                }
            }
        );

        return mainFrame;
    }

    public void back() {
        var cp = mainFrame.getContentPane();
        cp.removeAll();
        mainFrame = menu.setUp();
    }
}
