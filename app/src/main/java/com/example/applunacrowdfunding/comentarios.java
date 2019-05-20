package com.example.applunacrowdfunding;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.example.applunacrowdfunding.Conexion.ApiError;
import com.example.applunacrowdfunding.Conexion.ApiInterface;
import com.example.applunacrowdfunding.Conexion.Respuesta;
import com.example.applunacrowdfunding.Conexion.conexion;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class comentarios extends AppCompatActivity {
    String nombre;
    private RecyclerView recyclerView;
    ArrayList<coments> c= new ArrayList<>();
    private comAdapter coAd;
    String nick;
    final SharedPreferences sp = getSharedPreferences("info", Context.MODE_PRIVATE);
    String emailLogueado= sp.getString("correoLogueado","sinusuario");
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
                c = new Gson().fromJson(response.body().getMessage(), new TypeToken<List<coments>>(){}.getType());
                        //new ArrayList<>(response.body().getMessage());
                coAd = new comAdapter(comentarios.this, c);
                recyclerView.setAdapter(coAd);
                final String nickUsuL = nickLog(emailLogueado);

                new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        for(coments co : c){
                            if(co.getNickUsuario() == nickUsuL){
                                coAd.removeItem(viewHolder.getAdapterPosition());
                                borrCom(co.getId());
                            }
                        }
                    }
                }).attachToRecyclerView(recyclerView);
        }

            @Override
            public void onFailure(Call<Respuesta> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
            });
    }

    private String nickLog(String correo){
        ApiInterface apiService = conexion.getClient().create(ApiInterface.class);
        Call<Respuesta> call = apiService.UsuCorreo(correo);
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

                JsonArray js = response.body().getMessage();
                nick = js.get(0).getAsJsonObject().get("nick").getAsString();

            }

            @Override
            public void onFailure(Call<Respuesta> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
        return nick;
    }

    private void borrCom(String id){
        ApiInterface apiService = conexion.getClient().create(ApiInterface.class);
        Call<Respuesta> call = apiService.BorrCom(id);
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
                Log.d("Error", t.getMessage());
            }
        });
    }
    }

