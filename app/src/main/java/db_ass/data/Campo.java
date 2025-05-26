package app.src.main.java.db_ass.data;

import java.util.List;
import java.util.Objects;

public final class Campo {

    public final int numeroCampo;
    public final String tipo;

    public Campo(int numeroCampo, String tipo) {
        this.numeroCampo = numeroCampo;
        this.tipo = tipo == null ? "" : tipo;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (other == null) {
            return false;
        } else if (other instanceof Campo) {
            var c = (Campo)other;
            return (
                c.numeroCampo == this.numeroCampo &&
                c.tipo.equals(this.tipo)
            );
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.numeroCampo, this.tipo);
    }

    @Override
    public String toString() {
        return Printer.stringify(
            "Campo",
            List.of(
                Printer.field("Numero Campo", this.numeroCampo),
                Printer.field("Tipo", this.tipo)
            )
        );
    }

    public static final class DAO {
        //da fare
    }
    
}
