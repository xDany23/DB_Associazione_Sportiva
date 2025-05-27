package app.src.main.java.db_ass.data;

public enum Giorno {
    
    LUNEDI("Lunedi"),
    MARTEDI("Martedi"),
    MERCOLEDI("Mercoledi"),
    GIOVEDI("Giovedi"),
    VENERDI("Venerdi"),
    SABATO("Sabato"),
    DOMENICA("Domenica");

    private final String giorno;

    Giorno(String giorno) {
        this.giorno = giorno;
    }

    @Override
    public String toString() {
        return this.giorno;
    }
}
