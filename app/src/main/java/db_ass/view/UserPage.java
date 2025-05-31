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

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;

import db_ass.data.Giorno;
import db_ass.data.LezionePrivata;
import db_ass.data.Persona;
import db_ass.data.Sport;

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
        String[] giorniLezioniPrivate = {"Lunedi", "Martedi", "Mercoledi", "Giovedi", "Venerdi"};
        String[] orari = {"15:00:00", "16:30:00", "18:00:00"};
        String[] sports = {"Calcetto", "Padel", "Tennis singolo", "Tennis doppio"};

        //PAGINE DEL MAIN
        //pannello squadra
        JPanel squadre = new JPanel();
        squadre.setLayout(new BoxLayout(squadre, BoxLayout.Y_AXIS));
        JLabel squadreTitolo = new JLabel("Le mie squadre", SwingConstants.CENTER);
        squadreTitolo.setFont(new Font("Arial", Font.BOLD, 30));
        squadreTitolo.setAlignmentX(Component.CENTER_ALIGNMENT);
        squadreTitolo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        JPanel contentSquadra = new JPanel();
        contentSquadra.setLayout(new BoxLayout(contentSquadra, BoxLayout.Y_AXIS));
        for (int i = 0; i < 30; i++) {
            contentSquadra.add(new JLabel("Riga #" + i));
        }
        JScrollPane scrollSquadre = new JScrollPane(contentSquadra);
        squadre.add(squadreTitolo);
        squadre.add(scrollSquadre);
        
        //pannello tornei
        JPanel tornei = new JPanel();
        tornei.setLayout(new BoxLayout(tornei, BoxLayout.Y_AXIS));
        JLabel torneiTitolo = new JLabel("I miei tornei", SwingConstants.CENTER);
        torneiTitolo.setFont(new Font("Arial", Font.BOLD, 30));
        torneiTitolo.setAlignmentX(Component.CENTER_ALIGNMENT);
        torneiTitolo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        JPanel contentTornei = new JPanel();
        contentTornei.setLayout(new BoxLayout(contentTornei, BoxLayout.Y_AXIS));
        for (int i = 0; i < 30; i++) {
            contentTornei.add(new JLabel("Riga #" + i));
        }
        JScrollPane scrollTornei = new JScrollPane(contentTornei);
        tornei.add(torneiTitolo);
        tornei.add(scrollTornei);

        //pannello iscrizioni
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
        datiIscrizione.setLayout(new GridLayout(7,2,2,0));
        JLabel numCampoLabel = new JLabel("Numero campo: ");
        JTextField numCampoField = new JTextField(16);
        JLabel giornoLabel = new JLabel("Giorno: ");
        JComboBox<String> giornoBox = new JComboBox<>(giorniLezioniPrivate);
        JLabel orarioInizioLabel = new JLabel("Orario inizio: ");
        JComboBox<String> orarioInizioBox = new JComboBox<>(orari);
        JLabel dataLabel = new JLabel("Data: ");
        JTextField dataField = new JTextField(16);
        JLabel sportLabel = new JLabel("Sport: ");
        JComboBox<String> sportBox = new JComboBox<>(sports);
        JButton buttonIscrizione = new JButton("Iscrivimi");
        JButton buttonRicerca = new JButton("Cerca");
        JButton buttonCercaSpazio = new JButton("Trova spazio");
        datiIscrizione.add(numCampoLabel);
        datiIscrizione.add(numCampoField);
        datiIscrizione.add(giornoLabel);
        datiIscrizione.add(giornoBox);
        datiIscrizione.add(orarioInizioLabel);
        datiIscrizione.add(orarioInizioBox);
        datiIscrizione.add(dataLabel);
        datiIscrizione.add(dataField);
        datiIscrizione.add(sportLabel);
        datiIscrizione.add(sportBox);
        datiIscrizione.add(buttonIscrizione);
        datiIscrizione.add(buttonRicerca);
        datiIscrizione.add(buttonCercaSpazio);

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
                    "Il numero del campo e il giorno non servono per la ricerca", 
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
            if (giornoProva == "LUNEDI") {
                JOptionPane.showMessageDialog(
                    null, 
                    "Lo legge come LUNEDI", 
                    "BENE", 
                    JOptionPane.WARNING_MESSAGE);
            }
            Giorno giorno = (giornoBox.getSelectedIndex() == 0)
                            ? Giorno.LUNEDI
                            : (giornoBox.getSelectedIndex() == 1)
                            ? Giorno.MARTEDI
                            : (giornoBox.getSelectedIndex() == 2)
                            ? Giorno.MERCOLEDI
                            : (giornoBox.getSelectedIndex() == 3) 
                            ? Giorno.GIOVEDI
                            : Giorno.VENERDI;
            String numCampo = numCampoField.getText();
            if (data.isEmpty() || numCampo.isEmpty()) {
                JOptionPane.showMessageDialog(
                    null, 
                    "Dati mancanti per la prenotazione", 
                    "Campi mancanti", 
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

        });

        //aggiungo il pannello dei dati delle iscrizioni dentro al pannello delle iscrizioni
        iscrizione.add(datiIscrizione, BorderLayout.WEST);

        JPanel prenotazione = new JPanel();
        prenotazione.add(new JLabel("Prenotazioni"));
        prenotazione.add(Box.createVerticalStrut(10));

        //aggiungo al main
        main.addTab("Squadre", squadre);
        main.addTab("Tornei", tornei);
        main.addTab("Iscrizioni", iscrizione);
        main.addTab("Prenotazioni", prenotazione);
        

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
