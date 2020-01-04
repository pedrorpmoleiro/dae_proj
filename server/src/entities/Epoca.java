package entities;

import javafx.scene.control.TableColumn;

import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;
@Entity
@NamedQueries(value = {
        @NamedQuery(
                name = "getAllEpocas",
                query = "SELECT e FROM Epoca e ORDER BY e.nome" // JPQL
        )
})
@Table(name = "EPOCAS")
public class Epoca implements Serializable {
    @Id
    private String nome;
    @OneToMany(mappedBy = "epoca",cascade = CascadeType.REMOVE)
    private Set<Modalidade> modalidades;

    public Epoca(String nome) {
        this.nome = nome;
        this.modalidades=new LinkedHashSet<>();
    }
    public Epoca() {
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getNome() {
        return nome;
    }

    public Set<Modalidade> getModalidades() {
        return modalidades;
    }

    public void setModalidades(Set<Modalidade> modalidades) {
        this.modalidades = modalidades;
    }

    public void addModalidade(Modalidade modalidade) {
        if (!modalidades.contains(modalidade)) {
            modalidades.add(modalidade);
        }
    }
    public void removeModalidade(Modalidade modalidade) {
        if (modalidades.contains(modalidade)) {
            modalidades.remove(modalidade);
        }
    }
}
