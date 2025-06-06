package db_ass.view.admin;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import db_ass.data.Campo;
import db_ass.data.FasciaOraria;
import db_ass.data.Giorno;
import db_ass.view.Menu;
import db_ass.view.OptionArea;

public class FieldPanel extends BasePanel{

    public FieldPanel(Menu menu) {
        super(menu);
        this.setUp(getMenu().getController().getAllFields(), "Tutti i campi");
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setUp(List<?> elements, String title) {
        List<Campo> campi = (List<Campo>)List.copyOf(elements);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setFields(List.of(new OptionArea("Numero Campo", "")));
        setTitle(title, SwingConstants.CENTER);
        getOptionPanel().removeAll();
        setButtons(createButtons());
        fillOptionPanel();
        createTablePanel(List.of("Numero Campo", "Tipo Campo"), 
                         campi.stream().map(c -> c.numeroCampo).toList(),
                         campi.stream().map(c -> c.tipo.toString()).toList());
        this.add(getTitle());
        this.add(getTablePanel());
        this.add(getOptionPanel());
        getOptionPanel().revalidate();
    }

    @Override
    public void update() {
        this.removeAll();
        this.setUp(getMenu().getController().getAllFields(), "Tutti i campi");
    }

    private void update(List<Campo> campi) {
        createTablePanel(List.of("Numero Campo", "Tipo Campo"), 
                         campi.stream().map(c -> c.numeroCampo).toList(),
                         campi.stream().map(c -> c.tipo.toString()).toList());
    }

    private List<JButton> createButtons() {
        JButton search = new JButton("Cerca");
        search.addActionListener(l -> {
            if (getSearchField().isBlank()) {
                update(getMenu().getController().getAllFields());
            } else {
                int code;
                try {
                    code =  Integer.parseInt(getSearchField());
                    Campo campo = getMenu().getController().findField(code);
                    update( campo == null
                        ? List.of()
                        : List.of(campo));
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Il numero campo deve essere un numero intero");
                }
            }
        });
        JButton allTimes = new JButton("Tutti gli orari del campo");
        allTimes.addActionListener(l -> {
            try {
                int code = Integer.parseInt(getSearchedOptionOutput("Numero Campo"));
                setUpTimes(code);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Scegli prima un campo");
            }
            
        });
        return List.of(search, allTimes);
    }
    
    private void setUpTimes(int field) {
        this.removeAll();
        getOptionPanel().removeAll();
        this.setTitle("Orari del campo " + field, JLabel.CENTER);
        List<FasciaOraria> orari = getMenu().getController().getAllTimesOfField(field);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setFields(List.of(new OptionArea("Giorno", ""), new OptionArea("Orario Inizio", "")));
        setButtons(createTimesButtons(field));
        fillOptionPanel();
        createTablePanel(List.of("Giorno", "Orario Inizio", "Orario Fine", "Tipo", "Prezzo"),
                         orari.stream().map(o -> o.giorno.toString()).toList(),
                         orari.stream().map(o -> o.orarioInizio).toList(),
                         orari.stream().map(o -> o.orarioFine).toList(),
                         orari.stream().map(o -> o.tipo.toString()).toList(),
                         orari.stream().map(o -> o.prezzo).toList());
        this.add(getTitle());
        this.add(getTablePanel());
        this.add(getOptionPanel());
        getOptionPanel().revalidate();
    }

    private List<JButton> createTimesButtons(int field) {
        JButton backButton = new JButton("Indietro");
        backButton.addActionListener(l -> {
            this.removeAll();
            this.setUp(getMenu().getController().getAllFields(), "Tutti i campi");
        });
        JButton AllOccupied = new JButton("Tutti gli orari occupati");
        AllOccupied.addActionListener(l -> {
            AddPanel insert = new AddPanel(List.of(new JTextField("Data")));
            int n = JOptionPane.showOptionDialog(this, insert, "inserisci la data interessata", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, new String[]{"Continua","Annulla"}, null);
            if (n == JOptionPane.OK_OPTION) {
                try {
                    String date = insert.getTexts().getFirst();
                    LocalDate.parse(date);
                    updateTimes(getMenu().getController().findAllOccupiedTimesOfField(field, date));
                } catch (DateTimeParseException e) {
                    JOptionPane.showMessageDialog(null, "Inserisci un formato valido per la data(anno-mese-giorno)");
                }
            }
        });
        JButton modify = new JButton("Modifica prezzo");
        modify.addActionListener(l -> {
            AddPanel insert = new AddPanel(List.of(new JTextField("Prezzo")));
            int n = JOptionPane.showOptionDialog(this, insert, "inserisci il nuovo prezzo", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, new String[]{"Continua","Annulla"}, null);
            if (n == JOptionPane.OK_OPTION) {
                try {
                    double price = Double.parseDouble(insert.getTexts().getFirst());
                    if (price <= 0) {
                        JOptionPane.showMessageDialog(null, "Inserisci un prezzo positivo");
                    } else if (getSearchedOptionOutput("Giorno").isBlank()) {
                        JOptionPane.showMessageDialog(null, "Seleziona una fascia prima di premere il pulsante");
                    } else {
                        getMenu().getController().modifyTimePrice(price, field, Giorno.valueOf(getSearchedOptionOutput("Giorno").toUpperCase()), getSearchedOptionOutput("Orario Inizio"));
                        updateTimes(getMenu().getController().getAllTimesOfField(field));
                    }
                    
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Inserisci un prezzo valido");
                }
            }
        });
        return List.of(AllOccupied, modify, backButton);
    }

    private void updateTimes(List<FasciaOraria> orari) {
        createTablePanel(List.of("Giorno", "Orario Inizio", "Orario Fine", "Tipo", "Prezzo"),
                         orari.stream().map(o -> o.giorno.toString()).toList(),
                         orari.stream().map(o -> o.orarioInizio).toList(),
                         orari.stream().map(o -> o.orarioFine).toList(),
                         orari.stream().map(o -> o.tipo.toString()).toList(),
                         orari.stream().map(o -> o.prezzo).toList());
    }
}
