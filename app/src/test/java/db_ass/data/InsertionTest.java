package db_ass.data;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class InsertionTest {
    
    private static Connection connection;
    private static Savepoint savepoint;

    @BeforeClass
    public static void setup() throws SQLException {
        connection = DAOUtils.localMySQLConnection("associazionesportiva", "root", "");
        connection.setAutoCommit(false);
        //savepoint = connection.setSavepoint();
    }

    /* @AfterClass
    public static void cleanup() throws SQLException {
        if (connection != null) {
            if (savepoint != null) {
                connection.rollback(savepoint);
            }
            connection.close();
        }
    } */ 

    @Test
    public void insertUser() throws SQLException{
        try (var statement = connection.createStatement();
            ) {
            int counter = 0;
            Persona.DAO.addUser(new Persona("CCCCCCCCCCCCCCCC", "awpfi", "plinky", "qualcosa", "ciao", true, false, false, 0), connection);
            var resultSet = statement.executeQuery("Select CF from persona");
            while (resultSet.next()) {
                counter = resultSet.getString(1).equals("CCCCCCCCCCCCCCCC") ? counter + 1 : counter;
                System.out.println(Persona.DAO.findPerson(resultSet.getString("CF"), connection).toString());
            }
            assertEquals(1, counter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection.close();
    }
}
