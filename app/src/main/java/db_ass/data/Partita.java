package db_ass.data;

import java.sql.Connection;
import java.sql.SQLException;
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
        public static Partita findMatch(int codicePartita, Connection connection) {
            Partita partita = null;
            try (
                var preparedStatement = DAOUtils.prepare(connection, Queries.FIND_MATCH, codicePartita);
                var resultSet = preparedStatement.executeQuery();
            ) {
                if (resultSet.next()) {
                    partita = new Partita(
                        codicePartita,
                        Persona.DAO.findPerson(resultSet.getString("Arbitro"), connection),
                        Torneo.DAO.findTournament(resultSet.getInt("CodiceTorneo"), connection),
                        Squadra.DAO.findTeam(resultSet.getInt("SquadraVincitrice"), connection)
                        );
                }
            } catch (SQLException e) {
                throw new DAOException(e);
            }
            return partita;
        }

        public static int insertNewMatch(int squadra1, int squadra2, int punti1, int punti2, int codiceTorneo, String arbitro, int squadraVincitrice, int codicePartita, Connection connection) {
            int rowsInserted = 0;
            try {
                connection.setAutoCommit(false);
                try (
                    var preparedStatement = DAOUtils.prepare(connection, Queries.INSERT_MATCH, codiceTorneo, codicePartita, arbitro, squadraVincitrice == 0 ? null : squadraVincitrice);
                ) {
                    rowsInserted = preparedStatement.executeUpdate();
                    rowsInserted += Gioca.DAO.insertNewplersInnewMatch(squadra1, codicePartita, punti1, connection);
                    rowsInserted += Gioca.DAO.insertNewplersInnewMatch(squadra2, codicePartita, punti2, connection);
                } catch (SQLException e) {
                    connection.rollback();
                    throw new DAOException(e);
                }
                connection.commit();
                connection.setAutoCommit(true);
            } catch (Exception e) {
                throw new DAOException(e);
            }
            return rowsInserted;
        }
    }
    
}
