package com.example.dell.baking;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {


    ArrayList<FoodItem>foodItems;
    RecyclerView recyclerView;
    GridLayoutManager gridLayoutManager;

    @Nullable
    private SimpleIdlingResource mIdlingResource;

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        foodItems = new ArrayList<FoodItem>();
        recyclerView = findViewById(R.id.foodList);
         gridLayoutManager = new GridLayoutManager(this,2);

        getIdlingResource();


        getData();



       recyclerView.addOnItemTouchListener(
               new RecyclerItemClickListener(this, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                   @Override public void onItemClick(View view, int position) {

                       String name = foodItems.get(position).getName();
                       String ings ="";
                       for (String s : foodItems.get(position).getIngredients()){
                           ings =ings+s+" \n";

                       }
                       SharedPreferences settings = getSharedPreferences("prefs", MODE_PRIVATE);
                       SharedPreferences.Editor editor = settings.edit();
                       editor.putString("name", name);
                       editor.putString("ings", ings);
                       editor.commit();

                       PreferenceService.startTheService(MainActivity.this);

                       Intent intent = new Intent(MainActivity.this,FoodDetails.class);
                       intent.putExtra("item",foodItems.get(position));
                       startActivity(intent);
                   }

                   @Override
                   public void onLongItemClick(View view, int position) {

                   }
               })
       );



    }

    public ArrayList<FoodItem> parseJson(String json){
        ArrayList<FoodItem>items = new ArrayList<FoodItem>();

        try {
            JSONArray root = new JSONArray(json);
            for (int i=0;i<root.length();i++){

                JSONObject object = root.getJSONObject(i);
                int id = object.getInt("id");
                String name = object.getString("name");
                String image = object.getString("image");
                ArrayList<String> ingsArray = new ArrayList<String>();
                ArrayList<String> ingsPartArray = new ArrayList<String>();
                JSONArray ings = object.getJSONArray("ingredients");
                for(int j=0;j<ings.length();j++){
                    JSONObject ing = ings.getJSONObject(j);
                    String s = ing.getInt("quantity")+" "+ing.getString("measure")+" "+ing.getString("ingredient");
                    ingsArray.add(s);
                    ingsPartArray.add(ing.getString("ingredient"));
                }

                JSONArray steps = object.getJSONArray("steps");
                ArrayList<Step>stepsArr = new ArrayList<Step>();

                for(int k=0;k<steps.length();k++){
                    JSONObject step = steps.getJSONObject(k);
                    int stepId =step.getInt("id");
                    String stepShortDescription = step.getString("shortDescription");
                    String stepDesc = step.getString("description");
                    String stepVideo = step.getString("videoURL");
                    String stepThumb = step.getString("thumbnailURL");
                    stepsArr.add(new Step(stepId,stepShortDescription,stepDesc,stepVideo,stepThumb));

                }

                items.add(new FoodItem(id,name,ingsArray,stepsArr,ingsPartArray,image));





            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return items;
    }

    private void getData() {


        String url = getResources().getString(R.string.foods_url);
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                foodItems=parseJson(response);



                recyclerView.setLayoutManager(gridLayoutManager);
                recyclerView.setAdapter(new MainRVAdapter(foodItems,MainActivity.this));

                if(mIdlingResource!=null){
                    mIdlingResource.setIdleState(true);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

}
