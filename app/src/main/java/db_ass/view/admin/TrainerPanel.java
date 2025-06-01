package db_ass.view.admin;

import java.util.List;

import javax.swing.JButton;

import db_ass.data.Persona;
import db_ass.view.Menu;

public class TrainerPanel extends AbstractPersonPanel{

    public TrainerPanel(Menu main) {
        super(main);
        setUp(main.getController().getAllTrainers(), "Tutti gli allenatori");
    }

    @Override
    public List<JButton> createButtons() {
        JButton search = new JButton("Cerca");
        search.addActionListener(l -> {
            Persona persona = getMainFrame().getController().findPersona(getSearchField());
            update(getSearchField().isBlank() 
                    ? getMainFrame().getController().getAllTrainers()
                    : persona == null || persona.allenatore == false
                    ? List.of()
                    : List.of(persona));
        });
        JButton demote = new JButton("Rimuovi allenatore");
        demote.addActionListener(l -> {
            getMainFrame().getController().demoteTrainer(getSearchedOptionOutput("Codice Fiscale"));
            update(getMainFrame().getController().getAllTrainers());
        });
        JButton promoteUser = new JButton("Aggiungi tra gli utenti");
        promoteUser.addActionListener(l -> {
            getMainFrame().getController().promoteToUser(getSearchedOptionOutput("Codice Fiscale"));
            update(getMainFrame().getController().getAllTrainers());
        });
        JButton promoteReferee = new JButton("Aggiungi tra gli arbitri");
        promoteReferee.addActionListener(l -> {
            getMainFrame().getController().promoteToReferee(getSearchedOptionOutput("Codice Fiscale"));
            update(getMainFrame().getController().getAllTrainers());
        });
        return List.of(search,demote,promoteUser,promoteReferee);
    }

    @Override
    public void update() {
        update(getMainFrame().getController().getAllTrainers());
    }
    
}
