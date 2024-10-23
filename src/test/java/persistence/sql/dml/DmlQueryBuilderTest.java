package persistence.sql.dml;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.entity.BrandNewPerson;

import static org.assertj.core.api.Assertions.assertThat;

public class DmlQueryBuilderTest {
    @Test
    @DisplayName("BrandNewPerson 엔티티 insert 쿼리를 생성한다")
    void testInsertBrandNewPersonQuery() {
        BrandNewPerson person = new BrandNewPerson("foo", 1, "foo@bar.com");
        DmlQueryBuilder dmlQueryBuilder = new DmlQueryBuilder();
        String expectedQuery = "INSERT INTO users (nick_name, old, email) VALUES ('foo', '1', 'foo@bar.com');";
        assertThat(dmlQueryBuilder.insert(BrandNewPerson.class, person)).isEqualTo(expectedQuery);
    }
}
