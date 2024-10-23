package persistence.sql.dml;

import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import persistence.sql.ddl.ColumnInfo;
import persistence.sql.ddl.TableInfo;

import java.util.Arrays;
import java.util.stream.Collectors;

public class DmlQueryBuilder {

    public String findAll(Class<?> clazz) {
        return "%s;".formatted(find(clazz));
    }

    public String findById(Class<?> clazz, Object object) {
        return whereClause(find(clazz), clazz, object);
    }

    public String insert(Class<?> clazz, Object object) {
        TableInfo tableInfo = new TableInfo(clazz);
        String tableName = tableInfo.getTableName();

        return "INSERT INTO %s (%s) VALUES (%s);".formatted(tableName, String.join(", ", columnsClause(clazz)), valueClause(object));
    }

    private String find(Class<?> clazz) {
        TableInfo tableInfo = new TableInfo(clazz);
        String tableName = tableInfo.getTableName();

        return "SELECT * FROM %s".formatted(tableName);
    }

    private String[] columnsClause(Class<?> clazz) {
        ColumnInfo columnInfo = new ColumnInfo(clazz);
        return columnInfo.getInsertColumns();
    }

    private String valueClause(Object object) {
        return Arrays.stream(object.getClass().getDeclaredFields())
            .filter(field -> !field.isAnnotationPresent(Transient.class) && !field.isAnnotationPresent(Id.class))
            .map(field -> {
                try {
                    field.setAccessible(true);
                    return field.get(object).toString();
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            })
            .map("'%s'"::formatted)
            .collect(Collectors.joining(", "));
    }

    private String whereClause(String selectQuery, Class<?> clazz, Object object) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(selectQuery);
        stringBuilder.append(" WHERE ");

        return Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .map(field -> {
                    field.setAccessible(true);
                    try {
                        if (field.get(object) == null) {
                            return "";
                        }
                        return "%s = '%s'".formatted(field.getName(), field.get(object).toString());
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                })
                .filter(s -> !s.isEmpty())
                .collect(Collectors.joining(" = ", stringBuilder.toString(), ";"));

    }
}
