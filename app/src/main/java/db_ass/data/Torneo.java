package app.src.main.java.db_ass.data;

import java.util.List;
import java.util.Objects;

public final class Torneo {

    public final int codiceTorneo;
    public final String dataSvolgimento;
    public final String nome;
    public final String premio;
    public final int massimoPartecipanti;
    public final float quotaIscrizione;
    public final TipoSquadra tipo;
    public final Squadra vincitore;

    public Torneo(int codiceTorneo, 
                  String dataSvolgimento,
                  String nome, 
                  String premio, 
                  int massimoPartecipanti, 
                  float quotaIscrizione, 
                  TipoSquadra tipo, 
                  Squadra vincitore) {
        this.codiceTorneo = codiceTorneo;
        this.dataSvolgimento = dataSvolgimento;
        this.nome = nome;
        this.premio = premio;
        this.massimoPartecipanti = massimoPartecipanti;
        this.quotaIscrizione = quotaIscrizione;
        this.tipo = tipo;
        this.vincitore = vincitore;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (other == null) {
            return false;
        } else if (other instanceof Torneo) {
            var t = (Torneo)other;
            return (
                t.codiceTorneo == this.codiceTorneo &&
                t.dataSvolgimento.equals(this.dataSvolgimento) &&
                t.nome.equals(this.nome) &&
                t.premio.equals(this.premio) &&
                t.massimoPartecipanti == this.massimoPartecipanti &&
                t.quotaIscrizione == this.quotaIscrizione &&
                t.tipo.equals(this.tipo) &&
                t.vincitore.equals(this.vincitore)
            );
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.codiceTorneo,
                            this.dataSvolgimento,
                            this.nome,
                            this.premio,
                            this.massimoPartecipanti,
                            this.quotaIscrizione,
                            this.tipo,
                            this.vincitore
        );           
    }

    @Override
    public String toString() {
        return Printer.stringify(
            "Torneo", 
            List.of(
                Printer.field("Codice Torneo", this.codiceTorneo),
                Printer.field("Data Svolgimento", this.dataSvolgimento),
                Printer.field("Nome", this.nome),
                Printer.field("Premio", this.premio),
                Printer.field("Massimo Partecipanti", this.massimoPartecipanti),
                Printer.field("Quota Iscrizione", this.quotaIscrizione),
                Printer.field("Tipo", this.tipo),
                Printer.field("Vincitore", this.vincitore)
            )
        );
    }

    public static final class DAO {
        //da fare
    }
    
}
