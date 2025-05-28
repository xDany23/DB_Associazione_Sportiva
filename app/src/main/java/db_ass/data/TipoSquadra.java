package db_ass.data;

public enum TipoSquadra {
    CALCETTO("Calcetto"),
    TENNIS_SINGOLO("Tennis singolo"),
    TENNIS_DOPPIO("Tennis doppio"),
    PADEL("Padel");

    private final String tipo;

    TipoSquadra(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return this.tipo;
    }
}
