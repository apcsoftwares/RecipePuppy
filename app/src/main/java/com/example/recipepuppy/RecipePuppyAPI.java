package com.example.recipepuppy;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RecipePuppyAPI {

    @GET("api")
    Call<Recipepuppy> getRecipes(
            @Query("q") String query,
            @Query("oi") Integer onlyImages
    );

}
