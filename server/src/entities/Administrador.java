package entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import java.io.Serializable;

@Entity

@NamedQueries({
        @NamedQuery(
                name = "getAllAdministradores",
                query = "SELECT a FROM Administrador a ORDER BY a.username" // JPQL
        )
})
public class Administrador extends User implements Serializable {

    public Administrador() {

    }

    public Administrador(String username, String password, String name, String email) {
        super(username, password, name, email);
    }
}
