package com.example.recipepuppy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.util.Linkify;
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


    private TextView textViewResult;
    private TextView textViewLink;
    private RecipePuppyAPI recipePuppyAPI;
    private LinearLayout linearLayout;
    private Retrofit retrofit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        textViewResult = findViewById(R.id.textview_result);
        textViewLink = findViewById(R.id.textview_link);
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
                       textViewResult.setText(response.code());
                       return;
                   }

                   Recipepuppy recipepuppy = response.body();

                   if (recipepuppy.getRecipes().isEmpty()) {
                       textViewResult.append("NO RESULTS");
                   }

                   for (Recipe recipe : recipepuppy.getRecipes()) {

                       TextView txtTitle = new TextView(ResultsActivity.this);
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
                       txtIngredients.setText("Ingredients: " + recipe.getIngredients());

                       TextView txtLink = new TextView(ResultsActivity.this);
                       txtLink.setAutoLinkMask(Linkify.WEB_URLS);
                       txtLink.setText("LINK:" + recipe.getHref());

                       linearLayout.addView(txtTitle);
                       linearLayout.addView(imageView);
                       linearLayout.addView(txtIngredients);
                       linearLayout.addView(txtLink);

                   }

               }

               @Override
               public void onFailure(Call<Recipepuppy> call, Throwable t) {
                   textViewResult.setText(t.getMessage());
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