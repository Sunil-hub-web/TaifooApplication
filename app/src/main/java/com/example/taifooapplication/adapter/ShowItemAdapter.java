package com.example.taifooapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taifooapplication.R;
import com.example.taifooapplication.activity.HomePageActivity;
import com.example.taifooapplication.modelclas.ShowItem_ModelClass;

import java.util.ArrayList;

public class ShowItemAdapter extends RecyclerView.Adapter<ShowItemAdapter.ViewHolder> {

    Context context;
    ArrayList<ShowItem_ModelClass> showItem;

    public ShowItemAdapter(Context context, ArrayList<ShowItem_ModelClass> showItem) {

        this.context = context;
        this.showItem = showItem;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.showitem,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ShowItem_ModelClass show_item = showItem.get(position);

        holder.itemImage.setImageResource(show_item.getImage());
        holder.itemName.setText(show_item.getName());

    }

    @Override
    public int getItemCount() {
        return showItem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView itemImage;
        TextView itemName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemImage = itemView.findViewById(R.id.itemImage);
            itemName = itemView.findViewById(R.id.itemName);

        }
    }
}
