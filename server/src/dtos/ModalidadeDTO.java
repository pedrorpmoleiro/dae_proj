package dtos;

import entities.Escalao;

import java.util.LinkedHashSet;
import java.util.Set;

public class ModalidadeDTO {
    private String nome;
    private Set<EscalaoDTO> escaloes;

    public ModalidadeDTO(String nome) {
        this.nome = nome;
        this.escaloes=new LinkedHashSet<>();
    }
    public ModalidadeDTO(){

    }
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Set<EscalaoDTO> getEscaloes() {
        return escaloes;
    }

    public void setEscaloes(Set<EscalaoDTO> escaloes) {
        this.escaloes = escaloes;
    }
}
