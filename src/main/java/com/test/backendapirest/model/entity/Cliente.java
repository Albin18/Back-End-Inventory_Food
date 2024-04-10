package com.test.backendapirest.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.lang.NonNull;

import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name="clientes")
public class Cliente implements Serializable {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NonNull
    @Column(nullable = false)
    @NotEmpty(message = "El campo nombre no puede estar vacio")
    private String nombre;
    @NonNull
    @Column(nullable = false)
    @NotEmpty(message = "El campo apellido no puede estar vacio")
    private String apellido;
    @NonNull
    @Column(unique = true)
    @NotEmpty(message = "El campo email no puede estar vacio")
    @Email(message = "El correo no es valido")
    private String email;
    @Column(name = "create_at")
    @Temporal(TemporalType.DATE)
    private Date createAt;
    @PrePersist
    public void prePersist(){
        createAt = new Date();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
}
