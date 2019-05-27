package com.example.applunacrowdfunding.Conexion;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
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

    @GET("propuesta/listadoCel")
    Call<Respuesta> getPropuestas();

    @GET("propuesta/borrComent/{id}")
    Call<Respuesta> BorrCom(@Path("id") String id);

    @GET("usuario/devuelveUsu/{correo}")
    Call<Respuesta> UsuCorreo(@Path("correo") String correo);

   @FormUrlEncoded
   @POST("propuesta/nuevaColaboracionCel")
   Call<Respuesta> colaborar(@Path("monto") int monto, @Path("mail") String mail, @Path("nombre") String nombresito);

  @FormUrlEncoded
  @POST("propuesta/likeCometario")
  Call<Respuesta> likeCometario(@Field("idCom") String id, @Field("mail") String emailLogueado);

  @FormUrlEncoded
  @POST("propuesta/dislikeCometario")
  Call<Respuesta> dislikeCometario(@Field("idCom") String id, @Field("mail") String emailLogueado);

}
