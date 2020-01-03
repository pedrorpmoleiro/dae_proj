package ejbs;

import entities.Product;
import entities.ProductType;
import exceptions.MyEntityExistsException;
import exceptions.MyEntityNotFoundException;
import exceptions.MyIllegalArgumentException;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import java.util.LinkedList;
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

    public List<Product> notDeleted() {
        try {
            List<Product> products = (List<Product>) em.createNamedQuery("getAllProducts").getResultList();
            List<Product> notDeleted = new LinkedList<>();

            for (Product product : products) {
                if (!product.isDeleted()) {
                    notDeleted.add(product);
                }
            }

            return notDeleted;

        } catch (Exception e) {
            throw new EJBException("ERROR_RETRIEVING_PRODUCTS", e);
        }
    }

    public Product create(int code, ProductType type, String description, double value) throws MyEntityExistsException {
        Product product = em.find(Product.class, code);

        if (product != null) {
            throw new MyEntityExistsException("Product Already Exists");
        }

        product = new Product(code, type, description, value);

        em.persist(product);

        return product;
    }

    public Product update(int code, ProductType type, String description, double value) throws MyEntityNotFoundException, MyIllegalArgumentException {
        Product product = em.find(Product.class, code);

        if (product == null) {
            throw new MyEntityNotFoundException("Product Not Found");
        }

        if (product.isDeleted()) {
            throw new MyIllegalArgumentException("Product has been removed and cannot be edited");
        }

        em.lock(product, LockModeType.PESSIMISTIC_WRITE);

        product.setType(type);
        product.setDescription(description);
        product.setValue(value);

        em.lock(product, LockModeType.NONE);

        return product;
    }

    public Product delete(int code) throws MyEntityNotFoundException {
        Product product = em.find(Product.class, code);

        if (product == null) {
            throw new MyEntityNotFoundException("Product Not Found");
        }

        if (product.isDeleted()) {
            return product;
        }

        em.lock(product, LockModeType.PESSIMISTIC_READ);

        product.setDeleted(true);

        em.lock(product, LockModeType.NONE);

        return product;
    }

    public Product find(int code) {
        try {
            return em.find(Product.class, code);
        } catch (Exception e) {
            throw new EJBException("ERROR_FINDING_PRODUCT", e);
        }
    }
}
