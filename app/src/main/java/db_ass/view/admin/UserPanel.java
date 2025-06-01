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
        return List.of(search);
    }
    
}
