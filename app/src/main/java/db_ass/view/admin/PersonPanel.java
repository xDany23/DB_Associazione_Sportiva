package db_ass.view.admin;

import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

import db_ass.data.Persona;
import db_ass.view.Menu;
import db_ass.view.OptionArea;

public class PersonPanel extends BasePanel{

    private final Menu mainFrame;

    public PersonPanel(Menu mainFrame) {
        super();
        this.mainFrame = mainFrame;
    }
    
    public void setUp(List<Persona> persone) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setTitle("Tutti gli utenti", SwingUtilities.CENTER);
        setFields(List.of(new OptionArea("Codice Fiscale", "Codice Fiscale")));
        JButton search = new JButton("CERCA");
        search.addActionListener(l -> {
            update(getSearchField().isBlank() 
                    ? this.mainFrame.getController().getAllUsers()
                    : this.mainFrame.getController().findPersona(getSearchField()) == null
                    ? List.of()
                    : List.of(this.mainFrame.getController().findPersona(getSearchField())));
        });
        setButtons(List.of(search));
        fillOptionPanel();
        createTablePanel(List.of("Nome","Cognome","Codice Fiscale","e-mail"),
                                        persone.stream().map(p -> p.nome).toList(),
                                        persone.stream().map(p -> p.cognome).toList(),
                                        persone.stream().map(p -> p.cf).toList(),
                                        persone.stream().map(p -> p.email).toList());
        this.add(getTitle());
        this.add(getTablePanel());
        this.add(getOptionPanel());
    }

    public void update(List<Persona> persone) {
        createTablePanel(List.of("Nome","Cognome","Codice Fiscale","e-mail"),
                                        persone.stream().map(p -> p.nome).toList(),
                                        persone.stream().map(p -> p.cognome).toList(),
                                        persone.stream().map(p -> p.cf).toList(),
                                        persone.stream().map(p -> p.email).toList());
        
    }
}
