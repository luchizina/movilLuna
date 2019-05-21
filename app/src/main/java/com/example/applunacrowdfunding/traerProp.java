package com.example.applunacrowdfunding;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.applunacrowdfunding.Conexion.ApiError;
import com.example.applunacrowdfunding.Conexion.ApiInterface;
import com.example.applunacrowdfunding.Conexion.Respuesta;
import com.example.applunacrowdfunding.Conexion.conexion;
import com.google.gson.JsonArray;

import java.io.IOException;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class traerProp extends AppCompatActivity {
    Button com;
    String nom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traer_prop);


        Call<Respuesta> call = null;
        Bundle extra = getIntent().getExtras();

        final ApiInterface apiService = conexion.getClient().create(ApiInterface.class);
<<<<<<< HEAD
        if(extra!=null){
        //call = apiService.traerPropuesta(extra.getString("prop"));
            call = apiService.traerPropuesta("propuestaprueba");
        }
        else {
            call = apiService.traerPropuesta("propuestaprueba");
=======
        if (extra != null) {
            call = apiService.traerPropuesta(extra.getString("prop"));
        } else {
            call = apiService.traerPropuesta("hola");
>>>>>>> 4994e76bead960be523724177d0087b709e09b3b
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
                String nick = arregloUsers.get(0).getAsJsonObject().get("NickUsuario").getAsString();
                nom = nombre;
             /*   JsonArray numero = response.body().getMessage();
                String nombre=numero.get(0).getAsJsonObject().get("numerito").getAsString();*/
                TextView txtNombre = findViewById(R.id.txtNombre);
                txtNombre.setText(nombre);

                TextView monto = findViewById(R.id.monto);
                monto.setText(montoT);
                TextView monto_actual = findViewById(R.id.monto_actual);
                monto_actual.setText(montoA);
                TextView descri = findViewById(R.id.descri);
                descri.setText(desc);
                TextView nic = findViewById(R.id.usuario);
                nic.setText(nick);
                ProgressBar me = (ProgressBar) findViewById(R.id.prg);
                int moT = Integer.parseInt(montoT);
                int moA = Integer.parseInt(montoA);
                int barra = ((moA * 100) / moT);
                me.setProgress(barra);



            }

            @Override
            public void onFailure(Call<Respuesta> call, Throwable t) {
                Log.d("LoginActivity", t.getMessage());


            }
        });
    }

    public void comentar(View vista) {
        final SharedPreferences sp = getSharedPreferences("info", Context.MODE_PRIVATE);
        String emailLogueado = sp.getString("correoLogueado", "sinusuario");
        EditText nombre = findViewById(R.id.txtNombre);
        TextView textito = findViewById(R.id.textView6);
        String text = textito.getText().toString();
        String s = nombre.getText().toString();
        ApiInterface apiService = conexion.getClient().create(ApiInterface.class);
        Call<Respuesta> call = apiService.comentar(s, emailLogueado, text);
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
                        Log.d("ComentarActivity", apiError.getDeveloperMessage());
                    } else {
                        try {
                            // Reportar causas de error no relacionado con la API
                            Log.d("ComentarActivity", response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    return;
                }
                EditText xD = findViewById(R.id.textView6);
                xD.setText("");
            }

            @Override
            public void onFailure(Call<Respuesta> call, Throwable t) {
                Log.d("LoginActivity", t.getMessage());
            }
        });
    }

    public void colaborar(View vista)
    {
        final SharedPreferences sp = getSharedPreferences("info", Context.MODE_PRIVATE);
        String emailLogueado = sp.getString("correoLogueado", "sinusuario");
        EditText textito = findViewById(R.id.donarText);
        EditText nombre = findViewById(R.id.txtNombre);
        String nombresito = nombre.getText().toString();
        int text = Integer.parseInt(textito.getText().toString());
        ApiInterface apiService = conexion.getClient().create(ApiInterface.class);
        Call<Respuesta> call = apiService.colaborar(text, emailLogueado, nombresito);
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
                        Log.d("ComentarActivity", apiError.getDeveloperMessage());
                    } else {
                        try {
                            // Reportar causas de error no relacionado con la API
                            Log.d("ComentarActivity", response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    return;
                }
                EditText xD = findViewById(R.id.donarText);
                xD.setText("");
            }

            @Override
            public void onFailure(Call<Respuesta> call, Throwable t) {
                Log.d("LoginActivity", t.getMessage());
            }
        });
    }

    public void verComentarios(View vista){
        com = findViewById(R.id.com);
        EditText x = findViewById(R.id.txtNombre);
        String s = x.getText().toString();
                Intent i = new Intent(traerProp.this, comentarios.class);
                i.putExtra("nom", s);
                startActivity(i);
            }
    }

<<<<<<< HEAD
    public void comentar(View vista)
    {
        final SharedPreferences sp = getSharedPreferences("info", Context.MODE_PRIVATE);
        String emailLogueado= sp.getString("correoLogueado","sinusuario");
        EditText nombre = findViewById(R.id.txtNombre);
        EditText textito = findViewById(R.id.textView6);
        String text = textito.getText().toString();
        String s = nombre.getText().toString();
        ApiInterface apiService = conexion.getClient().create(ApiInterface.class);
        Call<Respuesta> call = apiService.comentar(s,emailLogueado,text);
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
                        Log.d("ComentarActivity", apiError.getDeveloperMessage());
                    } else {
                        try {
                            // Reportar causas de error no relacionado con la API
                            Log.d("ComentarActivity", response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    return;
                }
                    EditText xD = findViewById(R.id.textView6);
                    xD.setText("");
            }

            @Override
            public void onFailure(Call<Respuesta> call, Throwable t) {
                Log.d("LoginActivity", t.getMessage());
            }

        });
    }
}
=======
>>>>>>> 4994e76bead960be523724177d0087b709e09b3b
