package ejbs;

import entities.*;
import exceptions.MyEntityNotFoundException;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Set;

@Stateless(name = "HorarioEJB")
public class HorarioBean {
    public HorarioBean() {
    }
    @PersistenceContext
    EntityManager em;
    @EJB
    private EscalaoBean escalaoBean;
    public void createAssiduidade(String usernameT,String escalao,String epoca,TipoDia tipoDia,int horaInicio,int horaFim,String usernameA,boolean isAssitude) throws MyEntityNotFoundException{
        try {
            Aula aula=findAulaTreinador(usernameT,escalao,epoca,tipoDia,horaInicio,horaFim);
            if(em.find(Atleta.class,usernameA)!=null){
                Assiduidade assiduidade= new Assiduidade(usernameA,isAssitude);
                assiduidade.setAula(aula);
                aula.addAssiduidade(assiduidade);
                em.persist(assiduidade);
            }else{
                throw new MyEntityNotFoundException("ATLETA_NOT_FOUND");
            }

        }catch (MyEntityNotFoundException e){
            throw e;
        }catch (Exception e){
            throw new EJBException( e.getMessage());
        }

    }
    public Aula findAula(String epocaNome,String modalidadeNome,String escalaoNome,String diaTipo,int horaInicio,int horaFim) throws MyEntityNotFoundException {
        try{
            Escalao escalao= escalaoBean.findEscalao(epocaNome,modalidadeNome,escalaoNome);
            if(escalao!=null){
                List<Dia> listaDias=escalao.getHorario().getDias();
                Dia findDia=null;
                for (Dia dia:listaDias) {
                    if(dia.getDiaType().equals(TipoDia.valueOf(diaTipo))){
                        findDia=dia;
                        break;
                    }
                }
                if(findDia==null){
                    throw  new MyEntityNotFoundException("DIA_NOT_FOUND");
                }else{
                    Aula aulaFind=null;
                    for (Aula aula:findDia.getAulas()) {
                        if(aula.getHoraInicio()==horaInicio&&aula.getHoraFim()==horaFim){
                            aulaFind=aula;
                        }
                    }
                    if(aulaFind==null){
                        throw  new MyEntityNotFoundException("AULA_NOT_FOUND");
                    }else{
                        return aulaFind;
                    }
                }
            }
            return null;

        }catch (MyEntityNotFoundException e){
            throw e;
        }
    }
    public Aula findAulaTreinador(String username,String escalao,String epoca,TipoDia tipoDia,int horaInicio,int horaFim) throws MyEntityNotFoundException{
        try {
            Treinador treinador=  (Treinador) em.find(Treinador.class,username);
            if(treinador==null){
                throw  new MyEntityNotFoundException("TREINADOR_NOT_FOUND");
            }else{
                Set<Escalao> escaloes= treinador.getEscaloes();
                Escalao escalaoFind=null;
                for (Escalao escalao1:escaloes) {
                    if(escalao1.getName().equals(escalao)&&escalao1.getModalidade().getEpoca().getNome().equals(epoca)){
                        escalaoFind=escalao1;
                        break;
                    }
                }
                List<Dia> listaDias=escalaoFind.getHorario().getDias();
                Dia findDia=null;
                for (Dia dia:listaDias) {
                    if(dia.getDiaType().equals(tipoDia)){
                        findDia=dia;
                        break;
                    }
                }
                if(findDia==null){
                    throw  new MyEntityNotFoundException("DIA_NOT_FOUND");
                }else{
                    Aula aulaFind=null;
                    for (Aula aula:findDia.getAulas()) {
                        if(aula.getHoraInicio()==horaInicio&&aula.getHoraFim()==horaFim){
                            aulaFind=aula;
                        }
                    }
                    if(aulaFind==null){
                        throw  new MyEntityNotFoundException("AULA_NOT_FOUND");
                    }else{
                        return aulaFind;
                    }
                }
            }
        }catch (MyEntityNotFoundException e){
            throw e;
        }

    }
}
