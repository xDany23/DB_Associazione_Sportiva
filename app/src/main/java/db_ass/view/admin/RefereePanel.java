package db_ass.view.admin;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import db_ass.data.Persona;
import db_ass.view.Menu;

public class RefereePanel extends AbstractPersonPanel{

    public RefereePanel(Menu main, JFrame mainFrame) {
        super(main, mainFrame);
        setUp(main.getController().getAllReferees(), "Tutti gli arbitri");
    }

    @Override
    public List<JButton> createButtons() {
        JButton search = new JButton("Cerca");
        search.addActionListener(l -> {
            Persona persona = getMenu().getController().findPersona(getSearchField());
            update(getSearchField().isBlank() 
                    ? getMenu().getController().getAllUsers()
                    : persona == null || persona.arbitro == false
                    ? List.of()
                    : List.of(persona));
        });
        JButton demote = new JButton("Rimuovi arbitro");
        demote.addActionListener(l -> {
            getMenu().getController().demoteReferee(getSearchedOptionOutput("Codice Fiscale"));
            update(getMenu().getController().getAllReferees());
        });
        JButton promoteUser = new JButton("Aggiungi tra gli utenti");
        promoteUser.addActionListener(l -> {
            getMenu().getController().promoteToUser(getSearchedOptionOutput("Codice Fiscale"));
            update(getMenu().getController().getAllReferees());
        });
        JButton promoteTrainer = new JButton("Aggiungi tra gli allenatori");
        promoteTrainer.addActionListener(l -> {
            getMenu().getController().promoteToTrainer(getSearchedOptionOutput("Codice Fiscale"));
            update(getMenu().getController().getAllReferees());
        });
        JButton addReferee =  new JButton("Aggiungi arbitro");
        addReferee.addActionListener(l -> {
            AddPanel insert = new AddPanel(List.of(new JTextField("Nome"), new JTextField("Cognome"), new JTextField("Codice Fiscale"), new JTextField("e-mail"), new JTextField("Password")));
            String[] options = {"Continua", "Annulla"};
            int n = JOptionPane.showOptionDialog(this, insert, "Aggiungi un arbitro",JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);
            if (n == JOptionPane.OK_OPTION) {
                List<String> texts = insert.getTexts();
                String pass = insert.getPassword();
                String cf = insert.getCF();
                if (texts.stream().allMatch(t -> !t.isBlank()) && !pass.isBlank()) {
                    if (cf.isBlank()) {
                        JOptionPane.showMessageDialog(null, "Codice Fiscale invalido");
                    } else {
                        getMenu().getController().newReferee(texts.get(0), texts.get(1), cf, texts.get(2), pass);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Devi inserire tutti i campi");
                }
            }
            update(getMenu().getController().getAllReferees());
        });
        return List.of(search,demote,promoteUser,promoteTrainer, addReferee);
    }

    @Override
    public void update() {
        update(getMenu().getController().getAllReferees());
    }
    
}
