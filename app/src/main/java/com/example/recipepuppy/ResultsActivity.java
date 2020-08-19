package com.example.recipepuppy;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ResultsActivity extends AppCompatActivity {


    private TextView textViewEmptyResult;
    private RecipePuppyAPI recipePuppyAPI;
    private LinearLayout linearLayout;
    private Retrofit retrofit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        textViewEmptyResult = findViewById(R.id.textview_result);
        linearLayout = findViewById(R.id.linearLayout);

        //Configura retrofit para comunicarse con API de RecipePuppy.com
        createRetrofit();

        getRecipes(getIntent().getExtras().getString("searchString"));

       }

       private void getRecipes(String query){

           Call<Recipepuppy> call = recipePuppyAPI.getRecipes(query, 1);

           call.enqueue(new Callback<Recipepuppy>() {
               @Override
               public void onResponse(Call<Recipepuppy> call, Response<Recipepuppy> response) {
                   if (!response.isSuccessful()) {
                       textViewEmptyResult.setText(response.code());
                       return;
                   }

                   Recipepuppy recipepuppy = response.body();

                   if (recipepuppy.getRecipes().isEmpty()) {
                       textViewEmptyResult.append("NO RESULTS");
                   }

                   for (Recipe recipe : recipepuppy.getRecipes()) {

                       TextView txtTitle = new TextView(ResultsActivity.this);
                       txtTitle.setGravity(Gravity.CENTER_HORIZONTAL);

                       txtTitle.setTextAppearance(ResultsActivity.this, R.style.TitleStyle);
                       txtTitle.setTypeface(null, Typeface.BOLD);
                       txtTitle.setText(recipe.getTitle());

                       ImageView imageView = new ImageView(ResultsActivity.this);
                       String imgUrl;
                       if (recipe.getThumbnail().isEmpty()) {
                           //Si el thumbnail viene vacio, muestro una imagen generica
                           imgUrl = getString(R.string.blank_image);
                       } else {
                           imgUrl = recipe.getThumbnail();
                       }

                       Glide.with(ResultsActivity.this).load(imgUrl).into(imageView);

                       TextView txtIngredients = new TextView(ResultsActivity.this);
                       txtIngredients.setText(getString(R.string.ingredients) + recipe.getIngredients());

                       TextView txtLink = new TextView(ResultsActivity.this);
                       txtLink.setAutoLinkMask(Linkify.WEB_URLS);
                       txtLink.setText(recipe.getHref());

                       linearLayout.addView(txtTitle);
                       linearLayout.addView(imageView);
                       linearLayout.addView(txtIngredients);
                       linearLayout.addView(txtLink);

                   }

               }

               @Override
               public void onFailure(Call<Recipepuppy> call, Throwable t) {
                   textViewEmptyResult.setText(t.getMessage());
               }
           });

       }

       private void createRetrofit()
       {
            retrofit = new Retrofit.Builder()
                    .baseUrl(getString(R.string.apiLocation))
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            recipePuppyAPI = retrofit.create((RecipePuppyAPI.class));
       }


}