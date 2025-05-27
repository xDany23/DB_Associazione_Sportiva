package app.src.main.java.db_ass.data;

import java.util.List;
import java.util.Objects;

public final class Prenotazione {
    
    public String dataPartita;
    public String dataPrenotazioneEffettuata;
    public Persona prenotante;
    public FasciaOraria fasciaOraria;

    public Prenotazione(String dataPartita, String dataPrenotazioneEffettuata, Persona prenotante, FasciaOraria fasciaOraria) {
        this.dataPartita = dataPartita;
        this.dataPrenotazioneEffettuata = dataPrenotazioneEffettuata;
        this.prenotante = prenotante;
        this.fasciaOraria = fasciaOraria;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (other == null) {
            return false;
        } else if (other instanceof Prenotazione) {
            var p = (Prenotazione)other;
            return (
                p.dataPartita.equals(this.dataPartita) &&
                p.dataPrenotazioneEffettuata.equals(this.dataPrenotazioneEffettuata) &&
                p.prenotante.equals(this.prenotante) &&
                p.fasciaOraria.equals(this.fasciaOraria)
            );
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.dataPartita, this.dataPrenotazioneEffettuata, this.prenotante, this.fasciaOraria);
    }

    @Override
    public String toString() {
        return Printer.stringify(
            "Prenotazione", 
            List.of(
                Printer.field("Data Partita", this.dataPartita),
                Printer.field("Data Prenotazione Effettuata", this.dataPrenotazioneEffettuata),
                Printer.field("Prenotante", this.prenotante),
                Printer.field("Fascia Oraria", this.fasciaOraria)      
            )
        );
    }

    public static final class DAO {
        //da fare
    }
}
