package db_ass.data;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;

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
}
