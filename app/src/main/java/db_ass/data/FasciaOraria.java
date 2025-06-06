package db_ass.data;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class FasciaOraria {

    public final Campo numeroCampo;
    public final Giorno giorno;
    public final String orarioInizio;
    public final String orarioFine;
    public final TipoFascia tipo;
    public final double prezzo;

    public FasciaOraria(Campo numeroCampo, Giorno giorno, String orarioInizio, String orarioFine, TipoFascia tipo, double prezzo) {
        this.numeroCampo = numeroCampo;
        this.giorno = giorno;
        this.orarioInizio = orarioInizio;
        this.orarioFine = orarioFine;
        this.tipo = tipo;
        this.prezzo = prezzo;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((numeroCampo == null) ? 0 : numeroCampo.hashCode());
        result = prime * result + ((giorno == null) ? 0 : giorno.hashCode());
        result = prime * result + ((orarioInizio == null) ? 0 : orarioInizio.hashCode());
        result = prime * result + ((orarioFine == null) ? 0 : orarioFine.hashCode());
        result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
        long temp;
        temp = Double.doubleToLongBits(prezzo);
        result = prime * result + (int) (temp ^ (temp >>> 32));
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
        FasciaOraria other = (FasciaOraria) obj;
        if (numeroCampo == null) {
            if (other.numeroCampo != null)
                return false;
        } else if (!numeroCampo.equals(other.numeroCampo))
            return false;
        if (giorno != other.giorno)
            return false;
        if (orarioInizio == null) {
            if (other.orarioInizio != null)
                return false;
        } else if (!orarioInizio.equals(other.orarioInizio))
            return false;
        if (orarioFine == null) {
            if (other.orarioFine != null)
                return false;
        } else if (!orarioFine.equals(other.orarioFine))
            return false;
        if (tipo != other.tipo)
            return false;
        if (Double.doubleToLongBits(prezzo) != Double.doubleToLongBits(other.prezzo))
            return false;
        return true;
    }
    
    @Override
    public String toString() {
        return Printer.stringify("FasciaOraria",List.of(
            Printer.field("Numero Campo", this.numeroCampo),
            Printer.field("Giorno", this.giorno),
            Printer.field("Orario Inizio", this.orarioInizio),
            Printer.field("Orario Fine", this.orarioFine),
            Printer.field("Tipo", this.tipo),
            Printer.field("Prezzo", this.prezzo)
        ));
    }

    public static final class DAO {
        public static FasciaOraria findPeriod(int campo, Giorno giorno, String orarioInizio, Connection connection) {
            FasciaOraria f;
            try (
                var preparedStatement = DAOUtils.prepare(connection, Queries.FIND_TIME, campo, giorno.toString(), orarioInizio);
                var resultSet = preparedStatement.executeQuery();
            ) {
                resultSet.next();
                f = new FasciaOraria(
                                    Campo.DAO.findField(campo, connection),
                                    giorno,
                                    orarioInizio,
                                    resultSet.getString("OrarioFine"),
                                    TipoFascia.valueOf(resultSet.getString("Tipo").toUpperCase()),
                                    resultSet.getDouble("Prezzo")
                                    );
            } catch (SQLException e) {
                throw new DAOException(e);
            }
            return f;
        }

        public static List<FasciaOraria> getAllTimesOfField(int numeroCampo, Connection connection) {
            List<FasciaOraria> fascie = new LinkedList<>();
            try (
                var preparedStatement = DAOUtils.prepare(connection, Queries.GET_ALL_TIMES_OF_FIELD, numeroCampo);
                var resultSet = preparedStatement.executeQuery();
            ) {
                while (resultSet.next()) {
                    fascie.add(findPeriod(numeroCampo,
                                          Giorno.valueOf(resultSet.getString("Giorno").toUpperCase()), 
                                          resultSet.getString("OrarioInizio"), 
                                          connection));
                }
            } catch (SQLException e) {
                throw new DAOException(e);
            }
            return fascie;
        }

        public static int modifyTimePrice(double price, int numeroCampo, Giorno giorno, String orarioInizio, Connection connection) {
            int rowsChanged = 0;
            try (
                var preparedStatement = DAOUtils.prepare(connection, Queries.MODIFY_TIMES_PRICE, price, numeroCampo, giorno.toString(), orarioInizio);
            ) {
                rowsChanged = preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new DAOException(e);
            }
            return rowsChanged;
        }
    }
}
