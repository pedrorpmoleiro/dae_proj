package dtos;

import java.util.LinkedHashSet;
import java.util.Set;

public class EpocaDTO {
    private String nome;
    private Set<ModalidadeDTO> modalidades;

    public EpocaDTO(String nome) {
        this.nome = nome;
        this.modalidades=new LinkedHashSet<>();
    }
    public  EpocaDTO(){

    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Set<ModalidadeDTO> getModalidades() {
        return modalidades;
    }

    public void setModalidades(Set<ModalidadeDTO> modalidades) {
        this.modalidades = modalidades;
    }
}
