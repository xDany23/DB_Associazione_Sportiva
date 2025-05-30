package db_ass.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestLezionePrivata {
    
    private static Connection connection;
    private static Savepoint savepoint;

    @BeforeClass
    public static void setup() throws SQLException {
        connection = DAOUtils.localMySQLConnection("associazionesportiva", "root", "");
        connection.setAutoCommit(false);
        savepoint = connection.setSavepoint();
    }

    @AfterClass
    public static void cleanup() throws SQLException {
        if (connection != null) {
            if (savepoint != null) {
                connection.rollback(savepoint);
            }
            connection.close();
        }
    }

    @Test
    public void tryFindJoinableLessonAndJoinLesson() {
        List<LezionePrivata> real = List.of(
            new LezionePrivata(Campo.DAO.findField(1, connection), Giorno.LUNEDI, "16:30:00", "2025-02-03", Sport.CALCETTO, 30.9500,
                Persona.DAO.findPerson("RRRRRRRRRRRRRRRR", connection),Persona.DAO.findPerson("AAAAAAAAAAAAAAAA", connection), null, Persona.DAO.findPerson("EEEEEEEEEEEEEEEE", connection))
        );
        assertEquals(real, LezionePrivata.DAO.findJoinableLesson("2025-02-03", "16:30:00", Sport.CALCETTO, connection));
        assertEquals(1, LezionePrivata.DAO.joinLesson(Persona.DAO.findPerson("DDDDDDDDDDDDDDDD", connection), Campo.DAO.findField(1, connection), Giorno.LUNEDI, "16:30:00", "2025-02-03", Sport.CALCETTO, connection));
        Persona.DAO.addUser(new Persona("CCCCCCCCCCCCCCCC", "awpfi", "plinky", "qualcosa", "ciao", true, false, false, 0), connection);
        assertNotEquals(real, LezionePrivata.DAO.findJoinableLesson("2025-02-03", "16:30:00", Sport.CALCETTO, connection));
        assertEquals(0, LezionePrivata.DAO.joinLesson(Persona.DAO.findPerson("CCCCCCCCCCCCCCCC", connection), Campo.DAO.findField(1, connection), Giorno.LUNEDI, "16:30:00", "2025-02-03", Sport.CALCETTO, connection));
    }

    @Test
    public void tryFindSpaceForNewLessonAndCreateNewLesson() {
        List<Campo> real = List.of(Campo.DAO.findField(5, connection),Campo.DAO.findField(6, connection));
        assertEquals(real, LezionePrivata.DAO.findSpaceForNewLesson(Sport.CALCETTO, "16:30:00", Giorno.LUNEDI, "2025-02-03", connection));
        assertEquals(1, LezionePrivata.DAO.createNewLesson(Campo.DAO.findField(5, connection), Giorno.LUNEDI, "16:30:00", "2025-02-03", Sport.CALCETTO, 10.02,
                        Persona.DAO.findPerson("RRRRRRRRRRRRRRRR", connection), Persona.DAO.findPerson("AAAAAAAAAAAAAAAA", connection), connection));
        Persona.DAO.updateTrainerLesson(Persona.DAO.findPerson("RRRRRRRRRRRRRRRR", connection), connection);
        assertEquals(2, Persona.DAO.findPerson("RRRRRRRRRRRRRRRR", connection).LezioniTenute);
    }
}
