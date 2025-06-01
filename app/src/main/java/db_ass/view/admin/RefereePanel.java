package db_ass.view.admin;

import java.util.List;

import javax.swing.JButton;

import db_ass.data.Persona;
import db_ass.view.Menu;

public class RefereePanel extends AbstractPersonPanel{

    public RefereePanel(Menu main) {
        super(main);
        setUp(main.getController().getAllReferees(), "Tutti gli arbitri");
    }

    @Override
    public List<JButton> createButtons() {
        JButton search = new JButton("Cerca");
        search.addActionListener(l -> {
            Persona persona = getMainFrame().getController().findPersona(getSearchField());
            update(getSearchField().isBlank() 
                    ? getMainFrame().getController().getAllUsers()
                    : persona == null || persona.arbitro == false
                    ? List.of()
                    : List.of(persona));
        });
        JButton demote = new JButton("Rimuovi arbitro");
        demote.addActionListener(l -> {
            getMainFrame().getController().demoteReferee(getSearchedOptionOutput("Codice Fiscale"));
            update(getMainFrame().getController().getAllReferees());
        });
        JButton promoteUser = new JButton("Aggiungi tra gli utenti");
        promoteUser.addActionListener(l -> {
            getMainFrame().getController().promoteToUser(getSearchedOptionOutput("Codice Fiscale"));
            update(getMainFrame().getController().getAllReferees());
        });
        JButton promoteTrainer = new JButton("Aggiungi tra gli allenatori");
        promoteTrainer.addActionListener(l -> {
            getMainFrame().getController().promoteToTrainer(getSearchedOptionOutput("Codice Fiscale"));
            update(getMainFrame().getController().getAllReferees());
        });
        return List.of(search,demote,promoteUser,promoteTrainer);
    }

    @Override
    public void update() {
        update(getMainFrame().getController().getAllReferees());
    }
    
}
