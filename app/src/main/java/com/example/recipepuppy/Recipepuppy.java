package com.example.recipepuppy;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Recipepuppy {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("version")
    @Expose
    private Double version;
    @SerializedName("href")
    @Expose
    private String href;
    @SerializedName("results")
    @Expose
    private List<Recipe> recipes = null;

    public String getTitle() {
        return title;
    }

    public Double getVersion() {
        return version;
    }

    public String getHref() {
        return href;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

}