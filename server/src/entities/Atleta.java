package entities;


import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedHashSet;
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
    @ManyToMany
    @JoinTable(name = "ESCALAO_ATLETA",
            joinColumns = @JoinColumn(name = "ATLETA_USERNAME",referencedColumnName = "USERNAME"),
            inverseJoinColumns
                    = @JoinColumn(name = "ESCALAO_ID",referencedColumnName = "CODE"))
    private Set<Escalao> escaloes;

    public Atleta() {
    }

    public Atleta(String username, String password, String name, String email) {
        super(username, password, name, email);
        setTipo(SocioType.ATLETA);
        this.escaloes = new LinkedHashSet<>();
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
