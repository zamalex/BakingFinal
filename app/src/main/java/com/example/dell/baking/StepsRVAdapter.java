package com.example.dell.baking;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class StepsRVAdapter extends RecyclerView.Adapter<StepsRVAdapter.StepsViewHolder> {


    ArrayList<Step> steps;
    StepsRVAdapter(ArrayList<Step>steps){
        this.steps = steps;

    }


    @Override
    public StepsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.stepitem, parent, false);

        StepsViewHolder mainViewHolder = new StepsViewHolder(itemView);
        return mainViewHolder;
    }

    @Override
    public void onBindViewHolder(StepsViewHolder holder, int position) {

        holder.tv.setText(steps.get(position).getShortDesc());

    }



    @Override
    public int getItemCount() {
        if(steps==null)
            return 0;
        else
            return steps.size();
    }


    class StepsViewHolder extends RecyclerView.ViewHolder{

        TextView tv;
        public StepsViewHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.step_short_desc);
        }

    }
}

