package entities;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import java.io.Serializable;

@Entity

@NamedQueries({
        @NamedQuery(
                name = "getAllTreinadores",
                query = "SELECT t FROM Treinador t ORDER BY t.idSocio" // JPQL
        )
})
public class Treinador extends Socio implements Serializable {
    public Treinador() {
    }

    public Treinador(long idSocio, String password, String name, String email) {
        super(idSocio, password, name, email);
    }
}
