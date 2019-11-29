package ejbs;

import entities.Modalidade;
import entities.ModalidadeType;

import javax.ejb.Stateless;

@Stateless(name = "ModalidadeEJB")
public class ModalidadeBean {
    public ModalidadeBean() {
    }
    @PersistenceContext
    EntityManager em;
    public void create(String nome, ModalidadeType tipo){
            try {
                Modalidade modalidade= em.find(Modalidade.class,nome);
                if(modalidade==null){
                    modalidade=new Modalidade(nome)
                    em.persist(student);
                }

            }catch (Exception e){

            }
    }
}
