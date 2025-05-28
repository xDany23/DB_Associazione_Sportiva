package db_ass.data;

public final class Queries {
    
    public static final String REGISTER_USER = 
            """
            INSERT INTO persona(Nome, Cognome, E_mail, Password, CF, Utente)
            VALUES (?, ?, ?, ?, ?, ?);
            """;
    
    public static final String NEW_TRAINER = 
            """
            INSERT INTO persona(Nome, Cognome, E_mail, Password, CF, Allenatore, LezioniTenute)
            VALUES (?, ?, ?, ?, ?, ?, 0);        
            """;

    public static final String NEW_REFEREE = 
            """
            INSERT INTO persona(Nome, Cognome, E_mail, Password, CF, Arbitro)
            VALUES (?, ?, ?, ?, ?, ?);
            """;

    public static final String FIND_FIELD_TO_BOOK = 
            """
            select NumeroCampo
            from campo
            where tipo = ?
            and NumeroCampo not in (SELECT c.NumeroCampo
						            FROM prenotazione p, fascia_oraria f, campo c
                                    WHERE p.NumeroCampo = f.NumeroCampo
                                    AND f.NumeroCampo = c.NumeroCampo
                                    AND f.Tipo = "Prenotabile"
                                    AND	c.Tipo = ?
                                    AND f.OrarioInizio = ?
                                    AND p.DataPartita = ?
                                    GROUP BY c.NumeroCampo);        
            """;

    public static final String BOOK_FIELD = 
            """
            INSERT INTO prenotazione(NumeroCampo, Giorno, OrarioInizio, DataPrenotazioneEffettuata, DataPartita, Prenotante)
            VALUES (?, ?, ?, ?, ?, ?);        
            """;
}
