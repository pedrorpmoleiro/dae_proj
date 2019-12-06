package dtos;

import javax.persistence.Id;
import java.io.Serializable;

public class AdministradorDTO implements Serializable {
    @Id
    private long idSocio;
    private String password;
    private String name;
    private String email;
    public AdministradorDTO() {
    }

    public AdministradorDTO(long idSocio, String password, String name, String email) {
        this.idSocio = idSocio;
        this.password = password;
        this.name = name;
        this.email = email;
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
