package com.example.taifooapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taifooapplication.R;
import com.example.taifooapplication.activity.ActivityCategoryPage;
import com.example.taifooapplication.activity.HomePageActivity;
import com.example.taifooapplication.fragment.CategoryPage;
import com.example.taifooapplication.fragment.MyOrder;
import com.example.taifooapplication.modelclas.ShowItem_ModelClass;
import com.squareup.picasso.Picasso;

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

        String image = "https://"+show_item.getImage();
        Picasso.with(context).load(image).into(holder.itemImage);
        holder.itemName.setText(show_item.getCategory_name());

        holder.rel_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String categoryId = show_item.getCategory_id();

               /* AppCompatActivity activity = (AppCompatActivity) v.getContext();
                CategoryPage categoryPage = new CategoryPage();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.framLayout, categoryPage,categoryId).addToBackStack(null).commit();

                HomePageActivity.loc.setVisibility(View.GONE);
                HomePageActivity.logo.setVisibility(View.GONE);
                HomePageActivity.search.setVisibility(View.GONE);

                String productName = show_item.getCategory_name();

                HomePageActivity.text_name.setTextSize(18);
                HomePageActivity.text_name.setText(productName);*/

                Intent intent = new Intent(context, ActivityCategoryPage.class);
                intent.putExtra("categoryId",show_item.getCategory_id());
                intent.putExtra("categoryName",show_item.getCategory_name());
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return showItem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView itemImage;
        TextView itemName;
        RelativeLayout rel_view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemImage = itemView.findViewById(R.id.itemImage);
            itemName = itemView.findViewById(R.id.itemName);
            rel_view = itemView.findViewById(R.id.rel_view);

        }
    }
}
