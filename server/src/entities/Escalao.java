package entities;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;


@Table(name = "ESCALOES")
@Entity
public class Escalao {
    @Id
    @GeneratedValue (strategy=GenerationType.SEQUENCE)
    private long code;
    @ManyToOne
    @JoinColumn(name = "MODALIDADE_CODE")
    private Modalidade modalidade;
    private String name;
    @ManyToMany(mappedBy = "escaloes")
    private Set<Atleta> atletas;
    @ManyToMany(mappedBy = "escaloes")
    private Set<Treinador> treinadores;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "horario_id", referencedColumnName = "id")
    private  Horario horario;

    public Escalao(Modalidade modalidade, String name) {
        this.modalidade = modalidade;
        this.name = name;
        this.treinadores=new LinkedHashSet<>();
        this.atletas=new LinkedHashSet<>();
        this.horario=null;
    }
    public  Escalao(){
        this(null,"");
    }
    public void setId(String name) {
        this.name = name;
    }


    public String getId() {
        return name;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public Modalidade getModalidade() {
        return modalidade;
    }

    public void setModalidade(Modalidade modalidade) {
        this.modalidade = modalidade;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    /*TODO sobre los atletas e socios
     */

    public Set<Atleta> getAtletas() {
        return atletas;
    }

    public void setAtletas(Set<Atleta> atletas) {
        this.atletas = atletas;
    }

    public Set<Treinador> getTreinadores() {
        return treinadores;
    }

    public void setTreinadores(Set<Treinador> treinadores) {
        this.treinadores = treinadores;
    }

    public void addAtleta(Atleta atleta) {
        if(!this.atletas.contains(atleta)){
            atletas.add(atleta);
        }
    }
    public void removeAtleta(Atleta atleta) {
        if(this.atletas.contains(atleta)){
            atletas.remove(atleta);
        }
    }

    public void addTreinador(Treinador treinador) {
        if(!this.treinadores.contains(treinador)){
            treinadores.add(treinador);
        }
    }
    public void removeTreinador(Treinador treinador) {
        if(this.treinadores.contains(treinador)){
            treinadores.remove(treinador);
        }
    }

    public Horario getHorario() {
        return horario;
    }

    public void setHorario(Horario horario) {
        this.horario = horario;
    }
}
