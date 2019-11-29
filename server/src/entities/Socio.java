package entities;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Table(name="SOCIOS")
public abstract class Socio {
    @Id
    private long idSocio;
    @NotNull
    private String password;
    @NotNull
    private String name;
    @NotNull
    @Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\."
            + "[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@"
            + "(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?",
            message = "{invalid.email}")
    private String email;

    public Socio(long idSocio, String password, String name, String email) {
        this.idSocio = idSocio;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public Socio() {
    }

    public long getIdSocio() {
        return idSocio;
    }

    public void setIdSocio(long id) {
        this.idSocio = id;
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
