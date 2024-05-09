package com.test.inventoryfood.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.lang.NonNull;

import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name="products")
public class Product implements Serializable {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NonNull
    @Column(nullable = false)
    @NotEmpty(message = "Agregue alguna descripcion")
    private String description;
    @NonNull
    @Column(nullable = false)
    @NotEmpty(message = "agregue una marca")
    private String brand;
    @NonNull
    @NotEmpty(message = "agregue la cantidad")
    private int quantity;

    @NonNull
    @NotEmpty(message = "agregue el precio")
    private int price;
    @Column(name = "buy_at")
    @Temporal(TemporalType.DATE)
    private Date buyAt;
    @PrePersist
    public void prePersist(){
        buyAt = new Date();
    }

    @NonNull
    @Column(unique = true)
    @NotEmpty(message = "seleccione la categoria")
    private String category;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NonNull
    public String getDescription() {
        return description;
    }

    public void setDescription(@NonNull String description) {
        this.description = description;
    }

    @NonNull
    public String getBrand() {
        return brand;
    }

    public void setBrand(@NonNull String brand) {
        this.brand = brand;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Date getBuyAt() {
        return buyAt;
    }

    public void setBuyAt(Date buyAt) {
        this.buyAt = buyAt;
    }

    @NonNull
    public String getCategory() {
        return category;
    }

    public void setCategory(@NonNull String category) {
        this.category = category;
    }
}
