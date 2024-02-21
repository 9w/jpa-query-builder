package persistence.sql.ddl.domain;

import jakarta.persistence.Column;

import java.lang.reflect.Field;

public class ColumnLength {

    private final Integer length;

    public ColumnLength(Field field) {
        Column column = field.getAnnotation(Column.class);
        if (column != null && column.length() > 0) {
            this.length = column.length();
            return;
        }
        this.length = null;
    }

    public ColumnLength(Integer length) {
        this.length = length;
    }

    public Integer getSize() {
        return length;
    }
}
