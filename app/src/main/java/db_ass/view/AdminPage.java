package db_ass.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import db_ass.data.Corso;
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
        this.panel.addTab("Utenti", userSetUp());
        this.panel.addTab("Allenatori", trainerSetUp());
        this.panel.addTab("Arbitri", refereeSetUp());
        this.panel.addTab("Corsi", courseSetUp());
        
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

    private JPanel userSetUp() {
        JPanel userPanel = new JPanel();
        userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.Y_AXIS));
        JLabel title = new JLabel("Tutti gli utenti", SwingConstants.CENTER);
        List<Persona> persone = menu.getController().getAllUsers();
        JComboBox<String> selectableUsers = new JComboBox<>();
        fillComboBox(selectableUsers, persone.stream().map(p -> p.cf).toList());
        JPanel selectionPanel = new JPanel(new GridLayout());
        selectionPanel.add(selectableUsers);
        userPanel.add(title);
        userPanel.add(createTablePanel(List.of("nome","cognome","codice Fiscale","e-mail"),
                                        persone.stream().map(p -> p.nome).toList(),
                                        persone.stream().map(p -> p.cognome).toList(),
                                        persone.stream().map(p -> p.cf).toList(),
                                        persone.stream().map(p -> p.email).toList()));
        userPanel.add(selectionPanel);
        return userPanel;
    }

    private JPanel trainerSetUp() {
        JPanel trainerPanel = new JPanel();
        trainerPanel.setLayout(new BoxLayout(trainerPanel, BoxLayout.Y_AXIS));
        JLabel title = new JLabel("Tutti gli allenatori", SwingConstants.CENTER);
        List<Persona> persone = menu.getController().getAllTrainers();
        JComboBox<String> selectableUsers = new JComboBox<>();
        fillComboBox(selectableUsers, persone.stream().map(p -> p.cf).toList());
        JPanel selectionPanel = new JPanel(new GridLayout());
        selectionPanel.add(selectableUsers);
        trainerPanel.add(title);
        trainerPanel.add(createTablePanel(List.of("nome","cognome","codice Fiscale","e-mail"),
                                        persone.stream().map(p -> p.nome).toList(),
                                        persone.stream().map(p -> p.cognome).toList(),
                                        persone.stream().map(p -> p.cf).toList(),
                                        persone.stream().map(p -> p.email).toList()));
        trainerPanel.add(selectionPanel);
        return trainerPanel;
    }

    private JPanel refereeSetUp() {
        JPanel refereePanel = new JPanel();
        refereePanel.setLayout(new BoxLayout(refereePanel, BoxLayout.Y_AXIS));
        JLabel title = new JLabel("Tutti gli arbitri", SwingConstants.CENTER);
        List<Persona> persone = menu.getController().getAllReferees();
        JComboBox<String> selectableUsers = new JComboBox<>();
        fillComboBox(selectableUsers, persone.stream().map(p -> p.cf).toList());
        JPanel selectionPanel = new JPanel(new GridLayout());
        selectionPanel.add(selectableUsers);
        refereePanel.add(title);
        refereePanel.add(createTablePanel(List.of("nome","cognome","codice Fiscale","e-mail"),
                                        persone.stream().map(p -> p.nome).toList(),
                                        persone.stream().map(p -> p.cognome).toList(),
                                        persone.stream().map(p -> p.cf).toList(),
                                        persone.stream().map(p -> p.email).toList()));
        refereePanel.add(selectionPanel);
        return refereePanel;
    }

    private JPanel courseSetUp() {
        JPanel coursePanel = new JPanel();
        coursePanel.setLayout(new BoxLayout(coursePanel, BoxLayout.Y_AXIS));
        JLabel title = new JLabel("Tutti i corsi attivi",SwingConstants.CENTER);
        List<Corso> corsi = menu.getController().getAllActiveCourses();
        JComboBox<Integer> selectables = new JComboBox<>();
        fillComboBox(selectables, corsi.stream().map(c -> c.codiceCorso).toList());
        JPanel selectionPanel = new JPanel(new GridLayout());
        selectionPanel.add(selectables);
        coursePanel.add(title);
        coursePanel.add(createTablePanel(List.of("Codice Corso", "Prezzo", "Allenatore", "Sport Praticato", "DataInizio", "Data Fine"),
                                         corsi.stream().map(c -> c.codiceCorso).toList(),
                                         corsi.stream().map(c -> c.prezzo).toList(),
                                         corsi.stream().map(c -> c.allenatore).toList(),
                                         corsi.stream().map(c -> c.sportPraticato).toList(),
                                         corsi.stream().map(c -> c.dataInizio).toList(),
                                         corsi.stream().map(c -> c.dataFine).toList()));
        coursePanel.add(selectionPanel);
        return coursePanel;
    }

    private <E> void fillComboBox(JComboBox<E> box, List<E> elements) {
        for(var elem: elements) {
            box.addItem(elem);
        }
    }

    private JScrollPane createTablePanel(List<String> columnNames, List<?>... elements) {
        Object[][] rowsData = new Object[elements[0].size()][elements.length];
        int j = 0;
        for(var element: elements) {
            int i = 0;
            for (var elem : element) {
                rowsData[i][j] = elem;
                i++;
            }
            j++;
        }
        JTable table = new JTable(rowsData, columnNames.toArray());
        table.setModel(new CustomTableModel(rowsData, columnNames.toArray()));
        JScrollPane tablePanel = new JScrollPane();
        tablePanel.setViewportView(table);
        return tablePanel;
    }
}
