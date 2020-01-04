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

    public void create(String username,String password, String name, String email)throws MyEntityExistsException {

        try {
            Socio socio = em.find(Socio.class, username);
            if (socio == null) {
                socio = new Socio(username,password, name, email);
                em.persist(socio);
            } else {
                throw new MyEntityExistsException("Invalid Username: Username in use");
            }
        }catch (Exception e){
            System.out.println("Server error");
        }
    }

    public boolean remove(String username)throws MyEntityExistsException {

        try {
            Socio socio = em.find(Socio.class, username);
            if (socio != null) {
                socio.setDelete(true);
                em.persist(socio);
                return true;
            } else {
                throw new MyEntityExistsException("Error: User dont exists");
            }
        }catch (Exception e){
            System.out.println("Server error");
        }
        return false;
    }
    public void update(String username, String name, String email) throws MyEntityNotFoundException{
        Socio socio = em.find(Socio.class, username);
        if (socio != null) {
            em.lock(socio, LockModeType.PESSIMISTIC_WRITE);

            socio.setName(name);
            socio.setEmail(email);

            em.persist(socio);
        }else{
            throw new MyEntityNotFoundException("Socio not found");
        }
    }


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
