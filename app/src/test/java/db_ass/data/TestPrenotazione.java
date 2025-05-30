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

public class TestPrenotazione {
    
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
    public void tryFindingAndBookingField() {
        List<Integer> real = List.of(7);
        assertEquals(real, Prenotazione.DAO.findFieldToBook("09:00:00","2025-02-03", Sport.PADEL, connection));
        Prenotazione.DAO.bookField(new Prenotazione("2025-02-03","2020-12-01" , Persona.DAO.findPerson("AAAAAAAAAAAAAAAA", connection), FasciaOraria.DAO.findPeriod(Campo.DAO.findField(7, connection), Giorno.LUNEDI, "09:00:00", connection)), connection);
        assertNotEquals(real, Prenotazione.DAO.findFieldToBook( "09:00:00","2025-02-03", Sport.PADEL, connection));
    }
}
