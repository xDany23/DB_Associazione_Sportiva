package db_ass.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.LinkedList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestCorso {

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
    public void tryFindActiveCourses() {
        Corso real = new Corso("2024-05-06", "2025-06-05", Sport.CALCETTO, 200.2500, 1, Persona.DAO.findPerson("RRRRRRRRRRRRRRRR", connection));
        assertEquals(real, Corso.DAO.findActiveCourses(1, connection));
    }

    @Test
    public void tryJoinCourse() {
        int before = -1;
        int after = -1;
        try (
            var stm = connection.createStatement();
            var resultSet = stm.executeQuery("select count(*) as numPartecipanti from partecipa where CodiceCorso = 1;");
        ) {
            resultSet.next();
            Persona newPer = new Persona("CCCCCCCCCCCCCCCC", "awpfi", "plinky", "qualcosa", "ciao", true, false, false, 0);
            Persona.DAO.addUser(newPer, connection);
            assertEquals(1, Corso.DAO.joinCourse(newPer, 1, connection));
            before = resultSet.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try (
            var stm = connection.createStatement();
            var resultSet = stm.executeQuery("select count(*) as numPartecipanti from partecipa where CodiceCorso = 1;");
        ) {
            resultSet.next();
            after = resultSet.getInt(1);
            assertEquals(before+1,after);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void tryFindMostActiveCourses() {
        List<Corso> real = new LinkedList<>();
        real.addAll(List.of(
            new Corso("2024-05-06","2025-06-05",Sport.CALCETTO,200.25,1,Persona.DAO.findPerson("RRRRRRRRRRRRRRRR", connection)),
	        new Corso("2024-05-06","2025-06-05",Sport.CALCETTO,321.2,2,Persona.DAO.findPerson("RRRRRRRRRRRRRRRR", connection))));
        assertEquals(real, Corso.DAO.findMostActiveCourses(connection));
        //real.add(new Corso("2024-05-06","2024-06-05",Sport.CALCETTO,321.2,3,Persona.DAO.findPerson("RRRRRRRRRRRRRRRR", connection)));
        assertNotEquals(real.reversed(), Corso.DAO.findMostActiveCourses(connection));
    }
}
