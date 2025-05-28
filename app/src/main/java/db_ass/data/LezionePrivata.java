package db_ass.data;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LezionePrivata {

    public final Campo numeroCampo;
    public final Giorno giorno;
    public final String orarioInizio;
    public final String dataSvolgimento;
    public final Sport sportPraticato;
    public final double prezzo;
    public final Persona allenatore;
    public final Persona partecipante1;
    public final Persona partecipante2;
    public final Persona partecipante3;


    public LezionePrivata(Campo numeroCampo, Giorno giorno, String orarioInizio, String dataSvolgimento,
            Sport sportPraticato, double prezzo, Persona allenatore, Persona partecipante1, Persona partecipante2, Persona partecipante3) {
        this.numeroCampo = numeroCampo;
        this.giorno = giorno;
        this.orarioInizio = orarioInizio;
        this.dataSvolgimento = dataSvolgimento;
        this.sportPraticato = sportPraticato;
        this.prezzo = prezzo;
        this.allenatore = allenatore;
        this.partecipante1 = partecipante1;
        this.partecipante2 = partecipante2;
        this.partecipante3 = partecipante3;
    }

    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((numeroCampo == null) ? 0 : numeroCampo.hashCode());
        result = prime * result + ((giorno == null) ? 0 : giorno.hashCode());
        result = prime * result + ((orarioInizio == null) ? 0 : orarioInizio.hashCode());
        result = prime * result + ((dataSvolgimento == null) ? 0 : dataSvolgimento.hashCode());
        result = prime * result + ((sportPraticato == null) ? 0 : sportPraticato.hashCode());
        long temp;
        temp = Double.doubleToLongBits(prezzo);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + ((allenatore == null) ? 0 : allenatore.hashCode());
        result = prime * result + ((partecipante1 == null) ? 0 : partecipante1.hashCode());
        result = prime * result + ((partecipante2 == null) ? 0 : partecipante2.hashCode());
        result = prime * result + ((partecipante3 == null) ? 0 : partecipante3.hashCode());
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
        LezionePrivata other = (LezionePrivata) obj;
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
        if (dataSvolgimento == null) {
            if (other.dataSvolgimento != null)
                return false;
        } else if (!dataSvolgimento.equals(other.dataSvolgimento))
            return false;
        if (sportPraticato != other.sportPraticato)
            return false;
        if (Double.doubleToLongBits(prezzo) != Double.doubleToLongBits(other.prezzo))
            return false;
        if (allenatore == null) {
            if (other.allenatore != null)
                return false;
        } else if (!allenatore.equals(other.allenatore))
            return false;
        if (partecipante1 == null) {
            if (other.partecipante1 != null)
                return false;
        } else if (!partecipante1.equals(other.partecipante1))
            return false;
        if (partecipante2 == null) {
            if (other.partecipante2 != null)
                return false;
        } else if (!partecipante2.equals(other.partecipante2))
            return false;
        if (partecipante3 == null) {
            if (other.partecipante3 != null)
                return false;
        } else if (!partecipante3.equals(other.partecipante3))
            return false;
        return true;
    }


    @Override
    public String toString() {
        return Printer.stringify("Lezione Privata", List.of(
            Printer.field("Numero Campo", this.numeroCampo),
            Printer.field("Giorno", this.giorno),
            Printer.field("Orario Inizio", this.orarioInizio),
            Printer.field("Data Svolgimento", this.dataSvolgimento),
            Printer.field("Sport Praticato", this.sportPraticato),
            Printer.field("Prezzo", this.prezzo),
            Printer.field("Allenatore", this.allenatore),
            Printer.field("Partecipante 1", this.partecipante1),
            Printer.field("Partecipante 2", this.partecipante2),
            Printer.field("Partecipante 3", this.partecipante3)
        ));
    }

    public static final class DAO {
        public static List<LezionePrivata> findJoinableLesson(String data, String orario, Sport sport, Connection connection) {
            var preview = new ArrayList<LezionePrivata>();

            try (
                var preparedStatement = DAOUtils.prepare(connection, Queries.FIND_JOINABLE_LESSON, data, orario, sport);
                var resultSet = preparedStatement.executeQuery();
            ) {
               while (resultSet.next()) {
                Campo campo = Campo.DAO.findField(resultSet.getInt("l.NumeroCampo"), connection);
                Giorno giorno = Giorno.valueOf(resultSet.getString("l.Giorno").toUpperCase());
                var orarioInizio = resultSet.getString("l.OrarioInizio");
                var dataSvolgimento = resultSet.getString("l.DataSvolgimento");
                Sport sportPraticato = Sport.valueOf(resultSet.getString("l.SportPraticato").toUpperCase());
                var prezzo = resultSet.getDouble("l.Prezzo");
                var allenatore = Persona.DAO.findPerson(resultSet.getString("l.Allenatore"), connection);
                var partecipante1 = Persona.DAO.findPerson(resultSet.getString("l.Partecipante1"), connection);
                var partecipante2 = Persona.DAO.findPerson(resultSet.getString("l.Partecipante2"), connection);
                var partecipante3 = Persona.DAO.findPerson(resultSet.getString("l.Partecipante3"), connection);
                var lezionePrivata = new LezionePrivata(campo, giorno, orarioInizio, dataSvolgimento, sportPraticato, prezzo, allenatore, partecipante1, partecipante2, partecipante3);
                preview.add(lezionePrivata);
               } 
            } catch (SQLException e) {
                throw new DAOException(e);
            }
            return preview;
        }

        public static int joinLesson(Persona persona, Campo campo, Giorno giorno, String orarioInizio, String data, Sport sport, Connection connection) {
            if (joinLessonAsSecondPartecipant(persona, campo, giorno, orarioInizio, data, sport, connection) == 0) {
                if (joinLessonAsThirdPartecipant(persona, campo, giorno, orarioInizio, data, sport, connection) == 0) {
                    return 0;
                }
            }
            return 1;
        }

        private static int joinLessonAsSecondPartecipant(Persona persona, Campo campo, Giorno giorno, String orarioInizio, String data, Sport sport, Connection connection) {
            int rowsInserted;
            try {
                var preparedStatement = DAOUtils.prepare(connection, Queries.JOIN_LESSON_AS_SECOND_PARTECIPANT, persona, campo, giorno, orarioInizio, data, sport);
                rowsInserted = preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new DAOException(e);
            }
            return rowsInserted;
        }

        private static int joinLessonAsThirdPartecipant(Persona persona, Campo campo, Giorno giorno, String orarioInizio, String data, Sport sport, Connection connection) {
            int rowsInserted;
            try {
                var preparedStatement = DAOUtils.prepare(connection, Queries.JOIN_LESSON_AS_SECOND_PARTECIPANT, persona, campo, giorno, orarioInizio, data, sport);
                rowsInserted = preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new DAOException(e);
            }
            return rowsInserted;
        }

        public static List<Campo> findSpaceForNewLesson(Sport sport, String orarioInizio, Giorno giorno, String data, Connection connection) {
            var preview = new ArrayList<Campo>();

            try (
                var preparedStatement = DAOUtils.prepare(connection, Queries.FIND_SPACE_FOR_NEW_LESSON, sport, orarioInizio, giorno, data);
                var resultSet = preparedStatement.executeQuery();
            ) {
                while (resultSet.next()) {
                    var numCampo = resultSet.getInt("NumeroCampo");
                    Campo campo = new Campo(numCampo, sport);
                    preview.add(campo);
                }
            } catch (SQLException e) {
                throw new DAOException(e);
            }
            return preview;
        }


    }
}
