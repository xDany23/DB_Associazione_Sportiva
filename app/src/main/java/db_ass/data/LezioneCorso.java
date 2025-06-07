package db_ass.data;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class LezioneCorso {
    
    public final Campo numeroCampo;
    public final Giorno giorno;
    public final String orarioInizio;
    public final String dataSvolgimento;
    public final Sport sportPraticato;
    public final Corso codiceCorso;

    public LezioneCorso(Campo numeroCampo, Giorno giorno, String orarioInizio, String dataSvolgimento, Sport sportPraticato, Corso codiceCorso) {
        this.numeroCampo = numeroCampo;
        this.giorno = giorno;
        this.orarioInizio = orarioInizio;
        this.dataSvolgimento = dataSvolgimento;
        this.sportPraticato = sportPraticato;
        this.codiceCorso = codiceCorso;
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
        result = prime * result + ((codiceCorso == null) ? 0 : codiceCorso.hashCode());
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
        LezioneCorso other = (LezioneCorso) obj;
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
        if (codiceCorso == null) {
            if (other.codiceCorso != null)
                return false;
        } else if (!codiceCorso.equals(other.codiceCorso))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return Printer.stringify("Lezione Corso", List.of(
            Printer.field("Numero Campo", this.numeroCampo),
            Printer.field("Giorno", this.giorno),
            Printer.field("Orario Inizio", this.orarioInizio),
            Printer.field("Data Svolgimento", this.dataSvolgimento),
            Printer.field("Sport Praticato", this.sportPraticato),
            Printer.field("Codice Corso", this.codiceCorso)
        ));
    }

    public static class DAO {
    
        public static int insertLesson(int numeroCampo, Giorno giorno, String orario, String dataSvolgimento, Sport sport, int codiceCorso, Connection connection) {
            int rowsInserted = 0;
            try (
                var preparedStatement = DAOUtils.prepare(connection, Queries.ADD_LESSON_TO_COURSE, numeroCampo, giorno.toString(), orario, dataSvolgimento, sport.toString(), codiceCorso);
            ) {
                rowsInserted = preparedStatement.executeUpdate();
            } catch (Exception e) {
                throw new DAOException(e);
            }
            return rowsInserted;
        }

        public static List<LezioneCorso> getAllCourseLessons(int CodiceCorso,Connection connection) {
            List<LezioneCorso> lezioni =  new LinkedList<>();
            try (
                var preparedStatement = DAOUtils.prepare(connection, Queries.GET_ALL_COURSE_LESSONS, CodiceCorso);
                var resultSet = preparedStatement.executeQuery();
            ) {
                while (resultSet.next()) {
                    lezioni.add(new LezioneCorso(Campo.DAO.findField(resultSet.getInt("NumeroCampo"),connection),
                                                 Giorno.valueOf(resultSet.getString("Giorno").toUpperCase()),
                                                 resultSet.getString("OrarioInizio"),
                                                 resultSet.getString("DataSvolgimento"),
                                                 Sport.valueOf(resultSet.getString("SportPraticato").toUpperCase()),
                                                 Corso.DAO.findCourse(CodiceCorso, connection)));
                }
            } catch (SQLException e) {
                throw new DAOException(e);
            }
            return lezioni;
        }

        public static int deleteCourseLesson(int numeroCampo, Giorno giorno, String orario, String dataSvolgimento, Sport sport, Connection connection) {
            int rowsInserted = 0;
            try (
                var preparedStatement = DAOUtils.prepare(connection, Queries.DELETE_COURSE_LESSON, numeroCampo, orario, giorno.toString(), dataSvolgimento, sport.toString());
            ) {
                rowsInserted = preparedStatement.executeUpdate();
            } catch (Exception e) {
                throw new DAOException(e);
            }
            return rowsInserted;
        }

        public static List<LezioneCorso> allLessonCourseOfTrainer(Persona persona, Connection connection) {
            List<LezioneCorso> lezioni = new ArrayList<>();
            try (
                var preparedStatement = DAOUtils.prepare(connection, Queries.ALL_LESSON_COURSE_OF_TRAINER, persona.cf);
                var resultSet = preparedStatement.executeQuery();
            ) {
                while (resultSet.next()) {
                    lezioni.add(new LezioneCorso(Campo.DAO.findField(resultSet.getInt("NumeroCampo"),connection),
                                                 Giorno.valueOf(resultSet.getString("Giorno").toUpperCase()),
                                                 resultSet.getString("OrarioInizio"),
                                                 resultSet.getString("DataSvolgimento"),
                                                 Sport.valueOf(resultSet.getString("SportPraticato").toUpperCase()),
                                                 Corso.DAO.findCourse(resultSet.getInt("CodiceCorso"), connection)));
                }
            } catch (SQLException e) {
                throw new DAOException(e);
            }
            return lezioni;
        }

        public static List<LezioneCorso> allLessonCourseInCertainDay(Persona persona, String data, Connection connection) {
            List<LezioneCorso> lezioni = new ArrayList<>();
            try (
                var preparedStatement = DAOUtils.prepare(connection, Queries.ALL_LESSON_COURSE_OF_TRAINER, persona.cf, data);
                var resultSet = preparedStatement.executeQuery();
            ) {
                while (resultSet.next()) {
                    lezioni.add(new LezioneCorso(Campo.DAO.findField(resultSet.getInt("NumeroCampo"),connection),
                                                 Giorno.valueOf(resultSet.getString("Giorno").toUpperCase()),
                                                 resultSet.getString("OrarioInizio"),
                                                 resultSet.getString("DataSvolgimento"),
                                                 Sport.valueOf(resultSet.getString("SportPraticato").toUpperCase()),
                                                 Corso.DAO.findCourse(resultSet.getInt("CodiceCorso"), connection)));
                }
            } catch (SQLException e) {
                throw new DAOException(e);
            }
            return lezioni;
        }
    }
}
