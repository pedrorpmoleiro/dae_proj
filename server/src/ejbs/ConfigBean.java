package ejbs;

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
    private EscalaoBean escalaoBean;
    public ConfigBean() {
    }

    @PostConstruct
    public void populateDB(){
        try{
            modalidadeBean.create("BASQUETBALL");
            modalidadeBean.create("FOOTEBOLL");
            escalaoBean.create("SUB20","BASQUETBALL");
            escalaoBean.create("SUB22","BASQUETBALL");
            escalaoBean.create("SUB19","BASQUETBALL");

        }catch (Exception e){
            System.err.println(e.getMessage());
        }

    }
}
