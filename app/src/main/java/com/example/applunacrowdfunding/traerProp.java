package com.example.applunacrowdfunding;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.example.applunacrowdfunding.Conexion.ApiError;
import com.example.applunacrowdfunding.Conexion.ApiInterface;
import com.example.applunacrowdfunding.Conexion.Respuesta;
import com.example.applunacrowdfunding.Conexion.conexion;
import com.google.gson.JsonArray;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class traerProp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traer_prop);


        Call<Respuesta> call = null;
        Bundle extra =getIntent().getExtras();

        final ApiInterface apiService = conexion.getClient().create(ApiInterface.class);
        if(extra!=null){
        call = apiService.traerPropuesta(extra.getString("prop"));
        }
        else {
            call = apiService.traerPropuesta("hola");
        }

        call.enqueue(new Callback<Respuesta>() {
            @Override
            public void onResponse(Call<Respuesta> call, Response<Respuesta> response) {
                if (!response.isSuccessful()) {
                    String error = "Ha ocurrido un error. Contacte al administrador";
                    if (response.errorBody()
                            .contentType()
                            .subtype()
                            .equals("json")) {
                        ApiError apiError = ApiError.fromResponseBody(response.errorBody());

                        error = apiError.getMessage();
                        Log.d("LoginActivity", apiError.getDeveloperMessage());
                    } else {
                        try {
                            // Reportar causas de error no relacionado con la API
                            Log.d("LoginActivity", response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }


                    return;


                }

                //Este es con call respuesta, para mostrar lista de usuarios
                JsonArray arregloUsers = response.body().getMessage();
              /* for(int i=0; i<arregloUsers.size(); i++){
                    JsonObject aux = arregloUsers.get(i).getAsJsonObject();
                    String nombre= aux.get("Nombre").getAsString();

                }*/
                String nombre = arregloUsers.get(0).getAsJsonObject().get("Nombre").getAsString();
                String montoT = arregloUsers.get(0).getAsJsonObject().get("Monto").getAsString();
                String montoA = arregloUsers.get(0).getAsJsonObject().get("MontoActual").getAsString();
                String desc = arregloUsers.get(0).getAsJsonObject().get("Descripcion").getAsString();

             /*   JsonArray numero = response.body().getMessage();
                String nombre=numero.get(0).getAsJsonObject().get("numerito").getAsString();*/
                EditText txtNombre= findViewById(R.id.txtNombre);
                txtNombre.setText(nombre);

                TextView monto= findViewById(R.id.monto);
                monto.setText(montoT);
                TextView monto_actual= findViewById(R.id.monto_actual);
                monto_actual.setText(montoA);
                TextView descri= findViewById(R.id.descri);
                descri.setText(desc);
                descri.setText(desc);


            }
            @Override
            public void onFailure(Call<Respuesta> call, Throwable t) {
                Log.d("LoginActivity", t.getMessage());


            }
        });




    }
}
