package com.example.applunacrowdfunding;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.example.applunacrowdfunding.Conexion.ApiError;
import com.example.applunacrowdfunding.Conexion.ApiInterface;
import com.example.applunacrowdfunding.Conexion.Respuesta;
import com.example.applunacrowdfunding.Conexion.conexion;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class listarProp extends AppCompatActivity {

    private ShimmerRecyclerView recyclerView;
    ArrayList<propuests> p= new ArrayList<>();
    TextView txtNombre;
    private propAdapter proAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_prop);
        recyclerView= findViewById(R.id.shimmer_recycler_view);
        txtNombre = findViewById(R.id.txtNombre);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadJSON();


    }

    private void loadJSON(){
        ApiInterface apiService = conexion.getClient().create(ApiInterface.class);
        Call<Respuesta> call = apiService.getPropuestas();
        recyclerView.showShimmerAdapter();
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
                p = new Gson().fromJson(response.body().getMessage(), new TypeToken<List<propuests>>(){}.getType());
                //new ArrayList<>(response.body().getMessage());
                Drawable dividerDrawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.divider);
                RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecoration(dividerDrawable);
                recyclerView.addItemDecoration(dividerItemDecoration);
                proAd = new propAdapter(p);
                recyclerView.setAdapter(proAd);
                recyclerView.hideShimmerAdapter();
                proAd.setOnItemClickListener(new propAdapter.OnItemClickListener() {
                    @Override
                    public void OnItemClick(int posicion) {
                        final propuests prop = p.get(posicion);

                        String nombreProp = prop.getNombre();
                        Intent intento = new Intent(listarProp.this,traerProp.class);
                        intento.putExtra("nombreProp",nombreProp);
                        startActivity(intento);

                    }
                });

            }

            @Override
            public void onFailure(Call<Respuesta> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
    }




}
