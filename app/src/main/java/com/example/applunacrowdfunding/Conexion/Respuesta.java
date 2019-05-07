package com.example.applunacrowdfunding.Conexion;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Administrador on 19/03/2018.
 */
public class Respuesta {
    @SerializedName("status")
    private String estado;
    @SerializedName("message")
    private JsonArray message;

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public JsonArray getMessage() {
        return message;
    }

    public void setMessage(JsonArray message) {
        this.message = message;
    }
}
