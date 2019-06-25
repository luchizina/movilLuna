package com.example.applunacrowdfunding;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.applunacrowdfunding.Conexion.ApiError;
import com.example.applunacrowdfunding.Conexion.ApiInterface;
import com.example.applunacrowdfunding.Conexion.Respuesta;
import com.example.applunacrowdfunding.Conexion.conexion;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.JsonArray;

import java.io.IOException;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class iniciarSesion extends AppCompatActivity {

    SweetAlertDialog pd;
    String token = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("ERROR", "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        token = task.getResult().getToken();

                        // Log and toast
                        String msg = getString(R.string.msg_token_fmt, token);
                        Log.d("SUCCESSTOKEN", msg);

                        // Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });

    }


    public void iniciarS(View vista) {
        pd = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pd.setCancelable(false);
        pd.setTitleText("Iniciando Sesión");
        pd.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pd.show();

        final SharedPreferences sp = getSharedPreferences("info", Context.MODE_PRIVATE);
        EditText txtCorreo = (EditText) findViewById(R.id.txtCorreo);
        final String correo = txtCorreo.getText().toString();
        EditText txtPass = (EditText) findViewById(R.id.txtPass);
        String pass = txtPass.getText().toString();

        if (correo.trim().equals("") || pass.trim().equals("")) {
            pd.changeAlertType(SweetAlertDialog.WARNING_TYPE);
            pd.setTitleText("¡Atención!");
            pd.setContentText("Hay campos vacíos");
            pd.setConfirmText("Aceptar");
        } else {
            ApiInterface apiService = conexion.getClient().create(ApiInterface.class);
            Call<Respuesta> call = apiService.loginCel(correo, pass);
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
                    final String nick = mensaje.get(0).getAsJsonObject().get("Nick").getAsString();
                    String estado = response.body().getEstado();
                    final CheckBox caja = (CheckBox) findViewById(R.id.checkBox);
                    if (estado.equals("ok")) {

                        ApiInterface apiService = conexion.getClient().create(ApiInterface.class);
                        final SharedPreferences sp = getSharedPreferences("info", Context.MODE_PRIVATE);
                        String nombre = sp.getString("nickLogueado", "sinnick");
                        call = apiService.sendRegistrationToServer(token,nick);
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
                                }
                                Log.d("ANDUVOJAJA","OKAYPOLISHA");
                            }
                            @Override
                            public void onFailure(Call<Respuesta> call, Throwable t) {

                                Log.d("LoginActivity", t.getMessage());
                            }
                        });
                        pd.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                        pd.setTitleText("¡Éxito!");
                        pd.setContentText("Presione aceptar para ser redirigido a la ventana principal");
                        pd.setConfirmText("Aceptar");
                        pd.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                if (caja.isChecked()) {
                                    SharedPreferences.Editor editor = sp.edit();
                                    editor.putString("correoLogueado", correo);
                                    editor.putString("nickLogueado", nick);
                                    editor.putString("mantieneAct", "si");
                                    editor.commit();
                                    Intent intento = new Intent(iniciarSesion.this, MainActivity.class);
                                    intento.putExtra("correoLogueado", correo);
                                    startActivity(intento);

                                } else {
                                    SharedPreferences.Editor editor = sp.edit();
                                    editor.putString("correoLogueado", correo);
                                    editor.putString("nickLogueado", nick);
                                    editor.putString("mantieneAct", "no");
                                    editor.commit();
                                    Intent intento = new Intent(iniciarSesion.this, MainActivity.class);
                                    intento.putExtra("correoLogueado", correo);
                                    startActivity(intento);
                                }
                            }
                        });
                    } else {
                        pd.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                        pd.setTitleText("Credenciales incorrectas");
                        pd.setConfirmText("Aceptar");
                    }


                }

                @Override
                public void onFailure(Call<Respuesta> call, Throwable t) {
                    pd.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                    pd.setTitleText("Ha ocurrido un error al comunicarse con el servidor");
                    pd.setConfirmText("Aceptar");
                    Log.d("LoginActivity", t.getMessage());
                }

            });


        }
    }

    public void regUsu(View vista) {
        Intent intento = new Intent(iniciarSesion.this, registro.class);
        startActivity(intento);
    }

    public void cambiarError(View vista) {
        TextView error = (TextView) findViewById(R.id.txtError);
        error.setVisibility(View.INVISIBLE);
    }


}

