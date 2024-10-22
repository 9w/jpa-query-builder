package persistence.sql.ddl;

import java.sql.Types;

public enum JavaSqlDialect {
    STRING(String.class, Types.VARCHAR),
    INTEGER(Integer.class, Types.INTEGER),
    LONG(Long.class, Types.BIGINT);

    private final Class<?> type;
    private final int sqlType;

    JavaSqlDialect(Class<?> type, int sqlType) {
        this.type = type;
        this.sqlType = sqlType;
    }

    public static int getSqlType(Class<?> clazz) {
        for (JavaSqlDialect fieldType : JavaSqlDialect.values()) {
            if (fieldType.type.equals(clazz)) {
                return fieldType.sqlType;
            }
        }
        throw new IllegalArgumentException("Unsupported field type: " + clazz.getSimpleName());
    }
}
