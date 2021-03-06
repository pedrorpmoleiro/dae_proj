package ejbs;

import entities.Treinador;
import exceptions.MyEntityExistsException;
import exceptions.MyEntityNotFoundException;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless(name = "TreinadorEJB")
public class TreinadorBean {
    @PersistenceContext
    EntityManager em;

    public void create( String username,String password, String name, String email)throws MyEntityExistsException {

        try {
            Treinador treinador = em.find(Treinador.class, username);
            if (treinador == null) {
                treinador = new Treinador(username,password, name, email);
                em.persist(treinador);
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
            Treinador treinador = em.find(Treinador.class, username);
            if (treinador != null) {
                treinador.setDelete(true);
                em.persist(treinador);
            } else {
                throw new MyEntityExistsException("Error: treinador dont exists");
            }
        }catch (Exception e){
            System.out.println("Server error");
        }
    }
    public void update(String username, String name, String email) throws MyEntityNotFoundException {
        Treinador treinador = em.find(Treinador.class, username);
        if (treinador != null) {
            em.lock(treinador, LockModeType.OPTIMISTIC);

            treinador.setName(name);
            treinador.setEmail(email);

            em.persist(treinador);
        }else{
            throw new MyEntityNotFoundException("Treinador not found");
        }
    }*/

    public List<Treinador> all() {
        try {
            // remember, maps to: “SELECT s FROM Student s ORDER BY s.name”
            return (List<Treinador>)
                    em.createNamedQuery("getAllTreinadores").getResultList();
        } catch (Exception e) {
            throw new EJBException("ERROR_RETRIEVING_TREINADORES", e);
        }

    }

    public Treinador findTreinador(String idSocio) {
        try {
            return em.find(Treinador.class, idSocio);
        } catch (Exception e) {
            throw new EJBException("ERROR_FINDING_TREINADOR", e);
        }
    }
}
