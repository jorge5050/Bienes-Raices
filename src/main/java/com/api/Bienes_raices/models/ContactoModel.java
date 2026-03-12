package com.api.Bienes_raices.models;

import jakarta.persistence.*;

@Entity
@Table(name = "contacto")
public class ContactoModel {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id")
    private long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "correo")
    private String correo;

    @Column(name = "mensaje")
    private String mensaje;

    @Column(name = "tipo_propiedad")
    private String tipo_propiedad;

    @Column(name = "contacto_preferido")
    private String contacto_preferido;

    @Column(name = "recibir_informacion")
    private String recibir_informacion;

    @Column(name = "telefono")
    private  String telefono;

    @Column(name = "presupuesto")
    private String presupuesto;

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

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getTipo_propiedad() {
        return tipo_propiedad;
    }

    public void setTipo_propiedad(String tipo_propiedad) {
        this.tipo_propiedad = tipo_propiedad;
    }

    public String getContacto_preferido() {
        return contacto_preferido;
    }

    public void setContacto_preferido(String contacto_preferido) {
        this.contacto_preferido = contacto_preferido;
    }

    public String getRecibir_informacion() {
        return recibir_informacion;
    }

    public void setRecibir_informacion(String recibir_informacion) {
        this.recibir_informacion = recibir_informacion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(String presupuesto) {
        this.presupuesto = presupuesto;
    }


}
