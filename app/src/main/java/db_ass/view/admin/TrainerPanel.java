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
        return List.of(search);
    }

    @Override
    public void update() {
        update(getMainFrame().getController().getAllTrainers());
    }
    
}
