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
import db_ass.view.trainer.LezioniCorsiPanel;
import db_ass.view.trainer.LezioniPrivatePanel;

public class TrainerPage {
    
    private Menu menu;
    private JFrame mainFrame;
    private Persona persona;
    LezioniPrivatePanel lezionePrivata = new LezioniPrivatePanel();
    LezioniCorsiPanel lezioneCorso = new LezioniCorsiPanel();
    private Runnable onClose;

    public TrainerPage(Menu menu, JFrame mainFrame, Persona persona, Runnable onClose) {
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

        JPanel trainerPanel = new JPanel();
        trainerPanel.setLayout(new BoxLayout(trainerPanel, BoxLayout.Y_AXIS));
        trainerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        //aggiungo al main
        main.addTab("Lezioni Private", lezionePrivata.setUp(persona, menu));
        main.addTab("Lezioni Corsi", lezioneCorso.setUp(persona, menu));
        

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
        trainerPanel.add(title);
        trainerPanel.add(Box.createVerticalStrut(20));
        trainerPanel.add(nomeLabel);
        trainerPanel.add(cognomeLabel);
        trainerPanel.add(Box.createVerticalStrut(5));
        trainerPanel.add(emailLabel);
        trainerPanel.add(Box.createVerticalStrut(5));
        trainerPanel.add(userCode);

        JPanel trainerWrapper = new JPanel(new BorderLayout());
        trainerWrapper.add(trainerPanel, BorderLayout.NORTH);


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
        mainFrame.getContentPane().add(trainerWrapper, BorderLayout.EAST);
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
