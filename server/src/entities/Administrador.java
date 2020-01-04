package entities;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllAdministradores",
                query = "SELECT a FROM Administrador a WHERE a.delete=false  ORDER BY a.idSocio" // JPQL
        )
})

public class Administrador extends Socio implements Serializable {

    public Administrador() {

    }

    public Administrador( String username,String password, String name, String email) {
        super( username,password, name, email);
        setTipo(SocioType.ADMINISTRADOR);
    }
}
