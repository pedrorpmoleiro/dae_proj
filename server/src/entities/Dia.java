package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name="DIAS")
public class Dia implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private long id;
    @ManyToOne
    @JoinColumn(name = "HORARIO_ID")
    private Horario horario;
    @Enumerated(EnumType.STRING)
    private TipoDia diaType;

    @OneToMany(mappedBy = "dia", cascade = CascadeType.REMOVE)
    private Set<Aula> aulas;

    public Dia(TipoDia dia) {
        this.diaType = dia;
        this.horario=null;
        this.aulas=new LinkedHashSet<>();
    }

    public Dia(){
        this(null);
    }

    public Horario getHorario() {
        return horario;
    }

    public void setHorario(Horario horario) {
        this.horario = horario;
    }

    public TipoDia getDiaType() {
        return diaType;
    }

    public void setDiaType(TipoDia diaType) {
        this.diaType = diaType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void addAula(Aula aula) {
        if(!aulas.contains(aula)){
            aulas.add(aula);
        }
    }

    public Set<Aula> getAulas() {
        return aulas;
    }

    public void setAulas(Set<Aula> aulas) {
        this.aulas = aulas;
    }
}
