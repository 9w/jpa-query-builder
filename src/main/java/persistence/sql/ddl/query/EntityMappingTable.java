package persistence.sql.ddl.query;

import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import persistence.sql.ddl.query.model.DomainType;
import persistence.sql.ddl.query.model.DomainTypes;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EntityMappingTable {

    private final String tableName;
    private final DomainTypes domainTypes;
    private final Class<?> clazz;

    public EntityMappingTable(final String tableName,
                              final DomainTypes domainTypes,
                              final Class<?> clazz) {
        this.tableName = tableName;
        this.domainTypes = domainTypes;
        this.clazz = clazz;
    }

    public String getTableName() {
        if(clazz.isAnnotationPresent(Table.class)) {
            return clazz.getAnnotation(Table.class).name();
        }

        return tableName;
    }

    public List<DomainType> getDomainTypes() {
        return Collections.unmodifiableList(domainTypes.getDomainTypes());
    }

    public static EntityMappingTable from(final Class<?> clazz) {
        return new EntityMappingTable(
                clazz.getSimpleName(),
                DomainTypes.from(clazz.getDeclaredFields()),
                clazz
        );
    }
}