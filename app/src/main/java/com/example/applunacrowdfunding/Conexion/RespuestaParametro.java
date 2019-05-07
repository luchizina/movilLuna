package com.example.applunacrowdfunding.Conexion;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrador on 19/03/2018.
 */
public class RespuestaParametro {
    @SerializedName("status")
    private String estado;
    @SerializedName("message")
    private JsonObject message;

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public JsonObject getMessage() {
        return message;
    }

    public void setMessage(JsonObject message) {
        this.message = message;
    }
}
