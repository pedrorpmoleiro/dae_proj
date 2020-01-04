package dtos;

import java.util.LinkedHashSet;
import java.util.Set;

public class AulaDTO {
    private String dia;
    private String description;
    private int horaInicio;
    private int horaFim;
    private Set<AssiduidadeDTO> assistudes;

    public AulaDTO(String dia, String description, int horaInicio, int horaFim) {
        this.dia = dia;
        this.description = description;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
        this.assistudes=new LinkedHashSet<>();
    }
    public AulaDTO(){

    }
    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(int horaInicio) {
        this.horaInicio = horaInicio;
    }

    public int getHoraFim() {
        return horaFim;
    }

    public void setHoraFim(int horaFim) {
        this.horaFim = horaFim;
    }

    public Set<AssiduidadeDTO> getAssistudes() {
        return assistudes;
    }

    public void setAssistudes(Set<AssiduidadeDTO> assistudes) {
        this.assistudes = assistudes;
    }
}
