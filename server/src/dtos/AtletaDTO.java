package dtos;

import entities.Treinador;

import javax.persistence.Id;
import java.util.LinkedHashSet;
import java.util.Set;

public class AtletaDTO {
    @Id
    private long idSocio;
    private String password;
    private String name;
    private String email;
    private Set<Treinador> treinadores;

    public AtletaDTO() {
        this.treinadores = new LinkedHashSet<Treinador>();
    }

    public AtletaDTO(long idSocio, String password, String name, String email) {
        this.idSocio = idSocio;
        this.password = password;
        this.name = name;
        this.email = email;
        this.treinadores = new LinkedHashSet<Treinador>();
    }

    public long getIdSocio() {
        return idSocio;
    }

    public void setIdSocio(long idSocio) {
        this.idSocio = idSocio;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
