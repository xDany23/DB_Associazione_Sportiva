package db_ass;

import java.util.List;
import java.util.Objects;

import db_ass.data.Campo;
import db_ass.data.Corso;
import db_ass.data.FasciaOraria;
import db_ass.data.Giorno;
import db_ass.data.LezioneCorso;
import db_ass.data.LezionePrivata;
import db_ass.data.Persona;
import db_ass.data.Prenotazione;
import db_ass.data.RisultatiTorneo;
import db_ass.data.Sport;
import db_ass.data.Squadra;
import db_ass.data.TipoSquadra;
import db_ass.data.Torneo;
import db_ass.model.Model;
import db_ass.utility.Pair;
import db_ass.view.Menu;

public final class Controller {
    
    private final Model model;
    private final Menu view;

    public Controller(Model model, Menu view) {
        Objects.requireNonNull(model, "Controller created with null model");
        Objects.requireNonNull(view, "Controller created with null view");
        this.view = view;
        this.model = model;
    }

    public void addUser(Persona persona) {
        this.model.registerUser(persona);
    }

    public Persona findPersona(String cf) {
        return this.model.findPersona(cf);
    }

    public List<LezionePrivata> findjoinableLesson(String data, String orario, Sport sport) {
        return this.model.findJoinableLesson(data, orario, sport);
    }

    public int joinLesson(Persona persona, int campo, Giorno giorno, String orarioInizio, String data, Sport sport) {
        return this.model.joinLesson(persona, campo, giorno, orarioInizio, data, sport);
    }

    public List<Persona> getAllUsers() {
        return this.model.getAllUsers();
    }

    public List<Persona> getAllTrainers() {
        return this.model.getAllTrainers();
    }

    public List<Persona> getAllReferees() {
        return this.model.getAllReferees();
    }

    public List<Campo> findSpaceForNewLesson(Sport sport, String orarioInizio, Giorno giorno, String data) {
        return this.model.findSpaceForNewLesson(sport, orarioInizio, giorno, data);
    }

    public Persona findFreeTrainer(String data, String ora) {
        return this.model.findFreeTrainer(data, ora);
    }

    public int createNewLesson(int numCampo, Giorno giorno, String orarioInizio, String dataSvolgimento, Sport sport, double prezzo, Persona persona) {
        return this.model.createNewLesson(numCampo, giorno, orarioInizio, dataSvolgimento, sport, prezzo, this.findFreeTrainer(dataSvolgimento, orarioInizio), persona);
    }

    public Corso findActiveCourse(int codiceCorso) {
        return this.model.findActiveCourse(codiceCorso);
    }

    public int joinCourse(Persona persona, int codiceCorso) {
        return this.model.joinCourse(persona, codiceCorso);
    }

    public List<Corso> findMostActiveCourses() {
        return this.model.findMostActiveCourses();
    }

    public List<Corso> getAllActiveCourses() {
        return this.model.getAllActiveCourses();
    }

    public List<Corso> allCoursesOfUser(Persona persona) {
        return this.model.allCoursesOfUser(persona);
    }

    public List<Squadra> allTeamsOfUser(Persona persona) {
        return this.model.allTeamsOfUser(persona);
    }

    public Squadra findTeam(int codiceSquadra) {
        return this.model.findTeam(codiceSquadra);
    }

    public int createNewTeam(String nome, TipoSquadra tipo, Persona p1, Persona p2, Persona p3, Persona p4, Persona p5) {
        return this.model.createNewTeam(nome, tipo, p1, p2, p3, p4, p5);
    }

    public int demoteUser(Persona persona) {
        return this.model.demoteUser(persona);
    }

    public Torneo findTournament(int codiceTorneo) {
        return this.model.findTournament(codiceTorneo);
    }

    public int enterTournament(int codicetorneo, int codiceSquadra) {
        return this.model.enterTournament(codicetorneo, codiceSquadra);
    }

    public List<Torneo> tournamentsEnterable(TipoSquadra tipo) {
        return this.model.isTournamentEnterable(tipo);
    }

    public List<Persona> findAllPartecipants(int codiceTorneo) {
        return this.model.findAllPartecipants(codiceTorneo);
    }

    public List<RisultatiTorneo> visualizeAllTournamentMatches(int codiceTorneo) {
        return this.model.visualizeAllTournamentMatches(codiceTorneo);
    }

