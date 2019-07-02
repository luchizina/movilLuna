package com.example.applunacrowdfunding;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.StrictMode;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class comAdapter extends RecyclerView.Adapter<comAdapter.ViewHolder> {

    ArrayList<coments> co;
    public comentarios c;


    public comAdapter(ArrayList<coments> coms, Activity a) {
        this.co = coms;
        this.c = (comentarios) a;
    }

    @NonNull
    @Override
    public comAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.com_list_item, parent, false);
        return new comAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final comAdapter.ViewHolder holder, final int position) {
        holder.asignarDatos(co.get(position));
        holder.heartr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setTag(position);
                holder.heartr.setVisibility(v.INVISIBLE);
                holder.heartw.setVisibility(v.VISIBLE);
                c.dislike(v);
            }
        });
        holder.heartw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setTag(position);
                holder.heartw.setVisibility(v.INVISIBLE);
                holder.heartr.setVisibility(v.VISIBLE);
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
        TextView come;
        ImageView heartr, heartw;
        CircleImageView fotito;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            fotito = itemView.findViewById(R.id.imgUsuComent);
            come = itemView.findViewById(R.id.txtComentItem);
            heartr = itemView.findViewById(R.id.heartr);
            heartw = itemView.findViewById(R.id.heartw);
        }

        public void asignarDatos(coments coments) {
            come.setText(Html.fromHtml("<b>" + coments.getNickUsuario() + "</b>" + "<br />" + "<br />" + coments.getTexto()));
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Picasso.get().load("http://192.168.1.9/phpLuna/imgUsus/" + coments.getNickUsuario() + ".jpg").resize(50, 50).centerCrop().into(fotito);
            fotito.setBackgroundColor(Color.TRANSPARENT);
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
