package db_ass.view.admin;

import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import db_ass.data.Corso;
import db_ass.data.Persona;
import db_ass.view.Menu;
import db_ass.view.OptionArea;

public class CoursesPanel extends BasePanel{

    public CoursesPanel(Menu menu) {
        super(menu);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setUp(List<?> elements, String title) {
        if (!(elements.getFirst() instanceof Corso)) {
            throw new IllegalArgumentException("Aspettato un corso");
        }
        List<Corso> corsi = (List<Corso>)List.copyOf(elements);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setFields(List.of(new OptionArea("Codice Corso", "0")));
        setTitle(title, SwingConstants.CENTER);
        setButtons(createButtons());
        fillOptionPanel();
        createTablePanel(List.of("Codice Corso","Sport Praticato", "Data Inizio", "Data Fine", "Allenatore", "Prezzo"),
                         corsi.stream().map(c -> c.codiceCorso).toList(),
                         corsi.stream().map(c -> c.sportPraticato.toString()).toList(),
                         corsi.stream().map(c -> c.dataInizio).toList(),
                         corsi.stream().map(c -> c.dataFine).toList(),
                         corsi.stream().map(c -> c.allenatore.cf).toList(),
                         corsi.stream().map(c -> c.prezzo).toList());
        this.add(getTitle());
        this.add(getTablePanel());
        this.add(getOptionPanel());
    }
    

    @Override
    public void update() {
        update(getMenu().getController().getAllActiveCourses());
    }

    private void update(List<Corso> corsi) {
        createTablePanel(List.of("Codice Corso","Sport Praticato", "Data Inizio", "Data Fine", "Allenatore", "Prezzo"),
                         corsi.stream().map(c -> c.codiceCorso).toList(),
                         corsi.stream().map(c -> c.sportPraticato.toString()).toList(),
                         corsi.stream().map(c -> c.dataInizio).toList(),
                         corsi.stream().map(c -> c.dataFine).toList(),
                         corsi.stream().map(c -> c.allenatore.cf).toList(),
                         corsi.stream().map(c -> c.prezzo).toList());
    }
    
    private List<JButton> createButtons() {
        JButton search = new JButton("Cerca");
        search.addActionListener(l -> {
            Corso corso = getMenu().getController().findCourse(Integer.parseInt(getSearchField()));
            update(getSearchField().isBlank() 
                    ? getMenu().getController().getAllActiveCourses()
                    : corso == null
                    ? List.of()
                    : List.of(corso));
        });
        JButton terminate = new JButton("Termina corso");
        terminate.addActionListener(l -> {
            String code = getSearchedOptionOutput("Codice Corso");
            if (!code.isBlank()) {
                getMenu().getController().terminateCourse(Integer.parseInt(code));
            } 
            update();
        });
        JButton addNew = new JButton("Aggiungi nuovo corso");
        addNew.addActionListener(l -> {
            JComboBox<String> box = new JComboBox<>();
            fillComboBox(box, getMenu().getController().getAllTrainers().stream().map(t -> t.cf).toList());
            AddPanel insert = new AddPanel(List.of(new JTextField("Data Inizio"), new JTextField("Data Fine"), new JTextField("Sport Praticato"), new JTextField("Prezzo")) ,
                                           box);
            String[] options = {"Continua", "Annulla"};
            int n = JOptionPane.showOptionDialog(this, insert, "Aggiungi Corso", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);
            update();
        });
        JButton promoteReferee = new JButton("Aggiungi tra gli arbitri");
        promoteReferee.addActionListener(l -> {
            getMenu().getController().promoteToReferee(getSearchedOptionOutput("Codice Fiscale"));
            update();
        });
        return List.of(search,terminate,addNew,promoteReferee);
    }

    private <E> void fillComboBox(JComboBox<E> box, List<E> elements) {
        for(var elem: elements) {
            box.addItem(elem);
        }
    }
}
