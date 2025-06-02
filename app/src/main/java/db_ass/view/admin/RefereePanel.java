package db_ass.view.admin;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;

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
        return List.of(search,demote,promoteUser,promoteTrainer);
    }

    @Override
    public void update() {
        update(getMenu().getController().getAllReferees());
    }
    
}