    public List<Integer> findFieldToBook(String orarioInizio, String data, Sport sport) {
        return this.model.findFieldToBook(orarioInizio, data, sport);
    }

    public int bookField(Prenotazione p) {
        return this.model.bookField(p);
    }

    public FasciaOraria findPeriod(int campo, Giorno giorno, String orarioInizio) {
        return this.model.findPeriod(campo, giorno, orarioInizio);
    }

    public int promoteToTrainer(String cf) {
        return this.model.promoteToTrainer(this.findPersona(cf));
    }

    public int promoteToReferee(String cf) {
        return this.model.promoteToReferee(this.findPersona(cf));
    }

    public int promoteToUser(String cf) {
        return this.model.promoteToUser(this.findPersona(cf));
    }

    public int demoteTrainer(String cf) {
        return this.model.demoteTrainer(this.findPersona(cf));
    }

    public int demoteReferee(String cf) {
        return this.model.demoteReferee(this.model.findPersona(cf));
    }

    public List<Prenotazione> allReservationsOfUser(Persona persona) {
        return this.model.allReservationsOfUser(persona);
    }

    public int newTrainer(String nome, String cognome, String cf, String email, String password) {
        return this.model.newTrainer(new Persona(cf, nome, cognome, email, password, false, true, false, 0));
    }

    public int newReferee(String nome, String cognome, String cf, String email, String password) {
        return this.model.newReferee(new Persona(cf, nome, cognome, email, password, false, false, true, 0));
    }

    public List<Persona> getAllDisabledUsers() {
        return this.model.getAllDisabledUsers();
    }

    public int terminateCourse(int codiceCorso) {
        return this.model.terminateCourse(codiceCorso);
    }

    public int addNewCourse(String DataInizio, String DataFine, Sport Sport, double prezzo, String allenatore) {
        return this.model.addNewCourse(DataInizio, DataFine, Sport, prezzo, allenatore);
    }

    public Corso findCourse(int codiceCorso) {
        return this.model.findCourse(codiceCorso);
    }

    public int addNewCourseLesson(int numeroCampo, Giorno giorno, String orario, String dataSvolgimento, Sport sport, int codiceCorso) {
        return this.model.addNewCourseLesson(numeroCampo, giorno, orario, dataSvolgimento, sport, codiceCorso);
    }

    public List<Integer> getFieldFromType(Sport sport) {
        return this.model.getFieldFromType(sport);
    }

    public List<LezioneCorso> getAllCourseLessons(int codiceCorso) {
        return this.model.getAllCourseLessons(codiceCorso);
    }

    public int deleteCourseLesson(int numeroCampo, Giorno giorno, String orario, String dataSvolgimento, Sport sport) {
        return this.model.deleteCourseLesson(numeroCampo, giorno, orario, dataSvolgimento, sport);
    }

    public List<Persona> findMostRequestedTrainer() {
        return this.model.findMostRequestedTrainer();
    }

    public List<Pair<Torneo,Integer>> getAllTournaments() {
        return this.model.getAllTournaments();
    }

    public List<Pair<Torneo,Integer>> getAllEnterableTournaments() {
        return this.model.getAllEnterableTournaments();
    }

    public Pair<Torneo,Integer> findTournamentAndPartecipants(int codiceTorneo) {
        return this.model.findTournamentAndPartecipants(codiceTorneo);
    }

    public int modifyTournamentDate(int codiceTorneo, String data) {
        return this.model.modifyTournamentDate(codiceTorneo, data);
    }

    public int modifyTournamentPrice(int codiceTorneo, double prezzo) {
        return this.model.modifyTournamentPrice(codiceTorneo, prezzo);
    }

    public int modifyTournamentWinner(int codiceTorneo, int codiceSquadra) {
        return this.model.modifyTournamentWinner(codiceTorneo, codiceSquadra);
    }

    public int createNewTournament(String dataSvolgimento, String nome, String premio, int maxp, double quota, TipoSquadra tipo) {
        return this.model.createNewTournament(dataSvolgimento, nome, premio, maxp, quota, tipo);
    }

    public List<Squadra> allTeamsInTournament(int codiceTorneo) {
        return this.model.allTeamsInTournament(codiceTorneo);
    }
}
