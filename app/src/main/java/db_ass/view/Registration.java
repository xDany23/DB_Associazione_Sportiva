package db_ass.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.AbstractDocument;

import db_ass.data.Persona;

public class Registration {

    private Menu menu;
    private JFrame mainFrame;
    private Persona persona;
    private UserPage userPage;
    private Runnable onClose;

    public Registration(Menu menu, JFrame mainFrame, Runnable onClose) {
        this.menu = menu;
        this.mainFrame = mainFrame;
        this.onClose = onClose;
    }

    public JFrame setUp() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JPanel backPanel = new JPanel();
        backPanel.setLayout(new BoxLayout(backPanel, BoxLayout.Y_AXIS));

        JPanel informationPanel = new JPanel(new GridLayout(5, 2, 0, 0));
        informationPanel.setMaximumSize(new Dimension(500, 200));
        informationPanel.setAlignmentX(JPanel.CENTER_ALIGNMENT);

        JLabel titleLabel = new JLabel("Registration", SwingConstants.CENTER);
        titleLabel.setFont(titleLabel.getFont().deriveFont(36.0f));
        titleLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(40, 0, 20, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(Box.createVerticalStrut(200));

        JLabel nomeLabel = new JLabel("Nome:");
        JTextField nomeField = new JTextField(16);
        nomeField.setMinimumSize(new Dimension(150, 15));
        nomeLabel.setAlignmentX(JLabel.RIGHT);
        nomeField.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        informationPanel.add(nomeLabel);
        informationPanel.add(nomeField);

        JLabel cognomeLabel = new JLabel("Cognome:");
        JTextField cognomeField = new JTextField(16);
        cognomeField.setMinimumSize(new Dimension(150, 15));
        cognomeLabel.setAlignmentX(JLabel.RIGHT);
        cognomeField.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        informationPanel.add(cognomeLabel);
        informationPanel.add(cognomeField);

        JLabel cfLabel = new JLabel("CF:");
        JTextField cfField = new JTextField(16);
        cfField.setMinimumSize(new Dimension(150, 15));
        ((AbstractDocument) cfField.getDocument()).setDocumentFilter(new LimitDocumentFilter(16));
        cfLabel.setAlignmentX(JLabel.RIGHT);
        cfField.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        informationPanel.add(cfLabel);
        informationPanel.add(cfField);

        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField(16);
        emailField.setMinimumSize(new Dimension(150, 15));
        emailLabel.setAlignmentX(JLabel.RIGHT);
        emailField.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        informationPanel.add(emailLabel);
        informationPanel.add(emailField);

        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField(16);
        passField.setMinimumSize(new Dimension(150, 15));
        passLabel.setAlignmentX(JLabel.RIGHT);
        passField.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        informationPanel.add(passLabel);
        informationPanel.add(passField);

        mainPanel.add(Box.createVerticalStrut(25));

        mainPanel.add(informationPanel);

        mainPanel.add(Box.createVerticalStrut(150));

        //bottone conferma login
        JButton confirmButton = new JButton("Register");
        confirmButton.setMinimumSize(new Dimension(200, 50));
        confirmButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
        mainPanel.add(confirmButton);
        confirmButton.addActionListener(e -> {
            String nome = nomeField.getText();
            String cognome = cognomeField.getText();
            String cf = cfField.getText();
            String email = emailField.getText();
            char[] passwordChars = passField.getPassword();
            String pass = new String(passwordChars);
            if (nome.isEmpty() || cognome.isEmpty() || cf.isEmpty() || email.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(
                    null, 
                    "Compila tutti i campi prima della registrazione", 
                    "Campi mancanti", 
                    JOptionPane.WARNING_MESSAGE);
            } else if (cf.length() != 16)  {
                JOptionPane.showMessageDialog(
                    null, 
                    "CF non valido", 
                    "Errore", 
                    JOptionPane.WARNING_MESSAGE);
            } else if (this.menu.getController().findPersona(cf) != null) {
                JOptionPane.showMessageDialog(
                    null, 
                    "Questo CF è già registrato nel DB", 
                    "Errore", 
                    JOptionPane.WARNING_MESSAGE);
            } else {
                menu.getController().addUser(new Persona(cf, nome, cognome, email, pass, true, false, false, 0));
                nomeField.setText("");
                cognomeField.setText("");
                cfField.setText("");
                emailField.setText("");
                passField.setText("");
                persona = this.menu.getController().findPersona(cf);
                this.goUserPage(persona);
            }
        });
        
        //bottone per tornare indietro
        JButton backButton = new JButton("Indietro");
        backButton.setAlignmentX(JButton.LEFT_ALIGNMENT);
        backButton.setAlignmentY(JButton.TOP_ALIGNMENT);
        backPanel.add(backButton);
        backButton.addActionListener(e -> {this.back();});

        mainFrame.getContentPane().add(mainPanel, BorderLayout.CENTER);
        mainFrame.getContentPane().add(backPanel, BorderLayout.WEST);
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

        return this.mainFrame;
    }

    public void back() {
        var cp = mainFrame.getContentPane();
        cp.removeAll();
        mainFrame = menu.setUp(onClose);
    }

    public void goUserPage(Persona persona) {
        this.userPage = new UserPage(menu, mainFrame, persona, onClose);
        var cp = mainFrame.getContentPane();
        cp.removeAll();
        userPage.setUp(); 
    }
    
}
