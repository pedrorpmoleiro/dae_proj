package ejbs;

import entities.Epoca;
import entities.Modalidade;
import exceptions.MyEntityExistsException;
import exceptions.MyEntityNotFoundException;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless(name = "EpocaEJB")
public class EpocaBean {
    public EpocaBean() {
    }
    @PersistenceContext
    EntityManager em;

    public List<Epoca> all() {
        try {
            // remember, maps to: “SELECT s FROM Student s ORDER BY s.name”
            return (List<Epoca>)
                    em.createNamedQuery("getAllEpocas").getResultList();
        } catch (Exception e) {
            throw new EJBException("ERROR_RETRIEVING_MODALIDADES", e);
        }

    }
    public void create(String nome) throws  MyEntityExistsException{
        try {
            Epoca epoca= em.find(Epoca.class,nome.toUpperCase());
            if(epoca==null){
                epoca=new Epoca(nome.toUpperCase());
                em.persist(epoca);
            }else{
                throw  new MyEntityExistsException("ERR_JA_EXISTE");
            }

        }catch (MyEntityExistsException e){
            throw e;
        }catch (Exception e){
            throw new EJBException(e.getMessage());
        }
    }
    public void deleteModalidade(String nome,String epoca) throws  MyEntityNotFoundException{
        try{

            Epoca epoca1=em.find(Epoca.class,epoca);
            if(epoca1!=null){
                Modalidade modalidade=null;
                for (Modalidade modalidade1:epoca1.getModalidades()) {
                    if((modalidade1.getEpoca().getNome().equals(epoca) && modalidade1.getNome().equals(nome))){
                        modalidade=modalidade1;
                        break;
                    }
                }
                if(modalidade!=null){
                    epoca1.removeModalidade(modalidade);
                }else {
                    throw new MyEntityNotFoundException("MODALIDADE_NOT_EXIST");
                }
            }else{
                throw new MyEntityNotFoundException("EPOCA_NOT_EXIST");
            }
        }catch (MyEntityNotFoundException e){
            throw  e;
        }catch (Exception e){

        }
    }
}
