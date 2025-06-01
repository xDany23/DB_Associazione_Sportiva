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
import db_ass.data.FasciaOraria;
import db_ass.data.Giorno;
import db_ass.data.LezionePrivata;
import db_ass.data.Persona;
import db_ass.data.Prenotazione;
import db_ass.data.RisultatiTorneo;
import db_ass.data.Sport;
import db_ass.data.Squadra;
import db_ass.data.TipoSquadra;
import db_ass.data.Torneo;
import db_ass.view.user.SquadraPanel;

public class UserPage {
    
    private Menu menu;
    private JFrame mainFrame;
    private Persona persona;
    SquadraPanel squadra = new SquadraPanel();

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
        String[] orariLezioni = {"15:00:00", "16:30:00", "18:00:00"};
        String[] orariPrenotazioni = {"7:30:00", "9:00:00", "10:30:00", "12:00:00", "13:30:00", "19:30:00", "21:00:00"};
        String[] sports = {"Calcetto", "Padel", "Tennis singolo", "Tennis doppio"};

        //PAGINE DEL MAIN
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
            } else if (this.menu.getController().findTeam(Integer.parseInt(codiceSquadra)).tipo.equals(TipoSquadra.TENNIS_SINGOLO) &&
                       !this.menu.getController().findTeam(Integer.parseInt(codiceSquadra)).componente1.equals(persona)) {
                JOptionPane.showMessageDialog(
                    null, 
                    "Non fai parte della squadra inserita! Riprovare", 
                    "Campi errati", 
                    JOptionPane.WARNING_MESSAGE);
            } else if ((this.menu.getController().findTeam(Integer.parseInt(codiceSquadra)).tipo.equals(TipoSquadra.TENNIS_DOPPIO) ||
                        this.menu.getController().findTeam(Integer.parseInt(codiceSquadra)).tipo.equals(TipoSquadra.PADEL)) &&
                        (!this.menu.getController().findTeam(Integer.parseInt(codiceSquadra)).componente1.equals(persona) ||
                        !this.menu.getController().findTeam(Integer.parseInt(codiceSquadra)).componente2.equals(persona))) {
                JOptionPane.showMessageDialog(
                    null, 
                    "Non fai parte della squadra inserita! Riprovare", 
                    "Campi errati", 
                    JOptionPane.WARNING_MESSAGE);
            } else if (this.menu.getController().findTeam(Integer.parseInt(codiceSquadra)).tipo.equals(TipoSquadra.CALCETTO) &&
                        (!this.menu.getController().findTeam(Integer.parseInt(codiceSquadra)).componente1.equals(persona) ||
                        !this.menu.getController().findTeam(Integer.parseInt(codiceSquadra)).componente2.equals(persona) ||
                        !this.menu.getController().findTeam(Integer.parseInt(codiceSquadra)).componente3.equals(persona) ||
                        !this.menu.getController().findTeam(Integer.parseInt(codiceSquadra)).componente4.equals(persona) ||
                        !this.menu.getController().findTeam(Integer.parseInt(codiceSquadra)).componente5.equals(persona))) {
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
            } else if (!this.menu.getController().tournamentsEnterable(this.menu.getController().findTeam(Integer.parseInt(codiceSquadra)).tipo).contains(this.menu.getController().findTournament(Integer.parseInt(codiceTorneo)))) { //controllo se il torneo è joinable oppure no
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
            output = this.menu.getController().tournamentsEnterable(tipo);
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
            contentTornei.revalidate();
            contentTornei.repaint();
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
        JComboBox<String> orarioInizioBox = new JComboBox<>(orariLezioni);
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

        //pannello principale
        JPanel contentPrenotazione = new JPanel();
        contentPrenotazione.setLayout(new BoxLayout(contentPrenotazione, BoxLayout.Y_AXIS));
        JScrollPane scrollPrenotazione = new JScrollPane(contentPrenotazione);
        prenotazione.add(scrollPrenotazione, BorderLayout.CENTER);

        //dati da compilare
        JPanel datiPrenotazione = new JPanel();
        datiPrenotazione.setLayout(new GridLayout(5,2,2,0));
        JLabel orarioInizioPrenLabel = new JLabel("Fascia oraria: ");
        JComboBox<String> orarioInizioPrenBox = new JComboBox<>(orariPrenotazioni);
        JLabel dataPrenLabel = new JLabel("Data: ");
        JTextField dataPrenField = new JTextField(16);
        JLabel sportPrenLabel = new JLabel("Sport: ");
        JComboBox<String> sportPrenBox = new JComboBox<>(sports); 
        JLabel campoPrenLabel = new JLabel("Campo: ");
        JTextField campoPrenField = new JTextField(16);
        JButton cercaCampoButton = new JButton("Cerca");
        JButton prenotaCampoButton = new JButton("Prenota");
        datiPrenotazione.add(dataPrenLabel);
        datiPrenotazione.add(dataPrenField);
        datiPrenotazione.add(campoPrenLabel);
        datiPrenotazione.add(campoPrenField);
        datiPrenotazione.add(orarioInizioPrenLabel);
        datiPrenotazione.add(orarioInizioPrenBox);
        datiPrenotazione.add(sportPrenLabel);
        datiPrenotazione.add(sportPrenBox);
        datiPrenotazione.add(cercaCampoButton);
        datiPrenotazione.add(prenotaCampoButton);

        //aggiungo un ActionListener per il bottone della ricerca di un campo
        cercaCampoButton.addActionListener(e -> {
            campoPrenField.setText("");
            List<Integer> output = new ArrayList<>();
            String oraInizio = orarioInizioPrenBox.getSelectedItem().toString();
            String data = dataPrenField.getText();
            Sport sport = (sportPrenBox.getSelectedIndex() == 0)
                        ? Sport.CALCETTO 
                        : (sportPrenBox.getSelectedIndex() == 1)
                        ? Sport.PADEL
                        : Sport.TENNIS;
            if (oraInizio.isEmpty()) {
                JOptionPane.showMessageDialog(
                    null, 
                    "Data non inserita", 
                    "Campi mancanti", 
                    JOptionPane.WARNING_MESSAGE);
            } else {
                contentPrenotazione.removeAll();
                output = this.menu.getController().findFieldToBook(oraInizio, data, sport);
                if (output.isEmpty()) {
                    contentPrenotazione.add(new JLabel("Non ci sono campi disponibili nel giorno e orari selezionati per questo sport"));
                } else {
                    contentPrenotazione.add(new JLabel("CAMPI:"));
                    contentPrenotazione.add(Box.createVerticalStrut(10));
                    for (int i = 0; i < output.size(); i++) {
                        contentPrenotazione.add(new JLabel("Campo " + output.get(i)));
                    }
                }
                contentPrenotazione.revalidate();
                contentPrenotazione.repaint();
            }
        });

        //aggiungo un ActionListener per il bottone per la prenotazione di un campo
        prenotaCampoButton.addActionListener(e -> {
            String oraInizio = orarioInizioPrenBox.getSelectedItem().toString();
            String data = dataPrenField.getText();
            Sport sport = (sportPrenBox.getSelectedIndex() == 0)
                        ? Sport.CALCETTO 
                        : (sportPrenBox.getSelectedIndex() == 1)
                        ? Sport.PADEL
                        : Sport.TENNIS;
            String campo = campoPrenField.getText();
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
            if (data.isEmpty() || campo.isEmpty() || oraInizio.isEmpty()) {
                JOptionPane.showMessageDialog(
                    null, 
                    "Non hai inserito tutti i dati per la prenotazione", 
                    "Campi mancanti", 
                    JOptionPane.WARNING_MESSAGE);
            } else if (!this.menu.getController().findFieldToBook(oraInizio, data, sport).contains(Integer.parseInt(campo))) {
                JOptionPane.showMessageDialog(
                    null, 
                    "Il campo che hai inserito non è disponibile, controlla meglio", 
                    "Campi errati", 
                    JOptionPane.WARNING_MESSAGE);
            } else {
                contentPrenotazione.removeAll();
                Prenotazione p = new Prenotazione(data, LocalDate.now().toString(), persona, this.menu.getController().findPeriod(Integer.parseInt(campo), giorno, oraInizio));
                if (this.menu.getController().bookField(p) == 0) {
                    contentPrenotazione.add(new JLabel("Qualcosa è andato storto, riprova"));
                } else {
                    contentPrenotazione.add(new JLabel("Prenotazione effettuata con successo!"));
                }
                contentPrenotazione.revalidate();
                contentPrenotazione.repaint();
            }
        });

        //aggiungo il pannello dei dati a quello principale
        prenotazione.add(datiPrenotazione, BorderLayout.WEST);


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
        main.addTab("Squadre", squadra.setUp());
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
