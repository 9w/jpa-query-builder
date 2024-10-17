package persistence.sql.ddl;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

public class TableInfo {
    private final Class<?> entity;
    public TableInfo(Class<?> entity) {
        this.entity = entity;
    }

    public String getTableName() {
        this.throwIfNotEntity();

        String defaultTableName = this.entity.getSimpleName();
        Table table = this.entity.getAnnotation(Table.class);
        if (table == null) {
            return this.entity.getSimpleName();
        }

        String tableName = table.name();
        if (tableName.isBlank()) {
            return defaultTableName;
        }
        return tableName;
    }

    private void throwIfNotEntity() {
        if (!this.entity.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException("Class %s is not an entity".formatted(entity.getSimpleName()));
        }
    }
}
