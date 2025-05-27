package app.src.main.java.db_ass.data;

import java.util.List;

public class Corso {
    
    public final String dataInizio;
    public final String dataFine;
    public final Sport sportPraticato;
    public final double prezzo;
    public final int codiceCorso;
    public final Persona allenatore;

    public Corso(String dataInizio, String dataFine, Sport sportPraticato, double prezzo, int codiceCorso, Persona allenatore) {
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        this.sportPraticato = sportPraticato;
        this.prezzo = prezzo;
        this.codiceCorso = codiceCorso;
        this.allenatore = allenatore;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((dataInizio == null) ? 0 : dataInizio.hashCode());
        result = prime * result + ((dataFine == null) ? 0 : dataFine.hashCode());
        result = prime * result + ((sportPraticato == null) ? 0 : sportPraticato.hashCode());
        long temp;
        temp = Double.doubleToLongBits(prezzo);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + codiceCorso;
        result = prime * result + ((allenatore == null) ? 0 : allenatore.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Corso other = (Corso) obj;
        if (dataInizio == null) {
            if (other.dataInizio != null)
                return false;
        } else if (!dataInizio.equals(other.dataInizio))
            return false;
        if (dataFine == null) {
            if (other.dataFine != null)
                return false;
        } else if (!dataFine.equals(other.dataFine))
            return false;
        if (sportPraticato != other.sportPraticato)
            return false;
        if (Double.doubleToLongBits(prezzo) != Double.doubleToLongBits(other.prezzo))
            return false;
        if (codiceCorso != other.codiceCorso)
            return false;
        if (allenatore == null) {
            if (other.allenatore != null)
                return false;
        } else if (!allenatore.equals(other.allenatore))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return Printer.stringify("Corso", List.of(
            Printer.field("Data Inizio", this.dataInizio),
            Printer.field("Data Fine", this.dataFine),
            Printer.field("Sport Praticato", this.sportPraticato),
            Printer.field("Prezzo", this.prezzo),
            Printer.field("Codice Corso", this.codiceCorso),
            Printer.field("Allenatore", this.allenatore)
        ));
    }
}
