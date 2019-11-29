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
    public ConfigBean() {
    }

    @PostConstruct
    public void populateDB(){
        modalidadeBean.create("BASQUETBALL");
        modalidadeBean.create("FOOTEBOLL");
    }
}
