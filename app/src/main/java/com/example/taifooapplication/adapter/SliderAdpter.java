package com.example.taifooapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.taifooapplication.R;
import com.example.taifooapplication.modelclas.ShowImage_ModelClass;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SliderAdpter extends RecyclerView.Adapter<SliderAdpter.ViewHOlder> {

    Context context;

    ArrayList<ShowImage_ModelClass> show_Image;

    ViewPager2 viewPager2;

    public SliderAdpter(Context context, ArrayList<ShowImage_ModelClass> showImage, ViewPager2 sliderViewPager2) {

        this.context = context;
        this.show_Image = showImage;
        this.viewPager2 = sliderViewPager2;
    }

    @NonNull
    @Override
    public ViewHOlder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_items,parent,false);
        return new ViewHOlder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHOlder holder, int position) {

        ShowImage_ModelClass slideImage = show_Image.get(position);

       /* int image = slideImage.getImage();
        int strImage = Integer.valueOf(image);*/

        String image = slideImage.getImage();
        Picasso.with(context).load(image).into(holder.img_showImage);

       // Glide.with(context).load(image).into(holder.img_showImage);


        if(position == show_Image.size() - 2){

            viewPager2.post(runnable);
        }

    }

    @Override
    public int getItemCount() {
        return show_Image.size();
    }

    public class ViewHOlder extends RecyclerView.ViewHolder {

        ImageView img_showImage;

        public ViewHOlder(@NonNull View itemView) {
            super(itemView);

            img_showImage = itemView.findViewById(R.id.img_showImage);
        }
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {

            show_Image.addAll(show_Image);
            notifyDataSetChanged();
        }
    };
}
