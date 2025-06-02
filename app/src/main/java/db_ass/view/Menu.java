package db_ass.view;

import java.util.Objects;
import java.util.Optional;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
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
import javax.swing.SwingConstants;

import db_ass.Controller;
import db_ass.view.admin.AdminLogin;

public final class Menu {

    private int count = 0;
    private Optional<Controller> controller;
    private JFrame mainFrame;
    private Login login;
    private Registration registration;
    private AdminLogin adminLogin;

    public Menu(Runnable onClose) {
        this.controller = Optional.empty();
        this.mainFrame = this.setUp(onClose);
        this.login = new Login(this, mainFrame, onClose);
        this.registration = new Registration(this, mainFrame, onClose);
        this.adminLogin = new AdminLogin(this, mainFrame, onClose);
    }

    public JFrame setUp(Runnable onClose) {
        if (count == 0) {
            this.mainFrame = new JFrame("PROGETTO");
            var padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);
            ((JComponent) mainFrame.getContentPane()).setBorder(padding);
            mainFrame.setLocationRelativeTo(null);  //centra la finestra nello schermo
            mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        }        

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
            loginFrame();
        });

        //bottone Register
        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(e -> {
            registrationFrame();
        });

        //bottone Login Administrator
        JButton loginAdministratorButton = new JButton("Login Administrator");
        loginAdministratorButton.addActionListener(e -> {
            adminLoginFrame();
        });

        //aggiungo i bottoni al buttonPanel
        buttonPanel.add(loginButton);
        //buttonPanel.add(Box.createHorizontalStrut(20)); //crea spazio orizzontale tra i due bottoni
        buttonPanel.add(registerButton);
        buttonPanel.add(loginAdministratorButton);

        //aggiungo il pannello buttonPanel al pannello principale
        mainPanel.add(Box.createVerticalStrut(50)); //crea spazio verticale dal titolo
        mainPanel.add(buttonPanel);

        JButton exitButton = new JButton("Exit");
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setPreferredSize(new Dimension(300, 20));
        exitButton.addActionListener(e -> {System.exit(0);});
        mainPanel.add(Box.createVerticalStrut(200));
        mainPanel.add(exitButton);

        //frame.pack();     //ridimensiona la finestra in base a cosa possiede all'interno
        mainFrame.getContentPane().add(mainPanel, BorderLayout.CENTER);
        mainFrame.setResizable(false);
        mainFrame.setVisible(true);
        mainFrame.addWindowListener(
            new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    onClose.run();
                    System.exit(0);
                }
            }
        );
        count++;
        return mainFrame;
    }


    public void loginFrame() {
        var cp = mainFrame.getContentPane();
        cp.removeAll();
        mainFrame = login.setUp();
    }

    public void registrationFrame() {
        var cp = mainFrame.getContentPane();
        cp.removeAll();
        mainFrame = registration.setUp();
    }

    public void adminLoginFrame() {
        var cp = mainFrame.getContentPane();
        cp.removeAll();
        mainFrame = adminLogin.setUp();
    }

    public void setController(Controller controller) {
        Objects.requireNonNull(controller, "Set null controller in view");
        this.controller = Optional.of(controller);
    }

    public Controller getController() {
        if (this.controller.isPresent()) {
            return this.controller.get();
        } else {
            throw new IllegalStateException(
                """
                The View's Controller is undefined, did you remember to call
                `setController` before starting the application?
                Remeber that `View` needs a reference to the controller in order
                to notify it of button clicks and other changes.  
                """
            );
        }
    }

}
