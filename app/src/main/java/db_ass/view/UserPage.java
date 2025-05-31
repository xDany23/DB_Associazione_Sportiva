package db_ass.view;

import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
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
        this.setUp();
    }

    public JFrame setUp() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JPanel backPanel = new JPanel();
        backPanel.setLayout(new BoxLayout(backPanel, BoxLayout.Y_AXIS));

        //bottone indietro
        JButton backButton = new JButton();
        backButton.setAlignmentX(JButton.LEFT_ALIGNMENT);
        backButton.setAlignmentY(JButton.TOP_ALIGNMENT);
        backPanel.add(backButton);


        //aggiungo tutto al mainFrame
        mainFrame.getContentPane().add(mainPanel, BorderLayout.CENTER);
        mainFrame.getContentPane().add(backPanel, BorderLayout.WEST);


        return mainFrame;
    }
}
