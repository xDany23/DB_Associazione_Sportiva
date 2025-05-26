package app.src.main.java.db_ass.data;

import java.util.List;
import java.util.stream.Collectors;

public final class Printer {

    public static class Field {

        public final String name;
        public final Object value;

        public Field(String name, Object value) {
            this.name = name;
            this.value = value;
        }

        public String toString() {
            if (this.value instanceof String) {
                return this.name + "='" + this.value + "'";
            } else {
                return this.name + "=" + this.value.toString();
            }
        }
    }

    public static Field field(String name, Object value) {
        return new Field(name, value);
    }

    public static String stringify(String name, List<Field> fields) {
        var builder = new StringBuilder(name);
        var fieldsString = fields.stream().map(Field::toString).collect(Collectors.joining(", "));
        builder.append("[");
        builder.append(fieldsString);
        builder.append("]");
        return builder.toString();
    }
}
