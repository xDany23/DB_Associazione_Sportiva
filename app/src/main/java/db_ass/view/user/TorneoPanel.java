package db_ass.view.user;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
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

import db_ass.data.Persona;
import db_ass.data.RisultatiTorneo;
import db_ass.data.TipoSquadra;
import db_ass.data.Torneo;
import db_ass.view.Menu;

public final class TorneoPanel {

    public JPanel setUp(Persona persona, Menu menu) {

        String[] sports = {"Calcetto", "Padel", "Tennis singolo", "Tennis doppio"};

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
        datiTorneo.setLayout(new GridLayout(6, 2, 2, 0));
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
        JButton mieiTornei = new JButton("I miei torneo");
        JButton vuoto = new JButton();
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
        datiTorneo.add(mieiTornei);
        datiTorneo.add(vuoto);

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
            } else if (menu.getController().findTeam(Integer.parseInt(codiceSquadra)).tipo.equals(TipoSquadra.TENNIS_SINGOLO) &&
                       !menu.getController().findTeam(Integer.parseInt(codiceSquadra)).componente1.equals(persona)) {
                JOptionPane.showMessageDialog(
                    null, 
                    "Non fai parte della squadra inserita! Riprovare", 
                    "Campi errati", 
                    JOptionPane.WARNING_MESSAGE);
            } else if ((menu.getController().findTeam(Integer.parseInt(codiceSquadra)).tipo.equals(TipoSquadra.TENNIS_DOPPIO) ||
                        menu.getController().findTeam(Integer.parseInt(codiceSquadra)).tipo.equals(TipoSquadra.PADEL)) &&
                        (!menu.getController().findTeam(Integer.parseInt(codiceSquadra)).componente1.equals(persona) ||
                        !menu.getController().findTeam(Integer.parseInt(codiceSquadra)).componente2.equals(persona))) {
                JOptionPane.showMessageDialog(
                    null, 
                    "Non fai parte della squadra inserita! Riprovare", 
                    "Campi errati", 
                    JOptionPane.WARNING_MESSAGE);
            } else if (menu.getController().findTeam(Integer.parseInt(codiceSquadra)).tipo.equals(TipoSquadra.CALCETTO) &&
                        (!menu.getController().findTeam(Integer.parseInt(codiceSquadra)).componente1.equals(persona) ||
                        !menu.getController().findTeam(Integer.parseInt(codiceSquadra)).componente2.equals(persona) ||
                        !menu.getController().findTeam(Integer.parseInt(codiceSquadra)).componente3.equals(persona) ||
                        !menu.getController().findTeam(Integer.parseInt(codiceSquadra)).componente4.equals(persona) ||
                        !menu.getController().findTeam(Integer.parseInt(codiceSquadra)).componente5.equals(persona))) {
            } else if (menu.getController().findTournament(Integer.parseInt(codiceTorneo)) == null) {
                JOptionPane.showMessageDialog(
                    null, 
                    "Il torneo inserito non esiste! Riprovare", 
                    "Campi errati", 
                    JOptionPane.WARNING_MESSAGE);
            } else if (!menu.getController().findTournament(Integer.parseInt(codiceTorneo)).tipo.equals(menu.getController().findTeam(Integer.parseInt(codiceSquadra)).tipo)) {
                JOptionPane.showMessageDialog(
                    null, 
                    "La squadra che hai inserito non è dello stesso tipo del torneo!", 
                    "Campi errati", 
                    JOptionPane.WARNING_MESSAGE);
            } else if (!menu.getController().tournamentsEnterable(menu.getController().findTeam(Integer.parseInt(codiceSquadra)).tipo).contains(menu.getController().findTournament(Integer.parseInt(codiceTorneo)))) { //controllo se il torneo è joinable oppure no
                JOptionPane.showMessageDialog(
                    null, 
                    "Il torneo che hai inserito purtroppo non accetta piú iscrizioni", 
                    "Torneo non disponibile", 
                    JOptionPane.WARNING_MESSAGE);
            } else {
                contentTornei.removeAll();
                if (menu.getController().enterTournament(Integer.parseInt(codiceTorneo), Integer.parseInt(codiceSquadra)) == 0) {
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
            } else if (menu.getController().findTournament(Integer.parseInt(codiceTorneo)) == null) {
                JOptionPane.showMessageDialog(
                    null, 
                    "Il torneo selezionato non esiste", 
                    "Campi errati", 
                    JOptionPane.WARNING_MESSAGE);
            } else {
                contentTornei.removeAll();
                output = menu.getController().findAllPartecipants(Integer.parseInt(codiceTorneo));
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
            } else if (menu.getController().findTournament(Integer.parseInt(codiceTorneo)) == null) {
                JOptionPane.showMessageDialog(
                    null, 
                    "Il torneo selezionato non esiste", 
                    "Campi errati", 
                    JOptionPane.WARNING_MESSAGE);
            } else {
                contentTornei.removeAll();
                output = menu.getController().visualizeAllTournamentMatches(Integer.parseInt(codiceTorneo));
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
            output = menu.getController().tournamentsEnterable(tipo);
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

        //aggiungo un ActionListener per il botton per visualizzare tutti i tornei dell'utente
        mieiTornei.addActionListener(e -> {
            List<Torneo> output = new ArrayList<>();
            codiceSquadraTorneoField.setText("");
            codiceTorneoField.setText("");
            output = menu.getController().allUserTournaments(persona);
            contentTornei.removeAll();
            if (output.isEmpty()) {
                contentTornei.add(new JLabel("Non sei iscritto a nessun torneo"));
            } else {
                contentTornei.add(new JLabel("I MIEI TORNEI"));
                contentTornei.add(Box.createVerticalStrut(10));
                for(int i = 0; i < output.size(); i++) {
                    contentTornei.add(new JLabel("- Codice torneo: " + output.get(i).codiceTorneo + ", " +
                                                "Nome: " + output.get(i).nome + ", " + 
                                                "Data di svolgimento: " + output.get(i).dataSvolgimento + ", " +
                                                "Sport: " + output.get(i).tipo + ", " +
                                                "Premio: " + output.get(i).premio));
                }
            }
            contentTornei.revalidate();
            contentTornei.repaint();
        });

        //aggiungo il pannello dei dati dentro a quello principale dei tornei
        tornei.add(datiTorneo, BorderLayout.WEST);

        return tornei;
    }
}
