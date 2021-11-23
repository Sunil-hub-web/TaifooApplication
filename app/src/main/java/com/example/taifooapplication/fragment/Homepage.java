package com.example.taifooapplication.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.example.taifooapplication.R;
import com.example.taifooapplication.adapter.ShowItemAdapter;
import com.example.taifooapplication.adapter.SliderAdpter;
import com.example.taifooapplication.modelclas.ShowImage_ModelClass;
import com.example.taifooapplication.modelclas.ShowItem_ModelClass;

import java.util.ArrayList;

public class Homepage extends Fragment {

    TextView text_name,text_shopByCategory;
    SliderAdpter sliderAdpter;
    ViewPager2 sliderViewPager2;
    TextView [] dots;
    LinearLayout dots_container;
    RecyclerView showitemRecycler;
    ShowItemAdapter showItemAdapter;
    GridLayoutManager gridLayoutManager;

    ArrayList<ShowItem_ModelClass> showItem = new ArrayList<>();

    Handler sliderhandler = new Handler();

    ArrayList<ShowImage_ModelClass> imageSlider = new ArrayList<>();

    int currentPossition = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull  LayoutInflater inflater,
                             @Nullable  ViewGroup container,
                             @Nullable  Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.homepage,container,false);

        sliderViewPager2 = view.findViewById(R.id.sliderViewPager2);
        dots_container = view.findViewById(R.id.dots_container);
        showitemRecycler = view.findViewById(R.id.showitemRecycler);
        text_shopByCategory = view.findViewById(R.id.shopByCategory);
        text_name = view.findViewById(R.id.name);


        String mystring=new String("Shop");
        String text = "by Category";
        SpannableString content = new SpannableString(mystring);
        content.setSpan(new UnderlineSpan(), 0, mystring.length(), 0);
        text_shopByCategory.setText(content+" "+text);


        imageSlider.add(new ShowImage_ModelClass(R.drawable.banner1));
        imageSlider.add(new ShowImage_ModelClass(R.drawable.banner2));
        imageSlider.add(new ShowImage_ModelClass(R.drawable.banner4));
        imageSlider.add(new ShowImage_ModelClass(R.drawable.banner3));
        imageSlider.add(new ShowImage_ModelClass(R.drawable.banner1));


        sliderAdpter = new SliderAdpter(getContext(),imageSlider,sliderViewPager2);
        sliderViewPager2.setAdapter(sliderAdpter);

        int arraysize = imageSlider.size();

        dots = new TextView[arraysize];

        dotsIndicator();

        sliderViewPager2.setClipToPadding(false);
        sliderViewPager2.setClipChildren(false);
        sliderViewPager2.setOffscreenPageLimit(3);
        sliderViewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull  View page, float position) {

                float r = 1 - Math.abs(position);
                page.setScaleY(1.2f +  r * 0.1f);

            }
        });

        sliderViewPager2.setPageTransformer(compositePageTransformer);

        Runnable sliderRunable = new Runnable() {
            @Override
            public void run() {

                sliderViewPager2.setCurrentItem(sliderViewPager2.getCurrentItem() + 1);
            }
        };

        sliderViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                selectedIndicatorPosition(position++);
                sliderhandler.removeCallbacks(sliderRunable);
                sliderhandler.postDelayed(sliderRunable,3000);

            }
        });


        showItem.add(new ShowItem_ModelClass(R.drawable.webcapture,"Today's Deal"));
        showItem.add(new ShowItem_ModelClass(R.drawable.webcapture,"Chicken"));
        showItem.add(new ShowItem_ModelClass(R.drawable.webcapture,"Mutton"));
        showItem.add(new ShowItem_ModelClass(R.drawable.webcapture,"Fish & SeaFood"));
        showItem.add(new ShowItem_ModelClass(R.drawable.webcapture,"Eggs"));
        showItem.add(new ShowItem_ModelClass(R.drawable.webcapture,"Prowns"));
        showItem.add(new ShowItem_ModelClass(R.drawable.webcapture,"Ready To Cook"));
        showItem.add(new ShowItem_ModelClass(R.drawable.webcapture,"Kebab & Tandoor"));

        gridLayoutManager = new GridLayoutManager(getContext(),4,GridLayoutManager.VERTICAL,false);
        showItemAdapter = new ShowItemAdapter(getContext(),showItem);
        showitemRecycler.setLayoutManager(gridLayoutManager);
        showitemRecycler.setHasFixedSize(true);
        showitemRecycler.setAdapter(showItemAdapter);

        return view;
    }

    private void dotsIndicator() {

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(10,10,10,10);

        for(int i=0;i<dots.length;i++){

            dots[i] = new TextView(getContext());
            dots[i].setText(Html.fromHtml("&#9679;"));
            dots[i].setTextSize(20);
            dots[i].setPadding(5, 0, 5, 0);
            dots[i].setLayoutParams(params);
            dots_container.addView(dots[i]);
        }
    }

    private void selectedIndicatorPosition(int position) {


        for(int i=0;i<dots.length;i++){


            if(i == position){

                dots[i].setTextColor(Color.RED);

            }else{

                dots[i].setTextColor(Color.GREEN);
            }
        }

    }
}
