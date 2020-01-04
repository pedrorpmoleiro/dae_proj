package ejbs;

import entities.PaymentStatus;
import entities.ProductType;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.Base64;

@Startup
@Singleton(name = "ConfigEJB")
public class ConfigBean {
    //@EJB
    //private ModalidadeBean modalidadeBean;
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
    //@EJB
    //private EscalaoBean escalaoBean;

    @PostConstruct
    public void populateDB() {
        try {
            //MODALIDADES
            //modalidadeBean.create("BASQUETBALL");
            //modalidadeBean.create("FOOTEBOLL");

            // ESCALOES
            //escalaoBean.create("SUB20", "BASQUETBALL");
            //escalaoBean.create("SUB22", "BASQUETBALL");
            //escalaoBean.create("SUB19", "BASQUETBALL");

            String password = "123123";
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            String passwordHash = Base64.getEncoder().encodeToString(hash);

            /* Admins */
            administradorBean.create("kenalx1999", passwordHash, "kevin", "kevin@gmail.com");
            administradorBean.create("chaca", passwordHash, "andres", "andres@gmail.com");
            administradorBean.create("pedrinsky", passwordHash, "pedro", "pedro@gmail.com");

            /* Treinador */
            treinadorBean.create("lucho", passwordHash, "luis", "luis@gmail.com");
            treinadorBean.create("mateo", passwordHash, "mateo", "mateo@gmail.com");

            /*Atleta*/
            atletaBean.create("mario", passwordHash, "mario", "mario@gmail.com");
            atletaBean.create("santiago", passwordHash, "santiago", "santiago@gmail.com");

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