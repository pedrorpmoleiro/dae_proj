package ejbs;

import entities.Epoca;
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
    public void create(String nome,String modalidadeCode,String epoca) throws MyEntityNotFoundException, MyEntityExistsException {
        try {
               Epoca epoca1= em.find(Epoca.class,epoca);
               if(epoca1==null){
                   throw new MyEntityNotFoundException("EPOCA_NOT_FOUND");
               }else{
                   Modalidade modalidade=null;
                   for (Modalidade modalidade1:epoca1.getModalidades()) {
                       if(modalidade1.getNome().equals(modalidadeCode)&&modalidade1.getEpoca().equals(epoca1)){
                           modalidade=modalidade1;
                           break;
                       }
                   }
                   if(modalidade==null){
                       throw  new MyEntityNotFoundException("MODALIDADE_NOT_FOUND");
                   }
                   Escalao escalao= new Escalao(modalidade,nome);
                   modalidade.addEscalao(escalao);
                   em.persist(escalao);
               }


        }catch (MyEntityNotFoundException e){
                throw  e;
        }catch (Exception e){
            throw new EJBException(e.getMessage());
        }
    }
    public Escalao findEscalao(String epocaNome,String modalidadeNome,String escalaoNome) throws MyEntityNotFoundException{
        try {
            Epoca epoca1 = em.find(Epoca.class, epocaNome);
            if (epoca1 == null) {
                throw new MyEntityNotFoundException("EPOCA_NOT_FOUND");
            }
            Modalidade modalidade=null;
            for (Modalidade modalidade1:epoca1.getModalidades()) {
                if(modalidade1.getNome().equals(modalidadeNome)&&modalidade1.getEpoca().getNome().equals(epocaNome)){
                    modalidade=modalidade1;
                    break;
                }
            }
            if(modalidade==null){
                throw  new MyEntityNotFoundException("MODALIDADE_NOT_FOUND");
            }
            Escalao escalao=null;
            for (Escalao escalao1:modalidade.getEscaloes()) {
                if(escalao1.getName().equals(escalaoNome)){
                    escalao=escalao1;
                    break;
                }
            }
            if(escalao==null){
                throw  new MyEntityNotFoundException("ESCALAO_NOT_FOUND");
            }
            return  escalao;
        } catch (MyEntityNotFoundException e) {
                throw  e;
        }catch (Exception e){
            throw new EJBException("ERROR_RETRIEVING_MODALIDADES", e);
        }

    }
    public void updateEscalao(String epocaNome,String modalidadeNome,String escalaoNome,String newEscalaoNome) throws MyEntityNotFoundException{
        try{
            Escalao escalao=findEscalao(epocaNome,modalidadeNome,escalaoNome);
            if(escalao!=null){
                escalao.setName(newEscalaoNome);
            }

        }catch (MyEntityNotFoundException e){
            throw  e;
        }catch (Exception e){
            System.err.println("SYS_ERROR_UPDATE" + e.getMessage());
        }
    }
}
