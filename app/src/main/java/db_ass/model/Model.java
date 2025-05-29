package db_ass.model;

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

    List<Integer> findFieldToBook(FasciaOraria fascia, String data);

    int bookField(Prenotazione p);

    List<LezionePrivata> findJoinableLesson(String data, String orario, Sport sport);

    int joinLesson(Persona persona, Campo campo, Giorno giorno, String orarioInizio, String data, Sport sport);

    List<Campo> findSpaceForNewLesson(Sport sport, String orarioInizio, Giorno giorno, String data);

    int createNewLesson(Campo numCampo, Giorno giorno, String orarioInizio, String dataSvolgimento, Sport sport, double prezzo, Persona allenatore, Persona persona);

    Corso findActiveCourse(int codiceCorso);

    int joinCourse(Persona persona, int codiceCorso);

    int createNewTeam(String nome, int codiceSquadra, TipoSquadra tipo, Persona p1, Persona p2, Persona p3, Persona p4, Persona p5);

    Torneo isTournamentEnterable(int codiceTorneo, TipoSquadra tipo);

    int enterTournament(int codiceTorneo, int codiceSquadra);

    int createNewTournament(String dataSvolgimento, String nome, String premio, int maxp, double quota, int codiceTorneo, TipoSquadra tipo, Squadra vincitore);

    List<Persona> findAllPartecipants(int codiceTorneo);

    List<FasciaOraria> findAllOccupiedTimesOfField(int numeroCampo);

    List<Corso> findMostActiveCourses();

    List<Persona> findMostRequestedTrainer();

    List<RisultatiTorneo> visualizeAllTournamentMatches(int codiceTorneo);

}
