package ejbs;

import entities.*;
import exceptions.MyEntityExistsException;
import exceptions.MyEntityNotFoundException;
import exceptions.MyIllegalArgumentException;

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
    public void create(String nome,String epoca)throws MyEntityExistsException, MyEntityNotFoundException{
            try {
                Epoca epoca1=em.find(Epoca.class,epoca);
                if(epoca1==null){
                    throw new  MyEntityNotFoundException("EPOCA_NOT_EXIST");
                }else{
                    Modalidade modalidade=null;
                    for (Modalidade modalidade1:epoca1.getModalidades()) {
                        if((modalidade1.getEpoca().getNome().equals(epoca) && modalidade1.getNome().equals(nome))){
                            modalidade=modalidade1;
                            break;
                        }
                    }
                    if(modalidade!=null){
                        throw  new MyEntityExistsException("MODALIDADE_EXIST");
                    }else{
                        modalidade=new Modalidade(nome.toUpperCase(),epoca1);
                        em.persist(modalidade);
                        epoca1.addModalidade(modalidade);
                    }
                }


            }catch (MyEntityNotFoundException e){
                throw  e;
            }catch (MyEntityExistsException e){
                    throw  e;
            }catch (Exception e){
                throw new EJBException("ERROR_FINDING_MODALIDADE", e);
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
    public Modalidade findModalidade(String epoca,String nome) throws MyEntityNotFoundException {
        try{
            Epoca epoca1= em.find(Epoca.class, epoca);
            if(epoca1==null){
                throw new MyEntityNotFoundException("EPOCA_NOT_EXIST");
            }
            else {
                Modalidade modalidade=null;
                for (Modalidade modalidade1:epoca1.getModalidades()) {
                    if((modalidade1.getEpoca().getNome().equals(epoca) && modalidade1.getNome().equals(nome))){
                          modalidade=modalidade1;
                          break;
                    }
                }
                if(modalidade==null){
                    throw new MyEntityNotFoundException("MODALIDADE_NOT_EXIST");
                }else{
                    return modalidade;
                }
            }
        }catch (MyEntityNotFoundException e){
            throw  e;
        }
        catch (Exception e) {
            throw new EJBException("ERROR_FINDING_MODALIDADE", e);
        }
    }
    public void update(String epoca,String name,String newName){
        try {
            Modalidade modalidade = this.findModalidade(epoca, name);
            if(modalidade!=null){
                modalidade.setNome(newName);
            }
        }catch (Exception e){
            System.err.println("SYS_ERROR_UPDATE" + e.getMessage());
        }

    }
    public void delete(String nome) throws MyEntityNotFoundException, MyIllegalArgumentException {
        try{
            Modalidade modalidade=em.find(Modalidade.class,nome);
            if(modalidade!=null){
                em.remove(modalidade);
            }else{
                throw new MyEntityNotFoundException("MODALIDADE_NOT_EXIST");
            }
        }catch (MyEntityNotFoundException e){
            throw  e;
        }catch (Exception e){

        }
    }

    public void enrollAtletaEscalao(String code,String username,String epocaNome,String modalidadeNome) throws MyEntityNotFoundException {
        try{
            Epoca epoca= (Epoca) em.find(Epoca.class,epocaNome);
            if(epoca==null){
                throw new MyEntityNotFoundException("EPOCA_NOT_FOUND");
            }else{
                Modalidade modalidade= null;
                for (Modalidade modalidade1:epoca.getModalidades()) {
                    if(modalidade1.getNome().equals(modalidadeNome)&&modalidade1.getEpoca().equals(epoca)){
                        modalidade=modalidade1;
                        break;
                    }
                }
                if(modalidade==null){
                    throw new MyEntityNotFoundException("MODALIDADE_NOT_FOUND");
                }else{
                    Escalao escalao=null;
                    for (Escalao escalao1:modalidade.getEscaloes()) {
                        if(escalao1.getName().equals(code)){
                            escalao=escalao1;
                            break;
                        }
                    }
                    if(escalao!=null){
                        Atleta atleta= (Atleta) em.find(Atleta.class,username);
                        if(atleta!=null){
                            atleta.setEscalao(escalao);
                            escalao.addAtleta(atleta);
                        }else{
                            throw new MyEntityNotFoundException("ATLETA_NOT_FOUND");
                        }
                    }else{
                        throw new MyEntityNotFoundException("ESCALAO_NOT_FOUND");
                    }
                }

            }
        }catch (MyEntityNotFoundException e){
            throw e;
        }
        catch (Exception e){
            throw new EJBException( e.getMessage());
        }
    }
    public void enrollTreinadorEscalao(String code,String username,String epocaNome,String modalidadeNome) throws MyEntityNotFoundException {
        try{
            Epoca epoca= (Epoca) em.find(Epoca.class,epocaNome);
            if(epoca==null){
                throw new MyEntityNotFoundException("EPOCA_NOT_FOUND");
            }else{
                Modalidade modalidade= null;
                for (Modalidade modalidade1:epoca.getModalidades()) {
                    if(modalidade1.getNome().equals(modalidadeNome)&&modalidade1.getEpoca().equals(epoca)){
                        modalidade=modalidade1;
                        break;
                    }
                }
                if(modalidade==null){
                    throw new MyEntityNotFoundException("MODALIDADE_NOT_FOUND");
                }else{
                    Escalao escalao=null;
                    for (Escalao escalao1:modalidade.getEscaloes()) {
                        if(escalao1.getName().equals(code)){
                            escalao=escalao1;
                            break;
                        }
                    }
                    if(escalao!=null){
                        Treinador treinador= (Treinador) em.find(Treinador.class,username);
                        if(treinador!=null){
                            treinador.setEscalao(escalao);
                            escalao.addTreinador(treinador);
                        }else{
                            throw new MyEntityNotFoundException("ATLETA_NOT_FOUND");
                        }
                    }else{
                        throw new MyEntityNotFoundException("ESCALAO_NOT_FOUND");
                    }
                }

            }
        }catch (MyEntityNotFoundException e){
            throw e;
        }
        catch (Exception e){
            throw new EJBException( e.getMessage());
        }
    }



}
