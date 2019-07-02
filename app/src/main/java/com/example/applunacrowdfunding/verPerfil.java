package com.example.applunacrowdfunding;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.applunacrowdfunding.Conexion.ApiError;
import com.example.applunacrowdfunding.Conexion.ApiInterface;
import com.example.applunacrowdfunding.Conexion.Respuesta;
import com.example.applunacrowdfunding.Conexion.conexion;
import com.google.gson.JsonArray;
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class verPerfil extends AppCompatActivity {
SweetAlertDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        pd = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pd.setCancelable(false);
        pd.setTitleText("Cargando Información.");
        pd.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pd.show();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_perfil);
        overrideDrawerImageLoaderPicasso();
        NavigationDrawerInstall nav = new NavigationDrawerInstall();
        nav.crearHamburguesita(this);
        String nick = "";
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            nick = extras.getString("nick");
        }
        Call<Respuesta> call = null;
        Bundle extra = getIntent().getExtras();
        final ApiInterface apiService = conexion.getClient().create(ApiInterface.class);
        call = apiService.traerPerfilM(nick);
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
                String nick = arregloUsers.get(0).getAsJsonObject().get("Nick").getAsString();
                String nombre = arregloUsers.get(0).getAsJsonObject().get("Nombre").getAsString();
                String apellido = arregloUsers.get(0).getAsJsonObject().get("Apellido").getAsString();
                String correo = arregloUsers.get(0).getAsJsonObject().get("Correo").getAsString();
                String celu = arregloUsers.get(0).getAsJsonObject().get("Celular").getAsString();
             /*   JsonArray numero = response.body().getMessage();
                String nombre=numero.get(0).getAsJsonObject().get("numerito").getAsString();*/
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                ImageView imageView = (ImageView) findViewById(R.id.img);
                Bundle extras = getIntent().getExtras();
                String npl = "";
                if (extras != null) {
                    npl = extras.getString("nick");
                }
                String nueva = "http://192.168.1.9/phpLuna/imgUsus/" + npl + ".jpg";
                try {
                    URL url = new URL(nueva);
                    imageView.setImageBitmap(BitmapFactory.decodeStream((InputStream) url.getContent()));
                } catch (IOException e) {
                    Log.e("nombre", e.getMessage());
                }
                TextView txtNick = findViewById(R.id.txtNick);
                txtNick.setText(nick);

                TextView txtNombre = findViewById(R.id.txtNombre);
                txtNombre.setText(nombre);

                TextView txtApellido = findViewById(R.id.txtApellido);
                txtApellido.setText(apellido);

                TextView txtCelu = findViewById(R.id.txtCelu);
                txtCelu.setText(celu);

                TextView txtCorreo = findViewById(R.id.txtCorreo);
                txtCorreo.setText(correo);
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

    private void overrideDrawerImageLoaderPicasso(){
        //initialize and create the image loader logic
        DrawerImageLoader.init(new AbstractDrawerImageLoader() {
            @Override
            public void set(ImageView imageView, Uri uri, Drawable placeholder) {
                Picasso.get().load(uri).placeholder(placeholder).into(imageView);
            }

            @Override
            public void cancel(ImageView imageView) {
                Picasso.get().cancelRequest(imageView);
            }

           /*
           @Override
           public Drawable placeholder(Context ctx) {
               return super.placeholder(ctx);
           }

           @Override
           public Drawable placeholder(Context ctx, String tag) {
               return super.placeholder(ctx, tag);
           }
           */
        });
    }
}
