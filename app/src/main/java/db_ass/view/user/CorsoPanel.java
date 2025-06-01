package db_ass.view.user;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import db_ass.data.Corso;
import db_ass.data.Persona;
import db_ass.view.Menu;

public final class CorsoPanel {

    public JPanel setUp(Persona persona, Menu menu) {

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
                output = menu.getController().findActiveCourse(Integer.parseInt(codice));
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
                if (menu.getController().joinCourse(persona, Integer.parseInt(codice)) == 1) {
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
            List<Corso> output = menu.getController().findMostActiveCourses();
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
            List<Corso> output = menu.getController().allCoursesOfUser(persona);
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

        return corso;
    }
}
