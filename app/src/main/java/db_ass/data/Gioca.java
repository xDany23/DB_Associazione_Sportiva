package db_ass.data;

import java.util.List;
import java.util.Objects;

public final class Gioca {
    
    public Squadra squadra;
    public Partita partita;
    public int punteggio;

    public Gioca(Squadra squadra, Partita partita, int punteggio) {
        this.squadra = squadra;
        this.partita = partita;
        this.punteggio = punteggio;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (other == null) {
            return false;
        } else if (other instanceof Gioca) {
            var g = (Gioca)other;
            return (
                g.squadra.equals(this.squadra) &&
                g.partita.equals(this.partita) &&
                g.punteggio == this.punteggio
            );
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.squadra, this.partita, this.punteggio);
    }

    @Override
    public String toString() {
        return Printer.stringify(
            "Gioca", 
            List.of(
                Printer.field("Squadra", this.squadra),
                Printer.field("Partita", this.partita),
                Printer.field("Punteggio", this.punteggio)
            )
        );
    }

    public static final class DAO {
        //da fare
    }
}
