package dtos;

import entities.SocioType;

import javax.persistence.Id;
import java.io.Serializable;

public class SocioCreateDTO implements Serializable {
    private long idSocio;
    private String name;
    private String email;
    @Id
    private String username;
    private SocioType tipo;
    private String password;

    public SocioCreateDTO() {
    }

    public SocioCreateDTO(long idSocio, String name, String password, String email, String username, SocioType tipo) {
        this.idSocio = idSocio;
        this.name = name;
        this.email = email;
        this.password = password;
        this.username = username;
        this.tipo = tipo;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public SocioType getTipo() {
        return tipo;
    }

    public void setTipo(SocioType tipo) {
        this.tipo = tipo;
    }
}