package com.example.applunacrowdfunding;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.applunacrowdfunding.Conexion.ApiError;
import com.example.applunacrowdfunding.Conexion.ApiInterface;
import com.example.applunacrowdfunding.Conexion.Respuesta;
import com.example.applunacrowdfunding.Conexion.conexion;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.JsonArray;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class traerProp extends AppCompatActivity {
    Button com;
    String nom;
    Dialog myDialog;
    SweetAlertDialog pd;
    int montoAA;
    int montoTT;
    boolean output = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        pd = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pd.setCancelable(false);
        pd.setTitleText("Cargando Información");
        pd.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pd.show();
        super.onCreate(savedInstanceState);
        myDialog = new Dialog(this);
        setContentView(R.layout.activity_traer_prop);
        String nomProp = "";
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            nomProp = extras.getString("nombreProp");
        }

        Call<Respuesta> call = null;
        Bundle extra = getIntent().getExtras();

        final ApiInterface apiService = conexion.getClient().create(ApiInterface.class);
        call = apiService.traerPropuesta(nomProp);

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
                String fechaP = arregloUsers.get(0).getAsJsonObject().get("FechaPublicada").getAsString();
                montoAA = Integer.parseInt(montoA);
                montoTT = Integer.parseInt(montoT);
                nom = nombre;
             /*   JsonArray numero = response.body().getMessage();
                String nombre=numero.get(0).getAsJsonObject().get("numerito").getAsString();*/
                TextView txtNombre = findViewById(R.id.txtNombre);
                txtNombre.setText(nombre);

                chequearLikePropCelu(nombre);
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);

                ImageView imageView = (ImageView) findViewById(R.id.img);
                Bundle extras = getIntent().getExtras();
                String np = "";
                if (extras != null) {
                    np = extras.getString("nombreProp");
                }
                Picasso.get().load("http://192.168.25.93/phpLuna/imgProps/" + np + ".jpg").resize(imageView.getWidth(), imageView.getHeight()).centerCrop().into(imageView);

                TextView monto = findViewById(R.id.monto);
                monto.setText("$" + montoA + " de $" + montoT);

                TextView descri = findViewById(R.id.descri);
                descri.setText(desc);
                TextView nic = findViewById(R.id.usuario);
                nic.setText(nick);
                ProgressBar me = (ProgressBar) findViewById(R.id.prg);
                int moT = Integer.parseInt(montoT);
                int moA = Integer.parseInt(montoA);
                int barra = ((moA * 100) / moT);
                me.setProgress(barra);
                TextView txtFecha = findViewById(R.id.txtFecha);
                txtFecha.setText(fechaP);
                pd.dismissWithAnimation();
            }

            @Override
            public void onFailure(Call<Respuesta> call, Throwable t) {
                Log.d("LoginActivity", t.getMessage());
                pd.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                pd.setTitleText("¡Error!");
                pd.setContentText("Ha ocurrido un error al cargar la información");
                pd.setConfirmText("Aceptar");
            }
        });

    }

    public void comentar(String text) {
        pd = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pd.setCancelable(false);
        pd.setTitleText("Enviando Comentario...");
        pd.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pd.show();
        final SharedPreferences sp = getSharedPreferences("info", Context.MODE_PRIVATE);
        String emailLogueado = sp.getString("correoLogueado", "sinusuario");
        TextView nombre = findViewById(R.id.txtNombre);
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
                }
                pd.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                pd.setTitleText("El comentario ha sido enviado!");
                pd.setConfirmText("Aceptar");
                pd.show();
            }

            @Override
            public void onFailure(Call<Respuesta> call, Throwable t) {
                pd.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                pd.setTitleText("Ha ocurrido un error :(");
                pd.setConfirmText("Aceptar");
                pd.show();
                Log.d("LoginActivity", t.getMessage());
            }
        });
    }

    public void colaborar(final int monto) {
        pd = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pd.setCancelable(false);
        pd.setTitleText("Enviando Colaboración");
        pd.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pd.show();
        final SharedPreferences sp = getSharedPreferences("info", Context.MODE_PRIVATE);
        String emailLogueado = sp.getString("correoLogueado", "sinusuario");
        TextView nombre = findViewById(R.id.txtNombre);
        String nombresito = nombre.getText().toString();
        ApiInterface apiService = conexion.getClient().create(ApiInterface.class);
        Call<Respuesta> call;
        call = apiService.colaborar(monto, emailLogueado, nombresito);
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
                pd.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                pd.setTitleText("¡La donación ha sido enviada!");
                pd.setConfirmText("Aceptar");
                pd.show();
                montoAA += monto;
                ProgressBar me = findViewById(R.id.prg);
                int barra = ((montoAA * 100) / montoTT);
                me.setProgress(barra);
                TextView monto = findViewById(R.id.monto);
                monto.setText("$" + montoAA + " de $" + montoTT);

            }

            @Override
            public void onFailure(Call<Respuesta> call, Throwable t) {
                pd.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                pd.setTitleText("Ha ocurrido un error :(");
                pd.setConfirmText("Aceptar");
                pd.show();
                Log.d("LoginActivity", t.getMessage());
            }
        });
    }

    public void verComentarios(View vista) {
        com = findViewById(R.id.com);
        TextView x = findViewById(R.id.txtNombre);
        String s = x.getText().toString();
        Intent i = new Intent(traerProp.this, comentarios.class);
        i.putExtra("nom", s);
        startActivity(i);
    }

    public void chequearLikePropCelu(String nombreProp) {
        final SharedPreferences sp = getSharedPreferences("info", Context.MODE_PRIVATE);
        String emailLogueado = sp.getString("correoLogueado", "sinusuario");

        ApiInterface apiService = conexion.getClient().create(ApiInterface.class);
        Call<Respuesta> call = apiService.chequearLikePropCel(emailLogueado, nombreProp);
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
                JsonArray mens = response.body().getMessage();
                String mensaje = mens.get(0).getAsJsonObject().get("mens").getAsString();
                ImageView starNegra = (ImageView) findViewById(R.id.starNegra);
                ImageView starVacia = (ImageView) findViewById(R.id.starVacia);


                if (mensaje.equals("tiene")) {
                    starVacia.setVisibility(View.INVISIBLE);
                    starNegra.setVisibility(View.VISIBLE);
                } else {
                    starVacia.setVisibility(View.VISIBLE);
                    starNegra.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<Respuesta> call, Throwable t) {
                Log.d("LoginActivity", t.getMessage());
            }
        });

    }


    public void likePropuesta(View vista) {
        final SharedPreferences sp = getSharedPreferences("info", Context.MODE_PRIVATE);
        String emailLogueado = sp.getString("correoLogueado", "sinusuario");
        TextView txtProp = findViewById(R.id.txtNombre);
        String nombreProp = String.valueOf(txtProp.getText());

        ApiInterface apiService = conexion.getClient().create(ApiInterface.class);
        Call<Respuesta> call = apiService.likePropuestaCel(emailLogueado, nombreProp);
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
                JsonArray mens = response.body().getMessage();
                ImageView starNegra = (ImageView) findViewById(R.id.starNegra);
                ImageView starVacia = (ImageView) findViewById(R.id.starVacia);
                String mensaje = mens.get(0).getAsJsonObject().get("mens").getAsString();
                if (mensaje.equals("ingresar")) {

                    starVacia.setVisibility(View.INVISIBLE);
                    starNegra.setVisibility(View.VISIBLE);
                } else {
                    starVacia.setVisibility(View.VISIBLE);
                    starNegra.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onFailure(Call<Respuesta> call, Throwable t) {
                Log.d("LoginActivity", t.getMessage());
            }
        });
    }

    public void colaborarUp(View v)
    {
        output = false;
        myDialog.setContentView(R.layout.colaborarpopup);
        final EditText txtDonar = myDialog.findViewById(R.id.donarText);
        ImageButton btn = myDialog.findViewById(R.id.donarBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!txtDonar.getText().toString().trim().equals("")) {
                    colaborar(Integer.parseInt(txtDonar.getText().toString()));
                    FirebaseMessaging.getInstance().subscribeToTopic("weather")
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    String msg = "Bien!";
                                    if (!task.isSuccessful()) {
                                        msg = "No tan bien";
                                    }
                                    Log.d("error:", msg);
                                    Toast.makeText(traerProp.this, msg, Toast.LENGTH_SHORT).show();
                                }
                            });
                    myDialog.dismiss();

                } else {
                    SweetAlertDialog pDialog = new SweetAlertDialog(traerProp.this, SweetAlertDialog.WARNING_TYPE);
                    pDialog.setConfirmText("Aceptar");
                    pDialog.setTitleText("¡Debes donar al menos $1!");
                    pDialog.show();
                }

            }
        });
        myDialog.show();
    }

    public void comentarUp(View v) {
        output = false;
        SharedPreferences sp = getSharedPreferences("info", Context.MODE_PRIVATE);
        String nicksito = sp.getString("nickLogueado", "sinnick");
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        myDialog.setContentView(R.layout.comentariopopup);
        TextView txtNick = myDialog.findViewById(R.id.txtNickPerfil);
        final EditText txtComent = myDialog.findViewById(R.id.txtComent);
        CircleImageView img = myDialog.findViewById(R.id.imgPerfilComent);
        txtNick.setText(nicksito);
        Picasso.get().load("http://192.168.25.93/phpLuna/imgUsus/" + nicksito + ".jpg").resize(96, 96).centerCrop().into(img);
        ImageButton btn = myDialog.findViewById(R.id.enviarBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!txtComent.getText().toString().trim().equals("")) {
                    comentar(txtComent.getText().toString());
                    myDialog.dismiss();
                } else {
                    SweetAlertDialog pDialog = new SweetAlertDialog(traerProp.this, SweetAlertDialog.WARNING_TYPE);
                    pDialog.setConfirmText("Aceptar");
                    pDialog.setTitleText("¡No puedes hacer un comentario vacío!");
                    pDialog.show();
                }

            }
        });
        myDialog.show();
    }


}

