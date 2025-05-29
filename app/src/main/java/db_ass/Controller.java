package db_ass;

import java.util.Objects;

import db_ass.model.Model;

public final class Controller {
    
    private final Model model;
    private final View view;

    public Controller(Model model, View view) {
        Objects.requireNonNull(model, "Controller created with null model");
        Objects.requireNonNull(view, "Controller created with null view");
        this.view = view;
        this.model = model;
    }
}
