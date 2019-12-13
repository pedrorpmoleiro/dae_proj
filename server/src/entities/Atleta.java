package entities;


import javax.persistence.*;
import java.io.Serializable;

@Entity

@NamedQueries({
        @NamedQuery(
                name = "getAllAtletas",
                query = "SELECT a FROM Atleta a ORDER BY a.idSocio" // JPQL
        )
})

public class Atleta extends Socio implements Serializable {

      /*  @ManyToMany(mappedBy = "socios")
        private Set<Treinador> treinadores;*/
        public Atleta() {
        }

    public Atleta( String username,String password, String name, String email) {
        super( username,password, name, email);
    }
}
