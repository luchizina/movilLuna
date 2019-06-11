package com.example.applunacrowdfunding;


import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class propuests {

    @SerializedName("Nombre")
    @Expose
    private String nombre;
    @SerializedName("Descripcion")
    @Expose
    private String descripcion;
    @SerializedName("FechaAgregada")
    @Expose
    private Object fechaAgregada;
    @SerializedName("FechaPublicada")
    @Expose
    private String fechaPublicada;
    @SerializedName("Monto")
    @Expose
    private String monto;
    @SerializedName("MontoActual")
    @Expose
    private String montoActual;
    @SerializedName("NickUsuario")
    @Expose
    private String nickUsuario;
    @SerializedName("Categoria")
    @Expose
    private Object categoria;
    @SerializedName("Recompensa")
    @Expose
    private List<Object> recompensa = null;
    @SerializedName("EstadoActual")
    @Expose
    private Object estadoActual;
    @SerializedName("Comentarios")
    @Expose
    private List<Object> comentarios = null;
    @SerializedName("favoritos")
    @Expose
    private List<Object> favoritos = null;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Object getFechaAgregada() {
        return fechaAgregada;
    }

    public void setFechaAgregada(Object fechaAgregada) {
        this.fechaAgregada = fechaAgregada;
    }

    public String getFechaPublicada() {
        return fechaPublicada;
    }

    public void setFechaPublicada(String fechaPublicada) {
        this.fechaPublicada = fechaPublicada;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public String getMontoActual() {
        return montoActual;
    }

    public void setMontoActual(String montoActual) {
        this.montoActual = montoActual;
    }

    public String getNickUsuario() {
        return nickUsuario;
    }

    public void setNickUsuario(String nickUsuario) {
        this.nickUsuario = nickUsuario;
    }

    public Object getCategoria() {
        return categoria;
    }

    public void setCategoria(Object categoria) {
        this.categoria = categoria;
    }

    public List<Object> getRecompensa() {
        return recompensa;
    }

    public void setRecompensa(List<Object> recompensa) {
        this.recompensa = recompensa;
    }

    public Object getEstadoActual() {
        return estadoActual;
    }

    public void setEstadoActual(Object estadoActual) {
        this.estadoActual = estadoActual;
    }

    public List<Object> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Object> comentarios) {
        this.comentarios = comentarios;
    }

    public List<Object> getFavoritos() {
        return favoritos;
    }

    public void setFavoritos(List<Object> favoritos) {
        this.favoritos = favoritos;
    }

}
