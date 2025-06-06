package db_ass.view.admin;

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
import db_ass.view.Menu;

public class AdminPage {
    
    private Menu menu;
    private JFrame mainFrame;
    private Persona persona;
    private JTabbedPane panel;
    private Runnable onClose;

    public AdminPage(Menu menu, JFrame mainFrame, Persona persona, Runnable onClose) {
        this.menu = menu;
        this.mainFrame = mainFrame;
        this.persona = persona;
        this.panel = new JTabbedPane(JTabbedPane.TOP);
        this.onClose = onClose;
    }

    public JFrame setUp() {
        BasePanel user = new UserPanel(this.menu, this.mainFrame);
        BasePanel trainer = new TrainerPanel(this.menu, this.mainFrame);
        BasePanel referee = new RefereePanel(this.menu, this.mainFrame);
        BasePanel disabled = new DisabledUsersPanel(this.menu, this.mainFrame);
        BasePanel courses = new CoursesPanel(this.menu);
        BasePanel tournaments = new TournamentPanel(this.menu);
        BasePanel fields = new FieldPanel(this.menu);
        this.panel.addTab("Utenti", user);
        this.panel.addTab("Allenatori", trainer);
        this.panel.addTab("Arbitri", referee);
        this.panel.addTab("Utenti disabilitati", disabled);
        this.panel.addTab("Corsi", courses);
        this.panel.addTab("Tornei", tournaments);
        this.panel.addTab("Campi", fields);

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
                    onClose.run();
                    System.exit(0);
                }
            }
        );
        this.panel.addChangeListener(e -> {
            JTabbedPane pane = (JTabbedPane)e.getSource();
            ((BasePanel)pane.getSelectedComponent()).update();
        });
        
        return mainFrame;
    }

    public void back() {
        var cp = mainFrame.getContentPane();
        cp.removeAll();
        mainFrame = menu.setUp(onClose);
    }

}
