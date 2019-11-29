package entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Socio extends User implements Serializable {
    @Id
    private int id;

    public Socio() {
    }

}
