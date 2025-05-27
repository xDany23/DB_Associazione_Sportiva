package app.src.main.java.db_ass.data;

public enum TipoFascia {
    
    PRENOTABILE("Prenotabile"),
    LEZIONE("Lezione");

    private final String tipo;
    
    TipoFascia(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return this.tipo;
    }
}
