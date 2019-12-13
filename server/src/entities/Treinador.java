package entities;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
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

    public Treinador( String username,String password, String name, String email) {
        super( username,password, name, email);
        setTipo(SocioType.TREINADOR);
    }
}
