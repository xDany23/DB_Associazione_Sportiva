package db_ass.data;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestPersona {
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
    public void tryFindPersona() {
        Persona real = new Persona("AAAAAAAAAAAAAAAA","Alessandro","Ravaioli","qualcosa@gmail.com","pipipupu",true,false,false,0);
        assertEquals(real, Persona.DAO.findPerson("AAAAAAAAAAAAAAAA", connection));
    }
}
