package db_ass.data;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class Prenotazione {
    
    public String dataPartita;
    public String dataPrenotazioneEffettuata;
    public Persona prenotante;
    public FasciaOraria fasciaOraria;

    public Prenotazione(String dataPartita, String dataPrenotazioneEffettuata, Persona prenotante, FasciaOraria fasciaOraria) {
        this.dataPartita = dataPartita;
        this.dataPrenotazioneEffettuata = dataPrenotazioneEffettuata;
        this.prenotante = prenotante;
        this.fasciaOraria = fasciaOraria;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (other == null) {
            return false;
        } else if (other instanceof Prenotazione) {
            var p = (Prenotazione)other;
            return (
                p.dataPartita.equals(this.dataPartita) &&
                p.dataPrenotazioneEffettuata.equals(this.dataPrenotazioneEffettuata) &&
                p.prenotante.equals(this.prenotante) &&
                p.fasciaOraria.equals(this.fasciaOraria)
            );
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.dataPartita, this.dataPrenotazioneEffettuata, this.prenotante, this.fasciaOraria);
    }

    @Override
    public String toString() {
        return Printer.stringify(
            "Prenotazione", 
            List.of(
                Printer.field("Data Partita", this.dataPartita),
                Printer.field("Data Prenotazione Effettuata", this.dataPrenotazioneEffettuata),
                Printer.field("Prenotante", this.prenotante),
                Printer.field("Fascia Oraria", this.fasciaOraria)      
            )
        );
    }

    public static final class DAO {
        public static List<Integer> findFieldToBook(String orarioInizio, String data, Sport sport, Connection connection) {
            var preview = new ArrayList<Integer>();

            try (
                var preparedStatement = DAOUtils.prepare(connection, Queries.FIND_FIELD_TO_BOOK, sport.toString(), sport.toString(), orarioInizio, data);
                var resultSet = preparedStatement.executeQuery();
            ) {
                while(resultSet.next()) {
                    var numCampo = resultSet.getInt("NumeroCampo");
                    preview.add(numCampo);
                }
            } catch (SQLException e) {
                throw new DAOException(e);
            }
            return preview;
        }

        public static int bookField(Prenotazione p, Connection connection) {
            int rowsInserted = 0;
            try (
                var preparedStatement = DAOUtils.prepare(connection, Queries.BOOK_FIELD, p.fasciaOraria.numeroCampo.numeroCampo, 
                                                                                         p.fasciaOraria.giorno.toString(),
                                                                                         p.fasciaOraria.orarioInizio,
                                                                                         p.dataPrenotazioneEffettuata,
                                                                                         p.dataPartita,
                                                                                         p.prenotante.cf);   
            ) {
                rowsInserted = preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new DAOException(e);
            }
            return rowsInserted;
        }

        public static List<Prenotazione> allReservationsOfUser(Persona persona, Connection connection) {
            List<Prenotazione> preview = new ArrayList<>();
            try (
                var preparedStatement = DAOUtils.prepare(connection, Queries.ALL_RESERVATIONS_OF_USER, persona.cf);
                var resultSet = preparedStatement.executeQuery();
            ) {
                while (resultSet.next()) {
                    preview.add(new Prenotazione(resultSet.getString("DataPartita"), 
                                                resultSet.getString("DataPrenotazioneEffettuata"), 
                                                persona, 
                                                FasciaOraria.DAO.findPeriod(resultSet.getInt("NumeroCampo"), 
                                                                            Giorno.valueOf(resultSet.getString("Giorno").toUpperCase()), 
                                                                            resultSet.getString("OrarioInizio"), connection)));
                }
                
            } catch (SQLException e) {
                throw new DAOException(e);
            }
            return preview;
        }
    }
}
