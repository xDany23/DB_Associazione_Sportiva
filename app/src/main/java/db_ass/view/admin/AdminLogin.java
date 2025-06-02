package db_ass.view.admin;

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
import db_ass.view.LimitDocumentFilter;
import db_ass.view.Menu;

public class AdminLogin {

    private Menu menu;
    private JFrame mainFrame;
    private Persona persona;
    private Runnable onClose;

    public AdminLogin(Menu main, JFrame mainFrame, Runnable onClose) {
        this.menu = main;
        this.mainFrame = mainFrame;
        this.onClose = onClose;
    }

    public JFrame setUp(/* Runnable onClose */) {
        //pannello principale
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        //pannello per back
        JPanel backPanel = new JPanel();
        backPanel.setLayout(new BoxLayout(backPanel, BoxLayout.Y_AXIS));

        JPanel informationPanel = new JPanel(new GridLayout(2, 2, 0, 0));
        informationPanel.setMaximumSize(new Dimension(500, 50));
        informationPanel.setAlignmentX(JPanel.CENTER_ALIGNMENT);

        //titolo
        JLabel titleLabel = new JLabel("Login as an Admin", SwingConstants.CENTER);
        titleLabel.setFont(titleLabel.getFont().deriveFont(36.0f));
        titleLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(40, 0, 20, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(Box.createVerticalStrut(200));

        //campo CF
        JLabel cfLabel = new JLabel("CF:");
        JTextField cfField = new JTextField(16);
        cfField.setMinimumSize(new Dimension(150, 15));
        ((AbstractDocument) cfField.getDocument()).setDocumentFilter(new LimitDocumentFilter(16));
        cfLabel.setAlignmentX(JLabel.RIGHT);
        cfField.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        informationPanel.add(cfLabel);
        informationPanel.add(cfField);

        //mainPanel.add(Box.createVerticalStrut(15));

        //campo password
        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField(20);
        passField.setMaximumSize(new Dimension(300, 30));
        informationPanel.add(passLabel);
        informationPanel.add(passField);

        mainPanel.add(Box.createVerticalStrut(25));

        mainPanel.add(informationPanel);

        mainPanel.add(Box.createVerticalStrut(170));

        //bottone conferma login
        JButton confirmButton = new JButton("Accedi");
        confirmButton.setMinimumSize(new Dimension(200, 50));
        confirmButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
        mainPanel.add(confirmButton);
        confirmButton.addActionListener(e -> {
            String cf = cfField.getText();
            char[] passChars = passField.getPassword();
            String pass = new String(passChars);
            if (cf.isEmpty() && pass.isEmpty()) {
                JOptionPane.showMessageDialog(
                    null, 
                    "Compila tutti i campi prima dell'accesso", 
                    "Campi mancanti", 
                    JOptionPane.WARNING_MESSAGE);
            } else if (cf.isEmpty()) {
                JOptionPane.showMessageDialog(
                    null, 
                    "Campo CF mancante", 
                    "Campi mancanti", 
                    JOptionPane.WARNING_MESSAGE);
            } else if (pass.isEmpty()) {
                JOptionPane.showMessageDialog(
                    null, 
                    "Campo Password mancante", 
                    "Campi mancanti", 
                    JOptionPane.WARNING_MESSAGE);
            } else {
                persona = menu.getController().findPersona(cf);
                if (persona != null && persona.password.equals(pass) && persona.admin) {
                    this.goAdminPage();
                } else {
                    JOptionPane.showMessageDialog(
                    null, 
                    "La persona inserita non esiste", 
                    "Persona mancante", 
                    JOptionPane.WARNING_MESSAGE);
                    cfField.setText("");
                    passField.setText("");
                }
            }
        });

        //bottone per tornare indietro
        JButton backButton = new JButton("Indietro");
        backButton.setAlignmentX(JButton.LEFT_ALIGNMENT);
        backButton.setAlignmentY(JButton.TOP_ALIGNMENT);
        backPanel.add(backButton);
        backButton.addActionListener(e -> {this.back();});
        

        //frame.pack();     //ridimensiona la finestra in base a cosa possiede all'interno
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
        return mainFrame;
    }

    /* public void loadingProduct() {
        freshPane(cp -> cp.add(new JLabel("Loading product...", SwingConstants.CENTER)));
    } */

    /* private void freshPane(Consumer<Container> consumer) {
        var cp = this.mainFrame.getContentPane();
        cp.removeAll();
        consumer.accept(cp);
        cp.validate();
        cp.repaint();
        this.mainFrame.pack();
    } */

    public void back() {
        var cp = mainFrame.getContentPane();
        cp.removeAll();
        mainFrame = menu.setUp(onClose);
    }

    public void goAdminPage() {
        var cp = mainFrame.getContentPane();
        cp.removeAll();
        mainFrame = new AdminPage(menu, mainFrame, persona, onClose).setUp();
    }
}
