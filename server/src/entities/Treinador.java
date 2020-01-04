package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity

@NamedQueries({
        @NamedQuery(
                name = "getAllTreinadores",
                query = "SELECT t FROM Treinador t WHERE t.delete=false  ORDER BY t.idSocio" // JPQL
        )
})

public class Treinador extends Socio implements Serializable {
    @ManyToMany
    @JoinTable(name = "ESCALAO_TREINADOR",
            joinColumns = @JoinColumn(name = "TREINADOR_USERNAME",referencedColumnName = "USERNAME"),
            inverseJoinColumns
                    = @JoinColumn(name = "ESCALAO_ID",referencedColumnName = "CODE"))
    private Set<Escalao> escaloes;
    public Treinador() {
    }

    public Treinador( String username,String password, String name, String email) {
        super( username,password, name, email);
        setTipo(SocioType.TREINADOR);
        this.escaloes=new LinkedHashSet<>();
    }

    public Set<Escalao> getEscaloes() {
        return escaloes;
    }

    public void setEscaloes(Set<Escalao> escaloes) {
        this.escaloes = escaloes;
    }
    public void addEscalao(Escalao escalao){
        if(!this.escaloes.contains(escalao)){
            escaloes.add(escalao);
        }
    }
}
