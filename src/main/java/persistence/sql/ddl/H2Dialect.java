package persistence.sql.ddl;

import java.sql.Types;

public enum H2Dialect {
    STRING(Types.VARCHAR, "VARCHAR(255)"),
    INTEGER(Types.INTEGER, "INTEGER"),
    LONG(Types.BIGINT, "BIGINT");

    private final Integer type;
    private final String sqlType;

    H2Dialect(Integer type, String sqlType) {
        this.type = type;
        this.sqlType = sqlType;
    }

    public static String getSqlType(Integer type) {
        for (H2Dialect fieldType : H2Dialect.values()) {
            if (fieldType.type.equals(type)) {
                return fieldType.sqlType;
            }
        }
        throw new IllegalArgumentException("Unsupported field type: " + type);
    }
}
