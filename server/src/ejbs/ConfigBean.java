package ejbs;

import entities.PaymentStatus;
import entities.ProductType;
import exceptions.MyEntityExistsException;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.time.Instant;
import java.util.Date;

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
    @EJB
    private ProductBean productBean;
    @EJB
    private PaymentBean paymentBean;

    public ConfigBean() {
    }

    @PostConstruct
    public void populateDB() {
        try {
            modalidadeBean.create("BASQUETBALL");
            modalidadeBean.create("FOOTEBOLL");

            /* Admins */

            administradorBean.create("kenalx1999", "123123", "kevin", "kevin@gmail.com");
            administradorBean.create("chaca", "123123", "andres", "andres@gmail.com");
            administradorBean.create("pedrinsky", "123123", "pedro", "pedro@gmail.com");

            /* Treinador */

            treinadorBean.create("lucho", "456456", "luis", "luis@gmail.com");
            treinadorBean.create("mateo", "456456", "mateo", "mateo@gmail.com");

            /*Atleta*/
            atletaBean.create("mario", "789789", "mario", "mario@gmail.com");
            atletaBean.create("santiago", "789789", "santiago", "santiago@gmail.com");

            // PRODUCTS
            productBean.create(1, ProductType.AULA, "123", 24.0);
            productBean.create(2, ProductType.ARTIGO, "123", 124.0);
            productBean.create(3, ProductType.INSCRICAO, "123", 424.0);

            // PAYMENTS
            paymentBean.create(1, "lucho", 1, Instant.now().getEpochSecond(), 2, 200.0,
                    PaymentStatus.PAID, "RECEIPT");
            paymentBean.create(2, "mario", 3, Instant.now().getEpochSecond(), 1, 50.0,
                    PaymentStatus.NOT_PAID, "RECEIPT");
            paymentBean.create(3, "mateo", 2, Instant.now().getEpochSecond(), 6, 2400.0,
                    PaymentStatus.PARCIAL, "RECEIPT");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
