package db_ass.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import db_ass.data.Persona;
import db_ass.view.user.CorsoPanel;
import db_ass.view.user.IscrizionePanel;
import db_ass.view.user.PrenotazionePanel;
import db_ass.view.user.SquadraPanel;
import db_ass.view.user.TorneoPanel;

public class UserPage {
    
    private Menu menu;
    private JFrame mainFrame;
    private Persona persona;
    SquadraPanel squadra = new SquadraPanel();
    TorneoPanel torneo = new TorneoPanel();
    IscrizionePanel iscrizione = new IscrizionePanel();
    PrenotazionePanel prenotazione = new PrenotazionePanel();
    CorsoPanel corso = new CorsoPanel();
    private Runnable onClose;

    public UserPage(Menu menu, JFrame mainFrame, Persona persona, Runnable onClose) {
        this.menu = menu;
        this.mainFrame = mainFrame;
        this.persona = persona;
        this.onClose = onClose;
    }

    public JFrame setUp() {
        JTabbedPane main = new JTabbedPane();

        JPanel backPanel = new JPanel();
        backPanel.setLayout(new BoxLayout(backPanel, BoxLayout.Y_AXIS));

        Font labelFont = new Font("Arial", Font.PLAIN, 18);
        Font titleFont = new Font("Arial", Font.BOLD, 24);

        JPanel userPanel = new JPanel();
        userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.Y_AXIS));
        userPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        //aggiungo al main
        main.addTab("Squadre", squadra.setUp(persona, menu));
        main.addTab("Tornei", torneo.setUp(persona, menu));
        main.addTab("Iscrizioni", iscrizione.setUp(persona, menu));
        main.addTab("Prenotazioni", prenotazione.setUp(persona, menu));
        main.addTab("Corsi", corso.setUp(persona, menu));
        

        //campi dello userPanel
        JLabel title = new JLabel("Informazioni personali", SwingConstants.CENTER);
        title.setFont(titleFont);
        JLabel nomeLabel = new JLabel("Nome: " + persona.nome);
        JLabel cognomeLabel = new JLabel("Cognome: " + persona.cognome);
        JLabel emailLabel = new JLabel("Email: "+ persona.email);
        JLabel userCode = new JLabel("CF: " + persona.cf);
        nomeLabel.setFont(labelFont);
        cognomeLabel.setFont(labelFont);
        emailLabel.setFont(labelFont);
        userCode.setFont(labelFont);
        userPanel.add(title);
        userPanel.add(Box.createVerticalStrut(20));
        userPanel.add(nomeLabel);
        userPanel.add(cognomeLabel);
        userPanel.add(Box.createVerticalStrut(5));
        userPanel.add(emailLabel);
        userPanel.add(Box.createVerticalStrut(5));
        userPanel.add(userCode);

        JPanel userWrapper = new JPanel(new BorderLayout());
        userWrapper.add(userPanel, BorderLayout.NORTH);


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
        mainFrame.getContentPane().add(userWrapper, BorderLayout.EAST);
        mainFrame.setResizable(false);
        mainFrame.setVisible(true);
        mainFrame.addWindowListener(
            (WindowListener) new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    onClose.run();
                    System.exit(0);
                }
            }
        );

        return mainFrame;
    }

    public void back() {
        var cp = mainFrame.getContentPane();
        cp.removeAll();
        mainFrame = menu.setUp(onClose);
    }
}
