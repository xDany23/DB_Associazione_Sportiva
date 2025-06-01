package db_ass.view.admin;

import java.util.List;

import javax.swing.JButton;

import db_ass.data.Persona;
import db_ass.view.Menu;

public class UserPanel extends AbstractPersonPanel{

    public UserPanel(Menu menu) {
        super(menu);
        setUp(menu.getController().getAllUsers(), "Tutti gli utenti");
    }

    @Override
    public List<JButton> createButtons() {
        JButton search = new JButton("Cerca");
        search.addActionListener(l -> {
            Persona persona = getMainFrame().getController().findPersona(getSearchField());
            update(getSearchField().isBlank() 
                    ? getMainFrame().getController().getAllUsers()
                    : persona == null || persona.utente == false
                    ? List.of()
                    : List.of(persona));
        });
        JButton demote = new JButton("Rimuovi utente");
        demote.addActionListener(l -> {
            String cf = getSearchedOptionOutput("Codice Fiscale");
            if (!cf.isBlank()) {
                getMainFrame().getController().demoteUser(getMainFrame().getController().findPersona(cf));
            } 
            update(getMainFrame().getController().getAllUsers());
        });
        JButton promoteTrainer = new JButton("Aggiungi tra gli allenatori");
        promoteTrainer.addActionListener(l -> {
            getMainFrame().getController().promoteToTrainer(getSearchedOptionOutput("Codice Fiscale"));
            update(getMainFrame().getController().getAllUsers());
        });
        JButton promoteReferee = new JButton("Aggiungi tra gli arbitri");
        promoteReferee.addActionListener(l -> {
            getMainFrame().getController().promoteToReferee(getSearchedOptionOutput("Codice Fiscale"));
            update(getMainFrame().getController().getAllUsers());
        });
        return List.of(search,demote,promoteTrainer,promoteReferee);
    }

    @Override
    public void update() {
        update(getMainFrame().getController().getAllUsers());
    }
    
}
