package db_ass.data;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public final class Torneo {

    public final int codiceTorneo;
    public final String dataSvolgimento;
    public final String nome;
    public final String premio;
    public final int massimoPartecipanti;
    public final double quotaIscrizione;
    public final TipoSquadra tipo;
    public final Squadra vincitore;

    public Torneo(int codiceTorneo, 
                  String dataSvolgimento,
                  String nome, 
                  String premio, 
                  int massimoPartecipanti, 
                  double quotaIscrizione, 
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
        public static Torneo isTournementEnterable(int codiceTorneo, TipoSquadra tipo, Connection connection) {
            Torneo torneo;
            try (
                var preparedStatement = DAOUtils.prepare(connection, Queries.IS_TOURNAMENT_ENTERABLE, codiceTorneo, tipo);
                var resultSet = preparedStatement.executeQuery();
            ) {
                resultSet.next();
                var dataSvolgimento = resultSet.getString("DataSvolgimento");
                var nome = resultSet.getString("Nome");
                var premio = resultSet.getString("Premio");
                var maxp = resultSet.getInt("MassimoPartecipanti");
                var quota = resultSet.getDouble("QuotaIscrizione");
                var vincitore = Squadra.findTeam(resultSet.getInt("Vincitore"), connection);
                torneo = new Torneo(codiceTorneo, dataSvolgimento, nome, premio, maxp, quota, tipo, vincitore);
            } catch (SQLException e) {
                throw new DAOException(e);
            }
            return torneo;
        }

        public static int enterTournament(int codiceTorneo, int codiceSquadra, Connection connection) {
            int rowsInserted;
            try (
                var preparedStatement = DAOUtils.prepare(connection, Queries.ENTER_TOURNAMENT, codiceTorneo, codiceSquadra);
            ) {
                rowsInserted = preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new DAOException(e);
            }
            return rowsInserted;
        }
    }
    
}
