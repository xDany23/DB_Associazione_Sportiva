package db_ass.data;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public final class Persona {

    public final String cf;
    public final String nome;
    public final String cognome;
    public final String email;
    public final String password;
    public final boolean utente;
    public final boolean allenatore;
    public final boolean arbitro;

    public Persona(String cf, String nome, String cognome, String email, String password, boolean utente, boolean allenatore, boolean arbitro) {
        this.cf = cf;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email == null ? "" : email;
        this.password = password;
        this.utente = utente;
        this.allenatore = allenatore;
        this.arbitro = arbitro;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (other == null) {
            return false;
        } else if (other instanceof Persona) {
            var p = (Persona)other;
            return (
                p.cf.equals(this.cf) &&
                p.nome.equals(this.nome) &&
                p.cognome.equals(this.cognome) &&
                p.email.equals(this.email) &&
                p.password.equals(this.password) &&
                p.utente == this.utente &&
                p.allenatore == this.allenatore &&
                p.arbitro == this.arbitro
            );
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.cf, this.nome, this.cognome, this.email, this.password, this.utente, this.allenatore, this.arbitro);
    }

    @Override
    public String toString() {
        return Printer.stringify(
            "Persona",
            List.of(
                Printer.field("CF", this.cf),
                Printer.field("Nome", this.nome),
                Printer.field("Cognome", this.cognome),
                Printer.field("E-mail", this.email),
                Printer.field("Password", this.password),
                Printer.field("Utente", this.utente),
                Printer.field("Allenatore", this.allenatore),
                Printer.field("Arbitro", this.arbitro)
            )
        );
    }

    public static final class DAO {
        public static int addUser(Persona p, Connection connection) {
            int rowsInserted;
            try {
                var preparedStatement = DAOUtils.prepare(connection, Queries.REGISTER_USER, p.nome, p.cognome, p.email, p.password, p.cf, true);
                rowsInserted = preparedStatement.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
                return 0;
            }
            return rowsInserted;
        }
    }

    
}
