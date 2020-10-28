package com.enderyasli.gameListingApp.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GameDetail {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBackground_image() {
        return background_image;
    }

    public void setBackground_image(String background_image) {
        this.background_image = background_image;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getReleased() {
        return released;
    }

    public void setReleased(String released) {
        this.released = released;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    @SerializedName("id")
    @Expose
    String id;


    @SerializedName("name")
    @Expose
    String name;

    @SerializedName("background_image")
    @Expose
    String background_image;


    @SerializedName("rating")
    @Expose
    float rating;

    @SerializedName("released")
    @Expose
    String released;

    @NonNull
    @Override
    public String toString() {
        String ret = "name: " + getName();
        return ret;
    }
}
