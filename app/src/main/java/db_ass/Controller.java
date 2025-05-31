package db_ass;

import java.util.List;
import java.util.Objects;

import db_ass.data.Campo;
import db_ass.data.Giorno;
import db_ass.data.LezionePrivata;
import db_ass.data.Persona;
import db_ass.data.Sport;
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

    public List<Campo> findSpaceForNewLesson(Sport sport, String orarioInizio, Giorno giorno, String data) {
        return this.model.findSpaceForNewLesson(sport, orarioInizio, giorno, data);
    }
}
