package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "PRODUCTS")
@NamedQueries({
        @NamedQuery(
                name = "getAllProducts",
                query = "SELECT p FROM Product p ORDER BY p.code"
        )
})
public class Product implements Serializable {
    @Id
    private int code;
    @NotNull
    private ProductType type;
    private String description;
    private double value;
    private boolean deleted;

    public Product() {
    }

    public Product(int code, ProductType type, String description, double value) {
        this.code = code;
        this.type = type;
        this.description = description;
        this.value = value;
        this.deleted = false;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ProductType getType() {
        return type;
    }

    public void setType(ProductType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
