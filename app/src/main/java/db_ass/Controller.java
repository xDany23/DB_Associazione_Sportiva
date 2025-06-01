package db_ass;

import java.util.List;
import java.util.Objects;

import db_ass.data.Campo;
import db_ass.data.Corso;
import db_ass.data.Giorno;
import db_ass.data.LezionePrivata;
import db_ass.data.Persona;
import db_ass.data.Sport;
import db_ass.data.Squadra;
import db_ass.data.TipoSquadra;
import db_ass.model.Model;
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

    public int createNewTeam(String nome, int codiceSquadra, TipoSquadra tipo, Persona p1, Persona p2, Persona p3, Persona p4, Persona p5) {
        return this.model.createNewTeam(nome, codiceSquadra, tipo, p1, p2, p3, p4, p5);
    }
}
