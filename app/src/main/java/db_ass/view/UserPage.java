package db_ass.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

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
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JPanel backPanel = new JPanel();
        backPanel.setLayout(new BoxLayout(backPanel, BoxLayout.Y_AXIS));

        JPanel userPanel = new JPanel(new GridLayout(4,1,10,0));
        userPanel.setMaximumSize(new Dimension(500, 30));
        userPanel.setAlignmentX(JPanel.CENTER_ALIGNMENT);

        //campi dello userPanel
        JLabel nomeLabel = new JLabel("Nome: " + persona.nome);
        JLabel cognomeLabel = new JLabel("Cognome: " + persona.cognome);
        JLabel emailLabel = new JLabel("Email: "+ persona.email);
        JLabel cfLabel = new JLabel("CF: " + persona.cf);
        nomeLabel.setAlignmentX(JLabel.RIGHT);
        cognomeLabel.setAlignmentX(JLabel.RIGHT);
        emailLabel.setAlignmentX(JLabel.RIGHT);
        cfLabel.setAlignmentX(JLabel.RIGHT);
        userPanel.add(nomeLabel);
        userPanel.add(cognomeLabel);
        userPanel.add(emailLabel);
        userPanel.add(cfLabel);



        //bottone indietro
        JButton backButton = new JButton("Indietro");
        backButton.setAlignmentX(JButton.LEFT_ALIGNMENT);
        backButton.setAlignmentY(JButton.TOP_ALIGNMENT);
        backPanel.add(backButton);
        backButton.addActionListener(e -> {
            this.back();
        });


        //aggiungo tutto al mainFrame
        mainFrame.getContentPane().add(mainPanel, BorderLayout.CENTER);
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
