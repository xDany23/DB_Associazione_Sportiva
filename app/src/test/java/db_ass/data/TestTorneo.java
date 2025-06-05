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

public class TestTorneo {

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
    public void tryCheckTournamentEnterTournamentCreateTournament() {
        Torneo torneo = new Torneo(10, "2026-02-03", "Toreno test", "qualcosa", 10, 0.10, TipoSquadra.CALCETTO, null);
        assertEquals(List.of(), Torneo.DAO.isTournementEnterable(TipoSquadra.CALCETTO, connection));
        assertEquals(List.of(Torneo.DAO.findTournament(1, connection)), Torneo.DAO.isTournementEnterable(TipoSquadra.TENNIS_SINGOLO, connection));
        
        assertEquals(1, Torneo.DAO.createTournament(torneo.dataSvolgimento, torneo.nome, torneo.premio, torneo.massimoPartecipanti, torneo.quotaIscrizione, torneo.tipo, connection));
        assertEquals(List.of(torneo), Torneo.DAO.isTournementEnterable(TipoSquadra.CALCETTO, connection));
        assertEquals(1, Torneo.DAO.enterTournament(10, 1, connection));
    }

    @Test
    public void testFindAllPartecipants() {
        List<Persona> persone = new LinkedList<>();
        persone.add(Persona.DAO.findPerson("AAAAAAAAAAAAAAAA", connection));
        assertEquals(persone, Torneo.DAO.findAllPartecipants(1, connection));
        persone.add(Persona.DAO.findPerson("DDDDDDDDDDDDDDDD", connection));
        Torneo.DAO.enterTournament(1, 2, connection);
        assertEquals(persone, Torneo.DAO.findAllPartecipants(1, connection));
    }

    /* @Test
    public void testVisualizeAllTournamentMatches() {
        List<RisultatiTorneo> matches = List.of(
            new RisultatiTorneo(1, 2, 3, "I brutti"),
            new RisultatiTorneo(1, 1, 2, "I belli"),
            new RisultatiTorneo(2, 1, 3, "I belli"),
            new RisultatiTorneo(2, 2, 3, "I brutti")
        );
        assertEquals(matches, Torneo.DAO.visualizeAllTournamentMatches(1, connection));
    } */
}
