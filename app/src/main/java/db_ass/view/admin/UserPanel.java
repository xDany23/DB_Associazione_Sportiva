package db_ass.view.admin;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;

import db_ass.data.Persona;
import db_ass.view.Menu;

public class UserPanel extends AbstractPersonPanel{

    public UserPanel(Menu menu, JFrame mainFrame) {
        super(menu, mainFrame);
        setUp(menu.getController().getAllUsers(), "Tutti gli utenti");
    }

    @Override
    public List<JButton> createButtons() {
        JButton search = new JButton("Cerca");
        search.addActionListener(l -> {
            Persona persona = getMenu().getController().findPersona(getSearchField());
            update(getSearchField().isBlank() 
                    ? getMenu().getController().getAllUsers()
                    : persona == null || persona.utente == false
                    ? List.of()
                    : List.of(persona));
        });
        JButton demote = new JButton("Rimuovi utente");
        demote.addActionListener(l -> {
            String cf = getSearchedOptionOutput("Codice Fiscale");
            if (!cf.isBlank()) {
                getMenu().getController().demoteUser(getMenu().getController().findPersona(cf));
            } 
            update(getMenu().getController().getAllUsers());
        });
        JButton promoteTrainer = new JButton("Aggiungi tra gli allenatori");
        promoteTrainer.addActionListener(l -> {
            getMenu().getController().promoteToTrainer(getSearchedOptionOutput("Codice Fiscale"));
            update(getMenu().getController().getAllUsers());
        });
        JButton promoteReferee = new JButton("Aggiungi tra gli arbitri");
        promoteReferee.addActionListener(l -> {
            getMenu().getController().promoteToReferee(getSearchedOptionOutput("Codice Fiscale"));
            update(getMenu().getController().getAllUsers());
        });
        return List.of(search,demote,promoteTrainer,promoteReferee);
    }

    @Override
    public void update() {
        update(getMenu().getController().getAllUsers());
    }
    
}
