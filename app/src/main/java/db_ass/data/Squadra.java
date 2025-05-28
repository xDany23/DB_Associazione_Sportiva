package db_ass.data;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public final class Squadra {

    public final int codiceSquadra;
    public final String nome;
    public final TipoSquadra tipo;
    public final Persona componente1;
    public final Persona componente2;
    public final Persona componente3;
    public final Persona componente4;
    public final Persona componente5;

    public Squadra(int codiceSquadra, String nome, TipoSquadra tipo, Persona componente1) {
        this.codiceSquadra = codiceSquadra;
        this.nome = nome;
        this.tipo = tipo;
        this.componente1 = componente1;
        this.componente2 = null;
        this.componente3 = null;
        this.componente4 = null;
        this.componente5 = null;
    }

    public Squadra(int codiceSquadra, String nome, TipoSquadra tipo, Persona componente1, Persona componente2) {
        this.codiceSquadra = codiceSquadra;
        this.nome = nome;
        this.tipo = tipo;
        this.componente1 = componente1;
        this.componente2 = componente2;
        this.componente3 = null;
        this.componente4 = null;
        this.componente5 = null;
    }

    public Squadra(int codiceSquadra, 
                   String nome, 
                   TipoSquadra tipo, 
                   Persona componente1, 
                   Persona componente2, 
                   Persona componente3, 
                   Persona componente4, 
                   Persona componente5) {
        this.codiceSquadra = codiceSquadra;
        this.nome = nome;
        this.tipo = tipo;
        this.componente1 = componente1;
        this.componente2 = componente2;
        this.componente3 = componente3;
        this.componente4 = componente4;
        this.componente5 = componente5;
    }
    
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (other == null) {
            return false;
        } else if (other instanceof Squadra) {
            var s = (Squadra)other;
            return (
                s.codiceSquadra == this.codiceSquadra &&
                s.nome.equals(this.nome) &&
                s.tipo.equals(this.tipo) &&
                s.componente1.equals(this.componente1) &&
                s.componente2.equals(this.componente2) &&
                s.componente3.equals(this.componente3) &&
                s.componente4.equals(this.componente4) &&
                s.componente5.equals(this.componente5)
            );
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.codiceSquadra, 
                            this.nome, 
                            this.tipo, 
                            this.componente1, 
                            this.componente2, 
                            this.componente3, 
                            this.componente4, 
                            this.componente5
        );   
    }

    @Override
    public String toString() {
        return (tipo.equals(TipoSquadra.CALCETTO)) 
        ? (Printer.stringify(
            "Squadra",
            List.of(
                Printer.field("Codice", this.codiceSquadra),
                Printer.field("Nome", this.nome),
                Printer.field("Tipo", this.tipo),
                Printer.field("Componente1", this.componente1),
                Printer.field("Componente2", this.componente2),
                Printer.field("Componente3", this.componente3),
                Printer.field("Componente4", this.componente4),
                Printer.field("Componente5", this.componente5)
            )
        )) : (tipo.equals(TipoSquadra.TENNIS_DOPPIO) || tipo.equals(TipoSquadra.PADEL))
        ? (Printer.stringify(
            "Squadra",
            List.of(
                Printer.field("Codice", this.codiceSquadra),
                Printer.field("Nome", this.nome),
                Printer.field("Tipo", this.tipo),
                Printer.field("Componente1", this.componente1),
                Printer.field("Componente2", this.componente2)
            )
        )) : (Printer.stringify(
            "Squadra",
            List.of(
                Printer.field("Codice", this.codiceSquadra),
                Printer.field("Nome", this.nome),
                Printer.field("Tipo", this.tipo),
                Printer.field("Componente1", this.componente1)
            )
        ));
    }

    public static final class DAO {
        public static int createNewTeam(String nome, int codiceSquadra, TipoSquadra tipo, Persona p1, Persona p2, Persona p3, Persona p4, Persona p5, Connection connection) {
            if (tipo.equals(TipoSquadra.TENNIS_SINGOLO)) {
                return createNewSingleTennisTeam(nome, codiceSquadra, tipo, p1, connection);
            } else if (tipo.equals(TipoSquadra.TENNIS_DOPPIO) || tipo.equals(TipoSquadra.PADEL)) {
                return createNewDoubleTennisTeam(nome, codiceSquadra, tipo, p1, p2, connection);
            } else if (tipo.equals(TipoSquadra.CALCETTO)) {
                return createNewSoccerTeam(nome, codiceSquadra, tipo, p1, p2, p3, p4, p5, connection);
            } else {
                return 0;
            }
        }
    }

    private static int createNewSingleTennisTeam(String nome, int codiceSquadra, TipoSquadra tipo, Persona p1, Connection connection) {
        int rowsInserted;
        try (
            var preparedStatement = DAOUtils.prepare(connection, Queries.CREATE_NEW_TEAM, nome, codiceSquadra, tipo, p1);
        ) {
            rowsInserted = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return rowsInserted;
    }

    private static int createNewDoubleTennisTeam(String nome, int codiceSquadra, TipoSquadra tipo, Persona p1, Persona p2, Connection connection) {
        int rowsInserted;
        try (
            var preparedStatement = DAOUtils.prepare(connection, Queries.CREATE_NEW_TEAM, nome, codiceSquadra, tipo, p1, p2);
        ) {
            rowsInserted = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return rowsInserted;
    }

    private static int createNewSoccerTeam(String nome, int codiceSquadra, TipoSquadra tipo, Persona p1, Persona p2,Persona p3, Persona p4, Persona p5, Connection connection) {
        int rowsInserted;
        try (
            var preparedStatement = DAOUtils.prepare(connection, Queries.CREATE_NEW_TEAM, nome, codiceSquadra, tipo, p1, p2, p3, p4, p5);
        ) {
            rowsInserted = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return rowsInserted;
    }
}
