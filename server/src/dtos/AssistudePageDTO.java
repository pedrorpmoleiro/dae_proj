package dtos;

import java.util.LinkedHashSet;
import java.util.Set;

public class AssistudePageDTO {
    private Set<EscalaoDTO> escaloes;

    public AssistudePageDTO() {
        this.escaloes=new LinkedHashSet<>();
    }

    public Set<EscalaoDTO> getEscaloes() {
        return escaloes;
    }

    public void setEscaloes(Set<EscalaoDTO> escaloes) {
        this.escaloes = escaloes;
    }
}
