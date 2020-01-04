package ejbs;

import entities.Epoca;
import entities.TipoDia;

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
    @EJB
    private EpocaBean epocaBean;
    @EJB
    private AtletaBean atletaBean;
    @EJB
    private TreinadorBean treinadorBean;
    @EJB
    private HorarioBean horarioBean;
    public ConfigBean() {
    }

    @PostConstruct
    public void populateDB(){
        try{
            //Epoca
            epocaBean.create("2018-2019");
            epocaBean.create("2019-2020");

            //Modalidades
            modalidadeBean.create("BASQUETBALL","2018-2019");
            //modalidadeBean.create("BASQUETBALL","2019-2020");


            //Escalao
            escalaoBean.create("SUB20","BASQUETBALL","2018-2019");
            //escalaoBean.create("SUB20","BASQUETBALL","2019-2020");
            //Atleta
            atletaBean.create("kevin20","123","Kevin","kevin@mail.pt");
            atletaBean.create("pedro1","123","Pedro","pedro@mail.pt");


            //TODO Treinador
            treinadorBean.create("profe1","123","KingMarco","king@mail.pt");
            treinadorBean.create("profe2","123","KingMarco","king1@mail.pt");

            //entroll Atleta-Modalidade


            modalidadeBean.enrollAtletaEscalao("SUB20","kevin20","2018-2019","BASQUETBALL");
            modalidadeBean.enrollTreinadorEscalao("SUB20","profe1","2018-2019","BASQUETBALL");
            escalaoBean.createAula("2018-2019","BASQUETBALL","SUB20",TipoDia.valueOf("SEGUNDA_FEIRA"),"pilates",10,12);
            horarioBean.createAssiduidade("profe1","SUB20","2018-2019",TipoDia.valueOf("SEGUNDA_FEIRA"),10,12,"kevin20",true);


        }catch (Exception e){
            System.err.println(e.getMessage());
        }

    }
}
