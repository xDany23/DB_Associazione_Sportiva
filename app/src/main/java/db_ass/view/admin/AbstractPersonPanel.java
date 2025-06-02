package db_ass.view.admin;

import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingConstants;

import db_ass.data.Persona;
import db_ass.view.Menu;
import db_ass.view.OptionArea;

public abstract class AbstractPersonPanel extends BasePanel{

    private final Menu menu;
    private final JFrame mainFrame;

    public AbstractPersonPanel(Menu menu, JFrame mainFrame) {
        super();
        this.menu = menu;
        this.mainFrame = mainFrame;
    }
    
    @Override
    public void setUp(List<?> elements, String text) {
        if (! (elements.get(0) instanceof Persona)) {
            throw new IllegalArgumentException("Expected a List of Persona");
        }
        List<Persona> persone = (List<Persona>)List.copyOf(elements);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setFields(List.of(new OptionArea("Codice Fiscale", "Codice Fiscale")));
        setTitle(text, SwingConstants.CENTER);
        setButtons(createButtons());
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

    public Menu getMenu() {
        return this.menu;
    }

    public JFrame getMainFrame() {
        return this.mainFrame;
    }

    public abstract List<JButton> createButtons();
}
