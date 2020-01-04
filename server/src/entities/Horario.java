package entities;

import javax.persistence.*;
import javax.xml.ws.Holder;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="HORARIOS")
public class Horario implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private long id;
    @OneToMany(mappedBy = "horario", cascade = CascadeType.REMOVE)
    private List<Dia> dias;
    @OneToOne(mappedBy = "horario")
    private Escalao escalao;


    public Horario(Escalao escalao) {
        this.escalao = escalao;
        this.dias=new LinkedList<>();
        //this.fillDias();
    }
    public Horario(){

    }
    private void fillDias(){
        this.dias.add(new Dia(TipoDia.SEGUNDA_FEIRA));
        this.dias.add(new Dia(TipoDia.TERCA_FEIRA));
        this.dias.add(new Dia(TipoDia.QUARTA_FEIRA));
        this.dias.add(new Dia(TipoDia.QUINTA_FEIRA));
        this.dias.add(new Dia(TipoDia.SEXTA_FEIRA));

    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Dia> getDias() {
        return dias;
    }

    public void setDias(List<Dia> dias) {
        this.dias = dias;
    }

    public Escalao getEscalao() {
        return escalao;
    }

    public void setEscalao(Escalao escalao) {
        this.escalao = escalao;
    }

    public  void addDia(Dia dia){
        if(!this.dias.contains(dia)){
            this.dias.add(dia);
        }
    }
    public  void removeDia(Dia dia){
        if(this.dias.contains(dia)){
            this.dias.remove(dia);
        }
    }
}
