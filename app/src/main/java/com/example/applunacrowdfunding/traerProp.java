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

import androidx.appcompat.app.AppCompatActivity;

import com.example.applunacrowdfunding.Conexion.ApiError;
import com.example.applunacrowdfunding.Conexion.ApiInterface;
import com.example.applunacrowdfunding.Conexion.Respuesta;
import com.example.applunacrowdfunding.Conexion.conexion;
import com.google.gson.JsonArray;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class traerProp extends AppCompatActivity {
    Button com;
    String nom;
    Dialog myDialog;
    boolean output = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myDialog = new Dialog(this);
        setContentView(R.layout.activity_traer_prop);
        String nomProp = "";
        Bundle extras =getIntent().getExtras();
        if(extras!=null){
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

                nom = nombre;
             /*   JsonArray numero = response.body().getMessage();
                String nombre=numero.get(0).getAsJsonObject().get("numerito").getAsString();*/
                TextView txtNombre = findViewById(R.id.txtNombre);
                txtNombre.setText(nombre);

                chequearLikePropCelu(nombre);




                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);

                ImageView imageView = (ImageView) findViewById(R.id.img);
                Bundle extras =getIntent().getExtras();
                String np= "";
                if(extras!=null){
                    np = extras.getString("nombreProp");
                }
                String nueva="http://192.168.25.26/phpLuna/imgProps/"+np+".jpg";
                try{
                    URL url = new URL(nueva);
                    imageView.setImageBitmap(BitmapFactory.decodeStream((InputStream)url.getContent()));
                }catch(IOException e){
                    Log.e("nombre",e.getMessage());
                }

                TextView monto = findViewById(R.id.monto);
                monto.setText("Monto:  $"+montoA+" de $"+montoT);

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

            }

            @Override
            public void onFailure(Call<Respuesta> call, Throwable t) {
                Log.d("LoginActivity", t.getMessage());


            }
        });

    }

    public void comentar(String text) {
        SweetAlertDialog pDialog = new SweetAlertDialog(traerProp.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Enviando Comentario...");
        pDialog.setCancelable(false);
        pDialog.show();
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
                SweetAlertDialog pDialog = new SweetAlertDialog(traerProp.this, SweetAlertDialog.SUCCESS_TYPE);
                pDialog.setTitleText("El comentario ha sido enviado!");
                pDialog.setConfirmText("Aceptar");
                pDialog.show();

            }

            @Override
            public void onFailure(Call<Respuesta> call, Throwable t) {
                SweetAlertDialog pDialog = new SweetAlertDialog(traerProp.this, SweetAlertDialog.ERROR_TYPE);
                pDialog.setTitleText("Ha ocurrido un error :(");
                pDialog.setConfirmText("Aceptar");
                pDialog.show();
                Log.d("LoginActivity", t.getMessage());

            }
        });
        pDialog.dismissWithAnimation();
    }

    public void colaborar(View vista)
    {
        final SharedPreferences sp = getSharedPreferences("info", Context.MODE_PRIVATE);
        String emailLogueado = sp.getString("correoLogueado", "sinusuario");
        EditText textito = findViewById(R.id.donarText);
        TextView nombre = findViewById(R.id.txtNombre);
        String nombresito = nombre.getText().toString();
        int text = Integer.parseInt(textito.getText().toString());
        ApiInterface apiService = conexion.getClient().create(ApiInterface.class);
        Call<Respuesta> call = null;
        try{
             call = apiService.colaborar(text, emailLogueado, nombresito);
        }catch(Exception e)
        {
            Log.d("Error", "No entiendo: "+e);
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
        TextView x = findViewById(R.id.txtNombre);
        String s = x.getText().toString();
                Intent i = new Intent(traerProp.this, comentarios.class);
                i.putExtra("nom", s);
                startActivity(i);
            }

public void chequearLikePropCelu(String nombreProp){
    final SharedPreferences sp = getSharedPreferences("info", Context.MODE_PRIVATE);
    String emailLogueado = sp.getString("correoLogueado", "sinusuario");

    ApiInterface apiService = conexion.getClient().create(ApiInterface.class);
    Call<Respuesta> call = apiService.chequearLikePropCel(emailLogueado,nombreProp);
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


            if(mensaje.equals("tiene")){
                starVacia.setVisibility(View.INVISIBLE);
                starNegra.setVisibility(View.VISIBLE);
            }else{
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


     public void likePropuesta(View vista){
         final SharedPreferences sp = getSharedPreferences("info", Context.MODE_PRIVATE);
         String emailLogueado = sp.getString("correoLogueado", "sinusuario");
        TextView txtProp =  findViewById(R.id.txtNombre);
        String nombreProp = String.valueOf(txtProp.getText());

         ApiInterface apiService = conexion.getClient().create(ApiInterface.class);
         Call<Respuesta> call = apiService.likePropuestaCel(emailLogueado,nombreProp);
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
               if(mensaje.equals("ingresar")){

                 starVacia.setVisibility(View.INVISIBLE);
                 starNegra.setVisibility(View.VISIBLE);
               }else{
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

     public void comentarUp(View v)
     {
         output = false;
         SharedPreferences sp = getSharedPreferences("info", Context.MODE_PRIVATE);
         String nicksito = sp.getString("nickLogueado","sinnick");
         String nueva="http://192.168.25.26/phpLuna/imgUsus/"+nicksito+".jpg";
         StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
         StrictMode.setThreadPolicy(policy);
         Bitmap c = null;
         try{
             URL url = new URL(nueva);
             c = BitmapFactory.decodeStream((InputStream)url.getContent());
         }catch(IOException e){
             Log.e("nombre",e.getMessage());
         }
         myDialog.setContentView(R.layout.comentariopopup);
         TextView txtNick = myDialog.findViewById(R.id.txtNickPerfil);
         final EditText txtComent = myDialog.findViewById(R.id.txtComent);
         CircleImageView img = myDialog.findViewById(R.id.imgPerfilComent);
         txtNick.setText(nicksito);
         img.setImageBitmap(c);
         ImageButton btn = myDialog.findViewById(R.id.enviarBtn);
         btn.setOnClickListener(new View.OnClickListener(){
             public void onClick(View v)
             {
                 if(!txtComent.getText().toString().trim().equals(""))
                 {

                     comentar(txtComent.getText().toString());
                     myDialog.dismiss();

                 }
                 else
                 {
                     SweetAlertDialog pDialog = new SweetAlertDialog(traerProp.this,SweetAlertDialog.WARNING_TYPE);
                     pDialog.setConfirmText("Aceptar");
                     pDialog.setTitleText("¡No puedes hacer un comentario vacío!");
                     pDialog.show();
                 }

             }
         });
         myDialog.show();
     }




    }

