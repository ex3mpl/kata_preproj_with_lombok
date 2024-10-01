package jm.task.core.jdbc.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable; // for correct work with Hibernate

@Data                   // equals and hashCode; getters and setters; and more
@NoArgsConstructor      // constructor without args for Reflection API that uses in Hibernate
@AllArgsConstructor     // constructor with all args from fields of class
@Builder                // for creating and initializing class
@Entity                 // this class is entity of Hibernate
@Table(name = "users")  // table name of database
public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 42L;

    @Id     // primary (first) key ---- ID type needs to be Serializable
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment ID value in table
    @Column(name = "id")  // column name in table  (default is property name)
    private Long id;

    @Column(name = "name") // this name needs for great mapping
    private String name;

    @Column(name = "lastname")
    private String lastName;

    @Column(name = "age")
    private Byte age;
}
