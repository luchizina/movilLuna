package com.example.applunacrowdfunding;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.applunacrowdfunding.Conexion.ApiError;
import com.example.applunacrowdfunding.Conexion.ApiInterface;
import com.example.applunacrowdfunding.Conexion.Respuesta;
import com.example.applunacrowdfunding.Conexion.conexion;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import androidx.appcompat.app.AppCompatActivity;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class registro extends AppCompatActivity {
    private static final String KEY_EMPTY = "";
    private EditText etNick;
    private EditText etCont;
    private EditText etNombre;
    Dialog myDialog;
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
    private ImageView imageview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        etNick = findViewById(R.id.nick);
        etCont = findViewById(R.id.cont);
        etNombre = findViewById(R.id.nom);
        etApe = findViewById(R.id.ape);
        etCorreo = findViewById(R.id.correo);
        etCel = findViewById(R.id.cel);
        etCI = findViewById(R.id.ci);
        myDialog = new Dialog(this);
        Button regis = findViewById(R.id.reg);
         imageview = findViewById(R.id.imgR);
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
                if (validateInputs()) {
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
                            Intent i = new Intent(registro.this, iniciarSesion.class);
                            startActivity(i);
                        }

                        @Override
                        public void onFailure(Call<Respuesta> call, Throwable t) {
                            Log.d("LoginActivity", t.getMessage());
                        }

                    });

                }

            }
        });
    }


    public void cargarImg(View v) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        myDialog.setContentView(R.layout.imagenpop);
     //   TextView txtNick = myDialog.findViewById(R.id.txtNickPerfil);
       // final EditText txtComent = myDialog.findViewById(R.id.txtComent);
        //CircleImageView img = myDialog.findViewById(R.id.imgPerfilComent);

       // Picasso.get().load("http://192.168.25.43/phpLuna/imgUsus/" + nicksito + ".jpg").resize(96, 96).centerCrop().into(img);
        ImageButton btn = myDialog.findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto , 1);//one can be replaced with any action code
            }
        });
        ImageButton btn2 = myDialog.findViewById(R.id.button2);
        btn2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePicture, 0);//zero can be replaced with any action code
            }
        });
        myDialog.show();
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch(requestCode) {
            case 0:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    imageview.setImageURI(selectedImage);
                }
                break;
            case 1:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    imageview.setImageURI(selectedImage);
                }
                break;
        }
    }

    private boolean validateInputs() {
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
        if (ci.length() != 8 && ci.length() != 7) {
            etCI.setError("La ci debe tener entre 7 y 8 caracteres");
            etCI.requestFocus();
            return false;
        }
        return true;
    }
}
