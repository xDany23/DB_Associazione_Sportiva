package db_ass.view.user;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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

import db_ass.data.Campo;
import db_ass.data.Giorno;
import db_ass.data.LezionePrivata;
import db_ass.data.Persona;
import db_ass.data.Sport;
import db_ass.view.Menu;

public final class IscrizionePanel {

    public JPanel setUp(Persona persona, Menu menu) {

        String[] orariLezioni = {"15:00:00", "16:30:00", "18:00:00"};
        String[] sports = {"Calcetto", "Padel", "Tennis singolo", "Tennis doppio"};

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
        JButton leMieLezioniButton = new JButton("Le mie lezioni");
        JButton vuoto = new JButton();
        datiIscrizione.add(numCampoLabel);
        datiIscrizione.add(numCampoField);
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
        datiIscrizione.add(leMieLezioniButton);
        datiIscrizione.add(vuoto);

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
                lezioni = menu.getController().findjoinableLesson(data, orario, sport);
                if (lezioni.isEmpty()) {
                    contentIscrizioni.removeAll();
                    contentIscrizioni.add(new JLabel("Non ci sono lezioni disponibili, prova in altre occasioni..."));
                } else {
                    contentIscrizioni.removeAll();
                    for (int i = 0; i < lezioni.size(); i++) {
                        contentIscrizioni.add(new JLabel("Campo: " + lezioni.get(i).numeroCampo.numeroCampo + ", " +
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
                if (menu.getController().joinLesson(persona, Integer.parseInt(numCampo), giorno, orario, data, sport) == 0) {
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
                campi = menu.getController().findSpaceForNewLesson(sport, orario, giorno, data);
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
            } else if (menu.getController().findFreeTrainer(data, orario) == null) {
                JOptionPane.showMessageDialog(
                    null, 
                    "Purtoppo non ci sono allenatori disponibili, prova in altre date o altri orari", 
                    "Allenatore non disponibile", 
                    JOptionPane.WARNING_MESSAGE);
            } else {
                contentIscrizioni.removeAll();
                contentIscrizioni.add(new JLabel("Lezione inserita con successo!"));
                menu.getController().createNewLesson(Integer.parseInt(numCampo), giorno, orario, data, sport, 30.00, persona);
            }
            contentIscrizioni.revalidate();
            contentIscrizioni.repaint();
        });

        //aggiungo un ActionListener al bottone per visualizzare tutte le lezioni dell'utente
        leMieLezioniButton.addActionListener(e -> {
            List<LezionePrivata> output = new ArrayList<>();
            numCampoField.setText("");
            dataField.setText("");
            output = menu.getController().allUserLessons(persona);
            contentIscrizioni.removeAll();
            if (output.isEmpty()) {
                contentIscrizioni.add(new JLabel("Non hai nessuna lezione in programma"));
            } else {
                contentIscrizioni.add(new JLabel("LE TUE PROSSIME LEZIONI:"));
                contentIscrizioni.add(Box.createVerticalStrut(10));
                for(int i = 0; i < output.size(); i++) {
                    contentIscrizioni.add(new JLabel("- Data: " + output.get(i).dataSvolgimento + ", " +
                                                    "Orario: " + output.get(i).orarioInizio + ", " +
                                                    "Sport: " + output.get(i).sportPraticato + ", " +
                                                    "Campo: " + output.get(i).numeroCampo.numeroCampo + ", " +
                                                    "Allenatore: " + output.get(i).allenatore.nome + " " + output.get(i).allenatore.cognome));
                }
            }
            contentIscrizioni.revalidate();
            contentIscrizioni.repaint();
        });

        //aggiungo il pannello dei dati delle iscrizioni dentro al pannello delle iscrizioni
        iscrizione.add(datiIscrizione, BorderLayout.WEST);

        return iscrizione;
    }
}
