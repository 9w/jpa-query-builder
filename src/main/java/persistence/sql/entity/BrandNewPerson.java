package persistence.sql.entity;

import jakarta.persistence.*;

@Table(name = "users")
@Entity
public class BrandNewPerson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nick_name")
    private String name;

    @Column(name = "old")
    private Integer age;

    @Column(nullable = false)
    private String email;

    @Transient
    private Integer index;

    public BrandNewPerson() {
    }

    public BrandNewPerson(Long id) {
        this.id = id;
    }

    public BrandNewPerson(Long id, String name, Integer age, String email) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
    }

    public BrandNewPerson(String name, Integer age, String email) {
        this.name = name;
        this.age = age;
        this.email = email;
    }
}
