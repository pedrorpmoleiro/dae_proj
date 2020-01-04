package dtos;

import entities.Treinador;

import javax.persistence.Id;
import java.util.LinkedHashSet;
import java.util.Set;

public class AtletaDTO {

    private long idSocio;
    private String name;
    private String email;
    private Set<Treinador> treinadores;
    @Id
    private String username;

    public AtletaDTO() {
        this.treinadores = new LinkedHashSet<Treinador>();
    }

    public AtletaDTO(long idSocio, String username, String name, String email) {
        this.idSocio = idSocio;
        this.username = username;
        this.name = name;
        this.email = email;
        this.treinadores = new LinkedHashSet<Treinador>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getIdSocio() {
        return idSocio;
    }

    public void setIdSocio(long idSocio) {
        this.idSocio = idSocio;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Treinador> getTreinadores() {
        return treinadores;
    }

    public void setTreinadores(Set<Treinador> treinadores) {
        this.treinadores = treinadores;
    }
}
