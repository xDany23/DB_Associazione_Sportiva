package db_ass.data;

public enum Sport {
    CALCETTO("Calcetto"),
    TENNIS("Tennis"),
    PADEL("Padel");

    private final String sport;

    Sport(String sport) {
        this.sport = sport;
    }

    @Override
    public String toString() {
        return this.sport;
    }
}
