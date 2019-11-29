package ejbs;

import entities.ProductType;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.util.logging.Level;
import java.util.logging.Logger;

@Startup
@Singleton(name = "ConfigEJB")
public class ConfigBean {
    @EJB
    private ModalidadeBean modalidadeBean;
    @EJB
    private ProductBean productBean;

    private static final Logger logger = Logger.getLogger("ejbs.ConfigBean");

    @PostConstruct
    public void populateDB() {
        try {
            //modalidadeBean.create("BASQUETBALL");
            //modalidadeBean.create("FOOTEBOLL");

            productBean.create(1, ProductType.AULA, "123", 24.0);
            productBean.create(2, ProductType.ARTIGO, "123", 124.0);
            productBean.create(3, ProductType.INSCRICAO, "123", 424.0);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            logger.log(Level.WARNING, ex.getMessage());
        }
    }
}
