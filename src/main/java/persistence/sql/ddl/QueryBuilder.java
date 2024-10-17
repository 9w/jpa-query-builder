package persistence.sql.ddl;

import jakarta.persistence.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class QueryBuilder {
    public String create(Class<?> entity) {
        TableInfo tableInfo = new TableInfo(entity);
        ColumnInfo columnInfo = new ColumnInfo(entity);

        return "CREATE TABLE %s (%s);".formatted(tableInfo.getTableName(), columnInfo.getColumnInfo());
    }

    public String drop(Class<?> entity) {
        TableInfo tableInfo = new TableInfo(entity);
        return "DROP TABLE %s;".formatted(tableInfo.getTableName());
    }
}
