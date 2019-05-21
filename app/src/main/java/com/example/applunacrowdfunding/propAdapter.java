package com.example.applunacrowdfunding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            propNomb =  itemView.findViewById(R.id.txtNombre);

        }

        public void asignarDatos(propuests propu) {
            propNomb.setText(propu.getNombre());

        }
    }
}
