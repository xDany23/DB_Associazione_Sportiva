package db_ass.view.admin;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import db_ass.data.Campo;
import db_ass.data.Corso;
import db_ass.data.Giorno;
import db_ass.data.Sport;
import db_ass.view.Menu;
import db_ass.view.OptionArea;

public class CoursesPanel extends BasePanel{

    public CoursesPanel(Menu menu) {
        super(menu);
        this.setUp(getMenu().getController().getAllActiveCourses(), "Tutti i corsi attivi");
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setUp(List<?> elements, String title) {
        List<Corso> corsi = (List<Corso>)List.copyOf(elements);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setFields(List.of(new OptionArea("Codice Corso", "0"), new OptionArea("Sport Praticato", ""), new OptionArea("Data Inizio", ""), new OptionArea("Data Fine", "")));
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
            if (getSearchField().isBlank()) {
                update();
            } else {
                int code;
                try {
                    code =  Integer.parseInt(getSearchField());
                    Corso corso = getMenu().getController().findCourse(code);
                    update( corso == null
                        ? List.of()
                        : List.of(corso));
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Il codice corso deve essere un numero intero");
                }
            }
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
            JComboBox<String> sports = new JComboBox<>();
            box.setName("Allenatore");
            sports.setName("SportPraticato");
            fillComboBox(box, getMenu().getController().getAllTrainers().stream().map(t -> t.cf).toList());
            fillComboBox(sports, List.of("Calcetto","Tennis","Padel"));
            AddPanel insert = new AddPanel(List.of(new JTextField("Data Inizio"), new JTextField("Data Fine"), new JTextField("Prezzo")) ,
                                           List.of(box,sports));
            String[] options = {"Continua", "Annulla"};
            int n = JOptionPane.showOptionDialog(this, insert, "Aggiungi Corso", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);
            if (n == JOptionPane.OK_OPTION) {
                List<String> texts = insert.getTexts();
                List<String> selected = insert.getSelection();
                if (texts.stream().allMatch(t -> !t.isBlank())) {
                    try {
                        var prezzo = Double.parseDouble(texts.get(2));
                        getMenu().getController().addNewCourse(texts.get(0), texts.get(1), Sport.valueOf(selected.get(1).toUpperCase()), prezzo, selected.get(0));
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Inserire un prezzo corretto");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Devi inserire tutti i campi");
                }
            }
            update();
        });
        JButton addLesson = new JButton("Aggiungi una lezione del corso");
        addLesson.addActionListener(l -> {
            if (!getSearchedOptionOutput("Sport Praticato").isBlank()) {
                int code = Integer.parseInt(getSearchedOptionOutput("Codice Corso"));
                Sport sport = Sport.valueOf(getSearchedOptionOutput("Sport Praticato").toUpperCase());
                String start = getSearchedOptionOutput("Data Inizio");
                String stop = getSearchedOptionOutput("Data Fine");
                JComboBox<Integer> fields = new JComboBox<>();
                JComboBox<String> hours = new JComboBox<>(new String[]{"Scegli Orario","15:00:00","16:30:00","18:00:00"});
                JTextField data = new JTextField("Data");
                hours.addItemListener(e -> {
                    try {
                        String text = e.getItem().toString();
                        LocalDate str = LocalDate.parse(data.getText());
                        String giornoProva = str.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ITALIAN);
                        Giorno giorno = (giornoProva.equals("lunedì"))
                                ? Giorno.LUNEDI
                                : (giornoProva.equals("martedì"))
                                ? Giorno.MARTEDI
                                : (giornoProva.equals("mercoledì"))
                                ? Giorno.MERCOLEDI
                                : (giornoProva.equals("giovedì")) 
                                ? Giorno.GIOVEDI
                                : (giornoProva.equals("venerdì"))
                                ? Giorno.VENERDI
                                : (giornoProva.equals("sabato"))
                                ? Giorno.SABATO
                                : Giorno.DOMENICA;
                        if (!text.equals("Scegli Orario") && (!giorno.equals(Giorno.SABATO) || !giorno.equals(Giorno.DOMENICA)) 
                            && LocalDate.parse(start).datesUntil(LocalDate.parse(stop)).toList().contains(str)) {
                            fillComboBox(fields, getMenu().getController().findSpaceForNewLesson(sport, text, giorno, data.getText()).stream().map(c -> c.numeroCampo).toList());
                        } else {
                            fillComboBox(fields, List.of());
                        }
                    } catch (DateTimeParseException exc) {
                        JOptionPane.showMessageDialog(null, "Inserisci un formato data valido (anno-mese-giorno)");
                    } 
                });
                AddPanel insert = new AddPanel(List.of(data), List.of(hours,fields));
                int n = JOptionPane.showOptionDialog(this, insert, "Aggiungi Corso", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, new String[]{"Continua", "Annulla"}, null);
                if (n == JOptionPane.OK_OPTION) {
                    List<String> text = insert.getTexts();
                    List<String> selections = insert.getSelection();
                    if (text.stream().allMatch(t -> !t.isBlank()) && selections.stream().allMatch(s -> !s.isBlank()) && selections.size() == 2) {
                        try {
                            LocalDate str = LocalDate.parse(data.getText());
                            String giornoProva = str.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ITALIAN);
                            Giorno giorno = (giornoProva.equals("lunedì"))
                                    ? Giorno.LUNEDI
                                    : (giornoProva.equals("martedì"))
                                    ? Giorno.MARTEDI
                                    : (giornoProva.equals("mercoledì"))
                                    ? Giorno.MERCOLEDI
                                    : (giornoProva.equals("giovedì")) 
                                    ? Giorno.GIOVEDI
                                    : (giornoProva.equals("venerdì"))
                                    ? Giorno.VENERDI
                                    : (giornoProva.equals("sabato"))
                                    ? Giorno.SABATO
                                    : Giorno.DOMENICA;
                                    if ((!giorno.equals(Giorno.SABATO) || !giorno.equals(Giorno.DOMENICA)) && LocalDate.parse(start).datesUntil(LocalDate.parse(stop)).toList().contains(str)) {
                                        if (getMenu().getController().findSpaceForNewLesson(sport, selections.getFirst(), giorno, data.getText()).stream()
                                                                                                                                .map(c -> c.numeroCampo)
                                                                                                                                .toList()
                                                                                                                                .contains(Integer.parseInt(selections.get(1)))) {
                                            getMenu().getController().addNewCourseLesson(Integer.parseInt(selections.get(1)), giorno, selections.getFirst(), data.getText(), sport, code);
                                        } else {
                                            JOptionPane.showMessageDialog(null, "Numero campo invalido");
                                        }
                                    }
                        } catch (DateTimeParseException e) {
                            JOptionPane.showMessageDialog(null, "Inserisci un formato data valido (anno-mese-giorno)");
                        }
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Seleziona un corso");
            }
            update();
        });
        return List.of(search,terminate,addNew,addLesson);
    }

    private <E> void fillComboBox(JComboBox<E> box, List<E> elements) {
        box.removeAllItems();
        for(var elem: elements) {
            box.addItem(elem);
        }
    }
}
