package persistence.sql.ddl;

import jakarta.persistence.Transient;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ColumnInfo {
    private final Field[] fields;

    public ColumnInfo(Class<?> entity) {
        this.fields = entity.getDeclaredFields();

    }
    public String getColumnInfo()  {
        return Arrays.stream(this.fields)
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .map(this::generateColumnDefinitions)
                .collect(Collectors.joining(", "));
    }

    private String generateColumnDefinitions(Field field) {
        List<String> columnInfo = new ArrayList<>();
        ColumnDefinitionMapper columnDefinitionMapper = new ColumnDefinitionMapper(field);
        String columnName = columnDefinitionMapper.getColumnName();
        Class<?> columnType = columnDefinitionMapper.getColumnType();
        String columnDataType = H2Dialect.getSqlType(columnType);

        columnInfo.add(columnName);
        columnInfo.add(columnDataType);
        columnInfo.addAll(columnDefinitionMapper.mapAnnotationToSQLDefinition());

        return String.join(" ", columnInfo);
    }
}
