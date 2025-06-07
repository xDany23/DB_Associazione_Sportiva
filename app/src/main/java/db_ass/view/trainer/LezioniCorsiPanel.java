package db_ass.view.trainer;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import db_ass.data.LezioneCorso;
import db_ass.data.Persona;
import db_ass.view.Menu;

public final class LezioniCorsiPanel {

    public JPanel setUp(Persona persona, Menu menu) {

        JPanel lezioneCorso = new JPanel();
        lezioneCorso.setLayout(new BoxLayout(lezioneCorso, BoxLayout.Y_AXIS));

        //titolo
        JLabel corsoTitolo = new JLabel("Lezioni Corsi", SwingConstants.CENTER);
        corsoTitolo.setFont(new Font("Arial", Font.BOLD, 30));
        corsoTitolo.setAlignmentX(Component.CENTER_ALIGNMENT);
        corsoTitolo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        lezioneCorso.add(corsoTitolo);

        //pannello principale
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        JScrollPane scrollCorsi = new JScrollPane(content);
        lezioneCorso.add(scrollCorsi, BorderLayout.CENTER);

        //dati da compilare
        JPanel dati = new JPanel();
        dati.setLayout(new GridLayout(2,2,2,0));
        JLabel dataLezioneLabel = new JLabel("Data lezione: ");
        JTextField dataLezioneField = new JTextField(16);
        JButton tutteLezioniButton = new JButton("Tutte le lezioni");
        JButton lezioniDataButton = new JButton("Lezioni data specifica");
        dati.add(dataLezioneLabel);
        dati.add(dataLezioneField);
        dati.add(tutteLezioniButton);
        dati.add(lezioniDataButton);

        //Aggiungo un ActionListener al bottone per visualizzare tutte le lezioni in programma
        tutteLezioniButton.addActionListener(e -> {
            dataLezioneField.setText("");
            List<LezioneCorso> output = new ArrayList<>();
            output = menu.getController().allLessonCourseOfTrainer(persona);
            content.removeAll();
            if(output.isEmpty()) {
                content.add(new JLabel("Non hai nessuna lezione in programma"));
            } else {
                content.add(new JLabel("LE TUE LEZIONI:"));
                content.add(Box.createVerticalStrut(20));
                for (int i = 0; i < output.size(); i++) {
                    content.add(new JLabel("- DATA: " + output.get(i).dataSvolgimento + ", " +
                                            "ORARIO: " + output.get(i).orarioInizio + ", " +
                                            "CAMPO: " + output.get(i).numeroCampo.numeroCampo + ", " +
                                            "SPORT: " + output.get(i).sportPraticato + ", " +
                                            "CORSO DI APPARTENENZA: " + output.get(i).codiceCorso.codiceCorso));
                }
            }
            content.revalidate();
            content.repaint();
        });

        //aggiungo un ActionListener al bottone per visualizzare le lezioni in una certa data
        lezioniDataButton.addActionListener(e -> {
            String data = dataLezioneField.getText();
            if(data.isEmpty()) {
                JOptionPane.showMessageDialog(
                    null, 
                    "Data mancante", 
                    "Campi mancanti", 
                    JOptionPane.WARNING_MESSAGE);
            } else if (this.check(data) == false) {
                JOptionPane.showMessageDialog(null, "Inserire un formato corretto per la data (anno-mese-giorno)");
                dataLezioneField.setText("");
                return;
            } else {
                List<LezioneCorso> output = new ArrayList<>();
                output = menu.getController().allLessonCourseInCertainDay(persona, data);
                content.removeAll();
                if(output.isEmpty()) {
                    content.add(new JLabel("Non hai nessuna lezione in programma questo giorno"));
                } else {
                    content.add(new JLabel("LE TUE LEZIONI IL " + data + ":"));
                    content.add(Box.createVerticalStrut(20));
                    for (int i = 0; i < output.size(); i++) {
                        content.add(new JLabel("- DATA: " + output.get(i).dataSvolgimento + ", " +
                                                "ORARIO: " + output.get(i).orarioInizio + ", " +
                                                "CAMPO: " + output.get(i).numeroCampo.numeroCampo + ", " +
                                                "SPORT: " + output.get(i).sportPraticato + ", " +
                                                "CORSO DI APPARTENENZA: " + output.get(i).codiceCorso.codiceCorso));
                    }
                }
                content.revalidate();
                content.repaint();
            }
        });

        //aggiungo al pannello corsi
        lezioneCorso.add(dati, BorderLayout.WEST);
        return lezioneCorso;
    }

    private boolean check(String data) {
        try {
            LocalDate.parse(data);
        } catch (DateTimeParseException f) {
            return false;
        }
        return true;
    }
}
