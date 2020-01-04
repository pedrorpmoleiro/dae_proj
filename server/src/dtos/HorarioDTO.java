package dtos;

import entities.Dia;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class HorarioDTO implements Serializable {
    private List<DiaDTO> dias;

    public HorarioDTO() {
        this.dias = new LinkedList<>();
    }

    public List<DiaDTO> getDias() {
        return dias;
    }

    public void setDias(List<DiaDTO> dias) {
        this.dias = dias;
    }
}
