package entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity

@NamedQueries({
        @NamedQuery(
                name = "getAllTreinadores",
                query = "SELECT t FROM Treinador t ORDER BY t.idSocio" // JPQL
        )
})

public class Treinador extends Socio implements Serializable {
    @ManyToOne
    @JoinColumn(name = "ESCALAO_CODE")
    private Escalao escalao;
    public Treinador() {
    }

    public Treinador( String username,String password, String name, String email) {
        super( username,password, name, email);
        setTipo(SocioType.TREINADOR);
        this.escalao=null;
    }

    public Escalao getEscalao() {
        return escalao;
    }

    public void setEscalao(Escalao escalao) {
        this.escalao = escalao;
    }
}
