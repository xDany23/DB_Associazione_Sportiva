package db_ass.data;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;

import org.junit.AfterClass;
import org.junit.BeforeClass;

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

}
