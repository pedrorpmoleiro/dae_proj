package entities;

import javafx.scene.control.TableColumn;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="EPOCA")
public class Epoca {
    @Id
    private String nome;
    @ManyToMany(mappedBy = "epocas")
    private Set<Modalidade> modalidades;
    public void setNome(String nome) {
        this.nome = nome;
    }


    public String getNome() {
        return nome;
    }
}
