package ejbs;

import entities.Product;
import entities.ProductType;
import exceptions.MyConstraintViolationException;
import exceptions.MyEntityExistsException;
import exceptions.MyEntityNotFoundException;
import exceptions.Utils;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import java.util.List;

@Stateless(name = "ProductEJB")
public class ProductBean {
    @PersistenceContext
    EntityManager em;

    public List<Product> all() {
        try {
            return (List<Product>) em.createNamedQuery("getAllProducts").getResultList();
        } catch (Exception e) {
            throw new EJBException("ERROR_RETRIEVING_PRODUCTS", e);
        }
    }

    public Product create(int code, ProductType type, String description, double value) throws MyConstraintViolationException {
        try {
            Product product = em.find(Product.class, code);

            if (product != null) {
                throw new MyEntityExistsException("Product Already Exists");
            }

            product = new Product(code, type, description, value);

            em.persist(product);

            return product;
        } catch (Exception e) {
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages((ConstraintViolationException) e));
        }
    }

    public Product update(int code, ProductType type, String description, double value) throws MyConstraintViolationException {
        try {
            Product product = em.find(Product.class, code);

            if (product == null) {
                throw new MyEntityNotFoundException("Product Not Found");
            }

            em.lock(product, LockModeType.OPTIMISTIC);

            product.setType(type);
            product.setDescription(description);
            product.setValue(value);

            em.lock(product, LockModeType.NONE);

            return product;
        } catch (Exception e) {
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages((ConstraintViolationException) e));
        }
    }
}
