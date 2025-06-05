package db_ass.view.admin;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import db_ass.data.Squadra;
import db_ass.data.TipoSquadra;
import db_ass.data.Torneo;
import db_ass.utility.Pair;
import db_ass.view.Menu;
import db_ass.view.OptionArea;

public class TournamentPanel extends BasePanel{

    public TournamentPanel(Menu menu) {
        super(menu);
        setUp(getMenu().getController().getAllEnterableTournaments(), "Tutti i tornei");
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setUp(List<?> elements, String title) {
        List<Pair<Torneo,Integer>> tornei = (List<Pair<Torneo,Integer>>)elements;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setFields(List.of(new OptionArea("Codice Torneo", "0")));
        setTitle(title, SwingConstants.CENTER);
        getOptionPanel().removeAll();
        setButtons(createButtons());
        fillOptionPanel();
        createTablePanel(List.of("Codice Torneo", "Nome", "Tipo", "Data Svolgimento", "Premio", "Quota Iscrizione", "Numero Partecipanti", "Limite Partecipanti", "Vincitore"),
                         tornei.stream().map(t -> t.first().codiceTorneo).toList(),
                         tornei.stream().map(t -> t.first().nome).toList(),
                         tornei.stream().map(t -> t.first().tipo.toString()).toList(),
                         tornei.stream().map(t -> t.first().dataSvolgimento).toList(),
                         tornei.stream().map(t -> t.first().premio).toList(),
                         tornei.stream().map(t -> t.first().quotaIscrizione).toList(),
                         tornei.stream().map(t -> t.second()).toList(),
                         tornei.stream().map(t -> t.first().massimoPartecipanti).toList(),
                         tornei.stream().map(t -> t.first().vincitore).map(s -> {String code;  
                                                                                 if (s == null) {
                                                                                    code = "Nessuno";
                                                                                 } else {
                                                                                    code = String.valueOf(s.codiceSquadra) + " " + s.nome;
                                                                                 }
                                                                                 return code;}).toList());
        this.add(getTitle());
        this.add(getTablePanel());
        this.add(getOptionPanel());
        getOptionPanel().revalidate();
    }

    @Override
    public void update() {
        this.removeAll();
        setUp(getMenu().getController().getAllEnterableTournaments(), "Tutti i tornei");
    }

    private void update(List<Pair<Torneo,Integer>> tornei) {
        createTablePanel(List.of("Codice Torneo", "Nome", "Tipo", "Data Svolgimento", "Premio", "Quota Iscrizione", "Numero Partecipanti", "Limite Partecipanti", "Vincitore"),
                         tornei.stream().map(t -> t.first().codiceTorneo).toList(),
                         tornei.stream().map(t -> t.first().nome).toList(),
                         tornei.stream().map(t -> t.first().tipo.toString()).toList(),
                         tornei.stream().map(t -> t.first().dataSvolgimento).toList(),
                         tornei.stream().map(t -> t.first().premio).toList(),
                         tornei.stream().map(t -> t.first().quotaIscrizione).toList(),
                         tornei.stream().map(t -> t.second()).toList(),
                         tornei.stream().map(t -> t.first().massimoPartecipanti).toList(),
                         tornei.stream().map(t -> t.first().vincitore).map(s -> {String code;  
                                                                                 if (s == null) {
                                                                                    code = "Nessuno";
                                                                                 } else {
                                                                                    code = String.valueOf(s.codiceSquadra) + " " + s.nome;
                                                                                 }
                                                                                 return code;}).toList());
    }

    private List<JButton> createButtons() {
        JButton search = new JButton("Cerca");
        search.addActionListener(l -> {
            if (getSearchField().isBlank()) {
                update(getMenu().getController().getAllEnterableTournaments());
            } else {
                int code;
                try {
                    code =  Integer.parseInt(getSearchField());
                    Pair<Torneo,Integer> torneo = getMenu().getController().findTournamentAndPartecipants(code);
                    update( torneo == null
                        ? List.of()
                        : List.of(torneo));
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Il codice torneo deve essere un numero intero");
                }
            }
        });
        JButton onlyActive = new JButton("Visualizza tutti");
        onlyActive.addActionListener(l -> {
            update(getMenu().getController().getAllTournaments());
        });
        JButton allTeams = new JButton("Tutte le squadre iscritte");
        allTeams.addActionListener(l -> {
            setTeams(Integer.parseInt(getSearchedOptionOutput("Codice Torneo")));
        });
        JButton modifyPrice = new JButton("Modifica quota");
        modifyPrice.addActionListener(l -> {
            AddPanel insert = new AddPanel(List.of(new JTextField("Quota Iscrizione")));
            int n = JOptionPane.showOptionDialog(this, insert, "Modifica quota iscrizione", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
                                                 null, new String[]{"Continua","Annulla"}, null);
            if (n == JOptionPane.OK_OPTION) {
                try {
                    List<String> texts = insert.getTexts();
                    double prezzo = Double.parseDouble(texts.getFirst());
                    getMenu().getController().modifyTournamentPrice(Integer.parseInt(getSearchedOptionOutput("Codice Torneo")), prezzo);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Inserire un formato valido per la quota");
                }
            }
            update(getMenu().getController().getAllEnterableTournaments());
        });
        JButton modifyDate = new JButton("Cambia data");
        modifyDate.addActionListener(l -> {
            AddPanel insert = new AddPanel(List.of(new JTextField("Data Svolgimento")));
            int n = JOptionPane.showOptionDialog(this, insert, "Modifica data svolgimento", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
                                                 null, new String[]{"Continua","Annulla"}, null);
            if (n == JOptionPane.OK_OPTION) {
                try {
                    List<String> texts = insert.getTexts();
                    LocalDate.parse(texts.getFirst());
                    getMenu().getController().modifyTournamentDate(Integer.parseInt(getSearchedOptionOutput("Codice Torneo")), texts.getFirst());
                } catch (DateTimeParseException e) {
                    JOptionPane.showMessageDialog(null, "Inserire un formato valido per la data(anno-mese-giorno)");
                }
            }
            update(getMenu().getController().getAllEnterableTournaments());
        });
        JButton modifyWinner = new JButton("Imposta vincitore");
        modifyWinner.addActionListener(l -> {
            JComboBox<Integer> partecipants = new JComboBox<>();
            fillComboBox(partecipants, getMenu().getController()
                            .allTeamsInTournament(Integer.parseInt(getSearchedOptionOutput("Codice Torneo")))
                            .stream().map(e -> e.codiceSquadra).toList());
            AddPanel insert = new AddPanel(List.of(), List.of(partecipants));
            int n = JOptionPane.showOptionDialog(this, insert, "Modifica vincitore", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
                                                 null, new String[]{"Continua","Annulla"}, null);
            if (n == JOptionPane.OK_OPTION) {
                int winner = Integer.parseInt(insert.getSelection().getFirst());
                getMenu().getController().modifyTournamentWinner(Integer.parseInt(getSearchedOptionOutput("Codice Torneo")), winner);  
            }
            update(getMenu().getController().getAllEnterableTournaments());
        });
        JButton createNew = new JButton("Nuovo Torneo");
        createNew.addActionListener(l -> {
            AddPanel insert = new AddPanel(List.of(new JTextField("Data Svolgimento"), new JTextField("Nome"), new JTextField("Premio"), new JTextField("Massimo Partecipanti"), new JTextField("Quota Iscrizione")),
                                            List.of(new JComboBox<>(new String[]{"Calcetto","Tennis_singolo","Tennis_doppio","Padel"})));
            int n = JOptionPane.showOptionDialog(this, insert, "Nuovo Torneo", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, new String[]{"Continua", "Annulla"}, null);
            if (n == JOptionPane.OK_OPTION) {
                try {
                    List<String> texts = insert.getTexts();
                    TipoSquadra sport = TipoSquadra.valueOf(insert.getSelection().getFirst().toUpperCase());
                    String data = texts.getFirst();
                    int maxP = Integer.parseInt(texts.get(3));
                    double quota = Double.parseDouble(texts.get(4));
                    if (maxP*quota <= 0 || LocalDate.parse(data).isBefore(LocalDate.now())) {
                        throw new IllegalArgumentException();
                    }
                    getMenu().getController().createNewTournament(data, texts.get(1), texts.get(2), maxP, quota, sport);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Inserire i formati corretti:\nData: anno-mese-giorno non passati\nPartecipanti: numero intero positivo\nQuota: numero reale positivo");
                }
            }
            update(getMenu().getController().getAllEnterableTournaments());
        });
        return List.of(search, onlyActive, allTeams, modifyPrice, modifyDate, modifyWinner, createNew);
    }
    
    private void setTeams(int tournamentCode) {
        this.removeAll();
        List<Squadra> squadre = getMenu().getController().allTeamsInTournament(tournamentCode);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setFields(List.of(new OptionArea("Codice Squadra", "0")));
        setTitle("Tutte le squadre partecipanti nel torneo " + tournamentCode, SwingConstants.CENTER);
        getOptionPanel().removeAll();
        setButtons(createTeamButtons(tournamentCode));
        fillOptionPanel();
        createTablePanel(List.of("Codice Squadra","Nome","Tipo","Componenti"),
                         squadre.stream().map(s -> s.codiceSquadra).toList(),
                         squadre.stream().map(s -> s.nome).toList(),
                         squadre.stream().map(s -> s.tipo.toString()).toList(),
                         squadre.stream().map(s -> givePartecipant(s)).toList());
        this.add(getTitle());
        this.add(getTablePanel());
        this.add(getOptionPanel());
        getOptionPanel().revalidate();
    }

    private void updateTeams(List<Squadra> squadre) {
        createTablePanel(List.of("Codice Squadra","Nome","Tipo","Componenti"),
                         squadre.stream().map(s -> s.codiceSquadra).toList(),
                         squadre.stream().map(s -> s.nome).toList(),
                         squadre.stream().map(s -> s.tipo.toString()).toList(),
                         squadre.stream().map(s -> givePartecipant(s)).toList());
    }

    private List<JButton> createTeamButtons(int tournamentCode) {
        JButton search = new JButton("Cerca");
        search.addActionListener(l -> {
            if (getSearchField().isBlank()) {
                updateTeams(getMenu().getController().allTeamsInTournament(tournamentCode));
            } else {
                int code;
                try {
                    code =  Integer.parseInt(getSearchField());
                    Squadra squadra = getMenu().getController().findTeam(code);
                    updateTeams( squadra == null
                        ? List.of()
                        : List.of(squadra));
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Il codice squadra deve essere un numero intero");
                }
            }
        });
        JButton removeFromTournament = new JButton("Rimuovi dal torneo");
        removeFromTournament.addActionListener(l -> {
            getMenu().getController().removeTeamFromTournament(tournamentCode, Integer.parseInt(getSearchedOptionOutput("Codice Squadra")));
            updateTeams(getMenu().getController().allTeamsInTournament(tournamentCode));
        });
        JButton turnBack = new JButton("Torna ai tornei");
        turnBack.addActionListener(l -> {
            this.removeAll();
            setUp(getMenu().getController().getAllEnterableTournaments(), "Tutti i tornei");
        });
        return List.of(search, removeFromTournament, turnBack);
    }

    private String givePartecipant(Squadra squadra) {
        String text = "";
        text = text.concat(squadra.componente1.cf + ", ");
        if (!squadra.tipo.equals(TipoSquadra.TENNIS_SINGOLO)) {
            text = text.concat(squadra.componente2.cf);
            if (squadra.tipo.equals(TipoSquadra.CALCETTO)) {
                text = text.concat(", " + squadra.componente3.cf + ", ");
                text = text.concat(squadra.componente4.cf + ", ");
                text = text.concat(squadra.componente5.cf);
            }
        }
        return text;
    }
}
