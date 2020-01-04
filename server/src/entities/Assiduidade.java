package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "ASSIDUIDADES")
public class Assiduidade implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private long id;
    @NotNull
    private String username;
    @NotNull
    private boolean assiteu;
    @ManyToOne
    @JoinColumn(name = "AULA_ID")
    private Aula aula;

    public Assiduidade(String username, boolean assiteu) {
        this.username = username;
        this.assiteu = assiteu;
    }
    public Assiduidade(){

    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isAssiteu() {
        return assiteu;
    }

    public void setAssiteu(boolean assiteu) {
        this.assiteu = assiteu;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Aula getAula() {
        return aula;
    }

    public void setAula(Aula aula) {
        this.aula = aula;
    }
}
