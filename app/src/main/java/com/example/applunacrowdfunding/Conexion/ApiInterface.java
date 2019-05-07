package com.example.applunacrowdfunding.Conexion;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiInterface {
    @GET("usuario/listadoMovil")
    Call<Respuesta> getUsuarios();
  @GET("usuario/multiplicidad/{numerito}")
    Call<Respuesta> multiplicidad(@Path("numerito") int num);
}
