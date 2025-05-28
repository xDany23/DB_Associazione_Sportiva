package app.src.main.java.db_ass.data;

public final class Queries {
    
    public final String registerUser = 
            """
            INSERT INTO persona(Nome, Cognome, E_mail, Password, CF, Utente)
            VALUES (?, ?, ?, ?, ?, ?);
            """;
    
    public final String newTrainer = 
            """
            INSERT INTO persona(Nome, Cognome, E_mail, Password, CF, Allenatore, LezioniTenute)
            VALUES (?, ?, ?, ?, ?, ?, 0);        
            """;

    public final String newReferee = 
            """
            INSERT INTO persona(Nome, Cognome, E_mail, Password, CF, Arbitro)
            VALUES (?, ?, ?, ?, ?, ?);
            """;
}
