package com.example.applunacrowdfunding;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class coments {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("Texto")
    @Expose
    private String texto;
    @SerializedName("Usuario")
    @Expose
    private Object usuario;
    @SerializedName("Propuesta")
    @Expose
    private Object propuesta;
    @SerializedName("NickUsuario")
    @Expose
    private String nickUsuario;
    @SerializedName("TituloPropuesta")
    @Expose
    private String tituloPropuesta;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Object getUsuario() {
        return usuario;
    }

    public void setUsuario(Object usuario) {
        this.usuario = usuario;
    }

    public Object getPropuesta() {
        return propuesta;
    }

    public void setPropuesta(Object propuesta) {
        this.propuesta = propuesta;
    }

    public String getNickUsuario() {
        return nickUsuario;
    }

    public void setNickUsuario(String nickUsuario) {
        this.nickUsuario = nickUsuario;
    }

    public String getTituloPropuesta() {
        return tituloPropuesta;
    }

    public void setTituloPropuesta(String tituloPropuesta) {
        this.tituloPropuesta = tituloPropuesta;
    }
}