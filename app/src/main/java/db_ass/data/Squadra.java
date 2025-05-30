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
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Squadra other = (Squadra) obj;
        if (codiceSquadra != other.codiceSquadra)
            return false;
        if (nome == null) {
            if (other.nome != null)
                return false;
        } else if (!nome.equals(other.nome))
            return false;
        if (tipo != other.tipo)
            return false;
        if (componente1 == null) {
            if (other.componente1 != null)
                return false;
        } else if (!componente1.equals(other.componente1))
            return false;
        if (componente2 == null) {
            if (other.componente2 != null)
                return false;
        } else if (!componente2.equals(other.componente2))
            return false;
        if (componente3 == null) {
            if (other.componente3 != null)
                return false;
        } else if (!componente3.equals(other.componente3))
            return false;
        if (componente4 == null) {
            if (other.componente4 != null)
                return false;
        } else if (!componente4.equals(other.componente4))
            return false;
        if (componente5 == null) {
            if (other.componente5 != null)
                return false;
        } else if (!componente5.equals(other.componente5))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + codiceSquadra;
        result = prime * result + ((nome == null) ? 0 : nome.hashCode());
        result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
        result = prime * result + ((componente1 == null) ? 0 : componente1.hashCode());
        result = prime * result + ((componente2 == null) ? 0 : componente2.hashCode());
        result = prime * result + ((componente3 == null) ? 0 : componente3.hashCode());
        result = prime * result + ((componente4 == null) ? 0 : componente4.hashCode());
        result = prime * result + ((componente5 == null) ? 0 : componente5.hashCode());
        return result;
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
            var preparedStatement = DAOUtils.prepare(connection, Queries.CREATE_NEW_TEAM, nome, codiceSquadra, tipo.toString(), p1.cf, null, null, null, null);
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
            var preparedStatement = DAOUtils.prepare(connection, Queries.CREATE_NEW_TEAM, nome, codiceSquadra, tipo.toString(), p1.cf, p2.cf, null, null, null);
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
            var preparedStatement = DAOUtils.prepare(connection, Queries.CREATE_NEW_TEAM, nome, codiceSquadra, tipo.toString(), p1.cf, p2.cf, p3.cf, p4.cf, p5.cf);
        ) {
            rowsInserted = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return rowsInserted;
    }

    public static Squadra findTeam(int codiceSquadra, Connection connection) {
        Squadra squadra;
        try (
            var preparedStatement = DAOUtils.prepare(connection, Queries.FIND_TEAM, codiceSquadra);
            var resultSet = preparedStatement.executeQuery();
        ) {
            resultSet.next();
            var nome = resultSet.getString("Nome");
            var p1 = Persona.DAO.findPerson(resultSet.getString("Componenti1"), connection);
            var tipo = TipoSquadra.valueOf(resultSet.getString("Tipo").toUpperCase());
            if (tipo.equals(TipoSquadra.TENNIS_SINGOLO)) {
                squadra = new Squadra(codiceSquadra, nome, tipo, p1);
            } else if (tipo.equals(TipoSquadra.TENNIS_DOPPIO) || tipo.equals(TipoSquadra.PADEL)) {
                var p2 = Persona.DAO.findPerson(resultSet.getString("Componenti2"), connection);
                squadra = new Squadra(codiceSquadra, nome, tipo, p1, p2);
            } else {
                var p2 = Persona.DAO.findPerson(resultSet.getString("Componenti2"), connection);
                var p3 = Persona.DAO.findPerson(resultSet.getString("Componenti3"), connection);
                var p4 = Persona.DAO.findPerson(resultSet.getString("Componenti4"), connection);
                var p5 = Persona.DAO.findPerson(resultSet.getString("Componenti5"), connection);
                squadra = new Squadra(codiceSquadra, nome, tipo, p1, p2, p3, p4, p5);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return squadra;
    }
}
