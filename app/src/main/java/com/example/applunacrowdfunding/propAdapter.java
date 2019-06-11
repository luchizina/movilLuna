package com.example.applunacrowdfunding;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class propAdapter extends RecyclerView.Adapter<propAdapter.ViewHolder> {

    ArrayList<propuests> p;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void OnItemClick(int posicion);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public propAdapter(ArrayList<propuests> props) {
        this.p = props;
    }

    @NonNull
    @Override
    public propAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.prop_list_item, parent, false);
        return new propAdapter.ViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull propAdapter.ViewHolder holder, int position) {
        holder.asignarDatos(p.get(position));
    }

    @Override
    public int getItemCount() {
        return p.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView propNomb;
        TextView propDesc;
        ImageView imagenota;

        public ViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            propNomb = itemView.findViewById(R.id.txtNombre);
            imagenota = itemView.findViewById(R.id.imgListaProp);
            propDesc = itemView.findViewById(R.id.txtDesc);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.OnItemClick(position);
                        }
                    }
                }
            });
        }

        public void asignarDatos(propuests propu) {
            propNomb.setText(propu.getNombre());
            propDesc.setText(propu.getDescripcion());
            propNomb.setBackgroundColor(Color.TRANSPARENT);
            propDesc.setBackgroundColor(Color.TRANSPARENT);
            imagenota.setBackgroundColor(Color.TRANSPARENT);
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            String nueva = "http://192.168.20.192/phpLuna/imgProps/" + propu.getNombre() + ".jpg";
            Picasso.get().load(nueva).resize(96, 96).centerCrop().into(imagenota);
        }
    }


}
