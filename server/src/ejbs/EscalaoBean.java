package ejbs;

import entities.Escalao;
import entities.Modalidade;
import exceptions.MyEntityExistsException;
import exceptions.MyEntityNotFoundException;


import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless(name = "EscalaoEJB")
public class EscalaoBean {
    public EscalaoBean() {
    }
    @PersistenceContext
    EntityManager em;
    public void create(String nome,String modalidadeCode) throws MyEntityNotFoundException, MyEntityExistsException {
        try {
            Escalao escalao= em.find(Escalao.class,nome);
            if(escalao!=null){
                throw  new MyEntityExistsException("ERROR_ENTITY_EXIST");
            }
                Modalidade modalidade= em.find(Modalidade.class,modalidadeCode);
                if(modalidade!=null){
                    escalao=new Escalao(nome,modalidade,nome);
                    modalidade.addEscalao(escalao);
                    em.persist(escalao);
                }else{
                    throw new MyEntityNotFoundException("ERROR_ENTITY_NOTFOUND");
                }


        }catch (MyEntityNotFoundException e){
                throw  e;
        }catch (MyEntityExistsException e){
                throw  e;
        }catch (Exception e){
            throw new EJBException(e.getMessage());
        }
    }
}
