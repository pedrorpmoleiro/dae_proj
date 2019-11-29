package ejbs;

import entities.Administrador;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import java.io.Console;
import java.util.List;

@Stateless(name = "AdministradorEJB")
public class AdministradorBean {

    @PersistenceContext
    EntityManager em;

    public void create(String username, String password, String name, String email) {

        try {
            Administrador administrador = em.find(Administrador.class, username);
            if (administrador == null) {
                    administrador = new Administrador(username, password, name, email);
                    em.persist(administrador);
                } else {
                System.out.println("Ese username ya esta siendo utilizado");
            }
        }catch (Exception e){
            System.out.println("Server error");
        }
    }

    public void update(String username, String password, String name, String email){
        Administrador administrador = em.find(Administrador.class, username);
        if (administrador != null) {
            em.lock(administrador, LockModeType.OPTIMISTIC);

                administrador.setUsername(username);
                administrador.setPassword(password);
                administrador.setName(name);
                administrador.setEmail(email);

                em.persist(administrador);
        }else{
            System.out.println("No existe ese Estudante");
        }
    }

    public List<Administrador> all() {
        try {
            // remember, maps to: “SELECT s FROM Student s ORDER BY s.name”
            return (List<Administrador>)
                    em.createNamedQuery("getAllAdministradores").getResultList();
        } catch (Exception e) {
            throw new EJBException("ERROR_RETRIEVING_STUDENTS", e);
        }

    }

    public Administrador findAdministrador(String username) {
        try {
            return em.find(Administrador.class, username);
        } catch (Exception e) {
            throw new EJBException("ERROR_FINDING_STUDENT", e);
        }
    }
}
