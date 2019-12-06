package ejbs;

import entities.Payment;
import entities.PaymentStatus;
import entities.Product;
import entities.Socio;
import exceptions.MyEntityNotFoundException;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Stateless(name = "PaymentEJB")
public class PaymentBean {
    @PersistenceContext
    private EntityManager em;

    public List<Payment> all() {
        try {
            return (List<Payment>) em.createNamedQuery("getAllPayments").getResultList();
        } catch (Exception e) {
            throw new EJBException("ERROR_RETRIEVING_PAYMENTS", e);
        }
    }

    public List<Payment> notDeleted() {
        try {
            List<Payment> payments = (List<Payment>) em.createNamedQuery("getAllPayments").getResultList();
            List<Payment> notDeleted = new LinkedList<>();

            for (Payment payment : payments) {
                if (!payment.isDeleted()) {
                    notDeleted.add(payment);
                }
            }

            return notDeleted;
        } catch (Exception e) {
            throw new EJBException("ERROR_RETRIEVING_PAYMENTS", e);
        }
    }

    public Payment find(int code) {
        try {
            return em.find(Payment.class, code);
        } catch (Exception e) {
            throw new EJBException("ERROR_FINDING_PAYMENT", e);
        }
    }

    public Payment create(int code, int socio_username, int product_code, Date date, int quantity, double price, PaymentStatus status, String receipt) throws MyEntityNotFoundException {
        Socio socio = em.find(Socio.class, socio_username);

        if (socio == null) {
            throw new MyEntityNotFoundException("ERROR_FINDING_SOCIO");
        }

        Product product = em.find(Product.class, product_code);

        if (product == null) {
            throw new MyEntityNotFoundException("ERROR_FINDING_PRODUCT");
        }

        
    }
}
