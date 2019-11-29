package entities;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import java.io.Serializable;
import java.util.List;

@Entity
/*
@NamedQueries({
        @NamedQuery(
                name = "getAllStudents",
                query = "SELECT s FROM Student s ORDER BY s.name" // JPQL
        )
})*/
public class Atleta extends Socio implements Serializable {
        @Id
        private int id;
        //private List<Atleta> atletas;
        public Atleta() {
        }
}
