package db_ass.view.admin;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import db_ass.data.Persona;
import db_ass.view.Menu;

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
            AddPanel insert = new AddPanel(List.of(new JTextField("Nome"), new JTextField("Cognome"), new JTextField("Codice Fiscale"), new JTextField("e-mail"), new JTextField("Password")));
            String[] options = {"Continua", "Annulla"};
            int n = JOptionPane.showOptionDialog(this, insert, "Aggiungi un allenatore",JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);
            if (n == JOptionPane.OK_OPTION) {
                List<String> texts = insert.getTexts();
                String pass = insert.getPassword();
                String cf = insert.getCF();
                if (texts.stream().allMatch(t -> !t.isBlank()) && !pass.isBlank()) {
                    if (cf.isBlank()) {
                        JOptionPane.showMessageDialog(null, "Codice Fiscale invalido");
                    } else {
                        getMenu().getController().newTrainer(texts.get(0), texts.get(1), cf, texts.get(2), pass);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Devi inserire tutti i campi");
                }
            }
            update(getMenu().getController().getAllTrainers());
        });
        return List.of(search,demote,promoteUser,promoteReferee,addTrainer);
    }

    @Override
    public void update() {
        update(getMenu().getController().getAllTrainers());
    }
    
}
