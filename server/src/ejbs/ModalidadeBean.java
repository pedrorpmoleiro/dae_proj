package ejbs;

import entities.Modalidade;
import entities.ModalidadeType;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless(name = "ModalidadeEJB")
public class ModalidadeBean {
    public ModalidadeBean() {
    }
    @PersistenceContext
    EntityManager em;
    public void create(String nome){
            try {
                Modalidade modalidade= em.find(Modalidade.class,nome);
                if(modalidade==null){
                    modalidade=new Modalidade(nome);
                    em.persist(modalidade);
                }

            }catch (Exception e){

            }
    }
    public List<Modalidade> all() {
        try {
            // remember, maps to: “SELECT s FROM Student s ORDER BY s.name”
            return (List<Modalidade>)
                    em.createNamedQuery("getAllModalidades").getResultList();
        } catch (Exception e) {
            throw new EJBException("ERROR_RETRIEVING_MODALIDADES", e);
        }

    }
    public Modalidade findStudent(String name) throws MyEntityNotFoundException{
        try{
            Modalidade modalidade= em.find(Modalidade.class, name);
            if(modalidade==null){
                throw new MyEntityNotFoundException("MODALIDADE_NOT_EXIST");
            }
            else {
                return modalidade;
            }
        }catch (MyEntityNotFoundException e){
            throw  e;
        }
        catch (Exception e) {
            throw new EJBException("ERROR_FINDING_MODALIDADE", e);
        }
    }
}
