package com.example.applunacrowdfunding;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class comAdapter extends RecyclerView.Adapter<comAdapter.ViewHolder> {

    ArrayList<coments> co;
<<<<<<< HEAD
=======



>>>>>>> 01789c6ccab1e3f04583a38dfce5efe0d437f0d1
    public comAdapter(ArrayList<coments> coms) {
        this.co = coms;
    }

    @NonNull
    @Override
    public comAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.com_list_item, parent, false);
        return new comAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull comAdapter.ViewHolder holder, int position) {
        holder.asignarDatos(co.get(position));
    }

    @Override
    public int getItemCount() {
        return co.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView usu, come;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            usu = itemView.findViewById(R.id.usu);
            come = itemView.findViewById(R.id.comen);
        }

        public void asignarDatos(coments coments) {
            usu.setText(coments.getNickUsuario());
            come.setText(coments.getTexto());
        }
    }

     public void removeItem(int position) {
        coments c = co.get(position);
        co.remove(c);
        notifyItemRemoved(position);
    }
}
