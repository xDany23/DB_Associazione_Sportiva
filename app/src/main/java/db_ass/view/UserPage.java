package db_ass.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import db_ass.data.Persona;

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
        String[] sport = {"Calcetto", "Padel", "Tennis singolo", "Tennis doppio"};

        //pagine del main
        JPanel squadre = new JPanel();
        JLabel squadreTitolo = new JLabel("Le mie squadre", SwingConstants.CENTER);
        squadreTitolo.setFont(new Font("Arial", Font.BOLD, 20));
        squadreTitolo.setAlignmentX(Component.CENTER_ALIGNMENT);
        squadreTitolo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        squadre.add(squadreTitolo);
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        for (int i = 0; i < 30; i++) {
            contentPanel.add(new JLabel("Riga #" + i));
        }
        JScrollPane scrollSquadre = new JScrollPane(contentPanel);
        squadre.add(scrollSquadre);
        
        JPanel tornei = new JPanel();
        JLabel torneiTitolo = new JLabel("I miei tornei", SwingConstants.CENTER);
        torneiTitolo.setFont(new Font("Arial", Font.BOLD, 20));
        torneiTitolo.setAlignmentX(Component.CENTER_ALIGNMENT);
        torneiTitolo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        tornei.add(squadreTitolo);
        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        for (int i = 0; i < 30; i++) {
            contentPanel.add(new JLabel("Riga #" + i));
        }
        JScrollPane scrollTornei = new JScrollPane(contentPanel);
        squadre.add(scrollTornei);

        JPanel iscrizione = new JPanel();
        iscrizione.setLayout(new BoxLayout(iscrizione, BoxLayout.Y_AXIS));
        JLabel iscrizioneTitolo = new JLabel("Iscrizioni", SwingConstants.CENTER);
        iscrizioneTitolo.setFont(new Font("Arial", Font.BOLD, 20));
        iscrizioneTitolo.setAlignmentX(Component.CENTER_ALIGNMENT);
        iscrizioneTitolo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        iscrizione.add(iscrizioneTitolo);
        contentPanel = new JPanel(new GridLayout());
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        for (int i = 0; i < 30; i++) {
            contentPanel.add(new JLabel("Riga #" + i));
        }
        JScrollPane scrollLezioni = new JScrollPane(contentPanel);
        iscrizione.add(scrollLezioni, BorderLayout.CENTER);
        JPanel datiIscrizione = new JPanel();
        datiIscrizione.setLayout(new GridLayout(6,2,2,0));
        JLabel numCampoLabel = new JLabel("Numero campo: ");
        JTextField numCampoField = new JTextField(16);
        JLabel giornoLabel = new JLabel("Giorno: ");
        JComboBox<String> giornoBox = new JComboBox<>(giorniLezioniPrivate);
        JLabel orarioInizioLabel = new JLabel("Orario inizio: ");
        JComboBox<String> orarioInizioBox = new JComboBox<>(orari);
        JLabel dataLabel = new JLabel("Data: ");
        JTextField dataField = new JTextField(16);
        JLabel sportLabel = new JLabel("Sport: ");
        JComboBox<String> sportBox = new JComboBox<>(sport);
        JButton buttonIscrizione = new JButton("Iscrivimi");
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
