package ejbs;

import entities.Epoca;
import entities.PaymentStatus;
import entities.ProductType;
import entities.TipoDia;

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
    @EJB
    private ModalidadeBean modalidadeBean;
    @EJB
    private EscalaoBean escalaoBean;
    @EJB
    private EpocaBean epocaBean;
    @EJB
    private AtletaBean atletaBean;
    @EJB
    private TreinadorBean treinadorBean;
    @EJB
    private AdministradorBean administradorBean;
    @EJB
    private ProductBean productBean;
    @EJB
    private PaymentBean paymentBean;
    @EJB
    private HorarioBean horarioBean;

    public ConfigBean() {
    }

    @PostConstruct
    public void populateDB() {
        try {
            epocaBean.create("2018-2019");
            epocaBean.create("2019-2020");

            modalidadeBean.create("BASQUETBALL","2018-2019");
            modalidadeBean.create("BASQUETBALL","2019-2020");
            modalidadeBean.create("SOCCER","2019-2020");

            escalaoBean.create("SUB18","SOCCER","2019-2020");
            escalaoBean.create("SUB20","BASQUETBALL","2018-2019");

            String password = "123123";
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            String passwordHash = Base64.getEncoder().encodeToString(hash);

            administradorBean.create("kenalx1999", passwordHash, "kevin", "kevin@gmail.com");
            administradorBean.create("chaca", passwordHash, "andres", "andres@gmail.com");
            administradorBean.create("pedrinsky", passwordHash, "pedro", "pedro@gmail.com");

            treinadorBean.create("lucho", passwordHash, "luis", "luis@gmail.com");
            treinadorBean.create("mateo", passwordHash, "mateo", "mateo@gmail.com");

            atletaBean.create("mario", passwordHash, "mario", "mario@gmail.com");
            atletaBean.create("santiago", passwordHash, "santiago", "santiago@gmail.com");

            productBean.create(1, ProductType.AULA, "123", 24.0);
            productBean.create(2, ProductType.ARTIGO, "123", 124.0);
            productBean.create(3, ProductType.INSCRICAO, "123", 424.0);

            paymentBean.create(1, "lucho", 1, Instant.now().getEpochSecond(), 2, 200.0,
                    PaymentStatus.PAID, "RECEIPT");
            paymentBean.create(2, "mario", 3, Instant.now().getEpochSecond(), 1, 50.0,
                    PaymentStatus.NOT_PAID, "RECEIPT");
            paymentBean.create(3, "mateo", 2, Instant.now().getEpochSecond(), 6, 2400.0,
                    PaymentStatus.PARCIAL, "RECEIPT");

            modalidadeBean.enrollAtletaEscalao("SUB20","mario","2018-2019","BASQUETBALL");
            modalidadeBean.enrollAtletaEscalao("SUB18","santiago","2019-2020","SOCCER");
            modalidadeBean.enrollTreinadorEscalao("SUB20","lucho","2018-2019","BASQUETBALL");

            escalaoBean.createAula("2018-2019","BASQUETBALL","SUB20",TipoDia.valueOf("SEGUNDA_FEIRA"),"pilates",10,12);
            escalaoBean.createAula("2018-2019","BASQUETBALL","SUB20",TipoDia.valueOf("TERCA_FEIRA"),"trote",10,12);

            horarioBean.createAssiduidade("lucho","SUB20","2018-2019",TipoDia.valueOf("SEGUNDA_FEIRA"),10,12,"mario",true);

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
