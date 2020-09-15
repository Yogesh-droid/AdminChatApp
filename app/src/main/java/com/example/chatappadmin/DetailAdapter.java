package com.example.chatappadmin;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.ViewHolder>{
    Context context;
    ArrayList<ModelClass> arrayList;

    public DetailAdapter(Context context, ArrayList<ModelClass> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public DetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.detail_adapter,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailAdapter.ViewHolder holder, int position) {
        Toast.makeText(context,(DateFormat.format("dd-mm-yyyy(HH:mm:ss)",arrayList.get(position).getTime())),Toast.LENGTH_SHORT).show();
        holder.name.setText(arrayList.get(position).getDisplayName());
        holder.message.setText(arrayList.get(position).getMessage());
        holder.time.setText(DateFormat.format("dd-mm-yyyy(HH:mm:ss)",arrayList.get(position).getTime()));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,message,time;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.name=itemView.findViewById(R.id.message_user);
            this.message=itemView.findViewById(R.id.message_text);
            this.time=itemView.findViewById(R.id.message_time);
        }
    }
}
