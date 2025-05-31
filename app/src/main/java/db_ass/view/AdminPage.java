package db_ass.view;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import db_ass.data.Persona;

public class AdminPage {
    
    private Menu menu;
    private JFrame mainFrame;
    private Persona persona;
    private JTabbedPane panel;

    public AdminPage(Menu menu, JFrame mainFrame, Persona persona) {
        this.menu = menu;
        this.mainFrame = mainFrame;
        this.persona = persona;
        this.panel = new JTabbedPane(JTabbedPane.TOP);
    }

    public JFrame setUp() {
        JPanel userPanel = new JPanel();
        userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.Y_AXIS));

        this.panel.addTab("Utenti", userPanel);

        JPanel trainerPanel = new JPanel();
        trainerPanel.setLayout(new BoxLayout(trainerPanel, BoxLayout.Y_AXIS));

        this.panel.addTab("Allenatori", trainerPanel);

        JPanel refereePanel = new JPanel();
        refereePanel.setLayout(new BoxLayout(refereePanel, BoxLayout.Y_AXIS));
        
        this.panel.addTab("Arbitri", refereePanel);
        
        JPanel coursePanel = new JPanel();
        coursePanel.setLayout(new BoxLayout(coursePanel, BoxLayout.Y_AXIS));

        this.panel.addTab("Corsi", coursePanel);
        
        JPanel tournamentPanel = new JPanel();
        tournamentPanel.setLayout(new BoxLayout(tournamentPanel, BoxLayout.Y_AXIS));

        this.panel.addTab("Tornei", tournamentPanel);

        JPanel fieldPanel = new JPanel();
        fieldPanel.setLayout(new BoxLayout(fieldPanel, BoxLayout.Y_AXIS));

        this.panel.addTab("Campi", fieldPanel);

        JPanel backPanel = new JPanel();
        backPanel.setLayout(new BoxLayout(backPanel, BoxLayout.Y_AXIS));

        //bottone indietro
        JButton backButton = new JButton("Back");
        backButton.setAlignmentX(JButton.LEFT_ALIGNMENT);
        backButton.setAlignmentY(JButton.TOP_ALIGNMENT);
        backButton.addActionListener(e -> {this.back();});
        backPanel.add(backButton);


        //aggiungo tutto al mainFrame
        /* mainFrame.getContentPane().add(mainPanel, BorderLayout.CENTER);*/
        mainFrame.getContentPane().add(new JLabel("Benvenuto " + persona.nome), BorderLayout.NORTH);
        mainFrame.getContentPane().add(backPanel, BorderLayout.WEST);
        mainFrame.getContentPane().add(this.panel, BorderLayout.CENTER);
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
