package db_ass.model;

import java.sql.Connection;
import java.util.List;
import java.util.Objects;

import db_ass.data.Campo;
import db_ass.data.Corso;
import db_ass.data.FasciaOraria;
import db_ass.data.Giorno;
import db_ass.data.LezionePrivata;
import db_ass.data.Persona;
import db_ass.data.Prenotazione;
import db_ass.data.RisultatiTorneo;
import db_ass.data.Sport;
import db_ass.data.Squadra;
import db_ass.data.TipoSquadra;
import db_ass.data.Torneo;

public class DBModel implements Model{

    private final Connection connection;

    public DBModel(Connection connection) {
        Objects.requireNonNull(connection, "Model created with null connection");
        this.connection = connection;
    }

    @Override
    public int registerUser(Persona persona) {
        return Persona.DAO.addUser(persona, connection);
    }

    @Override
    public int newTrainer(Persona persona) {
        return Persona.DAO.newTrainer(persona, connection);
    }

    @Override
    public int newReferee(Persona persona) {
        return Persona.DAO.newReferee(persona, connection);
    }

    @Override
    public List<Integer> findFieldToBook(FasciaOraria fascia, String data, Sport sport) {
        return Prenotazione.DAO.findFieldToBook(fascia.orarioInizio, data, sport, connection);
    }

    @Override
    public int bookField(Prenotazione p) {
        return Prenotazione.DAO.bookField(p, connection);
    }

    @Override
    public List<LezionePrivata> findJoinableLesson(String data, String orario, Sport sport) {
        return LezionePrivata.DAO.findJoinableLesson(data, orario, sport, connection);
    }

    @Override
    public int joinLesson(Persona persona, int campo, Giorno giorno, String orarioInizio, String data, Sport sport) {
        return LezionePrivata.DAO.joinLesson(persona, Campo.DAO.findField(campo, connection), giorno, orarioInizio, data, sport, connection);
    }

    @Override
    public List<Campo> findSpaceForNewLesson(Sport sport, String orarioInizio, Giorno giorno, String data) {
        return LezionePrivata.DAO.findSpaceForNewLesson(sport, orarioInizio, giorno, data, connection);
    }

    @Override
    public int createNewLesson(int numCampo, Giorno giorno, String orarioInizio, String dataSvolgimento, Sport sport,
            double prezzo, Persona allenatore, Persona persona) {
        return LezionePrivata.DAO.createNewLesson(Campo.DAO.findField(numCampo, connection), giorno, orarioInizio, dataSvolgimento, sport, prezzo, allenatore, persona, connection);            
    }

    @Override
    public Corso findActiveCourse(int codiceCorso) {
        return Corso.DAO.findActiveCourses(codiceCorso, connection);
    }

    @Override
    public int joinCourse(Persona persona, int codiceCorso) {
        return Corso.DAO.joinCourse(persona, codiceCorso, connection);
    }

    @Override
    public int createNewTeam(String nome, int codiceSquadra, TipoSquadra tipo, Persona p1, Persona p2, Persona p3,
            Persona p4, Persona p5) {
        return Squadra.DAO.createNewTeam(nome, codiceSquadra, tipo, p1, p2, p3, p4, p5, connection);      
    }

    @Override
    public List<Torneo> isTournamentEnterable( TipoSquadra tipo) {
        return Torneo.DAO.isTournementEnterable(tipo, connection);
    }

    @Override
    public int enterTournament(int codiceTorneo, int codiceSquadra) {
        return Torneo.DAO.enterTournament(codiceTorneo, codiceSquadra, connection);
    }

    @Override
    public int createNewTournament(String dataSvolgimento, String nome, String premio, int maxp, double quota,
            int codiceTorneo, TipoSquadra tipo, Squadra vincitore) {
        return Torneo.DAO.createTournament(dataSvolgimento, nome, premio, maxp, quota, codiceTorneo, tipo, vincitore, connection);
    }

    @Override
    public List<Persona> findAllPartecipants(int codiceTorneo) {
        return Torneo.DAO.findAllPartecipants(codiceTorneo, connection);
    }

    @Override
    public List<FasciaOraria> findAllOccupiedTimesOfField(int numeroCampo, String date) {
        return Campo.DAO.findAllOccupiedTimesOfField(numeroCampo, date, connection);
    }

    @Override
    public List<Corso> findMostActiveCourses() {
        return Corso.DAO.findMostActiveCourses(connection);
    }

    @Override
    public List<Persona> findMostRequestedTrainer() {
        return Persona.DAO.findMostRequestedTrainer(connection);
    }

    @Override
    public List<RisultatiTorneo> visualizeAllTournamentMatches(int codiceTorneo) {
        return Torneo.DAO.visualizeAllTournamentMatches(codiceTorneo, connection);
    }

    @Override
    public Persona findPersona(String cf) {
        return Persona.DAO.findPerson(cf, connection);
    }

    @Override
    public Persona findFreeTrainer(String data, String ora) {
        return Persona.DAO.findFreeTrainer(data, ora, connection);
    }
    

    @Override
    public List<Persona> getAllUsers() {
        return Persona.DAO.getAllUsers(connection);
    }

    @Override
    public List<Persona> getAllTrainers() {
        return Persona.DAO.getAllTrainers(connection);
    }

    @Override
    public List<Persona> getAllReferees() {
        return Persona.DAO.getAllReferees(connection);
    }
    
}
