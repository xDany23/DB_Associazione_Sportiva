package db_ass.view.admin;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;

import db_ass.data.Persona;
import db_ass.view.Menu;

public class DisabledUsersPanel extends AbstractPersonPanel{

    public DisabledUsersPanel(Menu menu, JFrame mainFrame) {
        super(menu, mainFrame);
        setUp(getMenu().getController().getAllDisabledUsers(), "Tutti gli utenti disabilitati");
    }

    @Override
    public List<JButton> createButtons() {
        JButton search = new JButton("Cerca");
        search.addActionListener(l -> {
            Persona persona = getMenu().getController().findPersona(getSearchField());
            update(getSearchField().isBlank() 
                    ? getMenu().getController().getAllDisabledUsers()
                    : persona == null || persona.utente == true || persona.admin == true || persona.allenatore == true || persona.arbitro == true
                    ? List.of()
                    : List.of(persona));
        });
        JButton promoteUser = new JButton("Ripristina come utente");
        promoteUser.addActionListener(l -> {
            String cf = getSearchedOptionOutput("Codice Fiscale");
            if (!cf.isBlank()) {
                getMenu().getController().promoteToUser(cf);
            } 
            update(getMenu().getController().getAllDisabledUsers());
        });
        JButton promoteTrainer = new JButton("Ripristina come allenatore");
        promoteTrainer.addActionListener(l -> {
            getMenu().getController().promoteToTrainer(getSearchedOptionOutput("Codice Fiscale"));
            update(getMenu().getController().getAllDisabledUsers());
        });
        JButton promoteReferee = new JButton("Rispristina come arbitro");
        promoteReferee.addActionListener(l -> {
            getMenu().getController().promoteToReferee(getSearchedOptionOutput("Codice Fiscale"));
            update(getMenu().getController().getAllDisabledUsers());
        });
        return List.of(search,promoteUser,promoteTrainer,promoteReferee);
    }

    @Override
    public void update() {
        update(getMenu().getController().getAllDisabledUsers());
    }
    
}
