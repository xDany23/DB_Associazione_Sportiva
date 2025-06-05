package db_ass.model;

import java.sql.Connection;
import java.util.List;

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
import db_ass.utility.Pair;

public interface Model {
    
    int registerUser(Persona persona);

    int newTrainer(Persona persona);

    int newReferee(Persona persona);

    List<Integer> findFieldToBook(String orarioInizio, String data, Sport sport);

    int bookField(Prenotazione p);

    List<LezionePrivata> findJoinableLesson(String data, String orario, Sport sport);

    int joinLesson(Persona persona, int campo, Giorno giorno, String orarioInizio, String data, Sport sport);

    List<Campo> findSpaceForNewLesson(Sport sport, String orarioInizio, Giorno giorno, String data);

    int createNewLesson(int numCampo, Giorno giorno, String orarioInizio, String dataSvolgimento, Sport sport, double prezzo, Persona allenatore, Persona persona);

    Corso findActiveCourse(int codiceCorso);

    int joinCourse(Persona persona, int codiceCorso);

    int createNewTeam(String nome, TipoSquadra tipo, Persona p1, Persona p2, Persona p3, Persona p4, Persona p5);

    List<Torneo> isTournamentEnterable(TipoSquadra tipo);

    int enterTournament(int codiceTorneo, int codiceSquadra);

    int createNewTournament(String dataSvolgimento, String nome, String premio, int maxp, double quota, TipoSquadra tipo);

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

    List<Corso> getAllActiveCourses();

    List<Corso> allCoursesOfUser(Persona persona);

    List<Squadra> allTeamsOfUser(Persona persona);

    Squadra findTeam(int codiceSquadra);

    int demoteUser(Persona persona);

    int demoteTrainer(Persona persona);
    
    int demoteReferee(Persona persona);

    Torneo findTournament(int codiceTorneo);

    FasciaOraria findPeriod(int campo, Giorno giorno, String orarioInizio);

    int promoteToTrainer(Persona persona);

    int promoteToReferee(Persona persona);

    int promoteToUser(Persona persona);

    List<Prenotazione> allReservationsOfUser(Persona persona);

    List<Persona> getAllDisabledUsers();

    int terminateCourse(int codiceCorso);

    int addNewCourse(String DataInizio, String DataFine, Sport Sport, double prezzo, String allenatore);

    Corso findCourse(int codiceCorso);

    int addNewCourseLesson(int numeroCampo, Giorno giorno, String orario, String dataSvolgimento, Sport sport, int codiceCorso);

    List<Integer> getFieldFromType(Sport sport);

    List<LezioneCorso> getAllCourseLessons(int CodiceCorso);

    int deleteCourseLesson(int numeroCampo, Giorno giorno, String orario, String dataSvolgimento, Sport sport);

    List<Pair<Torneo,Integer>> getAllTournaments();

    List<Pair<Torneo,Integer>> getAllEnterableTournaments();

    Pair<Torneo,Integer> findTournamentAndPartecipants(int codiceTorneo);

    int modifyTournamentPrice(int codiceTorneo, double prezzo);

    int modifyTournamentDate(int codiceTorneo, String data);

    int modifyTournamentWinner(int codiceTorneo, int codiceSquadra);

    List<Squadra> allTeamsInTournament(int codiceTorneo);

    List<Torneo> allUserTournaments(Persona persona);

    List<LezionePrivata> allUserLessons(Persona persona);

    static Model fromConnection(Connection connection) {
        return new DBModel(connection);
    }

}
