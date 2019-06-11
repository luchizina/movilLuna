package com.example.applunacrowdfunding.Conexion;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class conexion {



    public static final String BASE_URL = "http://192.168.25.43/phpLuna/";
    private static Retrofit retrofit = null;


    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
