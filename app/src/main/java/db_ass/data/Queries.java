package db_ass.data;

public final class Queries {

	public static final String FIND_USER = 
			"""
			SELECT *
			FROM persona
			WHERE CF = ?;
			""";
    
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

	public static final String FIND_JOINABLE_LESSON = 
			"""
			SELECT l.*
			FROM fascia_oraria f join lezione_privata l on (f.NumeroCampo = l.NumeroCampo and f.Giorno = l.Giorno and f.OrarioInizio = l.OrarioInizio)
			WHERE l.DataSvolgimento = ?
			AND f.OrarioInizio = ?
			AND l.SportPraticato = ?
			AND f.Tipo = "Lezione"
			AND (l.Partecipante3 is NULL OR l.Partecipante2 is NULL);		
			""";

	public static final String JOIN_LESSON_AS_SECOND_PARTECIPANT = 
			"""
			UPDATE lezione_privata 
			SET Partecipante2 = ? 
			WHERE NumeroCampo = ?
			AND Giorno = ?
			AND OrarioInizio = ?
			AND DataSvolgimento = ?
			AND SportPraticato = ?
			AND Partecipante2 is null;
			""";

	public static final String JOIN_LESSON_AS_THIRD_PARTECIPANT = 
			"""
			UPDATE lezione_privata 
			SET Partecipante3 = ?
			WHERE NumeroCampo = ?
			AND Giorno = ?
			AND OrarioInizio = ?
			AND DataSvolgimento = ?
			AND SportPraticato = ?
			AND Partecipante3 is null;		
			""";
	
	public static final String FIND_SPACE_FOR_NEW_LESSON = 
			"""
			select c.NumeroCampo
			from campo c
			where Tipo = ?
			and NumeroCampo not in (select f.NumeroCampo
									from fascia_oraria f left join lezione_privata lp on (f.OrarioInizio = lp.OrarioInizio and f.Giorno = lp.Giorno and f.NumeroCampo = lp.NumeroCampo) 
									left join lezione_corso lc on (f.OrarioInizio = lc.OrarioInizio and f.Giorno = lc.Giorno and f.NumeroCampo = lc.NumeroCampo) 
									where f.OrarioInizio = ?
									and f.Giorno = ?
									and (lp.DataSvolgimento = ? or lc.DataSvolgimento = ?));		
			""";

	public static final String CREATE_NEW_LESSON = 
			"""
			INSERT INTO lezione_privata(NumeroCampo, Giorno, OrarioInizio, DataSvolgimento, SportPraticato, Prezzo, Allenatore, Partecipante1)
			VALUES (?,?,?,?,?,?,?,?);
			UPDATE persona
			SET LezioniTenute = LezioniTenute + 1
			WHERE CF = ?;		
			""";

	public static final String FIND_ACTIVE_COURSE = 
			"""
			SELECT *
			FROM corso
			WHERE CodiceCorso = ?
			AND DataFine > now();		
			""";

	public static final String JOIN_COURSE = 
			"""
			INSERT INTO partecipa(CF, CodiceCorso)
			VALUES (?,?);		
			""";
}
