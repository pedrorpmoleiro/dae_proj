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

    public void create(long idSocio, String password, String name, String email)throws MyEntityExistsException {

        try {
            Treinador treinador = em.find(Treinador.class, idSocio);
            if (treinador == null) {
                treinador = new Treinador(idSocio, password, name, email);
                em.persist(treinador);
            } else {
                throw new MyEntityExistsException("Invalid Username: Username in use");
            }
        }catch (Exception e){
            System.out.println("Server error");
        }
    }

    public void update(long idSocio, String password, String name, String email) throws MyEntityNotFoundException {
        Treinador treinador = em.find(Treinador.class, idSocio);
        if (treinador != null) {
            em.lock(treinador, LockModeType.OPTIMISTIC);

            treinador.setIdSocio(idSocio);
            treinador.setPassword(password);
            treinador.setName(name);
            treinador.setEmail(email);

            em.persist(treinador);
        }else{
            throw new MyEntityNotFoundException("Treinador not found");
        }
    }

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
