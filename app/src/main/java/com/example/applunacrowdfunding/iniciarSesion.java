package com.example.applunacrowdfunding;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.applunacrowdfunding.Conexion.ApiError;
import com.example.applunacrowdfunding.Conexion.ApiInterface;
import com.example.applunacrowdfunding.Conexion.Respuesta;
import com.example.applunacrowdfunding.Conexion.conexion;
import com.google.gson.JsonArray;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class iniciarSesion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);
    }


    public void iniciarS(View vista){

        final SharedPreferences sp = getSharedPreferences("info", Context.MODE_PRIVATE);
        EditText txtCorreo = (EditText) findViewById(R.id.txtCorreo);
       final String correo=txtCorreo.getText().toString();
        EditText txtPass = (EditText) findViewById(R.id.txtPass);
        String pass=txtPass.getText().toString();


        ApiInterface apiService = conexion.getClient().create(ApiInterface.class);
        Call<Respuesta> call = apiService.loginCel(correo,pass);
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


                JsonArray mensaje = response.body().getMessage();
                String estado = response.body().getEstado();
                CheckBox caja = (CheckBox) findViewById(R.id.checkBox);
                if(estado.equals("ok")){
                    if(caja.isChecked()){
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("correoLogueado",correo);
                        editor.putString("mantieneAct", "si");
                        editor.commit();
                        Intent intento = new Intent(iniciarSesion.this,MainActivity.class);
                        intento.putExtra("correoLogueado",correo);
                        startActivity(intento);

                    }else{
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("correoLogueado",correo);
                        editor.commit();
                        Intent intento = new Intent(iniciarSesion.this,MainActivity.class);
                        intento.putExtra("correoLogueado",correo);
                        startActivity(intento);
                    }
                   /* String emailArchivo= sp.getString("email","sinusuario");

                    if(emailArchivo.equals(correo)){

                        Intent intento = new Intent(iniciarSesion.this,MainActivity.class);
                        intento.putExtra("correoLogueado",correo);
                        startActivity(intento);
                    }*/

                }


            }

            @Override
            public void onFailure(Call<Respuesta> call, Throwable t) {
                Log.d("LoginActivity", t.getMessage());
            }

        });












    }
}

