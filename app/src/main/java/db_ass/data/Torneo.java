package db_ass.data;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
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
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Torneo other = (Torneo) obj;
        if (codiceTorneo != other.codiceTorneo)
            return false;
        if (dataSvolgimento == null) {
            if (other.dataSvolgimento != null)
                return false;
        } else if (!dataSvolgimento.equals(other.dataSvolgimento))
            return false;
        if (nome == null) {
            if (other.nome != null)
                return false;
        } else if (!nome.equals(other.nome))
            return false;
        if (premio == null) {
            if (other.premio != null)
                return false;
        } else if (!premio.equals(other.premio))
            return false;
        if (massimoPartecipanti != other.massimoPartecipanti)
            return false;
        if (Double.doubleToLongBits(quotaIscrizione) != Double.doubleToLongBits(other.quotaIscrizione))
            return false;
        if (tipo != other.tipo)
            return false;
        if (vincitore == null) {
            if (other.vincitore != null)
                return false;
        } else if (!vincitore.equals(other.vincitore))
            return false;
        return true;
    }



    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + codiceTorneo;
        result = prime * result + ((dataSvolgimento == null) ? 0 : dataSvolgimento.hashCode());
        result = prime * result + ((nome == null) ? 0 : nome.hashCode());
        result = prime * result + ((premio == null) ? 0 : premio.hashCode());
        result = prime * result + massimoPartecipanti;
        long temp;
        temp = Double.doubleToLongBits(quotaIscrizione);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
        result = prime * result + ((vincitore == null) ? 0 : vincitore.hashCode());
        return result;
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
        public static List<Torneo> isTournementEnterable(TipoSquadra tipo, Connection connection) {
            List<Torneo> torneo = new LinkedList<>();
            try (
                var preparedStatement = DAOUtils.prepare(connection, Queries.IS_TOURNAMENT_ENTERABLE, tipo.toString());
                var resultSet = preparedStatement.executeQuery();
            ) {
                System.out.println("Entro tipo = " + tipo.toString());
                if (resultSet.next()) {
                    var codiceTorneo = resultSet.getInt("CodiceTorneo");
                    var dataSvolgimento = resultSet.getString("DataSvolgimento");
                    var nome = resultSet.getString("Nome");
                    var premio = resultSet.getString("Premio");
                    var maxp = resultSet.getInt("MassimoPartecipanti");
                    var quota = resultSet.getDouble("QuotaIscrizione");
                    var vincitore = Squadra.DAO.findTeam(resultSet.getInt("SquadraVincitrice"), connection);
                    torneo.add(new Torneo(codiceTorneo, dataSvolgimento, nome, premio, maxp, quota, tipo, vincitore));
                    System.out.println(torneo);
                }
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
                var preparedStatement = DAOUtils.prepare(connection, Queries.CREATE_TOURNAMENT, dataSvolgimento, nome, premio, maxp, quota, codiceTorneo, tipo.toString(), vincitore);
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
            Torneo torneo = null;
            try (
                var preparedStatement = DAOUtils.prepare(connection, Queries.FIND_TOURNAMENT, codiceTorneo);
                var resultSet = preparedStatement.executeQuery();
            ) {
                if (resultSet.next()) {
                    var data = resultSet.getString("DataSvolgimento");
                    var nome = resultSet.getString("Nome");
                    var premio = resultSet.getString("Premio");
                    var maxp = resultSet.getInt("MassimoPartecipanti");
                    var quota = resultSet.getDouble("QuotaIscrizione");
                    var tipo = TipoSquadra.valueOf(resultSet.getString("Tipo").toUpperCase());
                    var vincitore = Squadra.DAO.findTeam(resultSet.getInt("SquadraVincitrice"), connection);
                    torneo = new Torneo(codiceTorneo, data, nome, premio, maxp, quota, tipo, vincitore);
                }
            } catch (SQLException e) {
                throw new DAOException(e);
            }
            return torneo;
        }

        public static List<RisultatiTorneo> visualizeAllTournamentMatches(int codiceTorneo, Connection connection) {
            List<RisultatiTorneo> preview = new ArrayList<>();
            try (
                var preparedStatement = DAOUtils.prepare(connection, Queries.VISUALIZE_ALL_TOURNAMENT_MATCHES, codiceTorneo);
                var resultSet = preparedStatement.executeQuery();
            ) {
                while (resultSet.next()) {
                    var codicePartita = resultSet.getInt("p.CodicePartita");
                    var codiceSquadra = resultSet.getInt("g.CodiceSquadra");
                    var nome = resultSet.getString("s.Nome");
                    var punteggio = resultSet.getInt("g.punteggio");
                    var ris = new RisultatiTorneo(codicePartita, codiceSquadra, punteggio, nome);
                    preview.add(ris);
                }
            } catch (SQLException e) {
                throw new DAOException(e);
            }
            return preview;
        }
    }
    
}
