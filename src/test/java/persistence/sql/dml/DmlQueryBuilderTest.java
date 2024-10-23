package persistence.sql.dml;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.QueryBuilder;
import persistence.sql.entity.BrandNewPerson;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class DmlQueryBuilderTest {
    private static DatabaseServer server;
    private static JdbcTemplate jdbcTemplate;
    private static QueryBuilder queryBuilder = new QueryBuilder();

    @BeforeAll
    public static void setup() {
        try {
            server = new H2();
            server.start();
            jdbcTemplate = new JdbcTemplate(server.getConnection());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeEach
    public void init() {
        jdbcTemplate.execute("DROP TABLE IF EXISTS users;");
        jdbcTemplate.execute(queryBuilder.create(BrandNewPerson.class));

        DmlQueryBuilder dmlQueryBuilder = new DmlQueryBuilder();
        String insertQuery = dmlQueryBuilder.insert(BrandNewPerson.class, new BrandNewPerson("foo", 1, "foo@bar.com:") );
        jdbcTemplate.execute(insertQuery);
    }

    @Test
    @DisplayName("BrandNewPerson 엔티티 insert 쿼리를 생성한다")
    void testInsertBrandNewPersonQuery() {
        BrandNewPerson person = new BrandNewPerson("foo", 1, "foo@bar.com");
        DmlQueryBuilder dmlQueryBuilder = new DmlQueryBuilder();
        String expectedQuery = "INSERT INTO users (nick_name, old, email) VALUES ('foo', '1', 'foo@bar.com');";
        assertThat(dmlQueryBuilder.insert(BrandNewPerson.class, person)).isEqualTo(expectedQuery);
    }

    @Test
    @DisplayName("findAll 쿼리를 리턴한다")
    void testFindAll() {
        DmlQueryBuilder dmlQueryBuilder = new DmlQueryBuilder();
        String expectedQuery = "SELECT * FROM users;";
        assertThat(dmlQueryBuilder.findAll(BrandNewPerson.class)).isEqualTo(expectedQuery);
    }

    @Test
    @DisplayName("findAll 실행시 users 데이터를 리턴한다")
    void testFindAllExecuteQuery() {
    }


    @Test
    @DisplayName("findById 쿼리를 생성한다")
    void testFindById() {
        BrandNewPerson person = new BrandNewPerson(1L);
        DmlQueryBuilder dmlQueryBuilder = new DmlQueryBuilder();
        String expectedQuery = "SELECT * FROM users WHERE id = '1';";
        assertThat(dmlQueryBuilder.findById(BrandNewPerson.class, person)).isEqualTo(expectedQuery);
    }
}
