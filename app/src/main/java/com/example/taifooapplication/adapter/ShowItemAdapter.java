package com.example.taifooapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taifooapplication.R;
import com.example.taifooapplication.activity.ActivityCategoryPage;
import com.example.taifooapplication.fragment.ActivityCategory;
import com.example.taifooapplication.modelclas.ShowItem_ModelClass;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ShowItemAdapter extends RecyclerView.Adapter<ShowItemAdapter.ViewHolder> {

    Context context;
    ArrayList<ShowItem_ModelClass> showItem;
    String cartCount;

    public ShowItemAdapter(Context context, ArrayList<ShowItem_ModelClass> showItem, String cart_count) {

        this.context = context;
        this.showItem = showItem;
        this.cartCount = cart_count;

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

        String image = show_item.getImage();

        if(image.equals("")){
        }else{
            Picasso.with(context).load(image).into(holder.itemImage);
        }

        String plainText = Html.fromHtml(show_item.getCategory_name()).toString();
        holder.itemName.setText(plainText);

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

                //Intent intent = new Intent(context, ActivityCategoryPage.class);
                //intent.putExtra("categoryId",show_item.getCategory_id());
                //intent.putExtra("categoryName",show_item.getCategory_name());
                //intent.putExtra("cartCount",cartCount);
                //context.startActivity(intent);

                ActivityCategory activityCategory = new ActivityCategory();
                Bundle bundle=new Bundle();
                bundle.putString("categoryId",show_item.getCategory_id());
                bundle.putString("categoryName",show_item.getCategory_name());
                activityCategory.setArguments(bundle);
                FragmentManager fragmentManager = ((FragmentActivity)context).getSupportFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.replace(R.id.framLayout,activityCategory);
                ft.addToBackStack(null);
                ft.commit();

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
