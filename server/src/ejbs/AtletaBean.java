package ejbs;

import entities.Atleta;
import exceptions.MyEntityExistsException;
import exceptions.MyEntityNotFoundException;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless(name = "AtletaEJB")
public class AtletaBean {

    @PersistenceContext
    EntityManager em;

    public void create(String username,String password, String name, String email)throws MyEntityExistsException {

        try {
            Atleta atleta = em.find(Atleta.class, username);
            if (atleta == null) {
                atleta = new Atleta(username,password, name, email);
                em.persist(atleta);
            } else {
                throw new MyEntityExistsException("Invalid Username: Username in use");
            }
        }catch (Exception e){
            System.out.println("Server error");
        }
    }
/*
    public void remove(String username)throws MyEntityExistsException {

        try {
            Atleta atleta = em.find(Atleta.class, username);
            if (atleta != null) {
                atleta.setDelete(true);
                em.persist(atleta);
            } else {
                throw new MyEntityExistsException("Error: User dont exists");
            }
        }catch (Exception e){
            System.out.println("Server error");
        }
    }
    public void update(String username, String name, String email) throws MyEntityNotFoundException{
        Atleta atleta = em.find(Atleta.class, username);
        if (atleta != null) {
            em.lock(atleta, LockModeType.OPTIMISTIC);

            atleta.setName(name);
            atleta.setEmail(email);

            em.persist(atleta);
        }else{
            throw new MyEntityNotFoundException("Atleta not found");
        }
    }*/

    public List<Atleta> all() {
        try {
            // remember, maps to: “SELECT s FROM Student s ORDER BY s.name”
            return (List<Atleta>)
                    em.createNamedQuery("getAllAtletas").getResultList();
        } catch (Exception e) {
            throw new EJBException("ERROR_RETRIEVING_ATLETAS", e);
        }

    }

    public Atleta findAtleta(String username) {
        try {
            return em.find(Atleta.class, username);
        } catch (Exception e) {
            throw new EJBException("ERROR_FINDING_ATLETAS", e);
        }
    }
}
