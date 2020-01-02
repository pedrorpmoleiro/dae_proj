package ejbs;

import entities.Payment;
import entities.PaymentStatus;
import entities.Product;
import entities.Socio;
import exceptions.MyEntityExistsException;
import exceptions.MyEntityNotFoundException;
import exceptions.MyIllegalArgumentException;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
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

    public Payment create(int code, String socio_username, int product_code, long timestamp, int quantity,
                          double price, PaymentStatus status, String receipt)
            throws MyEntityNotFoundException, MyEntityExistsException {

        Payment payment = em.find(Payment.class, code);

        if (payment != null) {
            throw new MyEntityExistsException("ERROR_PAYMENTS_EXISTS");
        }

        Socio socio = em.find(Socio.class, socio_username);

        if (socio == null) {
            throw new MyEntityNotFoundException("ERROR_FINDING_SOCIO");
        }

        Product product = em.find(Product.class, product_code);

        if (product == null) {
            throw new MyEntityNotFoundException("ERROR_FINDING_PRODUCT");
        }

        payment = new Payment(code, socio, product, new Date(timestamp * 1000), quantity, price, status, receipt);

        em.persist(payment);

        return payment;
    }

    public Payment update(int code, String socio_username, int product_code, long timestamp, int quantity,
                          double price, PaymentStatus status, String receipt)
            throws MyEntityNotFoundException, MyIllegalArgumentException {

        Payment payment = em.find(Payment.class, code);

        if (payment == null) {
            throw new MyEntityNotFoundException("ERROR_PAYMENT_NOT_FOUND");
        }

        if (payment.isDeleted()) {
            throw new MyIllegalArgumentException("Payment has been removed and cannot be edited");
        }

        Socio socio = em.find(Socio.class, socio_username);

        if (socio == null) {
            throw new MyEntityNotFoundException("ERROR_SOCIO_NOT_FOUND");
        }

        Product product = em.find(Product.class, product_code);

        if (product == null) {
            throw new MyEntityNotFoundException("ERROR_PRODUCT_NOT_FOUND");
        }

        em.lock(payment, LockModeType.PESSIMISTIC_WRITE);

        payment.setSocio(socio);
        payment.setProduct(product);
        payment.setDateTime(new Date(timestamp));
        payment.setQuantity(quantity);
        payment.setPrice(price);
        payment.setStatus(status);
        payment.setReceipt(receipt);

        em.lock(payment, LockModeType.NONE);

        return payment;
    }

    public Payment delete(int code) throws MyEntityNotFoundException {
        Payment payment = em.find(Payment.class, code);

        if (payment == null) {
            throw new MyEntityNotFoundException("ERROR_PAYMENT_NOT_FOUND");
        }

        if (payment.isDeleted()) {
            return payment;
        }

        em.lock(payment, LockModeType.PESSIMISTIC_WRITE);

        payment.setDeleted(true);

        em.lock(payment, LockModeType.NONE);

        return payment;
    }
}
