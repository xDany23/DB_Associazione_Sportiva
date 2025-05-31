package db_ass;

import java.util.Objects;

import db_ass.data.Persona;
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
}
