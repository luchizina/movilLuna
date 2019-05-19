package com.example.applunacrowdfunding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.applunacrowdfunding.Conexion.ApiError;
import com.example.applunacrowdfunding.Conexion.ApiInterface;
import com.example.applunacrowdfunding.Conexion.Respuesta;
import com.example.applunacrowdfunding.Conexion.conexion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class comentarios extends AppCompatActivity {
    String nombre;
    private RecyclerView recyclerView;
    ArrayList<coments> c= new ArrayList<>();
    private comAdapter coAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comentarios);
        nombre = getIntent().getStringExtra("nom");
        recyclerView=(RecyclerView) findViewById(R.id.listit);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadJSON();
    }

   /* private void initViews(){
        recyclinitViews()erView = (RecyclerView)findViewById(R.id.listit);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        loadJSON();
    }*/

    private void loadJSON(){
        ApiInterface apiService = conexion.getClient().create(ApiInterface.class);
        Call<Respuesta> call = apiService.getCom(nombre);
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
                c = new ArrayList<>(response.body().getMessage());
                coAd = new comAdapter(comentarios.this, c);
                recyclerView.setAdapter(coAd);

        }

            @Override
            public void onFailure(Call<Respuesta> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
            });
    }
}

