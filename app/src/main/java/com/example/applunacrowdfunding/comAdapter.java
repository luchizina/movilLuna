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

    private ArrayList<coments> co = new ArrayList<>();
    private Context cont;

    public comAdapter(Context con, ArrayList<coments> coms){
        this.co = coms;
        this.cont = con;
    }

    @NonNull
    @Override
    public comAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.com_list_item, parent, false);
        return new comAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull comAdapter.ViewHolder holder, int position) {
        holder.usu.setText(co.get(position).getNickUsuario());
        holder.come.setText(co.get(position).getTexto());
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView usu, come;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            usu = (TextView) itemView.findViewById(R.id.usu);
            come = (TextView) itemView.findViewById(R.id.comen);
        }
    }
}
