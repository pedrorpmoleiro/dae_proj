package dtos;

import java.util.LinkedHashSet;
import java.util.Set;

public class EscalaoDTO {
    private String name;
    private Set<AtletaDTO> atletas;
    private Set<TreinadorDTO> treinadores;
    private String modalidade;
    private String epoca;

    public EscalaoDTO( String name,String modalidade,String epoca) {
        this.name = name;
        this.atletas=new LinkedHashSet<>();
        this.treinadores=new LinkedHashSet<>();
        this.modalidade=modalidade;
        this.epoca=epoca;
    }
    public EscalaoDTO(){
        this("","","");
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<AtletaDTO> getAtletas() {
        return atletas;
    }

    public void setAtletas(Set<AtletaDTO> atletas) {
        this.atletas = atletas;
    }

    public Set<TreinadorDTO> getTreinadores() {
        return treinadores;
    }

    public void setTreinadores(Set<TreinadorDTO> treinadores) {
        this.treinadores = treinadores;
    }

    public String getModalidade() {
        return modalidade;
    }

    public void setModalidade(String modalidade) {
        this.modalidade = modalidade;
    }

    public String getEpoca() {
        return epoca;
    }

    public void setEpoca(String epoca) {
        this.epoca = epoca;
    }
}
