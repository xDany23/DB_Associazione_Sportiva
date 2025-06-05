package db_ass.data;

import java.util.List;
import java.util.Objects;

public final class RisultatiTorneo {
    
    public final int codicePartita;
    public final int codiceSquadra;
    public final int punteggio;
    public final String nomeSquadra;
    public final String arbitro;
    public final int squadraVincitrice;

    public RisultatiTorneo(int codicePartita, int codiceSquadra, int punteggio, String nomeSquadra, String arbitro, int squadraVincitrice) {
        this.codicePartita = codicePartita;
        this.codiceSquadra = codiceSquadra;
        this.punteggio = punteggio;
        this.nomeSquadra = nomeSquadra;
        this.arbitro = arbitro;
        this.squadraVincitrice = squadraVincitrice == 0 ? null : squadraVincitrice;
    }
    

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (other == null) {
            return false;
        } else if (other instanceof RisultatiTorneo) {
            var r = (RisultatiTorneo)other;
            return (
                r.codicePartita == this.codicePartita &&
                r.codiceSquadra == this.codiceSquadra &&
                r.punteggio == this.punteggio &&
                r.nomeSquadra.equals(this.nomeSquadra)
            );
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.codicePartita, this.codiceSquadra, this.punteggio, this.nomeSquadra);
    }

    @Override
    public String toString() {
        return Printer.stringify(
            "Risultato", 
            List.of(
                Printer.field("Codice Partita", this.codicePartita),
                Printer.field("Codice Squara", this.codiceSquadra),
                Printer.field("Nome Squadra", this.nomeSquadra),
                Printer.field("Punteggio", this.punteggio),
                Printer.field("Arbitro", this.arbitro),
                Printer.field("Squadra vincitrice", this.squadraVincitrice)
            )
        );
    }


}
