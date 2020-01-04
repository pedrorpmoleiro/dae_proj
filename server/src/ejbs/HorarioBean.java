package ejbs;

import entities.Aula;
import entities.Dia;
import entities.Escalao;
import entities.TipoDia;
import exceptions.MyEntityNotFoundException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless(name = "HorarioEJB")
public class HorarioBean {
    public HorarioBean() {
    }
    @PersistenceContext
    EntityManager em;
    @EJB
    private EscalaoBean escalaoBean;
    public void createAssiduidade(){

    }
    public void findAula(String epocaNome,String modalidadeNome,String escalaoNome,String diaTipo) throws MyEntityNotFoundException {
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
                        //if(aula.ge)
                    }
                }
            }

        }catch (MyEntityNotFoundException e){
            throw e;
        }
    }
}
