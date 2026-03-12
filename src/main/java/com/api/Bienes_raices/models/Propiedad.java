package com.api.Bienes_raices.models;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "propiedades")
public class Propiedad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private Double precio;
    private String imagen;
    private Integer habitaciones;
    private Integer wc;
    private Integer estacionamiento;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    // Nuevo campo para coincidir con tu base de datos
    private LocalDate creado;

    // Esto asigna la fecha automáticamente antes de guardar en la DB
    @PrePersist
    protected void onCreate() {
        this.creado = LocalDate.now();
    }

    // --- GETTERS Y SETTERS ---

    public Long getId() {
       return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Double getPrecio() {
        return precio;
    }
    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getImagen() {
        return imagen;
    }
    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Integer getHabitaciones() {
        return habitaciones;
    }
    public void setHabitaciones(Integer habitaciones) {
        this.habitaciones = habitaciones;
    }

    public Integer getWc() {
        return wc;
    }
    public void setWc(Integer wc) {
        this.wc = wc;
    }

    public Integer getEstacionamiento() {
        return estacionamiento;
    }
    public void setEstacionamiento(Integer estacionamiento) {
        this.estacionamiento = estacionamiento;
    }

    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getCreado() {
        return creado;
    }
    public void setCreado(LocalDate creado) {
        this.creado = creado;
    }
}