package dtos;

import entities.PaymentStatus;
import entities.Product;
import entities.Socio;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

public class PaymentDTO implements Serializable {
    @Id
    private int code;
    private Socio socio;
    private Product product;
    private Date date;
    private int quantity;
    private double price;
    private PaymentStatus status;
    private String receipt;

    public PaymentDTO() {
    }

    public PaymentDTO(int code, Socio socio, Product product, Date date, int quantity, double price, PaymentStatus status, String receipt) {
        this.code = code;
        this.socio = socio;
        this.product = product;
        this.date = date;
        this.quantity = quantity;
        this.price = price;
        this.status = status;
        this.receipt = receipt;
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
}
