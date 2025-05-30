package db_ass.view;

import java.util.Optional;
import java.util.function.Consumer;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import db_ass.Controller;
import db_ass.view.Login;

public final class Menu {

    private Optional<Controller> controller;
    private JFrame mainFrame = this.setUp();
    private Login login = new Login(this, mainFrame);

    public Menu(/* Runnable onClose */) {
        this.controller = Optional.empty();
        this.setUp();
    }

    private JFrame setUp(/* Runnable onClose */) {
        var frame = new JFrame("PROGETTO");
        var padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        ((JComponent) frame.getContentPane()).setBorder(padding);
        //frame.setPreferredSize(new Dimension(1000, 500));
        frame.setLocationRelativeTo(null);  //centra la finestra nello schermo
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        //pannello principale
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        //titolo
        JLabel titleLabel = new JLabel("Associazione Sportiva", SwingConstants.CENTER);
        titleLabel.setFont(titleLabel.getFont().deriveFont(36.0f));
        titleLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(40, 0, 20, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(Box.createVerticalStrut(200));

        //creo un pannello orizzontale per affiancare i bottoni
        JPanel buttonPanel = new JPanel(new GridLayout(1,3,20,0)); //crea un pannello con 1 riga, 2 colonne
        buttonPanel.setMaximumSize(new Dimension(1000, 50));
        buttonPanel.setAlignmentX(JPanel.CENTER_ALIGNMENT);

        //bottone Login
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> {
            loginFrame(this);
        });

        //bottone Register
        JButton registerButton = new JButton("Register");

        //bottone Login Administrator
        JButton loginAdministratorButton = new JButton("Login Administrator");

        //aggiungo i bottoni al buttonPanel
        buttonPanel.add(loginButton);
        //buttonPanel.add(Box.createHorizontalStrut(20)); //crea spazio orizzontale tra i due bottoni
        buttonPanel.add(registerButton);
        buttonPanel.add(loginAdministratorButton);

        //aggiungo il pannello buttonPanel al pannello principale
        mainPanel.add(Box.createVerticalStrut(50)); //crea spazio verticale dal titolo
        mainPanel.add(buttonPanel);

        //frame.pack();     //ridimensiona la finestra in base a cosa possiede all'interno
        frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.addWindowListener(
            new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    //onClose.run();
                    System.exit(0);
                }
            }
        );
        return frame;
    }

    public JFrame restoreMain() {
        //pannello principale
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        //titolo
        JLabel titleLabel = new JLabel("Associazione Sportiva", SwingConstants.CENTER);
        titleLabel.setFont(titleLabel.getFont().deriveFont(36.0f));
        titleLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(40, 0, 20, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(Box.createVerticalStrut(200));

        //creo un pannello orizzontale per affiancare i bottoni
        JPanel buttonPanel = new JPanel(new GridLayout(1,3,20,0)); //crea un pannello con 1 riga, 2 colonne
        buttonPanel.setMaximumSize(new Dimension(1000, 50));
        buttonPanel.setAlignmentX(JPanel.CENTER_ALIGNMENT);

        //bottone Login
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> {
            loginFrame(this);
        });

        //bottone Register
        JButton registerButton = new JButton("Register");

        //bottone Login Administrator
        JButton loginAdministratorButton = new JButton("Login Administrator");

        //aggiungo i bottoni al buttonPanel
        buttonPanel.add(loginButton);
        //buttonPanel.add(Box.createHorizontalStrut(20)); //crea spazio orizzontale tra i due bottoni
        buttonPanel.add(registerButton);
        buttonPanel.add(loginAdministratorButton);

        //aggiungo il pannello buttonPanel al pannello principale
        mainPanel.add(Box.createVerticalStrut(50)); //crea spazio verticale dal titolo
        mainPanel.add(buttonPanel);

        //frame.pack();     //ridimensiona la finestra in base a cosa possiede all'interno
        mainFrame.getContentPane().add(mainPanel, BorderLayout.CENTER);
        mainFrame.setResizable(false);
        mainFrame.setVisible(true);
        mainFrame.addWindowListener(
            new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    //onClose.run();
                    System.exit(0);
                }
            }
        );
        return mainFrame;
    }

    public void loginFrame(Menu main) {
        var cp = mainFrame.getContentPane();
        cp.removeAll();
        mainFrame = login.setUp();
    }

}
