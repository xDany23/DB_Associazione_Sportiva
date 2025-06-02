package db_ass.view.admin;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;

import db_ass.data.Persona;
import db_ass.view.LimitDocumentFilter;
import db_ass.view.Menu;
import db_ass.view.OptionArea;

public class TrainerPanel extends AbstractPersonPanel{

    public TrainerPanel(Menu main, JFrame mainFrame) {
        super(main, mainFrame);
        setUp(main.getController().getAllTrainers(), "Tutti gli allenatori");
    }

    @Override
    public List<JButton> createButtons() {
        JButton search = new JButton("Cerca");
        search.addActionListener(l -> {
            Persona persona = getMenu().getController().findPersona(getSearchField());
            update(getSearchField().isBlank() 
                    ? getMenu().getController().getAllTrainers()
                    : persona == null || persona.allenatore == false
                    ? List.of()
                    : List.of(persona));
        });
        JButton demote = new JButton("Rimuovi allenatore");
        demote.addActionListener(l -> {
            getMenu().getController().demoteTrainer(getSearchedOptionOutput("Codice Fiscale"));
            update(getMenu().getController().getAllTrainers());
        });
        JButton promoteUser = new JButton("Aggiungi tra gli utenti");
        promoteUser.addActionListener(l -> {
            getMenu().getController().promoteToUser(getSearchedOptionOutput("Codice Fiscale"));
            update(getMenu().getController().getAllTrainers());
        });
        JButton promoteReferee = new JButton("Aggiungi tra gli arbitri");
        promoteReferee.addActionListener(l -> {
            getMenu().getController().promoteToReferee(getSearchedOptionOutput("Codice Fiscale"));
            update(getMenu().getController().getAllTrainers());
        });
        JButton addTrainer =  new JButton("Aggiungi allenatore");
        addTrainer.addActionListener(l -> {
            /* JPanel insert = new JPanel();
            insert.setLayout(new GridLayout(5, 2));

            JLabel nomeLabel = new JLabel("Nome:");
            JTextField nomeField = new JTextField(16);
            nomeField.setMinimumSize(new Dimension(150, 15));
            nomeLabel.setAlignmentX(JLabel.RIGHT);
            nomeField.setAlignmentX(JLabel.LEFT_ALIGNMENT);
            insert.add(nomeLabel);
            insert.add(nomeField);

            JLabel cognomeLabel = new JLabel("Cognome:");
            JTextField cognomeField = new JTextField(16);
            cognomeField.setMinimumSize(new Dimension(150, 15));
            cognomeLabel.setAlignmentX(JLabel.RIGHT);
            cognomeField.setAlignmentX(JLabel.LEFT_ALIGNMENT);
            insert.add(cognomeLabel);
            insert.add(cognomeField);

            JLabel cfLabel = new JLabel("CF:");
            JTextField cfField = new JTextField(16);
            cfField.setMinimumSize(new Dimension(150, 15));
            ((AbstractDocument) cfField.getDocument()).setDocumentFilter(new LimitDocumentFilter(16));
            cfLabel.setAlignmentX(JLabel.RIGHT);
            cfField.setAlignmentX(JLabel.LEFT_ALIGNMENT);
            insert.add(cfLabel);
            insert.add(cfField);

            JLabel emailLabel = new JLabel("Email:");
            JTextField emailField = new JTextField(16);
            emailField.setMinimumSize(new Dimension(150, 15));
            emailLabel.setAlignmentX(JLabel.RIGHT);
            emailField.setAlignmentX(JLabel.LEFT_ALIGNMENT);
            insert.add(emailLabel);
            insert.add(emailField);

            JLabel passLabel = new JLabel("Password:");
            JPasswordField passField = new JPasswordField(16);
            passField.setMinimumSize(new Dimension(150, 15));
            passLabel.setAlignmentX(JLabel.RIGHT);
            passField.setAlignmentX(JLabel.LEFT_ALIGNMENT);
            insert.add(passLabel);
            insert.add(passField); */
            AddPanel insert = new AddPanel(List.of(new JTextField("Nome"), new JTextField("Cognome"), new JTextField("Codice Fiscale"), new JTextField("e-mail"), new JTextField("Password")));
            String[] options = {"Continua", "Annulla"};
            int n = JOptionPane.showOptionDialog(this, insert, "Aggiungi un allenatore",JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);
            System.out.println(n == JOptionPane.OK_OPTION ? "Continuato" : "Annullato");
            //System.out.println(nomeField.getText() + cognomeField.getText()+emailField.getText()+cfField.getText()+new String(passField.getPassword()));
        });
        return List.of(search,demote,promoteUser,promoteReferee,addTrainer);
    }

    @Override
    public void update() {
        update(getMenu().getController().getAllTrainers());
    }
    
}
