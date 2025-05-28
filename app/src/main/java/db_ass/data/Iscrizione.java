package db_ass.data;

import java.util.List;
import java.util.Objects;

public final class Iscrizione {
    
    public final Squadra squadra;
    public final Torneo torneo;

    public Iscrizione(Squadra squadra, Torneo torneo) {
        this.squadra = squadra;
        this.torneo = torneo;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (other == null) {
            return false;
        } else if (other instanceof Iscrizione) {
            var i = (Iscrizione)other;
            return (
                i.squadra.equals(this.squadra) &&
                i.torneo.equals(this.torneo)
            );
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.squadra, this.torneo);
    }

    @Override
    public String toString() {
        return Printer.stringify(
            "Iscrizione", 
            List.of(
                Printer.field("Squadra", this.squadra),
                Printer.field("Torneo", this.torneo)
            )
        );
    }

    public static final class DAO {
        //da fare
    }
}
