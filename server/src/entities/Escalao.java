package entities;

import javax.persistence.*;


@Table(name = "ESCALAOES", uniqueConstraints =
@UniqueConstraint(columnNames = {"NAME"}))
@Entity
public class Escalao {
    @Id
    private String code;
    @ManyToOne
    @JoinColumn(name = "MODALIDADE_CODE")
    private Modalidade modalidade;
    private String name;

    public Escalao(String code, Modalidade modalidade, String name) {
        this.code = code;
        this.modalidade = modalidade;
        this.name = name;
    }
    public  Escalao(){
        this("",null,"");
    }
    public void setId(String name) {
        this.name = name;
    }


    public String getId() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
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
}
