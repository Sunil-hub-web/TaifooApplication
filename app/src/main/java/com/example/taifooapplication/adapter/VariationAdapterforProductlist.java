package com.example.taifooapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.taifooapplication.R;
import com.example.taifooapplication.modelclas.VariationDetails;

import java.util.List;

public class VariationAdapterforProductlist extends RecyclerView.Adapter<VariationAdapterforProductlist.MyViewHolder> {

    private LayoutInflater inflater;
    private Context context;
    private List<VariationDetails> accepetedJobLists;

    public TextView name, price, regprice;
    //    public Button viewprofile;
    public ImageView thumbnail;

    public VariationAdapterforProductlist(List<VariationDetails> accepetedJobLists, Context context) {
        this.accepetedJobLists = accepetedJobLists;
        this.context = context;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.from(parent.getContext()).inflate(R.layout.sub_cat_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        VariationDetails movie = accepetedJobLists.get(position);

        holder.heading.setText(movie.getVarations());


    }


    @Override
    public int getItemCount() {
        return accepetedJobLists.size();
//        return 6;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView heading;

        public MyViewHolder(View itemView) {
            super(itemView);

            heading = (TextView) itemView.findViewById(R.id.heading);

        }
    }

}