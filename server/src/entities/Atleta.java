package entities;


import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
/*
@NamedQueries({
        @NamedQuery(
                name = "getAllStudents",
                query = "SELECT s FROM Student s ORDER BY s.name" // JPQL
        )
})*/
public class Atleta extends Socio implements Serializable {

      /*  @ManyToMany(mappedBy = "socios")
        private Set<Treinador> treinadores;*/
        public Atleta() {
        }
}
