package entities;

import javax.persistence.Entity;
import java.io.Serializable;

@Entity
/*
@NamedQueries({
        @NamedQuery(
                name = "getAllStudents",
                query = "SELECT s FROM Student s ORDER BY s.name" // JPQL
        )
})*/
public class Administrador extends User implements Serializable {
    public Administrador() {
    }
}
