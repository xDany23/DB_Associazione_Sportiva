package db_ass.data;

public final class Queries {

	public static final String FIND_FIELD = 
			"""
			SELECT *
			FROM campo
			WHERE NumeroCampo = ?;		
			""";

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
			SELECT l.Giorno, l.NumeroCampo, l.OrarioInizio
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
			select c.*
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
			""";

	public static final String UPDATE_TRAINER_LESSONS = 
			"""
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

	public static final String CREATE_NEW_TEAM = 
			"""
			INSERT INTO squadra(Nome, CodiceSquadra, Tipo, Componenti1, Componenti2, Componenti3, Componenti4, Componenti5)
			VALUES (?,?,?,?,?,?,?,?);		
			""";

	public static final String IS_TOURNAMENT_ENTERABLE = 
			"""
			SELECT *
			FROM torneo t 
			WHERE t.CodiceTorneo = ?
			AND t.DataSvolgimento > now()
			AND t.Tipo = ?
			AND t.MassimoPartecipanti > (select count(*)
										from iscrizione i
										where i.CodiceTorneo = t.CodiceTorneo);		
			""";

	public static final String ENTER_TOURNAMENT = 
			"""
			INSERT INTO iscrizione(CodiceTorneo, CodiceSquadra)
			VALUES (?,?);		
			""";

	public static final String CREATE_TOURNAMENT = 
			"""
			INSERT INTO torneo(DataSvolgimento, Nome, Premio, MassimoPartecipanti, QuotaIscrizione, CodiceTorneo, Tipo)
			VALUES (?,?,?,?,?,?,?);
			""";

	public static final String FIND_ALL_PARTECIPANTS = 
			"""
			SELECT p.Nome, p.Cognome
			FROM persona p, squadra s, iscrizione i, torneo t
			WHERE t.CodiceTorneo = ?
			AND t.CodiceTorneo = i.CodiceTorneo
			AND i.CodiceSquadra = s.CodiceSquadra
			AND (s.Componenti1 = p.CF OR s.Componenti2 = p.CF OR s.Componenti3 = p.CF OR s.Componenti4 = p.CF OR s.Componenti5 = p.CF);		
			""";

	public static final String FIND_ALL_OCCUPIED_TIMES_OF_FIELD = 
			"""
			select f.*
			from fascia_oraria f left join lezione_privata lp on (f.OrarioInizio = lp.OrarioInizio and f.Giorno = lp.Giorno and f.NumeroCampo = lp.NumeroCampo) 
			left join lezione_corso lc on (f.OrarioInizio = lc.OrarioInizio and f.Giorno = lc.Giorno and f.NumeroCampo = lc.NumeroCampo) 
			left join prenotazione p on (f.OrarioInizio = p.OrarioInizio and f.Giorno = p.Giorno and f.NumeroCampo = p.NumeroCampo)
			where (weekofyear(lp.DataSvolgimento) = weekofyear(?)
			or weekofyear(lc.DataSvolgimento) = weekofyear(?)
			or weekofyear(p.DataPartita) = weekofyear(?))
			and f.NumeroCampo = ?;		
			""";

	public static final String FIND_MOST_ACTIVE_COURSES = 
			"""
			select c.*, count(p.CF) as numeroIscritti
			from corso c, partecipa p
			where c.CodiceCorso = p.CodiceCorso
			and c.DataFine > now()
			group by c.CodiceCorso
			order by numeroIscritti desc limit 15;		
			""";

	public static final String FIND_MOST_REQUESTED_TRAINER = 
			"""
			select Nome, Cognome, LezioniTenute
			from persona
			where Allenatore = TRUE
			order by LezioniTenute desc limit 15;		
			""";

	public static final String VISUALIZE_ALL_TOURNAMENT_MATCHES = 
			"""
			select p.CodicePartita, g.CodiceSquadra, s.Nome, g.punteggio
			from partita p, gioca g, squadra s
			where p.CodiceTorneo = ?
			and p.CodicePartita = g.CodicePartita
			and g.CodiceSquadra = s.CodiceSquadra;
			""";
}
