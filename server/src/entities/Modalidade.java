package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@NamedQueries(value = {
        @NamedQuery(
                name = "getAllModalidades",
                query = "SELECT s FROM Modalidade s ORDER BY s.nome" // JPQL
        )
})
@Table(name="MODALIDADES")
public class Modalidade implements Serializable {
   // private List<Socio> socios;
   @ManyToMany(mappedBy = "modalidades")
    private Set<Treinador> treinadores;
   @Id
    private String nome;
   // private List<Escalao> escaloes;

    public Modalidade( String nome) {
      //  this.socios = new LinkedList<Socio>();
        this.treinadores = new LinkedHashSet<>();
        this.nome = nome;
      //  this.escaloes = new LinkedList<Escalao>();
    }
    public Modalidade() {
        this("");
    }
    public Set<Treinador> getTreinadores(){
        return this.treinadores;
    }

    public void setTreinadores(Set<Treinador> treinadores) {
        this.treinadores = treinadores;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }}

//----------------------------------------ESCALAO---------------------------------
    /*
public void addEscalao(Escalao esc){
    if(!escaloes.contains(esc)){
        escaloes.add(esc);
    }
}
    public void removeEscalao(Escalao esc){
        if(escaloes.contains(esc)){
            escaloes.remove(esc);
        }
    }
}
//--------------------------------------SOCIO-----------------------------
public void addSocio(Socio socio){
    if(!escaloes.contains(socio)){
        escaloes.add(socio);
    }
}
    public void removeSocio(Socio socio){
        if(socios.contains(socio)){
            socios.remove(socio);
        }
    }
}
*/
