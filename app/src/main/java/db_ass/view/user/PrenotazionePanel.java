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

import db_ass.data.Giorno;
import db_ass.data.Persona;
import db_ass.data.Prenotazione;
import db_ass.data.Sport;
import db_ass.view.Menu;

public final class PrenotazionePanel {

    public JPanel setUp(Persona persona, Menu menu) {

        String[] orariPrenotazioni = {"7:30:00", "9:00:00", "10:30:00", "12:00:00", "13:30:00", "19:30:00", "21:00:00"};
        String[] sports = {"Calcetto", "Padel", "Tennis singolo", "Tennis doppio"};

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
        datiPrenotazione.setLayout(new GridLayout(6,2,2,0));
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
        JButton userPrenotazioniButton = new JButton("Le mie prenotazioni");
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
        datiPrenotazione.add(userPrenotazioniButton);

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
                output = menu.getController().findFieldToBook(oraInizio, data, sport);
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
            } else if (!menu.getController().findFieldToBook(oraInizio, data, sport).contains(Integer.parseInt(campo))) {
                JOptionPane.showMessageDialog(
                    null, 
                    "Il campo che hai inserito non è disponibile, controlla meglio", 
                    "Campi errati", 
                    JOptionPane.WARNING_MESSAGE);
            } else {
                contentPrenotazione.removeAll();
                Prenotazione p = new Prenotazione(data, LocalDate.now().toString(), persona, menu.getController().findPeriod(Integer.parseInt(campo), giorno, oraInizio));
                if (menu.getController().bookField(p) == 0) {
                    contentPrenotazione.add(new JLabel("Qualcosa è andato storto, riprova"));
                } else {
                    contentPrenotazione.add(new JLabel("Prenotazione effettuata con successo!"));
                }
                contentPrenotazione.revalidate();
                contentPrenotazione.repaint();
            }
        });

        //aggiungo un ActionListener per il bottone per visualizzare le prenotazioni dell'utente
        userPrenotazioniButton.addActionListener(e -> {
            dataPrenField.setText("");
            campoPrenField.setText("");
            List<Prenotazione> output = menu.getController().allReservationsOfUser(persona);
            contentPrenotazione.removeAll();
            if (output.isEmpty()) {
                contentPrenotazione.add(new JLabel("Non hai prenotazioni in programma"));
            } else {
                contentPrenotazione.add(new JLabel("LE TUE PRENOTAZIONI:"));
                contentPrenotazione.add(Box.createVerticalStrut(10));
                for (int i = 0; i < output.size(); i++) {
                    contentPrenotazione.add(new JLabel("Data: " + output.get(i).dataPartita + ", " +
                                                        "Campo: " + output.get(i).fasciaOraria.numeroCampo + ", " +
                                                        "Orario d'inizio: " + output.get(i).fasciaOraria.orarioInizio + ", " +
                                                        "Sport: " + output.get(i).fasciaOraria.tipo + ", "));
                }
            }
            contentPrenotazione.revalidate();
            contentPrenotazione.repaint();
        });

        //aggiungo il pannello dei dati a quello principale
        prenotazione.add(datiPrenotazione, BorderLayout.WEST);

        return prenotazione;
    }
}
