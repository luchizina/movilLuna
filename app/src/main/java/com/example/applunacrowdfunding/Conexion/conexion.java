package com.example.applunacrowdfunding.Conexion;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class conexion {


<<<<<<< HEAD
    public static final String BASE_URL = "http://192.168.20.75/phpLuna/";
=======
    public static final String BASE_URL = "http://192.168.1.2/phpLuna/";
>>>>>>> fd6a017afb2420c925d29431987f063f2f1922a5
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
