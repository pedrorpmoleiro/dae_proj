package dtos;

import java.util.LinkedHashSet;
import java.util.Set;

public class DiaDTO {
    private Set<AulaDTO> aulas;
    private String dia;

    public DiaDTO(String dia) {
        this.aulas = new LinkedHashSet<>();
        this.dia = dia;
    }
    public DiaDTO(){

    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public Set<AulaDTO> getAulas() {
        return aulas;
    }

    public void setAulas(Set<AulaDTO> aulas) {
        this.aulas = aulas;
    }
}
