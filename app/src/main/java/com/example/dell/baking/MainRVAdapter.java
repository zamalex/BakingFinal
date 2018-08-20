package com.example.dell.baking;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by dell on 21/02/2018.
 */

public class MainRVAdapter extends RecyclerView.Adapter<MainRVAdapter.MainViewHolder> {


    ArrayList<FoodItem>foodItems;
    Context context;
    MainRVAdapter(ArrayList<FoodItem>foodItems,Context context){
        this.context=context;
        this.foodItems = foodItems;

    }


    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fooditem, parent, false);

        MainViewHolder mainViewHolder = new MainViewHolder(itemView);
        return mainViewHolder;
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {

        holder.tv.setText(foodItems.get(position).getName());

        if (!foodItems.get(position).getImageUrl().equals("")){
            Picasso.with(context).load(foodItems.get(position).getImageUrl()).into(holder.iv);

        }

    }



    @Override
    public int getItemCount() {
        if(foodItems==null)
            return 0;
        else
            return foodItems.size();
    }


    class MainViewHolder extends RecyclerView.ViewHolder{

        TextView tv;
        ImageView iv;
        public MainViewHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.main_list_food_name);
            iv=itemView.findViewById(R.id.food_image);
        }

    }
}
