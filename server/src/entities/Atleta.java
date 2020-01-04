package entities;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity

@NamedQueries({
        @NamedQuery(
                name = "getAllAtletas",
                query = "SELECT a FROM Atleta a WHERE a.delete=false  ORDER BY a.idSocio" // JPQL
        )
})

public class Atleta extends Socio implements Serializable {

    /*  @ManyToMany(mappedBy = "socios")
      private Set<Treinador> treinadores;*/
    @ManyToOne
    @JoinColumn(name = "ESCALAO_CODE")
    private Escalao escalao;

    public Atleta() {
    }

    public Atleta(String username, String password, String name, String email) {
        super(username, password, name, email);
        setTipo(SocioType.ATLETA);
        this.escalao = null;
    }

    public Escalao getEscalao() {
        return escalao;
    }

    public void setEscalao(Escalao escalao) {
        this.escalao = escalao;
    }
}
