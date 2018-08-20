package com.example.dell.baking;

import android.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class FoodDetails extends AppCompatActivity  implements FoodDetailsList.OnStepSelectionChangeListener{

    FoodItem foodItem;
    FoodDetailsList foodDetailsList;
    StepDetails stepDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);


         foodDetailsList = (FoodDetailsList) getFragmentManager().findFragmentById(R.id.fragment1);
         stepDetails = (StepDetails) getFragmentManager().findFragmentById(R.id.fragment2);


         foodItem=(FoodItem) getIntent().getParcelableExtra("item");

        if(findViewById(R.id.large)==null){
            android.app.Fragment check =  getFragmentManager().findFragmentById(R.id.container);
            if(check==null){



                Bundle bundle = new Bundle();
                bundle.putParcelable("single",foodItem);
                FoodDetailsList foodDetailsList1 = new FoodDetailsList();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction().add(R.id.container,foodDetailsList1);
                foodDetailsList1.setArguments(bundle);
                //   fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        }
        else{
            if (foodDetailsList!=null){
                foodDetailsList.setData(foodItem);
            }

        }






    }

    @Override
    public void OnSelectionChanged(Step step) {
        if(findViewById(R.id.large)==null){

            Bundle bundle = new Bundle();
            bundle.putParcelable("step",step);
            StepDetails stepDetails = new StepDetails();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction().replace(R.id.container,stepDetails);
            stepDetails.setArguments(bundle);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
        else{
            StepDetails stepDetails = (StepDetails) getFragmentManager().findFragmentById(R.id.fragment2);
            if (step!=null){
                stepDetails.setStep(step);
                stepDetails.setImage(step.getThumbUrl());
                stepDetails.setDesc(step.getDesc());
                stepDetails.reset();
                stepDetails.releasePlayer();
                stepDetails.initializePlayer(step);
            }
        }

    }
}
