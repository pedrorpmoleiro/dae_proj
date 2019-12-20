package ejbs;

import entities.Socio;
import exceptions.MyEntityExistsException;
import exceptions.MyEntityNotFoundException;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless(name = "SocioEJB")
public class SocioBean {

    @PersistenceContext
    EntityManager em;
/*
    public void create(String username,String password, String name, String email)throws MyEntityExistsException {

        try {
            Administrador administrador = em.find(Administrador.class, username);
            if (administrador == null) {
                administrador = new Administrador(username,password, name, email);
                em.persist(administrador);
            } else {
                throw new MyEntityExistsException("Invalid Username: Username in use");
            }
        }catch (Exception e){
            System.out.println("Server error");
        }
    }

    public void update(String username, String password, String name, String email) throws MyEntityNotFoundException {
        Administrador administrador = em.find(Administrador.class, username);
        if (administrador != null) {
            em.lock(administrador, LockModeType.OPTIMISTIC);

            administrador.setUsername(username);
            administrador.setPassword(password);
            administrador.setName(name);
            administrador.setEmail(email);

            em.persist(administrador);
        }else{
            throw new MyEntityNotFoundException("Admin not found");
        }
    }*/

    public List<Socio> all() {
        try {
            // remember, maps to: “SELECT s FROM Student s ORDER BY s.name”
            return (List<Socio>)
                    em.createNamedQuery("getAllSocios").getResultList();
        } catch (Exception e) {
            throw new EJBException("ERROR_RETRIEVING_ADMIN", e);
        }

    }

    public Socio findSocio(String username) {
        try {
            return em.find(Socio.class, username);
        } catch (Exception e) {
            throw new EJBException("ERROR_FINDING_ADMIN", e);
        }
    }
}
