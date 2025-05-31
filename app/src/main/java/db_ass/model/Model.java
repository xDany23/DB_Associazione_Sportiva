package db_ass.model;

import java.sql.Connection;
import java.util.List;

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

public interface Model {
    
    int registerUser(Persona persona);

    int newTrainer(Persona persona);

    int newReferee(Persona persona);

    List<Integer> findFieldToBook(FasciaOraria fascia, String data, Sport sport);

    int bookField(Prenotazione p);

    List<LezionePrivata> findJoinableLesson(String data, String orario, Sport sport);

    int joinLesson(Persona persona, int campo, Giorno giorno, String orarioInizio, String data, Sport sport);

    List<Campo> findSpaceForNewLesson(Sport sport, String orarioInizio, Giorno giorno, String data);

    int createNewLesson(int numCampo, Giorno giorno, String orarioInizio, String dataSvolgimento, Sport sport, double prezzo, Persona allenatore, Persona persona);

    Corso findActiveCourse(int codiceCorso);

    int joinCourse(Persona persona, int codiceCorso);

    int createNewTeam(String nome, int codiceSquadra, TipoSquadra tipo, Persona p1, Persona p2, Persona p3, Persona p4, Persona p5);

    List<Torneo> isTournamentEnterable(TipoSquadra tipo);

    int enterTournament(int codiceTorneo, int codiceSquadra);

    int createNewTournament(String dataSvolgimento, String nome, String premio, int maxp, double quota, int codiceTorneo, TipoSquadra tipo, Squadra vincitore);

    List<Persona> findAllPartecipants(int codiceTorneo);

    List<FasciaOraria> findAllOccupiedTimesOfField(int numeroCampo, String date);

    List<Corso> findMostActiveCourses();

    List<Persona> findMostRequestedTrainer();

    List<RisultatiTorneo> visualizeAllTournamentMatches(int codiceTorneo);

    Persona findPersona(String cf);

    Persona findFreeTrainer(String data, String ora);

    List<Persona> getAllUsers();

    List<Persona> getAllTrainers();

    List<Persona> getAllReferees();

    static Model fromConnection(Connection connection) {
        return new DBModel(connection);
    }

}
