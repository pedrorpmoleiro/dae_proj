package ejbs;

import exceptions.MyEntityExistsException;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Startup
@Singleton(name = "ConfigEJB")
public class ConfigBean {
    @EJB
    private ModalidadeBean modalidadeBean;
    @EJB
    private AdministradorBean administradorBean;
    @EJB
    private TreinadorBean treinadorBean;
    @EJB
    private AtletaBean atletaBean;
    public ConfigBean() {
    }

    @PostConstruct
    public void populateDB() {
        try {
        modalidadeBean.create("BASQUETBALL");
        modalidadeBean.create("FOOTEBOLL");

        /* Admins */

            administradorBean.create("kenalx1999","123123","kevin","kevin@gmail.com");
            administradorBean.create("chaca","123123","andres","andres@gmail.com");
            administradorBean.create("pedrinsky","123123","pedro","pedro@gmail.com");

            /* Treinador */

            treinadorBean.create("lucho","456456","luis","luis@gmail.com");
            treinadorBean.create("mateo","456456","mateo","mateo@gmail.com");

            /*Atleta*/
            atletaBean.create("mario","789789","mario","mario@gmail.com");
            atletaBean.create("santiago","789789","santiago","santiago@gmail.com");

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
