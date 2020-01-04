package ejbs;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless(name = "HorarioEJB")
public class HorarioBean {
    public HorarioBean() {
    }
    @PersistenceContext
    EntityManager em;
    public void createHorario(){

    }
}
