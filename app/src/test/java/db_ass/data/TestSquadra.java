package db_ass.data;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestSquadra {

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
    public void tryCreateAndFindTeam() {
        List<Persona> persone = List.of(
            new Persona("codicepersona001", "awd", "saf", "d", "null", true, false, false, 0),
            new Persona("codicepersona002", "awd", "saf", "d", "null", true, false, false, 0),
            new Persona("codicepersona003", "awd", "saf", "d", "null", true, false, false, 0),
            new Persona("codicepersona004", "awd", "saf", "d", "null", true, false, false, 0),
            new Persona("codicepersona005", "awd", "saf", "d", "null", true, false, false, 0)
        );

        List<Squadra> squadre = List.of(
            new Squadra(5, "Ciao", TipoSquadra.TENNIS_SINGOLO, persone.get(0)),
            new Squadra(6, "Cosi", TipoSquadra.TENNIS_DOPPIO, persone.get(1), persone.get(2)),
            new Squadra(7, "nawfoi", TipoSquadra.PADEL, persone.get(3), persone.get(4)),
            new Squadra(8, "ahaha", TipoSquadra.CALCETTO, persone.get(0), persone.get(1), persone.get(2), persone.get(3),persone.get(4))
        );

        persone.stream().forEach(p -> Persona.DAO.addUser(p, connection));

        assertEquals(1, Squadra.DAO.createNewTeam("Ciao", TipoSquadra.TENNIS_SINGOLO, persone.get(0), null, null, null, null, connection));
        assertEquals(1, Squadra.DAO.createNewTeam("Cosi", TipoSquadra.TENNIS_DOPPIO, persone.get(1), persone.get(2), null, null, null, connection));
        assertEquals(1, Squadra.DAO.createNewTeam("nawfoi", TipoSquadra.PADEL, persone.get(3), persone.get(4), null, null, null, connection));
        assertEquals(1, Squadra.DAO.createNewTeam("ahaha", TipoSquadra.CALCETTO, persone.get(0), persone.get(1),persone.get(2), persone.get(3), persone.get(4), connection));

        assertEquals(squadre.get(0), Squadra.DAO.findTeam(5, connection));
        assertEquals(squadre.get(1), Squadra.DAO.findTeam(6, connection));
        assertEquals(squadre.get(2), Squadra.DAO.findTeam(7, connection));
        assertEquals(squadre.get(3), Squadra.DAO.findTeam(8, connection));
    }
}
