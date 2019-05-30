package com.example.applunacrowdfunding;

import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class propAdapter extends RecyclerView.Adapter<propAdapter.ViewHolder> {

     ArrayList<propuests> p;

    public propAdapter(ArrayList<propuests> props){
        this.p = props;
    }

    @NonNull
    @Override
    public propAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.prop_list_item, parent, false);
        return new propAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull propAdapter.ViewHolder holder, int position) {
        holder.asignarDatos(p.get(position));
    }

    @Override
    public int getItemCount() {
        return p.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView propNomb;
        ImageView imagenota;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            propNomb =  itemView.findViewById(R.id.txtNombre);
            imagenota = itemView.findViewById(R.id.imgListaProp);
        }

        public void asignarDatos(propuests propu) {
            propNomb.setText(propu.getNombre());
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            String nueva="http://192.168.1.2/phpLuna/imgProps/"+propu.getNombre()+".jpg";
            try{
                URL url = new URL(nueva);
                imagenota.setImageBitmap(BitmapFactory.decodeStream((InputStream)url.getContent()));

            }catch(IOException e){
                Log.e("nombre",e.getMessage());
            }
        }
    }
}
