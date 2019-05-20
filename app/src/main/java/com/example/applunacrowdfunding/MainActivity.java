package com.example.applunacrowdfunding;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

public class MainActivity extends AppCompatActivity {
Button registro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//codigo mio
        final SharedPreferences sp = getSharedPreferences("info", Context.MODE_PRIVATE);
        String emailLogueado= sp.getString("correoLogueado","sinusuario");

        String mantieneAct = sp.getString("mantieneAct","algo");
        if((!emailLogueado.equals("sinusuario"))){
        View btnCerrar =  findViewById(R.id.btnCerrar);
        btnCerrar.setVisibility(View.VISIBLE);
            View btnRegistrar = findViewById(R.id.buttonR);
            btnCerrar.setVisibility(View.GONE);
        }
        //hasta aca



    }
    public void traerProp(View vista){
        String prop = "hola";
        Intent intento = new Intent(MainActivity.this,traerProp.class);
        intento.putExtra("prop",prop);
        startActivity(intento);

    }

    public void iniciarS(View vista){

        String algo ="oh";
        Intent intento = new Intent(MainActivity.this,iniciarSesion.class);
        intento.putExtra("algo",algo);
        startActivity(intento);
    }

    public void listarProp(View vista){
        Intent intento = new Intent(MainActivity.this,listarProp.class);
        startActivity(intento);
    }

    public void cerrarSesion(View vista){
        SharedPreferences prefs =  getSharedPreferences("info", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove("correoLogueado");
        editor.remove("mantieneAct");
        editor.commit();
        Intent intento = new Intent(MainActivity.this,iniciarSesion.class);
        startActivity(intento);
    }


    public void registrarUsu(View vista){
        Intent i = new Intent(MainActivity.this, registro.class);
        startActivity(i);
    }



}