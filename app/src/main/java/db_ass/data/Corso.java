package db_ass.data;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
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

    public static final class DAO {
        public static Corso findActiveCourses(int codiceCorso, Connection connection) {
            Corso corso = null;
            try (
                var preparedStatement = DAOUtils.prepare(connection, Queries.FIND_ACTIVE_COURSE, codiceCorso);
                var resultSet = preparedStatement.executeQuery();
            ) {
                resultSet.next();
                var dataInizio = resultSet.getString("DataInizio");
                var dataFine = resultSet.getString("DataFine");
                Sport sport = Sport.valueOf(resultSet.getString("SportPraticato").toUpperCase());
                var prezzo = resultSet.getDouble("Prezzo");
                Persona allenatore = Persona.DAO.findPerson(resultSet.getString("Allenatore"), connection);
                corso = new Corso(dataInizio, dataFine, sport, prezzo, codiceCorso, allenatore);
            } catch (SQLException e) {
                throw new DAOException(e);
            }
            return corso;
        }

        public static int joinCourse(Persona persona, int codiceCorso, Connection connection) {
            int rowsInserted;
            try (
                var preparedStatement = DAOUtils.prepare(connection, Queries.JOIN_COURSE, persona.cf, codiceCorso);
            ) {
                rowsInserted = preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new DAOException(e);
            }
            return rowsInserted;
        }

        public static List<Corso> findMostActiveCourses(Connection connection) {
            List<Corso> preview = new ArrayList<>();
            try (
                var preparedStatement = DAOUtils.prepare(connection, Queries.FIND_MOST_ACTIVE_COURSES);
                var resultSet = preparedStatement.executeQuery();
            ) {
                while (resultSet.next()) {
                    var dataInizio = resultSet.getString("DataInizio");
                    var dataFine = resultSet.getString("DataFine");
                    var sport = Sport.valueOf(resultSet.getString("SportPraticato").toUpperCase());
                    var prezzo = resultSet.getDouble("Prezzo");
                    var codiceCorso = resultSet.getInt("CodiceCorso");
                    var allenatore = Persona.DAO.findPerson(resultSet.getString("Allenatore"), connection);
                    var corso = new Corso(dataInizio, dataFine, sport, prezzo, codiceCorso, allenatore);
                    preview.add(corso);
                }
            } catch (SQLException e) {
                throw new DAOException(e);
            }
            return preview;
        }
    }
}
