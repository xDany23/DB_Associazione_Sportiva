package db_ass.data;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class Campo {

    public final int numeroCampo;
    public final Sport tipo;

    public Campo(int numeroCampo, Sport tipo) {
        this.numeroCampo = numeroCampo;
        this.tipo = tipo;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (other == null) {
            return false;
        } else if (other instanceof Campo) {
            var c = (Campo)other;
            return (
                c.numeroCampo == this.numeroCampo &&
                c.tipo.equals(this.tipo)
            );
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.numeroCampo, this.tipo);
    }

    @Override
    public String toString() {
        return Printer.stringify(
            "Campo",
            List.of(
                Printer.field("Numero Campo", this.numeroCampo),
                Printer.field("Tipo", this.tipo)
            )
        );
    }

    public static final class DAO {
        public static Campo findField(int num, Connection connection) {
            Campo campo;
            try (
                var preparedStatement = DAOUtils.prepare(connection, Queries.FIND_FIELD, num);
                var resultSet = preparedStatement.executeQuery();
            ) {
                resultSet.next();
                var numCampo = resultSet.getInt("NumeroCampo");
                Sport sport = Sport.valueOf(resultSet.getString("Tipo").toUpperCase());
                campo = new Campo(numCampo, sport);
            } catch (SQLException e) {
                throw new DAOException(e);
            }
            return campo;
        }

        public static List<FasciaOraria> findAllOccupiedTimesOfField(int numeroCampo, Connection connection) {
            List<FasciaOraria> preview = new ArrayList<>();
            var campo = Campo.DAO.findField(numeroCampo, connection);
            try (
                var preparedStatement = DAOUtils.prepare(connection, Queries.FIND_ALL_OCCUPIED_TIMES_OF_FIELD, numeroCampo);
                var resultSet = preparedStatement.executeQuery();
            ) {
                while (resultSet.next()) {
                    var giorno = Giorno.valueOf(resultSet.getString("Giorno").toUpperCase());
                    var orarioInizio = resultSet.getString("OrarioInizio");
                    var orarioFine = resultSet.getString("OrarioFine");
                    var tipo = TipoFascia.valueOf(resultSet.getString("Tipo").toUpperCase());
                    var prezzo = resultSet.getDouble("Prezzo");
                    var fascia = new FasciaOraria(campo, giorno, orarioInizio, orarioFine, tipo, prezzo);
                    preview.add(fascia);
                }
            } catch (SQLException e) {
                throw new DAOException(e);
            }
            return preview;
        }
    }
    
}
