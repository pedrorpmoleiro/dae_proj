package dtos;

import javax.persistence.Id;
import java.io.Serializable;

public class AdministradorDTO implements Serializable {

    private long idSocio;
    private String password;
    private String name;
    private String email;
    @Id
    private String username;
    public AdministradorDTO() {
    }

    public AdministradorDTO(long idSocio, String username, String password, String name, String email) {
        this.idSocio = idSocio;
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
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
}
