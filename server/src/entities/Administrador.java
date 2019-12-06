package entities;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import java.io.Serializable;

@Entity

@NamedQueries({
        @NamedQuery(
                name = "getAllAdministradores",
                query = "SELECT a FROM Administrador a ORDER BY a.idSocio" // JPQL
        )
})
public class Administrador extends Socio implements Serializable {

    public Administrador() {

    }

    public Administrador(long idSocio, String password, String name, String email) {
        super(idSocio, password, name, email);
    }
}
