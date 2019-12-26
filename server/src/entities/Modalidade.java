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
@Table(name = "MODALIDADES")
public class Modalidade implements Serializable {
    // private List<Socio> socios;
   /*
   @ManyToMany(mappedBy = "modalidades")
    private Set<Treinador> treinadores;
    */
    @Id
    private String nome;
    @OneToMany(mappedBy = "modalidade", cascade = CascadeType.REMOVE)
    private Set<Escalao> escaloes;
    @ManyToMany
    @JoinTable(name = "EPOCA_MODALIDADE",
            joinColumns = @JoinColumn(name = "MODALIDADE_NOME",referencedColumnName = "CODE"),
            inverseJoinColumns
                    = @JoinColumn(name = "EPOCA_NOME",referencedColumnName = "USERNAME"))
    private Set<Epoca> epocas;

    public Modalidade(String nome) {
        //  this.socios = new LinkedList<Socio>();
        //   this.treinadores = new LinkedHashSet<>();
        this.nome = nome;
        this.escaloes = new LinkedHashSet<>();
        this.epocas=new LinkedHashSet<>();
    }

    public Modalidade() {
        this("");
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Set<Escalao> getEscaloes() {
        return this.escaloes;
    }

    public void setEscaloes(Set<Escalao> escaloes) {
        this.escaloes = escaloes;
    }

    public Set<Epoca> getEpocas() {
        return epocas;
    }

    public void setEpocas(Set<Epoca> epocas) {
        this.epocas = epocas;
    }
//----------------------------------------ESCALAO---------------------------------

    public void addEscalao(Escalao esc) {
        if (!escaloes.contains(esc)) {
            escaloes.add(esc);
        }
    }

    public void removeEscalao(Escalao esc) {
        if (escaloes.contains(esc)) {
            escaloes.remove(esc);
        }
    }
    //------------------------------EPOCA--------------------------------------
    public void addEpoca(Epoca epoca) {
        if (!epocas.contains(epoca)) {
            epocas.add(epoca);
        }
    }
    public void removeEpoca(Epoca epoca) {
        if (epocas.contains(epoca)) {
            epocas.remove(epoca);
        }
    }
}
/*
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
