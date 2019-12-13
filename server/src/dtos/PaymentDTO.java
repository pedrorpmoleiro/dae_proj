package dtos;

import entities.PaymentStatus;

import javax.persistence.Id;
import java.io.Serializable;

public class PaymentDTO implements Serializable {
    @Id
    private int code;
    private String socioUsername;
    private int productCode;
    private long timestamp;
    private int quantity;
    private double price;
    private PaymentStatus status;
    private String receipt;

    public PaymentDTO() {
    }

    public PaymentDTO(int code, String socioUsername, int productCode, long timestamp, int quantity, double price, PaymentStatus status, String receipt) {
        this.code = code;
        this.socioUsername = socioUsername;
        this.productCode = productCode;
        this.timestamp = timestamp;
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

    public String getSocioUsername() {
        return socioUsername;
    }

    public void setSocioUsername(String socioUsername) {
        this.socioUsername = socioUsername;
    }

    public int getProductCode() {
        return productCode;
    }

    public void setProductCode(int productCode) {
        this.productCode = productCode;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
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
