package db_ass.data;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class Torneo {

    public final int codiceTorneo;
    public final String dataSvolgimento;
    public final String nome;
    public final String premio;
    public final int massimoPartecipanti;
    public final double quotaIscrizione;
    public final TipoSquadra tipo;
    public final Squadra vincitore;

    public Torneo(int codiceTorneo, 
                  String dataSvolgimento,
                  String nome, 
                  String premio, 
                  int massimoPartecipanti, 
                  double quotaIscrizione, 
                  TipoSquadra tipo, 
                  Squadra vincitore) {
        this.codiceTorneo = codiceTorneo;
        this.dataSvolgimento = dataSvolgimento;
        this.nome = nome;
        this.premio = premio;
        this.massimoPartecipanti = massimoPartecipanti;
        this.quotaIscrizione = quotaIscrizione;
        this.tipo = tipo;
        this.vincitore = vincitore;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (other == null) {
            return false;
        } else if (other instanceof Torneo) {
            var t = (Torneo)other;
            return (
                t.codiceTorneo == this.codiceTorneo &&
                t.dataSvolgimento.equals(this.dataSvolgimento) &&
                t.nome.equals(this.nome) &&
                t.premio.equals(this.premio) &&
                t.massimoPartecipanti == this.massimoPartecipanti &&
                t.quotaIscrizione == this.quotaIscrizione &&
                t.tipo.equals(this.tipo) &&
                t.vincitore.equals(this.vincitore)
            );
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.codiceTorneo,
                            this.dataSvolgimento,
                            this.nome,
                            this.premio,
                            this.massimoPartecipanti,
                            this.quotaIscrizione,
                            this.tipo,
                            this.vincitore
        );           
    }

    @Override
    public String toString() {
        return Printer.stringify(
            "Torneo", 
            List.of(
                Printer.field("Codice Torneo", this.codiceTorneo),
                Printer.field("Data Svolgimento", this.dataSvolgimento),
                Printer.field("Nome", this.nome),
                Printer.field("Premio", this.premio),
                Printer.field("Massimo Partecipanti", this.massimoPartecipanti),
                Printer.field("Quota Iscrizione", this.quotaIscrizione),
                Printer.field("Tipo", this.tipo),
                Printer.field("Vincitore", this.vincitore)
            )
        );
    }

    public static final class DAO {
        public static Torneo isTournementEnterable(int codiceTorneo, TipoSquadra tipo, Connection connection) {
            Torneo torneo;
            try (
                var preparedStatement = DAOUtils.prepare(connection, Queries.IS_TOURNAMENT_ENTERABLE, codiceTorneo, tipo);
                var resultSet = preparedStatement.executeQuery();
            ) {
                resultSet.next();
                var dataSvolgimento = resultSet.getString("DataSvolgimento");
                var nome = resultSet.getString("Nome");
                var premio = resultSet.getString("Premio");
                var maxp = resultSet.getInt("MassimoPartecipanti");
                var quota = resultSet.getDouble("QuotaIscrizione");
                var vincitore = Squadra.findTeam(resultSet.getInt("Vincitore"), connection);
                torneo = new Torneo(codiceTorneo, dataSvolgimento, nome, premio, maxp, quota, tipo, vincitore);
            } catch (SQLException e) {
                throw new DAOException(e);
            }
            return torneo;
        }

        public static int enterTournament(int codiceTorneo, int codiceSquadra, Connection connection) {
            int rowsInserted;
            try (
                var preparedStatement = DAOUtils.prepare(connection, Queries.ENTER_TOURNAMENT, codiceTorneo, codiceSquadra);
            ) {
                rowsInserted = preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new DAOException(e);
            }
            return rowsInserted;
        }

        public static int createTournament(String dataSvolgimento, String nome, String premio, int maxp, double quota, int codiceTorneo, TipoSquadra tipo, Squadra vincitore, Connection connection) {
            int rowsInserted;
            try (
                var preparedStatement = DAOUtils.prepare(connection, Queries.CREATE_TOURNAMENT, dataSvolgimento, nome, premio, maxp, quota, codiceTorneo, tipo, vincitore);
            ) {
                rowsInserted = preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new DAOException(e);
            }
            return rowsInserted;
        }

        public static List<Persona> findAllPartecipants(int codiceTorneo, Connection connection) {
            List<Persona> preview = new ArrayList<>();
            try (
                var preparedStatement = DAOUtils.prepare(connection, Queries.FIND_ALL_PARTECIPANTS, codiceTorneo);
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
                    var persona = new Persona(cf, nome, cognome, email, pass, utente, allenatore, arbitro, lezioniTenute);
                    preview.add(persona); 
                }
            } catch (SQLException e) {
                throw new DAOException(e);
            }
            return preview;
        }

        public static Torneo findTournament(int codiceTorneo, Connection connection) {
            Torneo torneo;
            try (
                var preparedStatement = DAOUtils.prepare(connection, Queries.FIND_TOURNAMENT, codiceTorneo);
                var resultSet = preparedStatement.executeQuery();
            ) {
                resultSet.next();
                var data = resultSet.getString("DataSvolgimento");
                var nome = resultSet.getString("Nome");
                var premio = resultSet.getString("Premio");
                var maxp = resultSet.getInt("MassimoPartecipanti");
                var quota = resultSet.getDouble("QuotaIscrizione");
                var tipo = TipoSquadra.valueOf(resultSet.getString("Tipo").toUpperCase());
                var vincitore = Squadra.findTeam(resultSet.getInt("Vincitore"), connection);
                torneo = new Torneo(codiceTorneo, data, nome, premio, maxp, quota, tipo, vincitore);
            } catch (SQLException e) {
                throw new DAOException(e);
            }
            return torneo;
        }

        public static List<Partita> visualizeAllTournamentMatches(int codiceTorneo, Connection connection) {
            List<Partita> preview = new ArrayList<>();
            try (
                var preparedStatement = DAOUtils.prepare(connection, Queries.VISUALIZE_ALL_TOURNAMENT_MATCHES, codiceTorneo);
                var resultSet = preparedStatement.executeQuery();
            ) {
                while (resultSet.next()) {
                    var codicePartita = resultSet.getInt("CodicePartita");
                    var arbitro = 
                }
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    }
    
}
