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
        String columnDefinitions = this.generateColumnInfoQuery(entity);

        return "CREATE TABLE %s (%s);".formatted(tableInfo.getTableName(), columnDefinitions);
    }

    public String drop(Class<?> entity) {
        TableInfo tableInfo = new TableInfo(entity);
        return "DROP TABLE %s;".formatted(tableInfo.getTableName());
    }

    private String generateColumnInfoQuery(Class<?> entity) {
        Field[] fields = entity.getDeclaredFields();
        return Arrays.stream(fields)
            .filter(field -> !field.isAnnotationPresent(Transient.class))
            .map(this::generateColumnDefinitions)
            .collect(Collectors.joining(", "));
    }

    private String generateColumnDefinitions(Field field) {
        List<String> columnInfo = new ArrayList<>();
        ColumnDefinitionMapper columnDefinitionMapper = new ColumnDefinitionMapper(field);
        String columnName = columnDefinitionMapper.getColumnName();
        Class<?> columnType = columnDefinitionMapper.getColumnType();
        String columnDataType = ColumnDataType.getSqlType(columnType);

        columnInfo.add(columnName);
        columnInfo.add(columnDataType);
        columnInfo.addAll(columnDefinitionMapper.mapAnnotationToSQLDefinition());

        return String.join(" ", columnInfo);
    }
}
