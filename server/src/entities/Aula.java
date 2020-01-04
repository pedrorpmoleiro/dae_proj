package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="AULAS")
public class Aula implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private long id;
    @ManyToOne
    @JoinColumn(name = "DIA_ID")
    private Dia dia;
    @NotNull
    private String description;
    @NotNull
    private int horaInicio;
    @NotNull
    private int horaFim;
    @OneToMany(mappedBy = "aula", cascade = CascadeType.REMOVE )
    private Set<Assiduidade> assiduidades;

    public Aula(String description, int horaInicio, int horaFim) {
        this.description = description;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
        this.dia=null;
        this.assiduidades=new LinkedHashSet<>();
    }
    public Aula(){

    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Dia getDia() {
        return dia;
    }

    public void setDia(Dia dia) {
        this.dia = dia;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(int horaInicio) {
        this.horaInicio = horaInicio;
    }

    public int getHoraFim() {
        return horaFim;
    }

    public void setHoraFim(int horaFim) {
        this.horaFim = horaFim;
    }
    public void addAssiduidade(Assiduidade assiduidade){
        if(!this.assiduidades.contains(assiduidade)){
            this.assiduidades.add(assiduidade);
        }
    }

    public Set<Assiduidade> getAssiduidades() {
        return assiduidades;
    }

    public void setAssiduidades(Set<Assiduidade> assiduidades) {
        this.assiduidades = assiduidades;
    }
}
