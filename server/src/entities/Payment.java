package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "PAYMENTS")
@NamedQueries({
        @NamedQuery(
                name = "getAllPayments",
                query = "SELECT p FROM Payment p ORDER BY p.code"
        )
})
public class Payment implements Serializable {
    @Id
    private int code;
    @ManyToOne
    @JoinColumn(name = "socio_username")
    private Socio socio;
    @ManyToOne
    @JoinColumn(name = "product_code")
    private Product product;
    @NotNull
    private Date date;
    @NotNull
    private int quantity;
    @NotNull
    private double price;
    @NotNull
    private PaymentStatus status;
    @NotNull
    private String receipt;
    private boolean deleted;

    public Payment() {
    }

    public Payment(int code, Socio socio, Product product, Date date, int quantity, double price, PaymentStatus status, String receipt) {
        this.code = code;
        this.socio = socio;
        this.product = product;
        this.date = date;
        this.quantity = quantity;
        this.price = price;
        this.status = status;
        this.receipt = receipt;
        this.deleted = false;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Socio getSocio() {
        return socio;
    }

    public void setSocio(Socio socio) {
        this.socio = socio;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public String getReceipt() {
        return receipt;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
