package dtos;

import javax.persistence.Id;
import java.io.Serializable;

public class TreinadorDTO implements Serializable {
    @Id
    private String username;
    private long idSocio;
    private String name;
    private String email;
    public TreinadorDTO() {
    }

    public TreinadorDTO(long idSocio,String username, String name, String email) {
        this.idSocio = idSocio;
        this.username = username;
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
