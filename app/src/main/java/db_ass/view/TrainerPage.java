package db_ass.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import db_ass.data.LezionePrivata;
import db_ass.data.Persona;

public class TrainerPage {
    
    private Menu menu;
    private JFrame mainFrame;
    private Persona persona;
    private Runnable onClose;

    public TrainerPage(Menu menu, JFrame mainFrame, Persona persona, Runnable onClose) {
        this.menu = menu;
        this.mainFrame = mainFrame;
        this.persona = persona;
        this.onClose = onClose;
    }

    public JFrame setup() {

        Font labelFont = new Font("Arial", Font.PLAIN, 18);
        Font titleFont = new Font("Arial", Font.BOLD, 24);

        JPanel backPanel = new JPanel();
        backPanel.setLayout(new BoxLayout(backPanel, BoxLayout.Y_AXIS));

        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));

        //bottone indietro
        JButton backButton = new JButton("Indietro");
        backButton.setAlignmentX(JButton.LEFT_ALIGNMENT);
        backButton.setAlignmentY(JButton.TOP_ALIGNMENT);
        backPanel.add(backButton);
        backButton.addActionListener(e -> {
            this.back();
        });


        JLabel title = new JLabel("Ciao " + persona.nome + ", ecco le prossime lezioni:", SwingConstants.CENTER);
        title.setFont(titleFont);
        main.add(title);
        main.add(Box.createVerticalStrut(20));
        List<LezionePrivata> lezioni = menu.getController().allLessonsOfTrainer(persona);
        if (lezioni.isEmpty()) {
            JLabel line = new JLabel("Non hai nessuna lezione in programma!");
            line.setFont(labelFont);
            main.add(line);
        } else {
            for(int i = 0; i < lezioni.size(); i++) {
                JLabel line = new JLabel("- Data: " + lezioni.get(i).dataSvolgimento + ", " + 
                                        "Orario: " + lezioni.get(i).orarioInizio + ", " +
                                        "Sport: " + lezioni.get(i).sportPraticato + ", " +
                                        "Campo: " + lezioni.get(i).numeroCampo.numeroCampo + ", ");
                line.setFont(labelFont);
                main.add(line);
            }
        }



        //aggiungo tutto al mainFrame
        mainFrame.getContentPane().add(main, BorderLayout.CENTER);
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

    public void back() {
        var cp = mainFrame.getContentPane();
        cp.removeAll();
        mainFrame = menu.setUp(onClose);
    }
}
