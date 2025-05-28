package db_ass.data;

import java.util.List;
import java.util.Objects;

public final class Partecipa {
    
    public Corso corso;
    public Persona persona;

    public Partecipa(Corso corso, Persona persona) {
        this.corso = corso;
        this.persona = persona;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (other == null) {
            return false;
        } else if (other instanceof Partecipa) {
            var p = (Partecipa)other;
            return (
                p.corso.equals(this.corso) &&
                p.persona.equals(this.persona)
            );
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.corso, this.persona);
    }

    @Override
    public String toString() {
        return Printer.stringify(
            "Partecipa", 
            List.of(
                Printer.field("Corso", this.corso),
                Printer.field("Persona", this.persona)
            )
        );
    }

    public static final class DAO {
        //da fare
    }
}
