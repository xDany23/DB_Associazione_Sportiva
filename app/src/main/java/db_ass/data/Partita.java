package app.src.main.java.db_ass.data;

import java.util.List;
import java.util.Objects;

public final class Partita {

    public final int codicePartita;
    public final Persona arbitro;
    public final Torneo torneo;
    public final Squadra vincitore;

    public Partita(int codicePartita, Persona arbitro, Torneo torneo, Squadra vincitore) {
        this.codicePartita = codicePartita;
        this.arbitro = arbitro;
        this.torneo = torneo;
        this.vincitore = vincitore;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (other == null) {
            return false;
        } else if (other instanceof Partita) {
            var p = (Partita)other;
            return (
                p.codicePartita == this.codicePartita &&
                p.arbitro.equals(this.arbitro) &&
                p.torneo.equals(this.torneo) &&
                p.vincitore.equals(this.vincitore)
            );
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.codicePartita, this.arbitro, this.torneo, this.vincitore);
    }

    @Override
    public String toString() {
        return Printer.stringify(
            "Partita",
            List.of( 
                Printer.field("Codice Partita", this.codicePartita),
                Printer.field("Arbitro", this.arbitro),
                Printer.field("Torneo", this.torneo),
                Printer.field("Vincitore", this.vincitore)
            )    
        );
    }

    public static final class DAO {
        //da fare
    }
    
}
