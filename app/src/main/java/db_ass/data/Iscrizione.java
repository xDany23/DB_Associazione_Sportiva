package db_ass.data;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
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
        public static List<Torneo> allUserTournaments(Persona persona, Connection connection) {
            List<Torneo> preview = new ArrayList<>();
            try (
                var preparedStatement = DAOUtils.prepare(connection, Queries.ALL_USER_TOURNAMENTS, persona.cf, persona.cf, persona.cf, persona.cf, persona.cf);
                var resultSet = preparedStatement.executeQuery();
            ) {
                while(resultSet.next()) {
                    var codice = resultSet.getInt("CodiceTorneo");
                    var data = resultSet.getString("DataSvolgimento");
                    var nome = resultSet.getString("Nome");
                    var premio = resultSet.getString("Premio");
                    var maxp = resultSet.getInt("MassimoPartecipanti");
                    var quota = resultSet.getDouble("QuotaIscrizione");
                    var tipo = TipoSquadra.valueOf(resultSet.getString("Tipo").toUpperCase());
                    var vincitore = Squadra.DAO.findTeam(resultSet.getInt("SquadraVincitrice"), connection);
                    preview.add(new Torneo(codice, data, nome, premio, maxp, quota, tipo, vincitore));
                }
            } catch (SQLException e) {
                throw new DAOException(e);
            }
            return preview;
        }
    }
}
