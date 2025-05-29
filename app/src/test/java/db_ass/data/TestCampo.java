package db_ass.data;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestCampo {
    
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
    public void testFindField() {
        Campo real = new Campo(1, Sport.CALCETTO);
        assertEquals(real, Campo.DAO.findField(1, connection));
    }

    @Test
    public void testFindAllOccupiedTimesOfField() {
        List<FasciaOraria> real = List.of(
            new FasciaOraria(Campo.DAO.findField(1, connection), Giorno.LUNEDI, "09:00:00", "10:30:00", TipoFascia.PRENOTABILE, 75.0000),
            new FasciaOraria(Campo.DAO.findField(1, connection), Giorno.LUNEDI, "12:00:00", "13:30:00", TipoFascia.PRENOTABILE, 75.0000));
        assertEquals(real, Campo.DAO.findAllOccupiedTimesOfField(1,"2025-02-03", connection));
    }
}

/*[FasciaOraria[Numero Campo=Campo[Numero Campo=1, Tipo=Calcetto], Giorno=Lunedi, Orario Inizio='09:00:00', Orario Fine='10:30:00', Tipo=Prenotabile, Prezzo=75.0], FasciaOraria[Numero Campo=Campo[Numero Campo=1, Tipo=Calcetto], Giorno=Lunedi, Orario Inizio='10:30:00', Orario Fine='12:00:00', Tipo=Prenotabile, Prezzo=75.0]]
 *[FasciaOraria[Numero Campo=Campo[Numero Campo=1, Tipo=Calcetto], Giorno=Lunedi, Orario Inizio='09:00:00', Orario Fine='10:30:00', Tipo=Prenotabile, Prezzo=75.0], FasciaOraria[Numero Campo=Campo[Numero Campo=1, Tipo=Calcetto], Giorno=Lunedi, Orario Inizio='12:00:00', Orario Fine='13:30:00', Tipo=Prenotabile, Prezzo=75.0]]
 */