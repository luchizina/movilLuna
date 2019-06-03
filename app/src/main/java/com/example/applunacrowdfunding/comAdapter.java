package com.example.applunacrowdfunding;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class comAdapter extends RecyclerView.Adapter<comAdapter.ViewHolder> {

    ArrayList<coments> co;
    public comentarios c;



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
    public void onBindViewHolder(@NonNull comAdapter.ViewHolder holder, final int position) {
        holder.asignarDatos(co.get(position));
        holder.heartr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setTag(position);
                c.dislike(v);
            }
        });
        holder.heartw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setTag(position);
                c.like(v);
            }
        });
        holder.itemView.setTag(position);
        holder.mView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return co.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View mView;
        TextView usu, come;
        ImageView heartr,heartw;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            usu = itemView.findViewById(R.id.usu);
            come = itemView.findViewById(R.id.comen);
            heartr = itemView.findViewById(R.id.heartr);
            heartw = itemView.findViewById(R.id.heartw);
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
         notifyDataSetChanged();
    }

    public Object getItem(int pos) {
        return co.get(pos);
    }
    
}
