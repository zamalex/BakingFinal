package com.example.dell.baking;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class FoodDetailsList extends Fragment {
    TextView ings;
    TextView txtbtn;
    RecyclerView recyclerView;
    String ingsString;
    OnStepSelectionChangeListener listener;
    FoodItem foodItem;


    public interface OnStepSelectionChangeListener {
        public void OnSelectionChanged(Step step);
    }

    public FoodDetailsList() {

    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (OnStepSelectionChangeListener) context;
        } catch (ClassCastException e) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();

        View view =inflater.inflate(R.layout.fragment_food_details_list, container, false);

        ings = view.findViewById(R.id.ingredients);
        recyclerView = view.findViewById(R.id.steps);
        txtbtn = view.findViewById(R.id.txtbtn);



        if (getArguments() != null && getArguments().containsKey("single")){
            foodItem = (FoodItem) getArguments().getParcelable("single");



            setData(foodItem);
        }


        return view;
    }

    public void setFoodItem(FoodItem foodItem) {
        this.foodItem = foodItem;
    }

    public void setData(final FoodItem foodItem){
        ingsString="";

        for (String s : foodItem.getIngredients()){
            ingsString =ingsString+s+" \n\n";


        }

        String ingsPart = "";
        for (String s : foodItem.getingredientsPart()){
            ingsPart =ingsPart+s+",";


        }
        ings.setText(ingsPart);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new StepsRVAdapter(foodItem.getSteps()));

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        listener.OnSelectionChanged(foodItem.getSteps().get(position));

                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                })
        );



        txtbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());


                builder.setTitle("More Details")
                        .setMessage(ingsString)
                        .show();


            }
        });


    }

}
