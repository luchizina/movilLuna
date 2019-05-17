package com.example.applunacrowdfunding;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.applunacrowdfunding.Conexion.ApiError;
import com.example.applunacrowdfunding.Conexion.ApiInterface;
import com.example.applunacrowdfunding.Conexion.Respuesta;
import com.example.applunacrowdfunding.Conexion.conexion;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class registro extends AppCompatActivity {
    private static final String KEY_EMPTY = "";
    private EditText etNick;
    private EditText etCont;
    private EditText etNombre;
    private EditText etApe;
    private EditText etCorreo;
    private EditText etCel;
    private EditText etCI;
    private String nick;
    private String cont;
    private String nombre;
    private String ape;
    private String correo;
    private String cel;
    private String ci;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        etNick= findViewById(R.id.nick);
        etCont= findViewById(R.id.cont);
        etNombre= findViewById(R.id.nom);
        etApe= findViewById(R.id.ape);
        etCorreo= findViewById(R.id.correo);
        etCel= findViewById(R.id.cel);
        etCI= findViewById(R.id.ci);
        Button regis = findViewById(R.id.reg);

        regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Retrieve the data entered in the edit texts
                nick = etNick.getText().toString().trim();
                cont = etCont.getText().toString().trim();
                nombre = etNombre.getText().toString().trim();
                ape = etApe.getText().toString().trim();
                correo = etCorreo.getText().toString().trim();
                cel = etCel.getText().toString().trim();
                ci = etCI.getText().toString().trim();
               // if (validateInputs()) {
                    ApiInterface apiService = conexion.getClient().create(ApiInterface.class);
                    Call<Respuesta> call = apiService.nuevoUsuCel(nick, cont, nombre, ape, correo, cel, ci);
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
                        }

                        @Override
                        public void onFailure(Call<Respuesta> call, Throwable t) {
                            Log.d("LoginActivity", t.getMessage());
                        }

                    });

                //}

            }
        });
    }

   /* private boolean validateInputs() {
        if (KEY_EMPTY.equals(nick)) {
            etNick.setError("El campo no puede estar vacio");
            etNick.requestFocus();
            return false;

        }
        if (KEY_EMPTY.equals(nombre)) {
            etNombre.setError("Este campo no puede estar vacio");
            etNombre.requestFocus();
            return false;
        }
        if (KEY_EMPTY.equals(ape)) {
            etApe.setError("Este campo no puede estar vacio");
            etApe.requestFocus();
            return false;
        }
        if (KEY_EMPTY.equals(cont)) {
            etCont.setError("Este campo no puede estar vacio");
            etCont.requestFocus();
            return false;
        }
        if (KEY_EMPTY.equals(correo)) {
            etCorreo.setError("Este campo no puede estar vacio");
            etCorreo.requestFocus();
            return false;
        }
        if (KEY_EMPTY.equals(ci)) {
            etCI.setError("Este campo no puede estar vacio");
            etCI.requestFocus();
            return false;
        }
        if (KEY_EMPTY.equals(cel)) {
            etCel.setError("Este campo no puede estar vacio");
            etCel.requestFocus();
            return false;
        }
        return true;
    }*/
}
