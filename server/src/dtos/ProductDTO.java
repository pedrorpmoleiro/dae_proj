package dtos;

import entities.ProductType;

import javax.persistence.Id;
import java.io.Serializable;

public class ProductDTO implements Serializable {
    @Id
    private int code;
    private ProductType type;
    private String description;
    private double value;

    public ProductDTO() {
    }

    public ProductDTO(int code, ProductType type, String description, double value) {
        this.code = code;
        this.type = type;
        this.description = description;
        this.value = value;
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
}
