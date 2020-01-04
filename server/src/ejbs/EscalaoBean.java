package ejbs;

import entities.*;
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
                   Horario horario= new Horario(escalao);
                   modalidade.addEscalao(escalao);
                   escalao.setHorario(horario);
                   //region HUEVADAS
                   Dia dia1=new Dia(TipoDia.SEGUNDA_FEIRA);
                   Dia dia2=new Dia(TipoDia.TERCA_FEIRA);
                   Dia dia3=new Dia(TipoDia.QUARTA_FEIRA);
                   Dia dia4=new Dia(TipoDia.QUINTA_FEIRA);
                   Dia dia5=new Dia(TipoDia.SEXTA_FEIRA);
                   dia1.setHorario(horario);
                   dia2.setHorario(horario);
                   dia3.setHorario(horario);
                   dia4.setHorario(horario);
                   dia5.setHorario(horario);
                   horario.addDia(dia1);
                   horario.addDia(dia2);
                   horario.addDia(dia3);
                   horario.addDia(dia4);
                   horario.addDia(dia5);

                   em.persist(dia1);
                   em.persist(dia2);
                   em.persist(dia3);
                   em.persist(dia4);
                   em.persist(dia5);

                   //endregion
                   em.persist(escalao);
                   em.persist(horario);
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
    public void createAula(String epocaNome,String modalidadeNome,String escalaoNome,TipoDia dia,String description,int horaInicio,int horaFim) throws MyEntityNotFoundException{
        try{
            Escalao escalao=findEscalao(epocaNome,modalidadeNome,escalaoNome);
            if(escalao!=null){
                Horario horario =escalao.getHorario();
                Dia dia1=null;
                for (Dia dia2:horario.getDias()) {
                    if(dia2.getDiaType().equals(dia)){
                            Aula aula=new Aula(description,horaInicio,horaFim);
                            dia2.addAula(aula);
                            aula.setDia(dia2);
                            em.persist(aula);
                    }
                }
            }

        }catch (MyEntityNotFoundException e){
            throw e;
        }

    }
}
