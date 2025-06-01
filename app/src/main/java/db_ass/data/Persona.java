package db_ass.data;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Collections;

public final class Persona {

    public final String cf;
    public final String nome;
    public final String cognome;
    public final String email;
    public final String password;
    public final boolean utente;
    public final boolean allenatore;
    public final boolean arbitro;
    public final int LezioniTenute;
    public final boolean admin;

    public Persona(String cf, String nome, String cognome, String email, String password, boolean utente, boolean allenatore, boolean arbitro, int LezioniTenute) {
        this(cf, nome, cognome, email, password, utente, allenatore, arbitro, LezioniTenute, false);
    }

    public Persona(String cf, String nome, String cognome, String email, String password, boolean utente, boolean allenatore, boolean arbitro, int LezioniTenute, boolean admin) {
        this.cf = cf;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email == null ? "" : email;
        this.password = password;
        this.utente = utente;
        this.allenatore = allenatore;
        this.arbitro = arbitro;
        this.LezioniTenute = LezioniTenute;
        this.admin = admin;
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
                p.arbitro == this.arbitro &&
                p.LezioniTenute == this.LezioniTenute
            );
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.cf, this.nome, this.cognome, this.email, this.password, this.utente, this.allenatore, this.arbitro, this.LezioniTenute);
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
                Printer.field("Arbitro", this.arbitro),
                (this.allenatore == true) ? Printer.field("Lezioni Tenute", this.LezioniTenute) : Printer.field("", "")
            )
        );
    }

    public static final class DAO {
        public static int addUser(Persona p, Connection connection) {
            int rowsInserted = 0;
            try (
                var preparedStatement = DAOUtils.prepare(connection, Queries.REGISTER_USER, p.nome, p.cognome, p.email, p.password, p.cf, true);
            ) {
                rowsInserted = preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                return 0;
            }
            return rowsInserted;
        }

        public static int newTrainer(Persona p, Connection connection) {
            int rowsInserted = 0;
            try (
                var preparedStatement = DAOUtils.prepare(connection, Queries.REGISTER_USER, p.nome, p.cognome, p.email, p.password, p.cf, true, p.LezioniTenute);
            ) {
                rowsInserted = preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                return 0;
            }
            return rowsInserted;
        }

        public static int newReferee(Persona p, Connection connection) {
            int rowsInserted = 0;
            try (
                var preparedStatement = DAOUtils.prepare(connection, Queries.REGISTER_USER, p.nome, p.cognome, p.email, p.password, p.cf, true);
            ) {
                rowsInserted = preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                return 0;
            }
            return rowsInserted;
        }

        public static Persona findPerson(String cf, Connection connection) {
            Persona persona = null;
            if (cf == null) {
                return null;
            }
            try (
                var preparedStatement = DAOUtils.prepare(connection, Queries.FIND_USER, cf);
                var resultSet = preparedStatement.executeQuery();
            ) {
                if (resultSet.next()) {
                    var Cf = resultSet.getString("CF");
                    var nome = resultSet.getString("Nome");
                    var cognome = resultSet.getString("Cognome");
                    var email = resultSet.getString("E_mail");
                    var password = resultSet.getString("Password");
                    var utente = resultSet.getBoolean("Utente");
                    var allenatore = resultSet.getBoolean("Allenatore");
                    var arbitro = resultSet.getBoolean("Arbitro");
                    var numTenute = resultSet.getInt("LezioniTenute");
                    var admin = resultSet.getBoolean("Admin");
                    persona = new Persona(Cf, nome, cognome, email, password, utente, allenatore, arbitro, numTenute, admin);
                }
            } catch (SQLException e) {
                throw new DAOException(e);
            }
            return persona;
        }

        public static int updateTrainerLesson(Persona persona, Connection connection) {
            int rowsInserted;
            try (
                var preparedStatement = DAOUtils.prepare(connection, Queries.UPDATE_TRAINER_LESSONS, persona.cf);
            ) {
                rowsInserted = preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new DAOException(e);
            }
            return rowsInserted;
        }

        public static List<Persona> findMostRequestedTrainer(Connection connection) {
            List<Persona> preview = new ArrayList<>();
            try (
                var preparedStatement = DAOUtils.prepare(connection, Queries.FIND_MOST_REQUESTED_TRAINER);
                var resultSet = preparedStatement.executeQuery();
            ) {
                while (resultSet.next()) {
                    var cf = resultSet.getString("CF");
                    var nome = resultSet.getString("Nome");
                    var cognome = resultSet.getString("Cognome");
                    var email = resultSet.getString("E_mail");
                    var pass = resultSet.getString("Password");
                    var utente = resultSet.getBoolean("Utente");
                    var allenatore = resultSet.getBoolean("Allenatore");
                    var arbitro = resultSet.getBoolean("Arbitro");
                    var lezioniTenute = resultSet.getInt("LezioniTenute");
                    var admin = resultSet.getBoolean("Admin");
                    var persona = new Persona(cf, nome, cognome, email, pass, utente, allenatore, arbitro, lezioniTenute, admin);
                    preview.add(persona);
                }
            } catch (SQLException e) {
                throw new DAOException(e);
            }
            return preview;
        }

        public static Persona findFreeTrainer(String data, String orario, Connection connection) {
            List<Persona> preview = new ArrayList<>();
            Persona output = null;
            try (
                var preparedStatement = DAOUtils.prepare(connection, Queries.FIND_FREE_TRAINER, orario, data, orario, data);
                var resultSet = preparedStatement.executeQuery();
            ) {
                while (resultSet.next()) {
                    var cf = resultSet.getString("CF");
                    var nome = resultSet.getString("Nome");
                    var cognome = resultSet.getString("Cognome");
                    var email = resultSet.getString("E_mail");
                    var pass = resultSet.getString("Password");
                    var utente = resultSet.getBoolean("Utente");
                    var allenatore = resultSet.getBoolean("Allenatore");
                    var arbitro = resultSet.getBoolean("Arbitro");
                    var lezioniTenute = resultSet.getInt("LezioniTenute");
                    var admin = resultSet.getBoolean("Admin");
                    var persona = new Persona(cf, nome, cognome, email, pass, utente, allenatore, arbitro, lezioniTenute, admin);
                    preview.add(persona); 
                }
            } catch (SQLException e) {
                throw new DAOException(e);
            }
            Collections.shuffle(preview);
            if (!preview.isEmpty()) {
                output = preview.get(0);
            }
            return output;
        }

        public static List<Persona> getAllUsers(Connection connection) {
            return getAllOfAtype(Queries.GET_ALL_USERS, connection);
        }

        public static List<Persona> getAllTrainers(Connection connection) {
            return getAllOfAtype(Queries.GET_ALL_TRAINERS, connection);
        }

        public static List<Persona> getAllReferees(Connection connection) {
            return getAllOfAtype(Queries.GET_ALL_REFEREES, connection);
        }

        private static List<Persona> getAllOfAtype(String Query,Connection connection) {
            List<Persona> persone = new LinkedList<>();
            try (
                var prepStm = DAOUtils.prepare(connection, Query);
                var resultSet = prepStm.executeQuery();
            ) {
                while (resultSet.next()) {
                    var cf = resultSet.getString("CF");
                    var nome = resultSet.getString("Nome");
                    var cognome = resultSet.getString("Cognome");
                    var email = resultSet.getString("E_mail");
                    var pass = resultSet.getString("Password");
                    var utente = resultSet.getBoolean("Utente");
                    var allenatore = resultSet.getBoolean("Allenatore");
                    var arbitro = resultSet.getBoolean("Arbitro");
                    var lezioniTenute = resultSet.getInt("LezioniTenute");
                    var admin = resultSet.getBoolean("Admin");
                    persone.add(new Persona(cf, nome, cognome, email, pass, utente, allenatore, arbitro, lezioniTenute, admin));
                }
            } catch (SQLException e) {
                throw new DAOException(e);
            }
            return persone;
        }

        public static int demoteUser(Persona persona, Connection connection) {
            return persona == null ? 0 : updateWithCF(persona.cf, Queries.DEMOTE_USER, connection);
        }

        public static int demoteTrainer(Persona persona, Connection connection) {
            return persona == null ? 0 : updateWithCF(persona.cf, Queries.DEMOTE_TRAINER, connection);
        }

        public static int demoteReferee(Persona persona, Connection connection) {
            return persona == null ? 0 : updateWithCF(persona.cf, Queries.DEMOTE_REFEREE, connection);
        }

        public static int promoteToTrainer(Persona persona, Connection connection) {
            return persona == null ? 0 : updateWithCF(persona.cf, Queries.PROMOTE_TO_TRAINER, connection);
        }

        public static int promoteToReferee(Persona persona, Connection connection) {
            return persona == null ? 0 : updateWithCF(persona.cf, Queries.PROMOTE_TO_REFEREE, connection);
        }

        public static int promoteToUser(Persona persona, Connection connection) {
            return persona == null ? 0 : updateWithCF(persona.cf, Queries.PROMOTE_TO_USER, connection);
        }

        private static int updateWithCF(String cf, String query, Connection connection) {
            int rowsInserted;
            try (
                var preparedStatement = DAOUtils.prepare(connection, query, cf);
            ) {
                rowsInserted = preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new DAOException(e);
            }
            return rowsInserted;
        }
    }

    
}
