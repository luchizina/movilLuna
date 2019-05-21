package com.example.applunacrowdfunding.Conexion;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInterface {
    @GET("usuario/listadoMovil")
    Call<Respuesta> getUsuarios();
  @GET("usuario/multiplicidad/{numerito}")
    Call<Respuesta> multiplicidad(@Path("numerito") int num);
  @FormUrlEncoded
    @POST("usuario/nuevoUsuCel")
    Call<Respuesta> nuevoUsuCel(@Field("nick") String nick, @Field("cont") String cont, @Field("nombre") String nom, @Field("ape") String ape, @Field("correo") String correo, @Field("cel") String cel,@Field("ci") String ci);

  @GET("propuesta/traerPropuesta/{nombre}")
    Call<Respuesta> traerPropuesta(@Path("nombre") String nombre);

  @FormUrlEncoded
    @POST("usuario/loginCel")
    Call<Respuesta> loginCel(@Field("email") String correo, @Field("pass") String pass);

  @FormUrlEncoded
    @POST("propuesta/comentar")
    Call<Respuesta> comentar(@Field("nombre") String s, @Field("mail") String emailLogueado, @Field("texto") String text);

  @GET("propuesta/listComent/{propu}")
  Call<Respuesta> getCom(@Path("propu") String nom);

}
